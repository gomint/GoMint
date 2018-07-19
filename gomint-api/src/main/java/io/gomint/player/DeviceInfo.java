package io.gomint.player;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class DeviceInfo {

    private final String deviceName;
    private final DeviceOS os;
    private final UI ui;

    /**
     * Information about the device of the player
     *
     * @param deviceOS   which the player is using
     * @param deviceName which the player is using
     * @param ui         which the player is using
     */
    public DeviceInfo( DeviceOS deviceOS, String deviceName, UI ui ) {
        this.os = deviceOS;
        this.deviceName = deviceName;
        this.ui = ui;
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

    public enum UI {
        /**
         * Classic UI with fixed sized chest inventories
         */
        CLASSIC,

        /**
         * Pocket UI which has a size flowed chest inventory
         */
        POCKET
    }

}
