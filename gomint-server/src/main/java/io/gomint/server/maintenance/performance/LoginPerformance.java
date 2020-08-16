package io.gomint.server.maintenance.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class LoginPerformance {

    private static final Logger LOGGER = LoggerFactory.getLogger( LoginPerformance.class );

    private long loginPacket;
    private long encryptionStart;
    private long encryptionEnd;
    private long resourceStart;
    private long resourceEnd;
    private long chunkStart;
    private long chunkEnd;

    public void print() {
        LOGGER.info( "Login performance: {} ms complete; {} ms encryption; {} ms resource pack; {} ms chunks",
            ( this.chunkEnd - this.loginPacket ), ( this.encryptionEnd - this.encryptionStart ),
            ( this.resourceEnd - this.resourceStart ), ( this.chunkEnd - this.chunkStart ) );
    }

    public void setLoginPacket(long loginPacket) {
        this.loginPacket = loginPacket;
    }

    public void setEncryptionStart(long encryptionStart) {
        this.encryptionStart = encryptionStart;
    }

    public void setEncryptionEnd(long encryptionEnd) {
        this.encryptionEnd = encryptionEnd;
    }

    public void setResourceStart(long resourceStart) {
        this.resourceStart = resourceStart;
    }

    public void setResourceEnd(long resourceEnd) {
        this.resourceEnd = resourceEnd;
    }

    public void setChunkStart(long chunkStart) {
        this.chunkStart = chunkStart;
    }

    public void setChunkEnd(long chunkEnd) {
        this.chunkEnd = chunkEnd;
    }

}
