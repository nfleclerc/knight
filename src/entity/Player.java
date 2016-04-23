package entity;


import audio.AudioPlayer;
import effects.Animation;
import entity.enemies.Enemy;
import gameStates.GameStateManager;
import gear.*;
import gear.boots.SimpleGreaves;
import gear.chests.SimpleBreastplate;
import gear.gloves.SimpleGauntlets;
import gear.helmets.SimpleHelm;
import gear.weapons.SimpleSword;
import entity.items.Item;
import messages.Message;
import messages.MessageFactory;
import save.Saver;
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

    private HashMap<String, AudioPlayer> sfx;
    private int frames;

    /**
     * Creates a new player
     * @param tm The tile map the player is on
     */
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


        attackBonus = 8;
        defenseBonus = 8;

        facingRight = true;

        health = maxHealth = 20;


        // load spritesheets
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/player/knightsprites.gif"
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

        sfx = new HashMap<>();
        sfx.put("swing", new AudioPlayer("/sfx/sword-swing.mp3"));

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

    /**
     * Creates a new player
     * @param tm The tile map the player is on
     * @param x The x position of the player
     * @param y The y position of the player
     * @param skillPoints The current skill points of the player
     * @param level The current level of the player
     * @param attackBonus The current bonus to attack of the player
     * @param defenseBonus The current bonus to defense of the player
     * @param health The current health of the player
     * @param maxHealth The maximum health of the player
     * @param facingRight True if the player is facing right
     * @param XP The current amount of experience of the player
     * @param skillIsActive Stores whether or not skills are active
     */
    public Player(TileMap tm, double x, double y, int skillPoints,
                  int level, double attackBonus, double defenseBonus, int health,
                  int maxHealth, boolean facingRight, int XP,
                  boolean[] skillIsActive){

        super(tm);

        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        this.x = x;
        this.y = y;

        inventory = new HashMap<>();
        skillTree = new SkillTree(this);


        moveSpeed = 1;
        maxSpeed = 1.8;
        stopSpeed = 0.4;
        fallSpeed = 0.2;
        maxFallSpeed = 4.0;
        jumpStart = -5.0;
        stopJumpSpeed = 0.3;

        this.skillPoints = skillPoints;
        this.level = level;
        this.XP = XP;

        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;

        this.facingRight = facingRight;

        this.health = health;
        this.maxHealth = maxHealth;

        for (int i = 0; i < skillTree.getSkills().size(); i++) {
            if (skillIsActive[i]) skillTree.getSkillAt(i).activate();
        }


        // load spritesheets
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/spritesheets/player/knightsprites.gif"
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

        sfx = new HashMap<>();
        sfx.put("swing", new AudioPlayer("/sfx/sword-swing.mp3"));

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
        sfx.get("swing").playOnce();
        attacking = true;
    }

    /**
     * Gets the next position of the player
     */
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

    /**
     * Checks if the player has reached the minimum level to enter certain areas
     */
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
        if (y >= 1550) dead = true;
    }

    /**
     * Updates the player information (gets the next position, checks for collisions, and updates animation)
     */
    public void update() {

        autoSave();

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
            setAnimation(JUMPING, 0, 30);
        }
        else if(left || right) {
            setAnimation(WALKING, 90, 30);
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
        frames++;

    }

    /**
     * Autosaves the game
     */
    private void autoSave() {
        if (frames % 3600 == 0 && !dead) {
            new Saver(this, GameStateManager.key);
            if (frames == 3600 && XP == 0){
                MessageFactory.getInstance().createMessage(
                        "This Game Uses AutoSave. Do Not Quit While Saving.",
                        Message.MessageType.TIP);
                MessageFactory.getInstance().createMessage(
                        "Press S To Save Manually",
                        Message.MessageType.TIP);
                MessageFactory.getInstance().createMessage(
                        "Press Q to Save and Quit To The Main Menu",
                        Message.MessageType.TIP);
            }
        }
    }

    /**
     * Checks if the player's health is ready to regenerate (after 10 seconds)
     */
    private void checkHealthRegen() {
        if (health < 2 && !healthMessagePlayedOnce){
            MessageFactory.getInstance().createMessage("Your Health Is Low! Go Somewhere Safe To Recover!",
                    Message.MessageType.TIP);
            MessageFactory.getInstance().createMessage("Press R To Revert To Your Last Save",
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

    /**
     * Checks if the player has moved and displays a message if they haven't (containing instructions on how to move)
     */
    private void checkIfMoved() {
        if (x == 52800 && XP == 0 && frames > 420){
            MessageFactory.getInstance().createMessage("Use The Arrow Keys to Move",
                    Message.MessageType.TIP);
        }
    }

    /**
     * Sets the animation of the character
     * @param currentAction The current action of the player
     * @param delay The delay between frames
     * @param width The width of the animation
     */
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

    /**
     * Checks to see if the player is attacking
     * @param enemies The list of enemies on the map
     */
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

    /**
     * Checks if the player's current attack has stopped
     */
    public void checkAttackHasStopped(){
        //check attack has stopped
        if (currentAction == ATTACKING){
            if (animation.hasPlayedOnce()){
                attacking = false;
            }
        }
    }

    /**
     * Called when the player is hit
     * @param damage The damage dealt to the player
     */
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

    /**
     * Gets a rectangle boxing the player in
     * @return A rectangle boxing the player in
     */
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

    /**
     * Gets a rectangle boxing the player and their attack range in (so the player and their attack range are a hitbox)
     * @return A rectangle boxing the player and their attack range in
     */
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

    /**
     * Checks to see if there is an enemy in range of the player's attack
     * @param enemies The list of enemies on the map
     * @return True if there is an enemy in range
     */
    public boolean enemyInRange(ArrayList<Enemy> enemies){
        Rectangle rangedRec = getRangedRectangle();
        for (Enemy enemy : enemies){
            if (rangedRec.intersects(enemy.getRectangle())){
                return true;
            }
        }
        return false;
    }

    /**
     * Causes the player to take damage
     * @param damage The raw damage dealt by the enemy
     */
    private void takeDamage(int damage){
        health -= Math.ceil((double)damage / getDefenseRating());
    }

    /**
     * Checks to see if an item can be picked up
     * @param items The list of items on the map
     */
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

    /**
     * Increments the player's level, incrementing skill points, health, etc. as well
     */
    private void updateLevel(){
        if (this.XP >= getXPRequiredForLevelUp()){
            level++;
            skillPoints++;
            maxHealth++;
            attackBonus++;
            defenseBonus++;
            health = maxHealth;
            MessageFactory.getInstance().createMessage("LEVEL UP!", Message.MessageType.LEVEL_UP);
            if (level == 2){
                MessageFactory.getInstance().createMessage("Press K to Access Your Skill Tree",
                        Message.MessageType.TIP);
            }
        }
    }

    public int getXPRequiredForLevelUp(){
        return level * 100;
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

    /**
     * Increases the player's movement speed
     * @param movementBonus The movement bonus to apply to the player's current speed
     */
    public void increaseMovement(double movementBonus) {
        maxSpeed *= movementBonus;
        moveSpeed *= movementBonus;
        jumpStart -= movementBonus / 3;

    }

    /**
     * Increases the player's attack power
     * @param attackBonus The attack bonus to apply to the player's current attack power
     */
    public void increaseAttack(double attackBonus) {
        this.attackBonus += attackBonus;
    }

    /**
     * Increases the player's defense power
     * @param attackBonus The defense bonus to apply to the player's current defense power
     */
    public void increaseDefense(double defenseBonus) {
        this.defenseBonus += defenseBonus;
    }

    /**
     * Purchases a skill
     * @param skill The skill that the player is purchasing
     */
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


    public SkillTree getSkillTree() {
        return skillTree;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDead() {
        return dead;
    }

    public double getDefenseBonus() {
        return defenseBonus;
    }

    public double getAttackBonus() {
        return attackBonus;
    }

    public boolean getFacingRight() {
        return facingRight;
    }
}