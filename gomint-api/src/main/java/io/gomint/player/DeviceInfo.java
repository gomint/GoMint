package io.gomint.player;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class DeviceInfo {

    private final String deviceName;
    private final DeviceOS os;

    /**
     * Information about the device of the player
     *
     * @param deviceOS which the player is using
     * @param deviceName which the player is using
     */
    public DeviceInfo( DeviceOS deviceOS, String deviceName ) {
        this.os = deviceOS;
        this.deviceName = deviceName;
    }

    public enum DeviceOS {
        /**
         * Android OS, can be tablet, phones or even tv sticks and other handhelds
         */
        ANDROID,

        /**
         * iOS, apple OS for iphones, ipads and some ipods
         */
        IOS,

        /**
         * Windows. What else?
         */
        WINDOWS
    }

}
