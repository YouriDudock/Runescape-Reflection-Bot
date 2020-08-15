package botting.rs.client.clients.ikov;

import botting.game.data.GamePrayer;
import botting.game.data.GameSkill;
import botting.game.data.GameTab;

import java.awt.*;

/**
 * Hardcoded static data storage of the Ikov client
 * @TODO properly declare this config
 *
 * @author Youri Dudock
 */
public interface Ikov {

    interface File {
        String JAR_LOCATION = System.getProperty("user.home") + "\\.ikov_cache\\test.jar";
    }

    interface Game {

        interface Dialog {
                int LOGIN_GREETING = 4900;
        }

        interface Tabs {
            GameTab INVENTORY = new GameTab(new Point(658, 186));
            GameTab QUEST = new GameTab(new Point(599, 182));
            GameTab PRAYER = new GameTab(new Point(718, 185));
            GameTab MAGIC_BOOK = new GameTab(new Point(749, 183));
            GameTab FRIENDS = new GameTab(new Point(566, 480));
        }

        interface Skills {
            GameSkill ATTACK = new GameSkill(0);
            GameSkill DEFENCE = new GameSkill(1);
            GameSkill STRENGTH = new GameSkill(2);
            GameSkill HITPOINTS = new GameSkill(3);
            GameSkill RANGED = new GameSkill(4);
            GameSkill PRAYER = new GameSkill(5);
            GameSkill MAGIC = new GameSkill(6);
            GameSkill COOKING = new GameSkill(7);
            GameSkill WOODCUTTING = new GameSkill(8);
            GameSkill FLETCHING = new GameSkill(9);
            GameSkill FISHING = new GameSkill(10);
            GameSkill FIREMAKING = new GameSkill(11);
            GameSkill CRAFTING = new GameSkill(12);
            GameSkill SMITHING = new GameSkill(13);
            GameSkill MINING = new GameSkill(14);
            GameSkill HERBLORE = new GameSkill(15);
            GameSkill AGILITY = new GameSkill(16);
            GameSkill THIEVING = new GameSkill(17);
            GameSkill SLAYER = new GameSkill(18);
            GameSkill FARMING = new GameSkill(19);
            GameSkill RUNECRAFTING = new GameSkill(20);
            GameSkill SUMMONING = new GameSkill(21);
            GameSkill DUNGENEERING = new GameSkill(23);
        }

        interface Prayer {

            interface Curses {
                GamePrayer TURMOIL = new GamePrayer(new Point(715, 339));
                GamePrayer SOUL_SPLIT = new GamePrayer(new Point(677, 339));
                GamePrayer DEFLECT_MELEE = new GamePrayer(new Point(713, 263));
                GamePrayer DEFLECT_MAGE = new GamePrayer(new Point(637, 267));
                GamePrayer DEFLECT_RANGE = new GamePrayer(new Point(677, 266));
            }



        }



    }

}
