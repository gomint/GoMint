/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.player;

import io.gomint.player.AnimationFrame;
import io.gomint.server.util.Picture;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author BlackyPaw
 * @author HerryYT
 * @version 1.0
 */
public class PlayerSkin implements io.gomint.player.PlayerSkin {

    @Getter
    private static final GeometryCache GEOMETRY_CACHE = new GeometryCache();
    private static final int SKIN_DATA_SIZE_STEVE = 8192;
    private static final int SKIN_DATA_SIZE_ALEX = 16384;
    private static final int SKIN_DATA_SIZE_FULL = 65536;

    @Getter
    public class PieceTintColor {
        private final String pieceType;
        private List<String> colors;

        public PieceTintColor(JSONObject o) {
            this.pieceType = (String) o.get("PieceType");

            JSONArray colours = (JSONArray) o.get("Colors");
            if (colours.size() > 0) {
                this.colors = new ArrayList<>();
                for (Object o1 : colours) {
                    this.colors.add((String) o1);
                }
            }
        }
    }

    @Getter
    public class PersonaPiece {
        private final String pieceId;
        private final String pieceType;
        private final String packId;
        private final boolean defaultValue;
        private final String productId;

        public PersonaPiece(JSONObject o) {
            this.pieceId = (String) o.get( "PieceId" );
            this.pieceType = (String) o.get( "PieceType" );
            this.packId = (String) o.get( "PackId" );
            this.defaultValue = (boolean) o.get( "IsDefault" );
            this.productId = (String) o.get( "ProductId" );
        }
    }

    @Getter
    public class AnimationFrame implements io.gomint.player.AnimationFrame {
        private final float frames;
        private final int type;
        private final int height;
        private final int width;
        private final byte[] data;

        // Cacheables
        private SoftReference<BufferedImage> image;

        private AnimationFrame(JSONObject o) {
            this.frames = ((Double) o.get("Frames")).floatValue();
            this.type = Math.toIntExact((Long) o.get("Type"));
            this.height = Math.toIntExact((Long) o.get("ImageHeight"));
            this.width = Math.toIntExact((Long) o.get("ImageWidth"));
            this.data = Base64.getDecoder().decode((String) o.get("Image"));

            // Some sanity checks
            int size = this.width * this.height * 4;
            if (this.data.length != size) {
                throw new IllegalArgumentException("Invalid animation data buffer length: " + this.data.length);
            }
        }

        private BufferedImage createImage() {
            BufferedImage bufferedImage = (this.image != null) ? this.image.get() : null;
            if (bufferedImage != null) {
                return bufferedImage;
            }

            bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            this.image = new SoftReference<>(bufferedImage);

            Picture.copyToImage(bufferedImage, this.width, this.height, this.data);

            return bufferedImage;
        }

        @Override
        public void saveTo(OutputStream out) throws IOException {
            ImageIO.write(this.createImage(), "PNG", out);
        }
    }

    @Getter
    private final String id;
    @Getter
    private final String resourcePatch;
    @Getter
    private final int imageWidth;
    @Getter
    private final int imageHeight;
    @Getter
    private final byte[] data;
    @Getter
    private List<AnimationFrame> animations;
    @Getter
    private final int capeImageWidth;
    @Getter
    private final int capeImageHeight;
    @Getter
    private final byte[] capeData;
    @Getter
    private final String geometry;
    @Getter
    private final String animationData;
    @Getter
    private final boolean premium;
    @Getter
    private final boolean persona;
    @Getter
    private final boolean personaCapeOnClassic;
    @Getter
    private final String capeId;
    @Getter
    private final String fullId;
    @Getter
    private final String colour;
    @Getter
    private final String armSize;
    @Getter
    private List<PersonaPiece> personaPieces;
    @Getter
    private List<PieceTintColor> pieceTintColours;
    @Getter
    private final boolean trusted = true;  // Not sent in JWT, broken "feature"

    // Internal image caching
    private SoftReference<BufferedImage> image;

