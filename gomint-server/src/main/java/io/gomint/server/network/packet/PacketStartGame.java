package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.server.network.Protocol;
import io.gomint.server.player.PlayerPermission;
import io.gomint.server.util.StringShortPair;
import io.gomint.taglib.AllocationLimitReachedException;
import io.gomint.taglib.NBTReader;
import io.gomint.world.Gamerule;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketStartGame extends Packet {

    public static final short BIOME_TYPE_DEFAULT = 0;
    public static final short BIOME_TYPE_USER_DEFINED = 1;

    // Entity data
    private long entityId;
    private long runtimeEntityId;
    private int gamemode;
    private Location location;

    // Level data
    private int seed;

    private short biomeType;
    private String biomeName;
    private int dimension;
    private Location spawn;

    private int generator;
    private int worldGamemode;
    private int difficulty;
    private boolean hasAchievementsDisabled = true;
    private int dayCycleStopTime;
    private int eduEditionOffer;
    private float rainLevel;
    private float lightningLevel;
    private boolean isMultiplayerGame = true;
    private boolean hasLANBroadcast = true;
    private boolean hasXboxLiveBroadcast = false;
    private boolean commandsEnabled;
    private boolean isTexturePacksRequired;

    // Gamerule data
    private Map<Gamerule, Object> gamerules;
    private boolean hasBonusChestEnabled;
    private boolean hasStartWithMapEnabled;
    private boolean hasTrustPlayersEnabled;
    private int defaultPlayerPermission = PlayerPermission.MEMBER.getId();
    private int xboxLiveBroadcastMode = 0;
    private boolean hasPlatformBroadcast = false;
    private int platformBroadcastMode = 0;
    private boolean xboxLiveBroadcastIntent = false;

    // World data
    private String levelId;
    private String worldName;
    private String templateId;
    private boolean isTrial;
    private boolean movementServerAuthoritative;
    private long currentTick;
    private int enchantmentSeed;

    // Server stuff
    private String correlationId;

    // Lookup tables
    private PacketBuffer blockPalette;
    private PacketBuffer itemPalette;

    /**
     * Create a new start game packet
     */
    public PacketStartGame() {
        super(Protocol.PACKET_START_GAME);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeSignedVarLong(this.entityId); // EntityUnique
        buffer.writeUnsignedVarLong(this.runtimeEntityId); // EntityRuntime
        buffer.writeSignedVarInt(this.gamemode); // VarInt
        buffer.writeLFloat(this.location.getX()); // Vec3
        buffer.writeLFloat(this.location.getY());
        buffer.writeLFloat(this.location.getZ());
        buffer.writeLFloat(this.location.getYaw()); // Vec2
        buffer.writeLFloat(this.location.getPitch());

        // LevelSettings
        buffer.writeSignedVarInt(this.seed);

        // Spawn
        buffer.writeLShort(this.biomeType);
        buffer.writeString(this.biomeName);
        buffer.writeSignedVarInt(this.dimension);

        buffer.writeSignedVarInt(this.generator);
        buffer.writeSignedVarInt(this.worldGamemode);
        buffer.writeSignedVarInt(this.difficulty);
        buffer.writeSignedVarInt((int) this.spawn.getX());
        buffer.writeUnsignedVarInt((int) this.spawn.getY());
        buffer.writeSignedVarInt((int) this.spawn.getZ());
        buffer.writeBoolean(this.hasAchievementsDisabled);
        buffer.writeSignedVarInt(this.dayCycleStopTime);
        buffer.writeSignedVarInt(this.eduEditionOffer);
        buffer.writeBoolean(true); // This is hasEduModeEnabled, we default to false until we have all EDU stuff in
        buffer.writeString(""); // This is eduProductUUID
        buffer.writeLFloat(this.rainLevel);
        buffer.writeLFloat(this.lightningLevel);
        buffer.writeBoolean(false);
        buffer.writeBoolean(this.isMultiplayerGame);
        buffer.writeBoolean(this.hasLANBroadcast);
        buffer.writeSignedVarInt(3);
        buffer.writeSignedVarInt(3);
        buffer.writeBoolean(this.commandsEnabled);
        buffer.writeBoolean(this.isTexturePacksRequired);
        writeGamerules(this.gamerules, buffer);

        // Experiments
        buffer.writeInt(0);
        buffer.writeBoolean(false);

        buffer.writeBoolean(this.hasBonusChestEnabled);
        buffer.writeBoolean(this.hasStartWithMapEnabled);
        buffer.writeSignedVarInt(this.defaultPlayerPermission);
        buffer.writeInt(32);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);
        buffer.writeBoolean(false);

        buffer.writeString(Protocol.MINECRAFT_PE_NETWORK_VERSION);
        buffer.writeLInt(0); // limit world width
        buffer.writeLInt(0); // limit world length
        buffer.writeBoolean(false); // new nether (1.16)
        buffer.writeBoolean(false); // experimental gameplay override, if true another boolean gets written with the override value

        buffer.writeString(this.levelId);
        buffer.writeString(this.worldName);
        buffer.writeString(this.templateId);
        buffer.writeBoolean(this.isTrial);
        buffer.writeUnsignedVarInt(0);
        buffer.writeLLong(this.currentTick);
        buffer.writeSignedVarInt(this.enchantmentSeed);

        // Write palette data
        buffer.writeUnsignedVarInt(0);
        // buffer.writeBytes(this.blockPalette.getBuffer().asReadOnly().readerIndex(0));

        // Item table
        buffer.writeBytes(this.itemPalette.getBuffer().asReadOnly().readerIndex(0));

        buffer.writeString(this.correlationId);
        buffer.writeBoolean(false); // TODO: use new inventory system
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.entityId = buffer.readSignedVarLong().longValue();
        this.runtimeEntityId = buffer.readUnsignedVarLong();
        this.gamemode = buffer.readSignedVarInt();

        this.spawn = new Location( null, buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );

        this.seed = buffer.readSignedVarInt();

        short biomeType = buffer.readLShort();
        String biomeName = buffer.readString();
        this.dimension = buffer.readSignedVarInt();

        this.generator = buffer.readSignedVarInt();
        this.worldGamemode = buffer.readSignedVarInt();
        this.difficulty = buffer.readSignedVarInt();

        int spawnX = buffer.readSignedVarInt();
        int spawnY = buffer.readSignedVarInt();
        int spawnZ = buffer.readSignedVarInt();

        this.hasAchievementsDisabled = buffer.readBoolean();
        this.dayCycleStopTime = buffer.readSignedVarInt();
        int eudOffer = buffer.readSignedVarInt();
        buffer.readBoolean();
        String eduProductID = buffer.readString();
        this.rainLevel = buffer.readLFloat();
        this.lightningLevel = buffer.readLFloat();
        buffer.readBoolean();
        this.isMultiplayerGame = buffer.readBoolean();
        this.hasLANBroadcast = buffer.readBoolean();
        buffer.readSignedVarInt();
        buffer.readSignedVarInt();
        this.commandsEnabled = buffer.readBoolean();
        this.isTexturePacksRequired = buffer.readBoolean();
        this.gamerules = readGamerules( buffer );

        // Experiments
        buffer.readInt();
        buffer.readBoolean();

        this.hasBonusChestEnabled = buffer.readBoolean();
        this.hasStartWithMapEnabled = buffer.readBoolean();

        this.defaultPlayerPermission = buffer.readSignedVarInt();
        buffer.readInt();
        buffer.readBoolean();
        buffer.readBoolean();
        buffer.readBoolean();
        buffer.readBoolean();
        buffer.readBoolean();
        buffer.readBoolean();
        buffer.readBoolean();

        buffer.readString();

        buffer.readLInt();
        buffer.readLInt();
        buffer.readBoolean();

        if (buffer.readBoolean()) {
            buffer.readBoolean();
        }

        this.levelId = buffer.readString();
        this.worldName = buffer.readString();
        buffer.readString();
        buffer.readBoolean();
        buffer.readUnsignedVarInt();
        this.currentTick = buffer.readLLong();
        this.enchantmentSeed = buffer.readSignedVarInt();

        buffer.readUnsignedVarInt();

        int itemPaletteAmount = buffer.readUnsignedVarInt();
        for ( int i = 0; i < itemPaletteAmount; i++ ) {
            buffer.readString();
            buffer.readLShort();
            buffer.readBoolean();
        }

        buffer.readString();
        buffer.readBoolean();
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public long getRuntimeEntityId() {
        return runtimeEntityId;
    }

    public void setRuntimeEntityId(long runtimeEntityId) {
        this.runtimeEntityId = runtimeEntityId;
    }

    public int getGamemode() {
        return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public short getBiomeType() {
        return biomeType;
    }

    public void setBiomeType(short biomeType) {
        this.biomeType = biomeType;
    }

    public String getBiomeName() {
        return biomeName;
    }

    public void setBiomeName(String biomeName) {
        this.biomeName = biomeName;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getGenerator() {
        return generator;
    }

    public void setGenerator(int generator) {
        this.generator = generator;
    }

    public int getWorldGamemode() {
        return worldGamemode;
    }

    public void setWorldGamemode(int worldGamemode) {
        this.worldGamemode = worldGamemode;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isHasAchievementsDisabled() {
        return hasAchievementsDisabled;
    }

    public void setHasAchievementsDisabled(boolean hasAchievementsDisabled) {
        this.hasAchievementsDisabled = hasAchievementsDisabled;
    }

    public int getDayCycleStopTime() {
        return dayCycleStopTime;
    }

    public void setDayCycleStopTime(int dayCycleStopTime) {
        this.dayCycleStopTime = dayCycleStopTime;
    }

    public int getEduEditionOffer() {
        return eduEditionOffer;
    }

    public void setEduEditionOffer(int eduEditionOffer) {
        this.eduEditionOffer = eduEditionOffer;
    }

    public float getRainLevel() {
        return rainLevel;
    }

    public void setRainLevel(float rainLevel) {
        this.rainLevel = rainLevel;
    }

    public float getLightningLevel() {
        return lightningLevel;
    }

    public void setLightningLevel(float lightningLevel) {
        this.lightningLevel = lightningLevel;
    }

    public boolean isMultiplayerGame() {
        return isMultiplayerGame;
    }

    public void setMultiplayerGame(boolean multiplayerGame) {
        isMultiplayerGame = multiplayerGame;
    }

    public boolean isHasLANBroadcast() {
        return hasLANBroadcast;
    }

    public void setHasLANBroadcast(boolean hasLANBroadcast) {
        this.hasLANBroadcast = hasLANBroadcast;
    }

    public boolean isHasXboxLiveBroadcast() {
        return hasXboxLiveBroadcast;
    }

    public void setHasXboxLiveBroadcast(boolean hasXboxLiveBroadcast) {
        this.hasXboxLiveBroadcast = hasXboxLiveBroadcast;
    }

    public boolean isCommandsEnabled() {
        return commandsEnabled;
    }

    public void setCommandsEnabled(boolean commandsEnabled) {
        this.commandsEnabled = commandsEnabled;
    }

    public boolean isTexturePacksRequired() {
        return isTexturePacksRequired;
    }

    public void setTexturePacksRequired(boolean texturePacksRequired) {
        isTexturePacksRequired = texturePacksRequired;
    }

    public Map<Gamerule, Object> getGamerules() {
        return gamerules;
    }

    public void setGamerules(Map<Gamerule, Object> gamerules) {
        this.gamerules = gamerules;
    }

    public boolean isHasBonusChestEnabled() {
        return hasBonusChestEnabled;
    }

    public void setHasBonusChestEnabled(boolean hasBonusChestEnabled) {
        this.hasBonusChestEnabled = hasBonusChestEnabled;
    }

    public boolean isHasStartWithMapEnabled() {
        return hasStartWithMapEnabled;
    }

    public void setHasStartWithMapEnabled(boolean hasStartWithMapEnabled) {
        this.hasStartWithMapEnabled = hasStartWithMapEnabled;
    }

    public boolean isHasTrustPlayersEnabled() {
        return hasTrustPlayersEnabled;
    }

    public void setHasTrustPlayersEnabled(boolean hasTrustPlayersEnabled) {
        this.hasTrustPlayersEnabled = hasTrustPlayersEnabled;
    }

    public int getDefaultPlayerPermission() {
        return defaultPlayerPermission;
    }

    public void setDefaultPlayerPermission(int defaultPlayerPermission) {
        this.defaultPlayerPermission = defaultPlayerPermission;
    }

    public int getXboxLiveBroadcastMode() {
        return xboxLiveBroadcastMode;
    }

    public void setXboxLiveBroadcastMode(int xboxLiveBroadcastMode) {
        this.xboxLiveBroadcastMode = xboxLiveBroadcastMode;
    }

    public boolean isHasPlatformBroadcast() {
        return hasPlatformBroadcast;
    }

    public void setHasPlatformBroadcast(boolean hasPlatformBroadcast) {
        this.hasPlatformBroadcast = hasPlatformBroadcast;
    }

    public int getPlatformBroadcastMode() {
        return platformBroadcastMode;
    }

    public void setPlatformBroadcastMode(int platformBroadcastMode) {
        this.platformBroadcastMode = platformBroadcastMode;
    }

    public boolean isXboxLiveBroadcastIntent() {
        return xboxLiveBroadcastIntent;
    }

    public void setXboxLiveBroadcastIntent(boolean xboxLiveBroadcastIntent) {
        this.xboxLiveBroadcastIntent = xboxLiveBroadcastIntent;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public boolean isTrial() {
        return isTrial;
    }

    public void setTrial(boolean trial) {
        isTrial = trial;
    }

    public boolean isMovementServerAuthoritative() {
        return movementServerAuthoritative;
    }

    public void setMovementServerAuthoritative(boolean movementServerAuthoritative) {
        this.movementServerAuthoritative = movementServerAuthoritative;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(long currentTick) {
        this.currentTick = currentTick;
    }

    public int getEnchantmentSeed() {
        return enchantmentSeed;
    }

    public void setEnchantmentSeed(int enchantmentSeed) {
        this.enchantmentSeed = enchantmentSeed;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public PacketBuffer getBlockPalette() {
        return blockPalette;
    }

    public void setBlockPalette(PacketBuffer blockPalette) {
        this.blockPalette = blockPalette;
    }

    public PacketBuffer getItemPalette() {
        return itemPalette;
    }

    public void setItemPalette(PacketBuffer itemPalette) {
        this.itemPalette = itemPalette;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
