/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.util;

import java.awt.image.BufferedImage;

public class Picture {

    public static void copyToImage(BufferedImage image, int imageWidth, int imageHeight, byte [] data) {
        int cursor = 0;
        for ( int y = 0; y < imageHeight; y++ ) {
            for ( int x = 0; x < imageWidth; x++ ) {
                byte r = data[cursor++];
                byte g = data[cursor++];
                byte b = data[cursor++];
                byte a = data[cursor++];

                int rgbValue = ( ( a & 0xFF ) << 24 ) |
                    ( ( r & 0xFF ) << 16 ) |
                    ( ( g & 0xFF ) << 8 ) |
                    ( b & 0xFF );

                image.setRGB( x, y, rgbValue );
            }
        }
    }

}
