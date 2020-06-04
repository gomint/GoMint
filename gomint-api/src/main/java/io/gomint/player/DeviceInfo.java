package io.gomint.player;

import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class DeviceInfo {

    private final String deviceName;
    private final String deviceId;
    private final DeviceOS os;
    private final UI ui;

    /**
     * Information about the device of the player
     *
     * @param deviceOS   which the player is using
     * @param deviceName which the player is using
     * @param ui         which the player is using
     */
    public DeviceInfo( DeviceOS deviceOS, String deviceName, String deviceId, UI ui ) {
        this.os = deviceOS;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
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
         * MacOS, apple OS for mac computers
         */
        OSX,

        AMAZON,

        /**
         * Oculus gear-vr
         */
        GEAR_VR,

        /**
         * Microsoft hololens
         */
        HOLOLENS,

        /**
         * Windows x64
         */
        WINDOWS,

        /**
         * Windows x32
         */
        WINDOWS_32,

        DEDICATED,

        /**
         * Any tv supporting MCBE?
         */
        TVOS,

        /**
         * PS console by sony
         */
        PLAYSTATION,

        /**
         * Switch console by nintendo
         */
        NINTENDO,

        /**
         * XBOX console by microsoft
         */
        XBOX,

        /**
         * Windows Mobile, microsoft os for mobile phones
         */
        WINDOWS_PHONE
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
