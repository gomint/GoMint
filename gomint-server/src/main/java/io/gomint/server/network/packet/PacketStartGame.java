package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.server.network.Protocol;
import io.gomint.server.player.PlayerPermission;
import io.gomint.world.Gamerule;

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
    private Map<Gamerule<?>, Object> gamerules;
    private boolean hasBonusChestEnabled;
    private boolean hasStartWithMapEnabled;
    private boolean hasTrustPlayersEnabled;
    private int defaultPlayerPermission = PlayerPermission.MEMBER.id();
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
        buffer.writeLFloat(this.location.x()); // Vec3
        buffer.writeLFloat(this.location.y());
        buffer.writeLFloat(this.location.z());
        buffer.writeLFloat(this.location.yaw()); // Vec2
        buffer.writeLFloat(this.location.pitch());

        // LevelSettings
        buffer.writeSignedVarInt(this.seed);

        // Spawn
        buffer.writeLShort(this.biomeType);
        buffer.writeString(this.biomeName);
        buffer.writeSignedVarInt(this.dimension);

        buffer.writeSignedVarInt(this.generator);
        buffer.writeSignedVarInt(this.worldGamemode);
        buffer.writeSignedVarInt(this.difficulty);
        buffer.writeSignedVarInt((int) this.spawn.x());
        buffer.writeUnsignedVarInt((int) this.spawn.y());
        buffer.writeSignedVarInt((int) this.spawn.z());
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
        buffer.writeBoolean(true); // TODO: use new inventory system
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
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public long getRuntimeEntityId() {
        return this.runtimeEntityId;
    }

    public void setRuntimeEntityId(long runtimeEntityId) {
        this.runtimeEntityId = runtimeEntityId;
    }

    public int getGamemode() {
        return this.gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getSeed() {
        return this.seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public short getBiomeType() {
        return this.biomeType;
    }

    public void setBiomeType(short biomeType) {
        this.biomeType = biomeType;
    }

    public String getBiomeName() {
        return this.biomeName;
    }

    public void setBiomeName(String biomeName) {
        this.biomeName = biomeName;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getGenerator() {
        return this.generator;
    }

    public void setGenerator(int generator) {
        this.generator = generator;
    }

    public int getWorldGamemode() {
        return this.worldGamemode;
    }

    public void setWorldGamemode(int worldGamemode) {
        this.worldGamemode = worldGamemode;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isHasAchievementsDisabled() {
        return this.hasAchievementsDisabled;
    }

    public void setHasAchievementsDisabled(boolean hasAchievementsDisabled) {
        this.hasAchievementsDisabled = hasAchievementsDisabled;
    }

    public int getDayCycleStopTime() {
        return this.dayCycleStopTime;
    }

    public void setDayCycleStopTime(int dayCycleStopTime) {
        this.dayCycleStopTime = dayCycleStopTime;
    }

    public int getEduEditionOffer() {
        return this.eduEditionOffer;
    }

    public void setEduEditionOffer(int eduEditionOffer) {
        this.eduEditionOffer = eduEditionOffer;
    }

    public float getRainLevel() {
        return this.rainLevel;
    }

    public void setRainLevel(float rainLevel) {
        this.rainLevel = rainLevel;
    }

    public float getLightningLevel() {
        return this.lightningLevel;
    }

    public void setLightningLevel(float lightningLevel) {
        this.lightningLevel = lightningLevel;
    }

    public boolean isMultiplayerGame() {
        return this.isMultiplayerGame;
    }

    public void setMultiplayerGame(boolean multiplayerGame) {
        this.isMultiplayerGame = multiplayerGame;
    }

    public boolean isHasLANBroadcast() {
        return this.hasLANBroadcast;
    }

    public void setHasLANBroadcast(boolean hasLANBroadcast) {
        this.hasLANBroadcast = hasLANBroadcast;
    }

    public boolean isHasXboxLiveBroadcast() {
        return this.hasXboxLiveBroadcast;
    }

    public void setHasXboxLiveBroadcast(boolean hasXboxLiveBroadcast) {
        this.hasXboxLiveBroadcast = hasXboxLiveBroadcast;
    }

    public boolean isCommandsEnabled() {
        return this.commandsEnabled;
    }

    public void setCommandsEnabled(boolean commandsEnabled) {
        this.commandsEnabled = commandsEnabled;
    }

    public boolean isTexturePacksRequired() {
        return this.isTexturePacksRequired;
    }

    public void setTexturePacksRequired(boolean texturePacksRequired) {
        this.isTexturePacksRequired = texturePacksRequired;
    }

    public Map<Gamerule<?>, Object> getGamerules() {
        return this.gamerules;
    }

    public void setGamerules(Map<Gamerule<?>, Object> gamerules) {
        this.gamerules = gamerules;
    }

    public boolean isHasBonusChestEnabled() {
        return this.hasBonusChestEnabled;
    }

    public void setHasBonusChestEnabled(boolean hasBonusChestEnabled) {
        this.hasBonusChestEnabled = hasBonusChestEnabled;
    }

    public boolean isHasStartWithMapEnabled() {
        return this.hasStartWithMapEnabled;
    }

    public void setHasStartWithMapEnabled(boolean hasStartWithMapEnabled) {
        this.hasStartWithMapEnabled = hasStartWithMapEnabled;
    }

    public boolean isHasTrustPlayersEnabled() {
        return this.hasTrustPlayersEnabled;
    }

    public void setHasTrustPlayersEnabled(boolean hasTrustPlayersEnabled) {
        this.hasTrustPlayersEnabled = hasTrustPlayersEnabled;
    }

    public int getDefaultPlayerPermission() {
        return this.defaultPlayerPermission;
    }

    public void setDefaultPlayerPermission(int defaultPlayerPermission) {
        this.defaultPlayerPermission = defaultPlayerPermission;
    }

    public int getXboxLiveBroadcastMode() {
        return this.xboxLiveBroadcastMode;
    }

    public void setXboxLiveBroadcastMode(int xboxLiveBroadcastMode) {
        this.xboxLiveBroadcastMode = xboxLiveBroadcastMode;
    }

    public boolean isHasPlatformBroadcast() {
        return this.hasPlatformBroadcast;
    }

    public void setHasPlatformBroadcast(boolean hasPlatformBroadcast) {
        this.hasPlatformBroadcast = hasPlatformBroadcast;
    }

    public int getPlatformBroadcastMode() {
        return this.platformBroadcastMode;
    }

    public void setPlatformBroadcastMode(int platformBroadcastMode) {
        this.platformBroadcastMode = platformBroadcastMode;
    }

    public boolean isXboxLiveBroadcastIntent() {
        return this.xboxLiveBroadcastIntent;
    }

    public void setXboxLiveBroadcastIntent(boolean xboxLiveBroadcastIntent) {
        this.xboxLiveBroadcastIntent = xboxLiveBroadcastIntent;
    }

    public String getLevelId() {
        return this.levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public boolean isTrial() {
        return this.isTrial;
    }

    public void setTrial(boolean trial) {
        this.isTrial = trial;
    }

    public boolean isMovementServerAuthoritative() {
        return this.movementServerAuthoritative;
    }

    public void setMovementServerAuthoritative(boolean movementServerAuthoritative) {
        this.movementServerAuthoritative = movementServerAuthoritative;
    }

    public long getCurrentTick() {
        return this.currentTick;
    }

    public void setCurrentTick(long currentTick) {
        this.currentTick = currentTick;
    }

    public int getEnchantmentSeed() {
        return this.enchantmentSeed;
    }

    public void setEnchantmentSeed(int enchantmentSeed) {
        this.enchantmentSeed = enchantmentSeed;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public PacketBuffer getBlockPalette() {
        return this.blockPalette;
    }

    public void setBlockPalette(PacketBuffer blockPalette) {
        this.blockPalette = blockPalette;
    }

    public PacketBuffer getItemPalette() {
        return this.itemPalette;
    }

    public void setItemPalette(PacketBuffer itemPalette) {
        this.itemPalette = itemPalette;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
