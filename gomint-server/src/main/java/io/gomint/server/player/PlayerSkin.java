/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.player;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PlayerSkin implements io.gomint.player.PlayerSkin {

    @Getter
    private static final GeometryCache GEOMETRY_CACHE = new GeometryCache();
    private static final int SKIN_DATA_SIZE_STEVE = 8192;
    private static final int SKIN_DATA_SIZE_ALEX = 16384;
    private static final int SKIN_DATA_SIZE_FULL = 65536;

    private String name;
    private byte[] data;
    private byte[] capeData;
    private String geometryName;
    private String geometryData;

    // Internal image caching
    private BufferedImage image;

    /**
     * Create new skin
     *
     * @param name         of the skin
     * @param data         byte array of skin data ( R G B A )
     * @param capeData     byte array of the cape data (mostly null) ( R G B A )
     * @param geometryName name of the geometry to use for this skin
     * @param geometryData json data of the geometry with parents which has been sent from the client
     */
    public PlayerSkin( String name, byte[] data, byte[] capeData, String geometryName, String geometryData ) {
        if ( data.length != SKIN_DATA_SIZE_STEVE && data.length != SKIN_DATA_SIZE_ALEX && data.length != SKIN_DATA_SIZE_FULL ) {
            throw new IllegalArgumentException( "Invalid skin data buffer length: " + data.length );
        }

        this.name = name;
        this.data = data;
        this.capeData = capeData;
        this.geometryName = geometryName;
        this.geometryData = geometryData;
    }

    private void createImageFromSkinData() {
        if ( this.image != null ) {
            return;
        }

        int height = this.data.length == SKIN_DATA_SIZE_FULL ? 128 : this.data.length == SKIN_DATA_SIZE_ALEX ? 64 : 32;
        this.image = new BufferedImage( this.data.length == SKIN_DATA_SIZE_FULL ? 128 : 64, height, BufferedImage.TYPE_INT_ARGB );

        int cursor = 0;
        for ( int y = 0; y < ( this.data.length == SKIN_DATA_SIZE_FULL ? 128 : 64 ); y++ ) {
            for ( int x = 0; x < height; x++ ) {
                byte r = this.data[cursor++];
                byte g = this.data[cursor++];
                byte b = this.data[cursor++];
                byte a = this.data[cursor++];

                int rgbValue = ( ( a & 0xFF ) << 24 ) |
                    ( ( r & 0xFF ) << 16 ) |
                    ( ( g & 0xFF ) << 8 ) |
                    ( b & 0xFF );

                this.image.setRGB( x, y, rgbValue );
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public byte[] getRawData() {
        return this.data;
    }

    @Override
    public byte[] getCapeData() {
        return this.capeData;
    }

    @Override
    public String getGeometryName() {
        return this.geometryName;
    }

    @Override
    public String getGeometryData() {
        return this.geometryData;
    }

    @Override
    public void saveSkinTo( OutputStream out ) throws IOException {
        this.createImageFromSkinData();
        ImageIO.write( this.image, "PNG", out );
    }

    /**
     * Create a skin from a given input stream
     *
     * @param inputStream which holds the data for this skin
     * @return skin which can be applied to entity human
     * @throws IOException when there was an error with the image
     */
    public static PlayerSkin fromInputStream( InputStream inputStream ) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream( inputStream );
        BufferedImage image = ImageIO.read( imageInputStream );

        if ( image.getWidth() != 64 && image.getWidth() != 128 ) {
            throw new IOException( "Input picture is not 64 / 128 pixel wide" );
        }

        if ( image.getHeight() == 128 || image.getHeight() == 64 || image.getHeight() == 32 ) {
            byte[] skinData = new byte[image.getHeight() == 128 ? SKIN_DATA_SIZE_FULL : image.getHeight() == 64 ? SKIN_DATA_SIZE_ALEX : SKIN_DATA_SIZE_STEVE];
            int cursor = 0;

            for ( int y = 0; y < image.getHeight(); y++ ) {
                for ( int x = 0; x < image.getWidth(); x++ ) {
                    int color = image.getRGB( x, y );
                    skinData[cursor++] = (byte) ( ( color >> 16 ) & 0xFF ); // R
                    skinData[cursor++] = (byte) ( ( color >> 8 ) & 0xFF );  // G
                    skinData[cursor++] = (byte) ( color & 0xFF );           // B
                    skinData[cursor++] = (byte) ( ( color >> 24 ) & 0xFF ); // A
                }
            }

            return new PlayerSkin( "Gomint_Skin", skinData, new byte[0], "geometry.humanoid.custom", GEOMETRY_CACHE.get( "geometry.humanoid.custom" ) );
        } else {
            throw new IOException( "Input picture is not 64 / 32 pixel high" );
        }
    }

    /**
     * Create a new empty skin
     *
     * @return empty skin
     */
    public static PlayerSkin emptySkin() {
        return new PlayerSkin( "Gomint_Skin", new byte[8192], new byte[0], "geometry.humanoid.custom", GEOMETRY_CACHE.get( "geometry.humanoid.custom" ) );
    }

}
