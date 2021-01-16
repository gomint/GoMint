package io.gomint.server.entity;

import io.gomint.server.network.packet.PacketAdventureSettings;
import io.gomint.server.player.PlayerPermission;

/**
 * @author geNAZt
 * @version 1.0
 */
public class AdventureSettings {

    // First flag set
    private static final int WORLD_IMMUTABLE = 0x01;
    private static final int NO_PVP = 0x02;
    private static final int AUTO_JUMP = 0x20;
    private static final int CAN_FLY = 0x40;
    private static final int NO_CLIP = 0x80;
    private static final int WORLD_BUILDER = 0x100;
    private static final int FLYING = 0x200;
    private static final int MUTED = 0x400;

    // Second flag set
    private static final int BUILD_AND_MINE = 0x01;
    private static final int USE_DOORS_AND_SWITCHES = 0x02;
    private static final int OPEN_CONTAINERS = 0x04;
    private static final int ATTACK_PLAYERS = 0x08;
    private static final int ATTACK_MOBS = 0x10;
    private static final int OPERATOR = 0x20;
    private static final int TELEPORT = 0x80;

    // Permissions
    private CommandPermission commandPermission = CommandPermission.NORMAL;
    private PlayerPermission playerPermission = PlayerPermission.MEMBER;

    // Settings

    // Flag 1
    private boolean worldImmutable = false; // 0x01
    private boolean noPvP = false;          // 0x02
    private boolean autoJump = true;        // 0x20
    private boolean canFly = false;         // 0x40
    private boolean noClip = false;         // 0x80
    private boolean worldBuilder = false;   // 0x100
    private boolean flying = false;         // 0x200
    private boolean muted = false;          // 0x400

    // Flag 2
    private boolean buildAndMine = true;        // 0x01
    private boolean useDoorsAndSwitches = true; // 0x02
    private boolean openContainers = true;      // 0x04
    private boolean attackPlayers = true;       // 0x08
    private boolean attackMobs = true;          // 0x10
    private boolean operator = false;           // 0x20
    private boolean teleport = false;           // 0x80

    private EntityPlayer player;

    /**
     * Adventure settings which control client behaviour
     *
     * @param entityPlayer The player for which this settings are
     */
    public AdventureSettings( EntityPlayer entityPlayer ) {
        this.player = entityPlayer;
    }

    /**
     * Adventure setting build up from a Packet
     *
     * @param flags  from the packet
     * @param flags2 from the packet
     */
    public AdventureSettings( int flags, int flags2 ) {
        // Flag 1
        this.worldImmutable = ( flags & WORLD_IMMUTABLE ) != 0;
        this.noPvP = ( flags & NO_PVP ) != 0;
        this.autoJump = ( flags & AUTO_JUMP ) != 0;
        this.canFly = ( flags & CAN_FLY ) != 0;
        this.noClip = ( flags & NO_CLIP ) != 0;
        this.worldBuilder = ( flags & WORLD_BUILDER ) != 0;
        this.flying = ( flags & FLYING ) != 0;
        this.muted = ( flags & MUTED ) != 0;

        // Flag 2
        this.buildAndMine = ( flags2 & BUILD_AND_MINE ) != 0;
        this.useDoorsAndSwitches = ( flags2 & USE_DOORS_AND_SWITCHES ) != 0;
        this.openContainers = ( flags2 & OPEN_CONTAINERS ) != 0;
        this.attackPlayers = ( flags2 & ATTACK_PLAYERS ) != 0;
        this.attackMobs = ( flags2 & ATTACK_MOBS ) != 0;
        this.operator = ( flags2 & OPERATOR ) != 0;
        this.teleport = ( flags2 & TELEPORT ) != 0;
    }

