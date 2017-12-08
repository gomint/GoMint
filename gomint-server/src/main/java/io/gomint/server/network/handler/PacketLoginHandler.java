/*
 *  Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 *  This code is licensed under the BSD license found in the
 *  LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerLoginEvent;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.jwt.*;
import io.gomint.server.network.EncryptionHandler;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.PlayerConnectionState;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.PacketEncryptionRequest;
import io.gomint.server.network.packet.PacketLogin;
import io.gomint.server.network.packet.PacketPlayState;
import io.gomint.server.player.DeviceInfo;
import io.gomint.server.player.PlayerSkin;
import io.gomint.server.scheduler.SyncScheduledTask;
import io.gomint.server.world.WorldAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.Key;
import java.util.Base64;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketLoginHandler implements PacketHandler<PacketLogin> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketLoginHandler.class );
    private static final EncryptionRequestForger FORGER = new EncryptionRequestForger();
    private static final Pattern NAME_PATTERN = Pattern.compile( "[a-zA-z0-9_\\.]{1,16}" );

    @Override
    public void handle( PacketLogin packet, long currentTimeMillis, PlayerConnection connection ) {
        // Check versions
        LOGGER.debug( "Trying to login with protocol version: " + packet.getProtocol() );
        if ( packet.getProtocol() != Protocol.MINECRAFT_PE_PROTOCOL_VERSION
            && packet.getProtocol() != Protocol.MINECRAFT_PE_BETA_PROTOCOL_VERSION ) {
            String message;
            if ( packet.getProtocol() < Protocol.MINECRAFT_PE_PROTOCOL_VERSION ) {
                message = "disconnectionScreen.outdatedClient";
                connection.sendPlayState( PacketPlayState.PlayState.LOGIN_FAILED_CLIENT );
            } else {
                message = "disconnectionScreen.outdatedServer";
                connection.sendPlayState( PacketPlayState.PlayState.LOGIN_FAILED_SERVER );
            }

            connection.disconnect( message );
            return;
        }

        // Async login sequence
        connection.getServer().getExecutorService().execute( () -> {
            // More data please
            ByteBuffer byteBuffer = ByteBuffer.wrap( packet.getPayload() );
            byteBuffer.order( ByteOrder.LITTLE_ENDIAN );
            byte[] stringBuffer = new byte[byteBuffer.getInt()];
            byteBuffer.get( stringBuffer );

            // Parse chain and validate
            String jwt = new String( stringBuffer );
            JSONObject json;
            try {
                json = parseJwtString( jwt );
            } catch ( ParseException e ) {
                e.printStackTrace();
                return;
            }

            Object jsonChainRaw = json.get( "chain" );
            if ( jsonChainRaw == null || !( jsonChainRaw instanceof JSONArray ) ) {
                return;
            }

            MojangChainValidator chainValidator = new MojangChainValidator( connection.getServer().getEncryptionKeyFactory() );
            JSONArray jsonChain = (JSONArray) jsonChainRaw;
            for ( Object jsonTokenRaw : jsonChain ) {
                if ( jsonTokenRaw instanceof String ) {
                    try {
                        JwtToken token = JwtToken.parse( (String) jsonTokenRaw );
                        chainValidator.addToken( token );
                    } catch ( IllegalArgumentException e ) {
                        e.printStackTrace();
                    }
                }
            }

            boolean valid = chainValidator.validate();

            // Parse skin
            byte[] skin = new byte[byteBuffer.getInt()];
            byteBuffer.get( skin );

            JwtToken skinToken = JwtToken.parse( new String( skin ) );
            boolean validSkin = true;

            try {
                skinToken.validateSignature( JwtAlgorithm.ES384, chainValidator.getClientPublicKey() );
            } catch ( JwtSignatureException e ) {
                LOGGER.info( "Invalid skin: ", e );
                validSkin = false;
            }

            // Sync up for disconnecting etc.
            boolean finalValidSkin = validSkin;
            connection.getServer().getSyncTaskManager().addTask( new SyncScheduledTask( connection.getServer().getSyncTaskManager(), new Runnable() {
                @Override
                public void run() {
                    // Invalid skin
                    if ( !finalValidSkin && connection.getNetworkManager().getServer().getServerConfig().isOnlyXBOXLogin() ) {
                        connection.disconnect( "Skin is invalid or corrupted" );
                        return;
                    }

                    // Check if valid user (xbox live)
                    if ( !valid && connection.getNetworkManager().getServer().getServerConfig().isOnlyXBOXLogin() ) {
                        connection.disconnect( "Only valid XBOX Logins are allowed" );
                        return;
                    }

                    // Check for names
                    String name = chainValidator.getUsername();
                    if ( name.length() < 1 || name.length() > 16 ) {
                        connection.disconnect( "disconnectionScreen.invalidName" );
                        return;
                    } else if ( !NAME_PATTERN.matcher( name ).matches() ) {
                        connection.disconnect( "disconnectionScreen.invalidName" );
                        return;
                    }

                    // Check for name / uuid collision
                    for ( io.gomint.entity.EntityPlayer player : connection.getNetworkManager().getServer().getPlayers() ) {
                        if ( player.getName().equals( name ) ||
                            player.getUUID().equals( chainValidator.getUuid() ) ) {
                            connection.disconnect( "Player already logged in on this server" );
                            return;
                        }
                    }

                    // Create additional data wrappers
                    String capeData = skinToken.getClaim( "CapeData" );
                    PlayerSkin playerSkin = new PlayerSkin(
                        skinToken.getClaim( "SkinId" ),
                        Base64.getDecoder().decode( (String) skinToken.getClaim( "SkinData" ) ),
                        capeData.isEmpty() ? null : Base64.getDecoder().decode( capeData ),
                        skinToken.getClaim( "SkinGeometryName" ),
                        Base64.getDecoder().decode( (String) skinToken.getClaim( "SkinGeometry" ) )
                    );

                    // Create needed device info
                    DeviceInfo deviceInfo = new DeviceInfo(
                        Math.toIntExact( skinToken.getClaim( "DeviceOS" ) ),
                        skinToken.getClaim( "DeviceModel" ) );
                    connection.setDeviceInfo( deviceInfo );

                    // Detect language
                    String languageCode = skinToken.getClaim( "LanguageCode" );
                    Locale locale;
                    if ( languageCode != null ) {
                        locale = Locale.forLanguageTag( languageCode.replace( "_", "-") );
                    } else {
                        locale = Locale.US;
                    }

                    // Create entity:
                    WorldAdapter world = connection.getNetworkManager().getServer().getDefaultWorld();
                    connection.setEntity( new EntityPlayer( world, connection, chainValidator.getUsername(),
                        chainValidator.getXboxId(), chainValidator.getUuid(), locale ) );
                    connection.getEntity().setSkin( playerSkin );
                    connection.getEntity().setNameTagVisible( true );
                    connection.getEntity().setNameTagAlwaysVisible( true );

                    // Fill in fast access maps
                    connection.getServer().getPlayersByUUID().put( chainValidator.getUuid(), connection.getEntity() );

                    // Post login event
                    PlayerLoginEvent event = new PlayerLoginEvent( connection.getEntity() );

                    // Default deny for maximum amount of players
                    if ( connection.getServer().getPlayers().size() >= connection.getServer().getServerConfig().getMaxPlayers() ) {
                        event.setCancelled( true );
                        event.setKickMessage( "Server is full" );
                    }

                    connection.getNetworkManager().getServer().getPluginManager().callEvent( event );
                    if ( event.isCancelled() ) {
                        connection.disconnect( event.getKickMessage() );
                        return;
                    }

                    if ( connection.getEntity().getWorld().getServer().getEncryptionKeyFactory().getKeyPair() == null ) {
                        // No encryption
                        connection.sendPlayState( PacketPlayState.PlayState.LOGIN_SUCCESS );
                        connection.setState( PlayerConnectionState.RESOURCE_PACK );
                        connection.initWorldAndResourceSend();
                    } else {
                        // Generating EDCH secrets can take up huge amount of time
                        connection.getServer().getExecutorService().execute( () -> {
                            // Enable encryption
                            EncryptionHandler encryptionHandler = new EncryptionHandler( connection.getEntity().getWorld().getServer().getEncryptionKeyFactory() );
                            encryptionHandler.supplyClientKey( chainValidator.getClientPublicKey() );
                            if ( encryptionHandler.beginClientsideEncryption() ) {
                                // Get the needed data for the encryption start
                                connection.setState( PlayerConnectionState.ENCRPYTION_INIT );
                                connection.setEncryptionHandler( encryptionHandler );

                                // Forge a JWT
                                String encryptionRequestJWT = FORGER.forge( encryptionHandler.getServerPublic(), encryptionHandler.getServerPrivate(), encryptionHandler.getClientSalt() );

                                PacketEncryptionRequest packetEncryptionRequest = new PacketEncryptionRequest();
                                packetEncryptionRequest.setJwt( encryptionRequestJWT );
                                connection.send( packetEncryptionRequest );
                            }
                        } );

                    }
                }
            }, 1, -1, TimeUnit.MILLISECONDS ) );
        } );
    }

    /**
     * Parses the specified JSON string and ensures it is a JSONObject.
     *
     * @param jwt The string to parse
     * @return The parsed JSON object on success
     * @throws ParseException Thrown if the given JSON string is invalid or does not start with a JSONObject
     */
    private JSONObject parseJwtString( String jwt ) throws ParseException {
        Object jsonParsed = new JSONParser().parse( jwt );
        if ( jsonParsed instanceof JSONObject ) {
            return (JSONObject) jsonParsed;
        } else {
            throw new ParseException( ParseException.ERROR_UNEXPECTED_TOKEN );
        }
    }

}
