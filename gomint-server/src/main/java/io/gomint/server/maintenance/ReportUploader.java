/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.maintenance;

import io.gomint.GoMint;
import io.gomint.config.YamlConfig;
import io.gomint.entity.EntityPlayer;
import io.gomint.math.Location;
import io.gomint.server.GoMintServer;
import io.gomint.server.maintenance.report.PlayerReportData;
import io.gomint.server.maintenance.report.WorldData;
import io.gomint.server.world.WorldAdapter;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author geNAZt
 * @version 1.0
 */
public final class ReportUploader {

    private static final Logger LOGGER = LoggerFactory.getLogger( ReportUploader.class );

    private Map<String, String> system = new HashMap<>();
    private Map<String, WorldData> worlds = new HashMap<>();
    private Map<String, PlayerReportData> players = new HashMap<>();
    private Map<String, String> properties = new HashMap<>();
    private String stacktrace = null;

    private ReportUploader() {
        // Get some basic system data
        this.system.put( "gomint_version", GoMint.instance().getVersion() );
        this.system.put( "java_version", System.getProperty( "java.vm.name" ) + " (" + System.getProperty( "java.runtime.version" ) + ")" );

        // Ask for OS and CPU info
        SystemInfo systemInfo = new SystemInfo();
        this.system.put( "os", systemInfo.getOperatingSystem().getFamily() + " [" + systemInfo.getOperatingSystem().getVersion().getVersion() + "]" );
        this.system.put( "memory", getCount( systemInfo.getHardware().getMemory().getTotal() ) );
        this.system.put( "cpu", systemInfo.getHardware().getProcessor().getName() );

        // Basic process stats
        this.system.put( "process_memory_total", getCount( Runtime.getRuntime().totalMemory() ) );
        this.system.put( "process_memory_free", getCount( Runtime.getRuntime().freeMemory() ) );
    }

    private static String getCount( long bytes ) {
        // Do we need to check for suffix
        if ( bytes < 1024 ) {
            return bytes + " B";
        }

        // Get exp and get the correct suffix
        int exp = (int) ( Math.log( bytes ) / Math.log( 1024 ) );
        char pre = "KMGTPE".charAt( exp - 1 );
        return String.format( "%.1f %siB", bytes / Math.pow( 1024, exp ), pre );
    }

    /**
     * Include world data
     *
     * @return the report uploader for chaining
     */
    public ReportUploader includeWorlds() {
        GoMintServer server = (GoMintServer) GoMint.instance();
        for ( WorldAdapter adapter : server.getWorldManager().getWorlds() ) {
            this.worlds.put( adapter.getWorldName(), new WorldData( adapter.getChunkCache().size() ) );
        }

        return this;
    }

    /**
     * Include player data
     *
     * @return the report uploader for chaining
     */
    public ReportUploader includePlayers() {
        for ( EntityPlayer player : GoMint.instance().getPlayers() ) {
            String key = player.getName() + ":" + player.getUUID().toString();
            Location location = player.getLocation();
            this.players.put( key, new PlayerReportData( location.getWorld().getWorldName(), location.getX(), location.getY(), location.getZ() ) );
        }

        return this;
    }

    /**
     * Convert a exception to a string and append it to the report
     *
     * @param e which should be reported
     * @return the report uploader for chaining
     */
    public ReportUploader exception( Throwable e ) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace( new PrintWriter( stringWriter ) );
        this.stacktrace = stringWriter.toString();
        return this;
    }

    /**
     * Add a new property to this upload request
     *
     * @param key   of the property
     * @param value of the property
     * @return the report uploader for chaining
     */
    public ReportUploader property( String key, String value ) {
        this.properties.put( key, value );
        return this;
    }

    /**
     * Upload this report
     */
    public void upload() {
        // Check if reporting has been disabled
        GoMintServer server = (GoMintServer) GoMint.instance();
        if ( server.getServerConfig().isDisableGomintReports() ) {
            return;
        }

        // Ask the round robin service
        String service = this.getUploadServer();
        if ( service == null ) {
            return;
        }

        // We got a server assigned, upload report
        this.uploadToServer( service );
    }

    private void uploadToServer( String service ) {
        GoMintServer server = (GoMintServer) GoMint.instance();

        Map<String, Map<String, ?>> data = new HashMap<>();
        data.put( "config", new HashMap<String, YamlConfig>() {{
            put( "server", server.getServerConfig() );
        }} );
        data.put( "system", this.system );

        if ( this.stacktrace != null ) {
            data.put( "thrown", new HashMap<String, String>() {{
                put( "trace", stacktrace );
            }} );
        }

        if ( this.worlds.size() > 0 ) {
            data.put( "worlds", this.worlds );
        }

        if ( this.players.size() > 0 ) {
            data.put( "players", this.players );
        }

        if ( this.properties.size() > 0 ) {
            data.put( "properties", this.properties );
        }

        String json = JSONObject.toJSONString( data );

        try {
            URL url = new URL( service );
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout( 500 );
            connection.setDoOutput( true );

            try ( OutputStream out = connection.getOutputStream() ) {
                out.write( json.getBytes( "UTF-8" ) );
            }

            connection.getInputStream().close();
        } catch ( MalformedURLException e ) {
            // This will never be malformed
        } catch ( IOException e ) {
            LOGGER.warn( "Could not upload report", e );
        }
    }

    private String getUploadServer() {
        try {
            URL url = new URL( "http://report.gomint.io" );
            URLConnection connection = url.openConnection();

            connection.setConnectTimeout( 500 );
            connection.setReadTimeout( 500 );

            try ( InputStream in = connection.getInputStream() ) {
                String server = IOUtils.readLines( in, "UTF-8" ).get( 0 );
                if ( "NONE".equals( server ) ) {    // We use this wanted breaking point to disable uploading reports if needed server wise
                    return null;
                } else {
                    return server;
                }
            }
        } catch ( MalformedURLException e ) {
            // This will never be malformed
        } catch ( IOException e ) {
            LOGGER.warn( "Could not get report upload service from round robin", e );
        }

        return null;
    }

    /**
     * Create a new report uploader
     *
     * @return a report uploader
     */
    public static ReportUploader create() {
        return new ReportUploader();
    }

}