    /**
     * Send the setting to the client
     */
    public void update() {
        PacketAdventureSettings adventureSettingsPacket = new PacketAdventureSettings();

        // Flags 1
        int flags = 0;
        if ( this.worldImmutable ) {
            flags |= WORLD_IMMUTABLE;              // Immutable World (Remove hit markers client-side).
        }

        if ( this.noPvP ) {
            flags |= NO_PVP;              // No PvP (Remove hit markers client-side).
        }

        if ( this.autoJump ) {
            flags |= AUTO_JUMP;
        }

        if ( this.canFly ) {
            flags |= CAN_FLY;
        }

        if ( this.noClip ) {
            flags |= NO_CLIP;              // No clip
        }

        if ( this.worldBuilder ) {          // TODO: Find out what world builder is
            flags |= WORLD_BUILDER;
        }

        if ( this.flying ) {
            flags |= FLYING;             // Set flying
        }

        if ( this.muted ) {
            flags |= MUTED;
        }

        // Flags 2
        int flags2 = 0;
        if ( this.buildAndMine ) {
            flags2 |= BUILD_AND_MINE;
        }

        if ( this.useDoorsAndSwitches ) {
            flags2 |= USE_DOORS_AND_SWITCHES;
        }

        if ( this.openContainers ) {
            flags2 |= OPEN_CONTAINERS;
        }

        if ( this.attackPlayers ) {
            flags2 |= ATTACK_PLAYERS;
        }

        if ( this.attackMobs ) {
            flags2 |= ATTACK_MOBS;
        }

        if ( this.operator ) {
            flags2 |= OPERATOR;
        }

        if ( this.teleport ) {
            flags2 |= TELEPORT;
        }

        adventureSettingsPacket.setFlags( flags );
        adventureSettingsPacket.setFlags2( flags2 );
        adventureSettingsPacket.setCommandPermission( this.commandPermission.getId() );
        adventureSettingsPacket.setPlayerPermission( this.playerPermission.getId() );
        adventureSettingsPacket.setEntityId( this.player.id() );
        this.player.connection().addToSendQueue( adventureSettingsPacket );
    }

    public CommandPermission getCommandPermission() {
        return commandPermission;
    }

    public void setCommandPermission(CommandPermission commandPermission) {
        this.commandPermission = commandPermission;
    }

    public PlayerPermission getPlayerPermission() {
        return playerPermission;
    }

    public void setPlayerPermission(PlayerPermission playerPermission) {
        this.playerPermission = playerPermission;
    }

    public boolean isWorldImmutable() {
        return worldImmutable;
    }

    public void setWorldImmutable(boolean worldImmutable) {
        this.worldImmutable = worldImmutable;
    }

    public boolean isNoPvP() {
        return noPvP;
    }

    public void setNoPvP(boolean noPvP) {
        this.noPvP = noPvP;
    }

    public boolean isAutoJump() {
        return autoJump;
    }

    public void setAutoJump(boolean autoJump) {
        this.autoJump = autoJump;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean isNoClip() {
        return noClip;
    }

    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
    }

    public boolean isWorldBuilder() {
        return worldBuilder;
    }

    public void setWorldBuilder(boolean worldBuilder) {
        this.worldBuilder = worldBuilder;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isBuildAndMine() {
        return buildAndMine;
    }

    public void setBuildAndMine(boolean buildAndMine) {
        this.buildAndMine = buildAndMine;
    }

    public boolean isUseDoorsAndSwitches() {
        return useDoorsAndSwitches;
    }

    public void setUseDoorsAndSwitches(boolean useDoorsAndSwitches) {
        this.useDoorsAndSwitches = useDoorsAndSwitches;
    }

    public boolean isOpenContainers() {
        return openContainers;
    }

    public void setOpenContainers(boolean openContainers) {
        this.openContainers = openContainers;
    }

    public boolean isAttackPlayers() {
        return attackPlayers;
    }

    public void setAttackPlayers(boolean attackPlayers) {
        this.attackPlayers = attackPlayers;
    }

    public boolean isAttackMobs() {
        return attackMobs;
    }

    public void setAttackMobs(boolean attackMobs) {
        this.attackMobs = attackMobs;
    }

    public boolean isOperator() {
        return operator;
    }

    public void setOperator(boolean operator) {
        this.operator = operator;
    }

    public boolean isTeleport() {
        return teleport;
    }

    public void setTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
