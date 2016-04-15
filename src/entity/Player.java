package entity;


import entity.enemies.Enemy;
import gear.*;
import gear.boots.SimpleGreaves;
import gear.chests.SimpleBreastplate;
import gear.gloves.SimpleGauntlets;
import gear.helmets.SimpleHelm;
import gear.weapons.SimpleSword;
import entity.items.Item;
import messages.Message;
import messages.MessageFactory;
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

    private double defenseBonus;
    private double attackBonus;

    //gear
    private Gloves gloves;
    private Boots boots;
    private Chest chest;
    private Helmet helmet;
    private Weapon weapon;

    private boolean[] canCraftGear;

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
    private int framesSinceAttack = 0;
    private boolean healthMessagePlayedOnce = false;

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

        skillPoints = 5;
        level = 10;
        XP = 0;

        canCraftGear = new boolean[5];

        for (int i = 0; i < canCraftGear.length; i++) {
            canCraftGear[i] = false;
        }

        attackBonus = 1;
        defenseBonus = 1;

        facingRight = true;

        health = maxHealth = 6;


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


        //check for out of level bounds
        checkLevelBounds();

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

    private void checkLevelBounds() {
        if (level < 10 && x <= 52514){
            x = 52514;
            facingRight = true;
            MessageFactory.getInstance().createMessage("You Must Be Level 10 or Higher to Enter This Area",
                    Message.MessageType.WARNING);
        } else if (level < 10 && x >= 75194){
            x = 75193;
            facingRight = false;
            MessageFactory.getInstance().createMessage("You Must Be Level 5 or Higher to Enter This Area",
                    Message.MessageType.WARNING);
        }
    }

    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        checkAttackHasStopped();

        checkDoneFlinching(1000);

        checkIfMoved();

        checkHealthRegen();

        // set animation
        if(attacking) {
            setAnimation(ATTACKING, (int)getAttackSpeed(), 60);
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

        framesSinceAttack++;

    }

    private void checkHealthRegen() {
        if (health < 2 && !healthMessagePlayedOnce){
            MessageFactory.getInstance().createMessage("Your Health Is Low! Go Somewhere Safe To Recover!",
                    Message.MessageType.TIP);
            healthMessagePlayedOnce = true;
        }
        if (health < maxHealth){
            if (framesSinceAttack >= 600){
                health++;
                framesSinceAttack = 570;
            }
        }
    }

    private void checkIfMoved() {
        if (x == 52800 && XP == 0){
            MessageFactory.getInstance().createMessage("Use The Arrow Keys to Move",
                    Message.MessageType.TIP);
        }
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

        try {
            enemies.stream().filter(this::intersects).forEach(enemy -> {
                framesSinceAttack = 0;
                if (attacking) {
                    enemy.hit((int) getAttackRating());
                } else {
                    hit(enemy.getDamage());
                }
            });
        } catch (ConcurrentModificationException e){
            e.printStackTrace();
        }

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
                    cWidth + (int)getAttackRange(),
                    cHeight
            );
        } else {
            return new Rectangle(
                    (int) x - cWidth - (int)getAttackRange(), (int) y - cHeight,
                    cWidth + (int)getAttackRange(),
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

    private double getDefenseRating(){
        return (boots.getRating() + gloves.getRating() +
                chest.getRating() + helmet.getRating()) / 4 * defenseBonus;
    }

    private void updateLevel(){
        if (this.XP >= level * 100){
            level++;
            skillPoints++;
            maxHealth++;
            health = maxHealth;
            MessageFactory.getInstance().createMessage("LEVEL UP!", Message.MessageType.LEVEL_UP);
            if (level == 2){
                MessageFactory.getInstance().createMessage("Press K to Access Your Skill Tree",
                        Message.MessageType.TIP);
            }
        }
    }

    private double getAttackRating(){
        return weapon.getRating() * attackBonus;
    }

    private double getAttackRange(){
        return weapon.getAttackRange() * attackBonus;
    }

    public double getAttackSpeed() {
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
        this.attackBonus = attackBonus;
    }

    public void increaseDefense(double defenseBonus) {
        this.defenseBonus = defenseBonus;
    }

    public void buySkill(Skill skill){
        if (skill.getPrevious() == null || skill.getPrevious().isActive()) {
            if (!skill.isActive()) {
                if (skillPoints > 0) {
                    skillPoints--;
                    skill.activate();
                }
            }
        }
    }

    public void setCraftingEnabled(int index){
        canCraftGear[index] = true;
    }

    public SkillTree getSkillTree() {
        return skillTree;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getLevel() {
        return level;
    }

    public boolean canCraft(int i) {
        return canCraftGear[i];
    }
}