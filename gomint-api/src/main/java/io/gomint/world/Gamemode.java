package io.gomint.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum Gamemode {

    /**
     * Survival mode can destroy blocks, attack mobs and other players like normal. They also can't fly or clip through
     * blocks
     */
    SURVIVAL("Survival", 0),

    /**
     * Creative mode allows flying and instant breaking blocks
     */
    CREATIVE("Creative", 1),

    /**
     * Adventure removes hitboxes so you can't hit mobs / players or interact with certain blocks. They also can't fly
     * or clip through blocks
     */
    ADVENTURE("Adventure", 2),

    /**
     * Spectator removed the ability to break blocks or hit mobs / players. This also allows flying and clipping through
     * blocks
     */
    SPECTATOR("Spectator", 3);

    private String name;
    private int value;

    Gamemode( String name, int value ) {
        this.name = name;
        this.value = value;
    }

    public int getIntegerValue() {
        return this.value;
    }

    public String getGamemodeName() {
        return this.name;
    }
}
