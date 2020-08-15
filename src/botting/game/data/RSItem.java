package botting.game.data;

/**
 * A few hardcoded 317 items (with some Ikov addons)
 * @TODO remove ikov addons + proper 317 item list config without enum
 *
 * @author Youri Dudock
 */
public enum RSItem {

    COINS(995),
    SHARK(385),
    SUPER_RESTORE_4(3024),
    FROST_DRAGON_BONES(18830),
    DRACONIC_VISAGE(11286),
    TOOTH_HALF_OF_A_KEY(986),
    LOOP_HALF_OF_A_KEY(987),
    NOTED_LOOP_HALF_OF_A_KEY(988),
    SUPER_ATTACK_4(2436),
    SUPER_STRENGTH_4(2440),
    DEATH_RUNE(560),
    BLOOD_RUNE(565),
    WATER_RUNE(555),
    BONES(526);

    private int ID;

    private RSItem(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
