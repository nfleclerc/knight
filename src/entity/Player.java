package entity;


import entity.enemies.Enemy;
import gear.*;
import gear.boots.SimpleGreaves;
import gear.chests.SimpleBreastplate;
import gear.gloves.SimpleGauntlets;
import gear.helmets.SimpleHelm;
import gear.shields.SimpleShield;
import gear.weapons.SimpleSword;
import entity.items.Item;
import skilltree.Skill;
import skilltree.SkillTree;
import tileMap.TileMap;

import java.util.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Player extends MapObject {

    // player stuff
    private int maxHealth;
    private int health;
    private boolean dead;
    private int XP;
    private int level;
    private int skillPoints;
    private SkillTree skillTree;
    private Map<Item, Integer> inventory;

    private int defenseBonus;
    private int attackBonus;

    //gear
    private Helmet helmet;
    private Gloves gloves;
    private Boots boots;
    private Chest chest;
    private Shield shield;
    private Weapon weapon;


    // attack
    private boolean attacking;



    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {
            2, 4, 1, 1, 4
    };

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int ATTACKING = 4;

    public Player(TileMap tm) {

        super(tm);

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        inventory = new HashMap<>();
        skillTree = new SkillTree(this);

        moveSpeed = 1;
        maxSpeed = 1.8;
        stopSpeed = 0.4;
        fallSpeed = 0.2;
        maxFallSpeed = 4.0;
        jumpStart = -5.0;
        stopJumpSpeed = 0.3;

        skillPoints = 0;
        level = 1;

        attackBonus = 1;
        defenseBonus = 1;

        facingRight = true;

        health = maxHealth = 5;


        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/sprites/player/knightsprites.gif"
                    )
            );

            sprites = new ArrayList<>();
            for(int i = 0; i < numFrames.length; i++) {

                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for(int j = 0; j < numFrames[i]; j++) {

                    if(i != ATTACKING) {
                        bi[j] = spritesheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(
                                j * width * 2,
                                i * height,
                                width * 2,
                                height
                        );
                    }

                }

                sprites.add(bi);

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(4000);

        boots = new SimpleGreaves();
        chest = new SimpleBreastplate();
        gloves = new SimpleGauntlets();
        helmet = new SimpleHelm();
        shield = new SimpleShield();
        weapon = new SimpleSword();

    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setAttacking() {
        attacking = true;
    }

    private void getNextPosition() {

        // movement
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            } else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }


        // cannot move while attacking, except in air
        if((currentAction == ATTACKING) && !(jumping || falling)) {
            dx = 0;
        }

        // jumping
        if(jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling
        if(falling) {
            dy += fallSpeed;
            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        //System.out.println(getX() + ", " + getY());

        checkAttackHasStopped();

        checkDoneFlinching(1000);

        // set animation
        if(attacking) {
            setAnimation(ATTACKING, getAttackSpeed(), 60);
        } else if(dy > 0) {
            setAnimation(FALLING, 40, 30);
        }
        else if(dy < 0) {
            setAnimation(JUMPING, -1, 30);
        }
        else if(left || right) {
            setAnimation(WALKING, 125, 30);
        }
        else {
            setAnimation(IDLE, 400, 30);
        }

        animation.update();

        // set direction
        if(currentAction != ATTACKING) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }

        updateLevel();
        System.out.println("Level: " + level);
        System.out.println("SkillPoints: " + skillPoints);
        System.out.println("Inventory: " + inventory);

    }

    private void setAnimation(int currentAction, int delay, int width){
        if(this.currentAction != currentAction){
            this.currentAction = currentAction;
            animation.setFrames(sprites.get(currentAction));
            animation.setDelay(delay);
            this.width = width;
        }
    }

    public void draw(Graphics2D g) {

        setMapPosition();

        // draw player
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1_000_000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);

    }

    public int getXP() {
        return XP;
    }

    public void checkAttack(ArrayList<Enemy> enemies) {

        enemies.stream().filter(this::intersects).forEach(enemy -> {
            if (attacking) {
                enemy.hit(getAttackRating());
            } else {
                hit(enemy.getDamage());
            }
        });

    }

    public void checkAttackHasStopped(){
        //check attack has stopped
        if (currentAction == ATTACKING){
            if (animation.hasPlayedOnce()){
                attacking = false;
            }
        }
    }

    private void hit(int damage) {
        if (flinching) return;
        takeDamage(damage);
        if (health <= 0){
            health = 0;
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    @Override
    public Rectangle getRectangle(){
        if (attacking){
            return getRangedRectangle();
        } else {
            return new Rectangle((int) x - cWidth, (int) y - cHeight,
                    cWidth,
                    cHeight
            );
        }
    }

    private Rectangle getRangedRectangle(){
        if (facingRight) {
            return new Rectangle(
                    (int) x - cWidth, (int) y - cHeight,
                    cWidth + getAttackRange(),
                    cHeight
            );
        } else {
            return new Rectangle(
                    (int) x - cWidth - getAttackRange(), (int) y - cHeight,
                    cWidth + getAttackRange(),
                    cHeight
            );
        }
    }

    public boolean enemyInRange(ArrayList<Enemy> enemies){
        Rectangle rangedRec = getRangedRectangle();
        for (Enemy enemy : enemies){
            if (rangedRec.intersects(enemy.getRectangle())){
                return true;
            }
        }
        return false;
    }

    private void takeDamage(int damage){
        health -= Math.ceil((double)damage /(double) getDefenseRating());
    }

    public void checkGather(List<Item> items){
        items.stream().filter(this::intersects).forEach(item -> {
            item.setGathered(true);
            if (!inventory.containsKey(item)) {
                inventory.put(item, 1);
            } else {
                inventory.replace(item, inventory.get(item) + 1);
            }
        });
    }

    private int getDefenseRating(){
        return (boots.getRating() + gloves.getRating() +
                chest.getRating() + helmet.getRating() +
                shield.getRating()) / 5 * defenseBonus;
    }

    private void updateLevel(){
        if (this.XP >= level * 100){
            level++;
            skillPoints++;
        }
    }

    private int getAttackRating(){
        return weapon.getRating() * attackBonus;
    }

    private int getAttackRange(){
        return weapon.getAttackRange() * attackBonus;
    }

    public int getAttackSpeed() {
        return weapon.getAttackSpeed() * attackBonus;
    }

    public void gainXP(int XP){
        this.XP += XP;
    }

    public void increaseMovement(double movementBonus) {
        maxSpeed *= movementBonus;
        moveSpeed *= movementBonus;
    }

    public void increaseAttack(double attackBonus) {
        this.attackBonus *= attackBonus;
    }

    public void increaseDefense(double defenseBonus) {
        this.defenseBonus *= defenseBonus;
    }

    public void buySkill(Skill skill){
        if (skillPoints > 0){
            skillPoints--;
            skill.activate();
        }
    }

    public SkillTree getSkillTree() {
        return skillTree;
    }
}