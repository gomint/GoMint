package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.Protocol;
import io.gomint.server.player.PlayerPermission;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.world.Gamerule;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketStartGame extends Packet {

    public static final short BIOME_TYPE_DEFAULT = 0;
    public static final short BIOME_TYPE_USER_DEFINED = 1;

    // Entity data
    private long entityId;
    private long runtimeEntityId;
    private int gamemode;
    private Location spawn;

    // Level data
    private int seed;

    private short biomeType;
    private String biomeName;
    private int dimension;

    private int generator;
    private int worldGamemode;
    private int difficulty;
    private int x;
    private int y;
    private int z;
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
        buffer.writeLFloat(this.spawn.getX()); // Vec3
        buffer.writeLFloat(this.spawn.getY());
        buffer.writeLFloat(this.spawn.getZ());
        buffer.writeLFloat(this.spawn.getYaw()); // Vec2
        buffer.writeLFloat(this.spawn.getPitch());

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
        buffer.writeBoolean(this.movementServerAuthoritative);
        buffer.writeLLong(this.currentTick);
        buffer.writeSignedVarInt(this.enchantmentSeed);

        // Write palette data
        ByteBuf data = BlockRuntimeIDs.getPacketCache();
        buffer.writeBytes(data.asReadOnly().readerIndex(0));

        // Item table
        PacketBuffer itemData = Items.getPacketCache();
        buffer.writeBytes(itemData.getBuffer().asReadOnly().readerIndex(0));

        buffer.writeString(this.correlationId);
        buffer.writeBoolean(false); // TODO: use new inventory system
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }

}
