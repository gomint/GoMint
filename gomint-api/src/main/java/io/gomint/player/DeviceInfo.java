package io.gomint.player;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
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

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceOS getOs() {
        return os;
    }

    public UI getUi() {
        return ui;
    }

    public enum DeviceOS {
        /**
         * Android OS, can be tablet, phones or even tv sticks and other handhelds
         */
        ANDROID( 1 ),

        /**
         * iOS, apple OS for iphones, ipads and some ipods
         */
        IOS( 2 ),

        /**
         * MacOS, apple OS for mac computers
         */
        OSX( 3 ),

        /**
         * Amazon Fire, amazon tablet
         */
        AMAZON( 4 ),

        /**
         * Oculus gear-vr
         */
        GEAR_VR( 5 ),

        /**
         * Microsoft hololens
         */
        HOLOLENS( 6 ),

        /**
         * Windows x64
         */
        WINDOWS( 7 ),

        /**
         * Windows x32
         */
        WINDOWS_32( 8 ),

        /**
         * Not documented
         */
        DEDICATED( 9 ),

        /**
         * Any tv supporting MCBE?
         */
        TVOS( 10 ),

        /**
         * PS console by sony
         */
        PLAYSTATION( 11 ),

        /**
         * Switch console by nintendo
         */
        NINTENDO( 12 ),

        /**
         * XBOX console by microsoft
         */
        XBOX( 13 ),

        /**
         * Windows Mobile, microsoft os for mobile phones
         */
        WINDOWS_PHONE( 14 );

        private final int id;

        DeviceOS( int id ) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum UI {
        /**
         * Classic UI with fixed sized chest inventories
         */
        CLASSIC( 0 ),

        /**
         * Pocket UI which has a size flowed chest inventory
         */
        POCKET( 1 );

        UI( int id ) {
            this.id = id;
        }

        private final int id;

        public int getId() {
            return id;
        }
    }

}
