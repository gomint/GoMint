/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.gomint.io;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class Section implements AutoCloseable {

    private SectionFile file;

    private ByteBuffer in;
    private DataOutputStream out;

    private ByteArrayOutputStream bOut;

    public Section( SectionFile file, SectionMode mode ) throws IOException {
        this.file = file;

        switch ( mode ) {
            case SKIP:
            case READ:
                // We need to read the section length
                ByteBuffer in = file.getReadBuffer();
                int sectionLength = in.getInt();

                if ( mode == SectionMode.SKIP ) {
                    in.position( in.position() + sectionLength );
                } else {
                    byte[] readData = new byte[sectionLength];
                    in.get( readData );
                    this.in = ByteBuffer.allocateDirect( readData.length );
                    this.in.put( readData );
                    this.in.position( 0 );
                }

                break;
            case WRITE:
                this.bOut = new ByteArrayOutputStream();
                this.out = new DataOutputStream( this.bOut );
        }
    }

    /**
     * Get the write side of this section
     *
     * @return write stream
     */
    public DataOutputStream getOutput() {
        return this.out;
    }

    public ByteBuffer getInput() {
        return this.in;
    }

    @Override
    public void close() throws IOException {
        if ( this.out != null ) {
            this.out.close();
            this.bOut.close();

            // Write to file
            DataOutputStream out = this.file.getOutputStream();
            out.writeInt( this.bOut.size() );
            out.write( this.bOut.toByteArray() );
        }
    }

}
