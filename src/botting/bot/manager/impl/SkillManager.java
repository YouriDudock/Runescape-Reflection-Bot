package botting.bot.manager.impl;

import botting.bot.BotInstance;
import botting.bot.manager.Manager;
import botting.game.data.GameSkill;
import botting.reflection.hooking.ClientHook;
import botting.reflection.modifiers.RefClass;
import botting.rs.client.clients.ikov.Ikov;

/**
 * @author Youri Dudock
 */
public class SkillManager extends Manager {
    public SkillManager(BotInstance bot) {
        super(bot);
    }

    public int getHitpoints() {
        return bot.getAccessors().getClient().getStats()[Ikov.Game.Skills.HITPOINTS.getID()];
    }

    public int getAttack() {
        return bot.getAccessors().getClient().getStats()[Ikov.Game.Skills.ATTACK.getID()];
    }

    public int getStrength() {
        return bot.getAccessors().getClient().getStats()[Ikov.Game.Skills.STRENGTH.getID()];
    }

    public int getStat(GameSkill skill) {return bot.getAccessors().getClient().getStats()[skill.getID()]; }


}