    public PlayerSkin(String id, String resourcePatch, int imageWidth, int imageHeight, byte[] data,
                      List<JSONObject> animations, int capeImageWidth, int capeImageHeight, byte[] capeData,
                      String geometry, String animationData, boolean premium, boolean persona,
                      boolean personaCapeOnClassic, String capeId, String colour, String armSize,
                      List<JSONObject> personaPieces, List<JSONObject> pieceTintColours) {
        // Some sanity checks
        int size = imageWidth * imageHeight * 4;
        if (data.length != size) {
            throw new IllegalArgumentException("Invalid skin data buffer length: " + data.length);
        }

        int capeSize = capeImageWidth * capeImageHeight * 4;
        if (capeData.length != capeSize) {
            throw new IllegalArgumentException("Invalid cape data buffer length: " + capeData.length);
        }

        // Now we need to check for the json stuff we got
        if (animations.size() > 0) {
            this.animations = new ArrayList<>();
            for (JSONObject object : animations) {
                this.animations.add(new AnimationFrame(object));
            }
        }

        if (personaPieces.size() > 0) {
            this.personaPieces = new ArrayList<>();
            for (JSONObject object : personaPieces) {
                this.personaPieces.add(new PersonaPiece(object));
            }
        }

        if (pieceTintColours.size() > 0) {
            this.pieceTintColours = new ArrayList<>();
            for (JSONObject object : pieceTintColours) {
                this.pieceTintColours.add(new PieceTintColor(object));
            }
        }

        this.id = id;
        this.resourcePatch = resourcePatch;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.data = data;
        this.capeImageWidth = capeImageWidth;
        this.capeImageHeight = capeImageHeight;
        this.capeData = capeData;
        this.geometry = geometry;
        this.animationData = animationData;
        this.premium = premium;
        this.persona = persona;
        this.personaCapeOnClassic = personaCapeOnClassic;
        this.capeId = capeId;
        this.fullId = id + capeId;  // Client doesn't send it, manually computed
        this.colour = colour;
        this.armSize = armSize;
    }

    private BufferedImage createImageFromSkinData() {
        BufferedImage bufferedImage = (this.image != null) ? this.image.get() : null;
        if (bufferedImage != null) {
            return bufferedImage;
        }

        bufferedImage = new BufferedImage(this.imageWidth, this.imageHeight, BufferedImage.TYPE_INT_ARGB);
        this.image = new SoftReference<>(bufferedImage);

        Picture.copyToImage(bufferedImage, this.imageWidth, this.imageHeight, this.data);

        return bufferedImage;
    }

    @Override
    public String getGeometryName() {
        return "";
        // return this.geometryName;
    }

    @Override
    public String getGeometryData() {
        return "";
        // return this.geometryData;
    }

    @Override
    public void saveSkinTo(OutputStream out) throws IOException {
        ImageIO.write(this.createImageFromSkinData(), "PNG", out);
    }

    /*
     * Create a skin from a given input stream
     *
     * @param inputStream which holds the data for this skin
     * @return skin which can be applied to entity human
     * @throws IOException when there was an error with the image
     */
    public static PlayerSkin fromInputStream(InputStream inputStream) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
        BufferedImage image = ImageIO.read(imageInputStream);
        if (image.getWidth() != 64 && image.getWidth() != 128) {
            throw new IOException("Input picture is not 64 / 128 pixel wide");
        }

        if (image.getHeight() == 128 || image.getHeight() == 64 || image.getHeight() == 32) {
            byte[] skinData = new byte[image.getHeight() == 128 ? SKIN_DATA_SIZE_FULL : image.getHeight() == 64 ? SKIN_DATA_SIZE_ALEX : SKIN_DATA_SIZE_STEVE];
            int cursor = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int color = image.getRGB(x, y);
                    skinData[cursor++] = (byte) ((color >> 16) & 0xFF); // R
                    skinData[cursor++] = (byte) ((color >> 8) & 0xFF);  // G
                    skinData[cursor++] = (byte) (color & 0xFF);           // B
                    skinData[cursor++] = (byte) ((color >> 24) & 0xFF); // A
                }
            }

            return null;
            // return new PlayerSkin( "Gomint_Skin", skinData, new byte[0], "geometry.humanoid.custom", GEOMETRY_CACHE.get( "geometry.humanoid.custom" ) );
        } else {
            throw new IOException("Input picture is not 64 / 32 pixel high");
        }
    }


    /*
     * Create a new empty skin
     *
     * @return empty skin
     */
    public static PlayerSkin emptySkin() {
        return null;
        // return new PlayerSkin( "Gomint_Skin", new byte[8192], new byte[0], "geometry.humanoid.custom", GEOMETRY_CACHE.get( "geometry.humanoid.custom" ) );
    }


}
