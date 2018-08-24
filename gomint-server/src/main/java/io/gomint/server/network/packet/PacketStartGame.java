package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Location;
import io.gomint.server.network.Protocol;
import io.gomint.server.player.PlayerPermission;
import io.gomint.server.world.BlockRuntimeIDs;
import io.gomint.world.Gamerule;
import lombok.Data;

import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketStartGame extends Packet {

    // Entity data
    private long entityId;
    private long runtimeEntityId;
    private int gamemode;
    private Location spawn;

    // Level data
    private int seed;
    private int dimension;
    private int generator;
    private int worldGamemode;
    private int difficulty;
    private int x;
    private int y;
    private int z;
    private boolean hasAchievementsDisabled = true;
    private int dayCycleStopTime;
    private boolean eduMode;
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
    private String templateName;
    private boolean unknown1 = true;
    private long currentTick;
    private int enchantmentSeed;

    // Server stuff
    private String correlationId;

    /**
     * Create a new start game packet
     */
    public PacketStartGame() {
        super( Protocol.PACKET_START_GAME );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarLong( this.entityId ); // EntityUnique
        buffer.writeUnsignedVarLong( this.runtimeEntityId ); // EntityRuntime
        buffer.writeSignedVarInt( this.gamemode ); // VarInt
        buffer.writeLFloat( this.spawn.getX() ); // Vec3
        buffer.writeLFloat( this.spawn.getY() );
        buffer.writeLFloat( this.spawn.getZ() );
        buffer.writeLFloat( this.spawn.getYaw() ); // Vec2
        buffer.writeLFloat( this.spawn.getPitch() );

        // LevelSettings
        buffer.writeSignedVarInt( this.seed );
        buffer.writeSignedVarInt( this.dimension );
        buffer.writeSignedVarInt( this.generator );
        buffer.writeSignedVarInt( this.worldGamemode );
        buffer.writeSignedVarInt( this.difficulty );
        buffer.writeSignedVarInt( (int) this.spawn.getX() );
        buffer.writeUnsignedVarInt( (int) this.spawn.getY() );
        buffer.writeSignedVarInt( (int) this.spawn.getZ() );
        buffer.writeBoolean( this.hasAchievementsDisabled );
        buffer.writeSignedVarInt( this.dayCycleStopTime );
        buffer.writeBoolean( this.eduMode );
        buffer.writeBoolean( true ); // This is hasEduModeEnabled, we default to false until we have all EDU stuff in
        buffer.writeLFloat( this.rainLevel );
        buffer.writeLFloat( this.lightningLevel );
        buffer.writeBoolean( this.isMultiplayerGame );
        buffer.writeBoolean( this.hasLANBroadcast );
        buffer.writeBoolean( this.hasXboxLiveBroadcast );
        buffer.writeBoolean( this.commandsEnabled );
        buffer.writeBoolean( this.isTexturePacksRequired );
        writeGamerules( this.gamerules, buffer );
        buffer.writeBoolean( this.hasBonusChestEnabled );
        buffer.writeBoolean( this.hasStartWithMapEnabled );
        buffer.writeBoolean( this.hasTrustPlayersEnabled );
        buffer.writeSignedVarInt( this.defaultPlayerPermission );
        buffer.writeSignedVarInt( this.xboxLiveBroadcastMode );
        buffer.writeInt( 32 );
        buffer.writeBoolean( this.hasPlatformBroadcast );
        buffer.writeSignedVarInt( this.platformBroadcastMode );
        buffer.writeBoolean( this.xboxLiveBroadcastIntent );
        buffer.writeBoolean( false );
        buffer.writeBoolean( false );
        buffer.writeBoolean( false );

        // TODO: PRTCL 290
        if ( protocolID == Protocol.MINECRAFT_PE_BETA_PROTOCOL_VERSION ) {
            buffer.writeBoolean( false );
        }

        buffer.writeString( this.levelId );
        buffer.writeString( this.worldName );
        buffer.writeString( this.templateName );
        buffer.writeBoolean( this.unknown1 );
        buffer.writeLLong( this.currentTick );
        buffer.writeSignedVarInt( this.enchantmentSeed );

        // TODO: PRTCL 282
        if ( protocolID >= Protocol.MINECRAFT_PE_NEXT_STABLE_PROTOCOL_VERSION ) {
            // Write palette data
            byte[] data = BlockRuntimeIDs.getPacketCache( protocolID );
            buffer.writeBytes( data );

            buffer.writeString( this.correlationId );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readSignedVarLong().longValue();
        this.runtimeEntityId = buffer.readUnsignedVarLong();
        buffer.readSignedVarInt();

        this.spawn = new Location( null, buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );

        // Skip the rest for now
        buffer.skip( buffer.getRemaining() );
    }

}
