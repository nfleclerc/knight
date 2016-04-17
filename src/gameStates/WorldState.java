/*
 * Copyright (c) 2016.
 */

package gameStates;

import audio.AudioPlayer;
import entity.enemies.*;
import hud.HUD;
import entity.Player;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nathaniel on 4/12/16.
 */
public class WorldState extends LevelState {

    public WorldState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        tileMap = new TileMap(30);
        tileMap.loadTiles("/tilesets/worldset_withoutramps.gif");
        tileMap.loadMap("/maps/worldwithoutramps.map");
        tileMap.setPosition(0, 0);
        init();
    }

    @Override
    public void init() {


        bg = new Background("/backgrounds/mountainbg.gif", 0.1);

        player = new Player(tileMap);

        //correct starting loc 52800 1280
        player.setPosition(52800, 1280);

        hud = new HUD(player);

        loadFrames = 359;

        populateEnemies();

        explosions = new ArrayList<>();
        items = new ArrayList<>();

        bgMusic = new AudioPlayer("/music/Clock-Maker-the-Hero_Looping.mp3");

        musicStarted = false;


    }

    @Override
    public void load(Player player) {

        bg = new Background("/backgrounds/mountainbg.gif", 0.1);

        this.player = player;

        hud = new HUD(player);

        loadFrames = 539;

        populateEnemies();

        explosions = new ArrayList<>();
        items = new ArrayList<>();

        bgMusic = new AudioPlayer("/music/Clock-Maker-the-Hero_Looping.mp3");

        musicStarted = false;

    }

    private void populateEnemies() {
        enemies = new ArrayList<>();

        List<Point> waspPoints = new ArrayList<>();
        waspPoints.add(new Point(52907, 1250));
        waspPoints.add(new Point(54339, 1220));
        waspPoints.add(new Point(54181, 1190));
        waspPoints.add(new Point(54125, 1190));
        waspPoints.add(new Point(53852, 1160));
        waspPoints.add(new Point(53602, 1160));
        waspPoints.add(new Point(53354, 1190));
        waspPoints.add(new Point(53102, 1220));
        waspPoints.add(new Point(54645, 1370));
        waspPoints.add(new Point(55143, 1340));
        waspPoints.add(new Point(55365, 1190));
        waspPoints.add(new Point(55638, 1100));
        waspPoints.add(new Point(56147, 1160));
        waspPoints.add(new Point(56511, 1250));
        waspPoints.add(new Point(56543, 1250));
        waspPoints.add(new Point(56967, 1250));
        waspPoints.add(new Point(55573, 1100));
        waspPoints.add(new Point(56848, 1250));
        waspPoints.add(new Point(57333, 1250));
        waspPoints.add(new Point(57650, 1220));
        waspPoints.add(new Point(57833, 1130));
        waspPoints.add(new Point(58127, 1130));
        waspPoints.add(new Point(58420, 1190));
        waspPoints.add(new Point(58716, 1160));
        waspPoints.add(new Point(59059, 1160));
        waspPoints.add(new Point(59629, 1100));
        waspPoints.add(new Point(60086, 1220));
        waspPoints.add(new Point(60312, 1190));
        waspPoints.add(new Point(60554, 1250));
        waspPoints.add(new Point(60834, 1220));
        waspPoints.add(new Point(61104, 1160));
        waspPoints.add(new Point(61423, 1190));
        waspPoints.add(new Point(62085, 1190));
        waspPoints.add(new Point(69805, 1220));
        waspPoints.add(new Point(70265, 1130));
        waspPoints.add(new Point(70690, 1100));
        waspPoints.add(new Point(74162, 1190));
        waspPoints.add(new Point(73379, 1310));
        waspPoints.add(new Point(74165, 1310));
        waspPoints.add(new Point(74445, 1160));
        waspPoints.add(new Point(74716, 1130));

        for (Point point : waspPoints) {
            Wasp wasp = new Wasp(tileMap, player);
            wasp.setPosition(point.x, point.y);
            enemies.add(wasp);
        }

        List<Point> spiderPoints = new ArrayList<>();
        spiderPoints.add(new Point(53010, 1370));
        spiderPoints.add(new Point(53321, 1430));
        spiderPoints.add(new Point(53987, 1400));
        spiderPoints.add(new Point(54300, 1400));
        spiderPoints.add(new Point(54389, 1460));
        spiderPoints.add(new Point(54068, 1460));
        spiderPoints.add(new Point(53799, 1460));
        spiderPoints.add(new Point(54878, 1460));
        spiderPoints.add(new Point(56550, 1430));
        spiderPoints.add(new Point(57528, 1400));
        spiderPoints.add(new Point(57830, 1400));
        spiderPoints.add(new Point(58181, 1400));
        spiderPoints.add(new Point(58479, 1400));
        spiderPoints.add(new Point(58811, 1400));
        spiderPoints.add(new Point(59121, 1400));
        spiderPoints.add(new Point(59473, 1400));
        spiderPoints.add(new Point(59787, 1400));
        spiderPoints.add(new Point(60144, 1400));
        spiderPoints.add(new Point(60452, 1400));
        spiderPoints.add(new Point(60780, 1400));
        spiderPoints.add(new Point(61085, 1400));
        spiderPoints.add(new Point(61440, 1400));
        spiderPoints.add(new Point(61734, 1400));
        spiderPoints.add(new Point(62481, 1460));
        spiderPoints.add(new Point(61858, 1460));
        spiderPoints.add(new Point(61243, 1460));
        spiderPoints.add(new Point(60449, 1460));
        spiderPoints.add(new Point(60020, 1460));
        spiderPoints.add(new Point(59272, 1460));
        spiderPoints.add(new Point(58710, 1460));
        spiderPoints.add(new Point(57814, 1460));
        spiderPoints.add(new Point(57293, 1460));
        spiderPoints.add(new Point(58044, 1280));
        spiderPoints.add(new Point(58751, 1280));
        spiderPoints.add(new Point(59196, 1220));
        spiderPoints.add(new Point(59669, 1220));
        spiderPoints.add(new Point(71146, 1340));
        spiderPoints.add(new Point(71600, 1370));
        spiderPoints.add(new Point(72601, 1310));
        spiderPoints.add(new Point(71036, 1190));
        spiderPoints.add(new Point(71201, 1190));
        spiderPoints.add(new Point(71360, 1190));
        spiderPoints.add(new Point(71506, 1190));
        spiderPoints.add(new Point(71674, 1190));
        spiderPoints.add(new Point(71803, 1190));
        spiderPoints.add(new Point(71963, 1190));
        spiderPoints.add(new Point(72105, 1190));
        spiderPoints.add(new Point(72274, 1190));
        spiderPoints.add(new Point(72421, 1190));
        spiderPoints.add(new Point(72553, 1190));
        spiderPoints.add(new Point(72702, 1190));
        spiderPoints.add(new Point(72860, 1190));
        spiderPoints.add(new Point(72979, 1190));
        spiderPoints.add(new Point(73146, 1190));
        spiderPoints.add(new Point(73297, 1190));
        spiderPoints.add(new Point(73453, 1190));
        spiderPoints.add(new Point(73610, 1190));
        spiderPoints.add(new Point(73754, 1190));
        spiderPoints.add(new Point(73886, 1190));
        spiderPoints.add(new Point(74576, 1460));

        for (Point point : spiderPoints) {
            RoboSpider spider = new RoboSpider(tileMap, player);
            spider.setPosition(point.x, point.y);
            enemies.add(spider);
        }

        List<Point> ratPoints = new ArrayList<>();

        ratPoints.add(new Point(63292, 1310));
        ratPoints.add(new Point(63604, 1310));
        ratPoints.add(new Point(64193, 1310));
        ratPoints.add(new Point(64647, 1310));
        ratPoints.add(new Point(64948, 1190));
        ratPoints.add(new Point(64967, 1430));
        ratPoints.add(new Point(64730, 1430));
        ratPoints.add(new Point(64396, 1460));
        ratPoints.add(new Point(63833, 1460));
        ratPoints.add(new Point(63147, 1460));
        ratPoints.add(new Point(65126, 1280));
        ratPoints.add(new Point(65380, 1460));
        ratPoints.add(new Point(66063, 1100));
        ratPoints.add(new Point(66504, 1100));
        ratPoints.add(new Point(66641, 1160));
        ratPoints.add(new Point(66333, 1160));
        ratPoints.add(new Point(65880, 1280));
        ratPoints.add(new Point(66267, 1280));
        ratPoints.add(new Point(66773, 1280));
        ratPoints.add(new Point(66704, 1340));
        ratPoints.add(new Point(65836, 1340));
        ratPoints.add(new Point(66009, 1400));
        ratPoints.add(new Point(66289, 1400));
        ratPoints.add(new Point(66795, 1400));
        ratPoints.add(new Point(70136, 1370));
        ratPoints.add(new Point(69691, 1340));
        ratPoints.add(new Point(69286, 1340));
        ratPoints.add(new Point(68913, 1340));
        ratPoints.add(new Point(68489, 1160));
        ratPoints.add(new Point(68141, 1160));
        ratPoints.add(new Point(67917, 1100));
        ratPoints.add(new Point(67674, 1160));
        ratPoints.add(new Point(67541, 1160));
        ratPoints.add(new Point(67297, 1100));
        ratPoints.add(new Point(63374, 1190));
        ratPoints.add(new Point(63526, 1190));
        ratPoints.add(new Point(63836, 1190));
        ratPoints.add(new Point(64866, 1100));

        for (Point point : ratPoints){
            Rat rat = new Rat(tileMap, player);
            rat.setPosition(point.x, point.y);
            enemies.add(rat);
        }
        
        List<Point> knightPoints = new ArrayList<>();

        knightPoints.add(new Point(76430, 1040));
        knightPoints.add(new Point(77414, 1040));
        knightPoints.add(new Point(77802, 1040));
        knightPoints.add(new Point(78268, 1040));
        knightPoints.add(new Point(78420, 980));
        knightPoints.add(new Point(78681, 920));
        knightPoints.add(new Point(78831, 950));
        knightPoints.add(new Point(79011, 1010));
        knightPoints.add(new Point(79225, 950));
        knightPoints.add(new Point(79377, 920));
        knightPoints.add(new Point(79552, 890));
        knightPoints.add(new Point(79757, 980));
        knightPoints.add(new Point(80286, 950));
        knightPoints.add(new Point(80567, 920));
        knightPoints.add(new Point(80954, 1010));
        knightPoints.add(new Point(81431, 1100));
        knightPoints.add(new Point(81557, 1040));
        knightPoints.add(new Point(81729, 980));
        knightPoints.add(new Point(81908, 950));
        knightPoints.add(new Point(82287, 890));
        knightPoints.add(new Point(82964, 1070));
        knightPoints.add(new Point(83223, 1040));
        knightPoints.add(new Point(83560, 950));
        knightPoints.add(new Point(83796, 980));
        knightPoints.add(new Point(84316, 1100));
        knightPoints.add(new Point(84653, 1040));
        knightPoints.add(new Point(85070, 950));
        knightPoints.add(new Point(85600, 950));
        knightPoints.add(new Point(85888, 920));
        knightPoints.add(new Point(86588, 1100));
        knightPoints.add(new Point(85257, 1460));
        knightPoints.add(new Point(85133, 1460));
        knightPoints.add(new Point(84817, 1460));
        knightPoints.add(new Point(84571, 1460));
        knightPoints.add(new Point(84229, 1460));
        knightPoints.add(new Point(83960, 1460));
        knightPoints.add(new Point(83576, 1460));
        knightPoints.add(new Point(83332, 1460));
        knightPoints.add(new Point(82938, 1460));
        knightPoints.add(new Point(82694, 1460));
        knightPoints.add(new Point(82290, 1460));
        knightPoints.add(new Point(82032, 1460));
        knightPoints.add(new Point(81569, 1460));
        knightPoints.add(new Point(81329, 1460));
        knightPoints.add(new Point(80944, 1460));
        knightPoints.add(new Point(80725, 1460));
        knightPoints.add(new Point(80407, 1460));
        knightPoints.add(new Point(80167, 1460));
        knightPoints.add(new Point(79694, 1460));
        knightPoints.add(new Point(79482, 1460));
        knightPoints.add(new Point(79000, 1460));
        knightPoints.add(new Point(78785, 1460));
        knightPoints.add(new Point(78403, 1460));
        knightPoints.add(new Point(78174, 1460));
        knightPoints.add(new Point(77803, 1460));
        knightPoints.add(new Point(77556, 1460));
        knightPoints.add(new Point(77254, 1460));
        knightPoints.add(new Point(77024, 1460));
        knightPoints.add(new Point(76612, 1460));
        knightPoints.add(new Point(76368, 1460));
        knightPoints.add(new Point(76046, 1460));
        knightPoints.add(new Point(75806, 1460));

        for (Point point : knightPoints){
            Enemy enemy;
            if (Math.random() > 0.5){
                enemy = new Knight(tileMap, player);
            } else {
                enemy = new Ghost(tileMap, player);
            }
            enemy.setPosition(point.x, point.y);
            enemies.add(enemy);
        }

        List<Point> batPoints = new ArrayList<>();

        batPoints.add(new Point(52296, 1460));
        batPoints.add(new Point(52022, 1460));
        batPoints.add(new Point(51659, 1460));
        batPoints.add(new Point(50903, 1460));
        batPoints.add(new Point(50554, 1370));
        batPoints.add(new Point(50215, 1370));
        batPoints.add(new Point(49988, 1370));
        batPoints.add(new Point(49501, 1460));
        batPoints.add(new Point(49148, 1460));
        batPoints.add(new Point(48862, 1460));
        batPoints.add(new Point(48417, 1460));
        batPoints.add(new Point(48320, 1370));
        batPoints.add(new Point(48457, 1310));
        batPoints.add(new Point(48571, 1250));
        batPoints.add(new Point(48683, 1190));
        batPoints.add(new Point(48810, 1130));
        batPoints.add(new Point(49094, 1130));
        batPoints.add(new Point(49213, 1070));
        batPoints.add(new Point(49316, 1130));
        batPoints.add(new Point(49385, 1070));
        batPoints.add(new Point(49474, 1130));
        batPoints.add(new Point(49573, 1070));
        batPoints.add(new Point(49662, 1130));
        batPoints.add(new Point(49764, 1070));
        batPoints.add(new Point(49838, 1130));
        batPoints.add(new Point(49937, 1070));
        batPoints.add(new Point(50025, 1130));
        batPoints.add(new Point(50117, 1070));
        batPoints.add(new Point(50202, 1130));
        batPoints.add(new Point(50300, 1070));
        batPoints.add(new Point(50376, 1130));
        batPoints.add(new Point(32155, 1250));
        batPoints.add(new Point(32338, 1250));
        batPoints.add(new Point(32712, 1280));
        batPoints.add(new Point(32999, 1190));
        batPoints.add(new Point(33608, 1250));
        batPoints.add(new Point(34207, 1280));
        batPoints.add(new Point(34629, 1160));
        batPoints.add(new Point(34998, 1160));
        batPoints.add(new Point(35432, 1160));
        batPoints.add(new Point(36902, 1160));
        batPoints.add(new Point(37691, 1160));
        batPoints.add(new Point(39223, 1160));
        batPoints.add(new Point(29681, 1400));
        batPoints.add(new Point(29587, 1310));
        batPoints.add(new Point(29079, 1310));
        batPoints.add(new Point(28413, 1250));
        batPoints.add(new Point(27728, 1160));
        batPoints.add(new Point(27106, 1070));
        batPoints.add(new Point(26894, 1040));
        batPoints.add(new Point(26761, 1010));
        batPoints.add(new Point(26620, 890));
        batPoints.add(new Point(26052, 890));
        batPoints.add(new Point(25693, 830));
        batPoints.add(new Point(25410, 800));
        batPoints.add(new Point(25369, 710));
        batPoints.add(new Point(24768, 710));
        batPoints.add(new Point(24637, 620));
        batPoints.add(new Point(23956, 620));
        batPoints.add(new Point(23638, 560));
        batPoints.add(new Point(23405, 530));
        batPoints.add(new Point(22892, 470));
        batPoints.add(new Point(22650, 435));
        batPoints.add(new Point(22288, 380));
        batPoints.add(new Point(21380, 260));
        batPoints.add(new Point(20888, 200));
        batPoints.add(new Point(20601, 170));
        batPoints.add(new Point(17758, 200));
        batPoints.add(new Point(17546, 140));
        batPoints.add(new Point(17139, 260));
        batPoints.add(new Point(16946, 200));
        batPoints.add(new Point(16630, 320));
        batPoints.add(new Point(16201, 350));
        batPoints.add(new Point(15866, 380));
        batPoints.add(new Point(15601, 350));
        batPoints.add(new Point(15302, 440));
        batPoints.add(new Point(15068, 470));
        batPoints.add(new Point(14578, 530));
        batPoints.add(new Point(14288, 560));
        batPoints.add(new Point(13929, 590));
        batPoints.add(new Point(13560, 620));
        batPoints.add(new Point(13241, 650));
        batPoints.add(new Point(12810, 710));
        batPoints.add(new Point(12483, 680));
        batPoints.add(new Point(11961, 800));
        batPoints.add(new Point(11499, 800));
        batPoints.add(new Point(11158, 920));
        batPoints.add(new Point(10003, 1070));
        batPoints.add(new Point(9702, 1100));
        batPoints.add(new Point(9415, 1130));
        batPoints.add(new Point(9225, 1160));
        batPoints.add(new Point(9060, 1160));
        batPoints.add(new Point(8762, 1190));
        batPoints.add(new Point(8561, 1160));
        batPoints.add(new Point(8068, 1280));
        batPoints.add(new Point(7871, 1310));
        batPoints.add(new Point(7594, 1340));

        for (Point point : batPoints){
            Bat bat = new Bat(tileMap, player);
            bat.setPosition(point.x, point.y);
            enemies.add(bat);
        }
        
        List<Point> ghostPoints = new ArrayList<>();
        ghostPoints.add(new Point(29300, 1340));
        ghostPoints.add(new Point(28701, 1280));
        ghostPoints.add(new Point(28206, 1220));
        ghostPoints.add(new Point(27552, 1130));
        ghostPoints.add(new Point(26318, 920));
        ghostPoints.add(new Point(25198, 770));
        ghostPoints.add(new Point(24992, 740));
        ghostPoints.add(new Point(23177, 500));
        ghostPoints.add(new Point(21078, 230));
        ghostPoints.add(new Point(17997, 170));
        ghostPoints.add(new Point(17343, 230));
        ghostPoints.add(new Point(15394, 440));
        ghostPoints.add(new Point(14890, 500));
        ghostPoints.add(new Point(13016, 680));
        ghostPoints.add(new Point(12255, 770));
        ghostPoints.add(new Point(11002, 950));
        ghostPoints.add(new Point(10300, 1040));
        ghostPoints.add(new Point(8396, 1250));

        for (Point point : ghostPoints){
            Ghost ghost = new Ghost(tileMap, player);
            ghost.setPosition(point.x, point.y);
            enemies.add(ghost);
        }

        List<Point> moosePoints = new ArrayList<>();
        moosePoints.add(new Point(44218, 1420));
        moosePoints.add(new Point(47928, 1420));
        moosePoints.add(new Point(47442, 1420));
        moosePoints.add(new Point(46187, 1420));
        moosePoints.add(new Point(45441, 1420));
        moosePoints.add(new Point(44742, 1420));
        moosePoints.add(new Point(44147, 1420));
        moosePoints.add(new Point(43827, 1420));
        moosePoints.add(new Point(42390, 1260));
        moosePoints.add(new Point(41595, 1260));


        for (Point point : moosePoints){
            Moose moose = new Moose(tileMap, player);
            moose.setPosition(point.x, point.y);
            enemies.add(moose);
        }

        List<Point> demonPoints = new ArrayList<>();
        demonPoints.add(new Point(67633, 1440));
        demonPoints.add(new Point(67997, 1440));
        demonPoints.add(new Point(69028, 1440));
        demonPoints.add(new Point(70237, 1440));

        for (Point point : demonPoints){
            Demon demon = new Demon(tileMap, player);
            demon.setPosition(point.x, point.y);
            enemies.add(demon);
        }

        BugBoss boss = new BugBoss(tileMap, player);
        boss.setPosition(42074, 1430);
        enemies.add(boss);

    }



}
