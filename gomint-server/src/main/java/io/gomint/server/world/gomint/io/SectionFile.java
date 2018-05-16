/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.gomint.io;

import net.jpountz.lz4.LZ4FrameInputStream;
import net.jpountz.lz4.LZ4FrameOutputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * @author geNAZt
 * @version 1.0
 */
public class SectionFile implements AutoCloseable {

    private static Field EXPECTED_CONTENT_SIZE_FIELD;

    static {
        try {
            EXPECTED_CONTENT_SIZE_FIELD = LZ4FrameInputStream.class.getDeclaredField( "expectedContentSize" );
            EXPECTED_CONTENT_SIZE_FIELD.setAccessible( true );
        } catch ( NoSuchFieldException e ) {
            e.printStackTrace();
        }
    }

    private File file;

    // Cached streams
    private ByteBuffer readBuffer;
    private DataOutputStream outputStream;

    // Write buffer
    private ByteArrayOutputStream writeBuffer;

    /**
     * Construct new section based file
     *
     * @param file which is used to read or write sections to
     */
    public SectionFile( File file ) {
        this.file = file;
    }

    /**
     * Create a file output which we can write to
     *
     * @return stream to write into the file
     * @throws IOException when there is an error creating the file
     */
    public DataOutputStream getOutputStream() throws IOException {
        if ( this.outputStream != null ) {
            return this.outputStream;
        }

        if ( !this.file.exists() ) {
            this.file.createNewFile();
        }

        return this.outputStream = new DataOutputStream( this.writeBuffer = new ByteArrayOutputStream() );
    }

    /**
     * Create a file input which we can read from
     *
     * @return stream to read from the file
     * @throws IOException when there is an error opening the file
     */
    public ByteBuffer getReadBuffer() throws IOException {
        // Return cached buffer
        if ( this.readBuffer != null ) {
            return this.readBuffer;
        }

        // Read file, uncompress and store in buffer
        try ( SeekableByteChannel channel = Files.newByteChannel( this.file.toPath(), StandardOpenOption.READ );
              InputStream fileInput = Channels.newInputStream( channel ) ) {
            LZ4FrameInputStream lz4In = new LZ4FrameInputStream( fileInput );

            // I don't know why but for some reason expectedContentSize is private in LZ4
            int expectedSize = (int) EXPECTED_CONTENT_SIZE_FIELD.getLong( lz4In );

            this.readBuffer = ByteBuffer.allocateDirect( expectedSize );

            byte[] buf = new byte[expectedSize];
            int read;
            while ( ( read = lz4In.read( buf ) ) != -1 ) {
                this.readBuffer.put( Arrays.copyOf( buf, read ) );
            }

            this.readBuffer.position( 0 );
        } catch ( IllegalAccessException e ) {
            e.printStackTrace();
        }

        return this.readBuffer;
    }

    /**
     * Create new section. This needs to be written in one session and closed before opening another section.
     *
     * @return new section to write data to
     * @throws IOException when something goes wrong with creating / opening files
     */
    public Section createSection() throws IOException {
        return new Section( this, SectionMode.WRITE );
    }

    public Section getSection() throws IOException {
        return new Section( this, SectionMode.READ );
    }

    @Override
    public void close() throws IOException {
        if ( this.outputStream != null ) {
            // Attach a empty section
            this.createSection().close();
            this.outputStream.close();

            byte[] data = this.writeBuffer.toByteArray();
            try ( LZ4FrameOutputStream fileWriter = new LZ4FrameOutputStream( new FileOutputStream( this.file ),
                LZ4FrameOutputStream.BLOCKSIZE.SIZE_256KB, data.length, LZ4FrameOutputStream.FLG.Bits.BLOCK_INDEPENDENCE,
                LZ4FrameOutputStream.FLG.Bits.CONTENT_SIZE ) ) {
                fileWriter.write( data );
            }
        }
    }

}
