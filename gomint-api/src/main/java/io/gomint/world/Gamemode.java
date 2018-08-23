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
    SURVIVAL( "Survival" ),

    /**
     * Creative mode allows flying and instant breaking blocks
     */
    CREATIVE( "Creative" ),

    /**
     * Adventure removes hitboxes so you can't hit mobs / players or interact with certain blocks. They also can't fly
     * or clip through blocks
     */
    ADVENTURE( "Adventure" ),

    /**
     * Spectator removed the ability to break blocks or hit mobs / players. This also allows flying and clipping through
     * blocks
     */
    SPECTATOR( "Spectator" );

    private String name;

    Gamemode( String name ) {
        this.name = name;
    }

    public String getGamemodeName() {
        return this.name;
    }
}
