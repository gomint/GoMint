package io.gomint.event.network;

import io.gomint.event.Event;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 *
 * This event gets fired when a unconnected Client pings the server. This can happen very often so care about putting
 * heavy calculation on this event since it may slow down other connections by a huge amount.
 */
public class PingEvent extends Event {

    private String motd;
    private int onlinePlayers;
    private int maxPlayers;

    public PingEvent( String motd, int onlinePlayers, int maxPlayers ) {
        this.motd = motd;
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Get the MOTD of this server
     *
     * @return MOTD of this server
     */
    public String getMotd() {
        return this.motd;
    }

    /**
     * Set a new MOTD for this ping
     *
     * @param motd to set
     */
    public void setMotd( String motd ) {
        this.motd = motd;
    }

    /**
     * Get the amount of users which should be shown
     *
     * @return amount of users
     */
    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    /**
     * Set the amount of users which should be shown
     *
     * @param onlinePlayers amount which should be shown
     */
    public void setOnlinePlayers( int onlinePlayers ) {
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers( int maxPlayers ) {
        this.maxPlayers = maxPlayers;
    }

}
