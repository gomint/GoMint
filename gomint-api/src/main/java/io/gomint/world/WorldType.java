package io.gomint.world;

/**
 * @author geNAZt
 * @version 1.0
 */
public enum WorldType {

    /**
     * Anvil worlds are NOT compatible with the PC / Java Edition of this game. If you create worlds in Anvil
     * format they only work in other server softwares like MiNet, Nukkit or PMMP
     */
    ANVIL,

    /**
     * LevelDB worlds will be compatible with the client and maybe other server software, depends on if they support
     * the normal PE leveldb format
     */
    LEVELDB,

    /**
     *
     */
    GOMINT,

}
