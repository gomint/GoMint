package io.gomint.server.config;

import io.gomint.config.annotation.Comment;
import io.gomint.config.YamlConfig;
import io.gomint.world.generator.integrated.NormalGenerator;

/**
 * @author geNAZt
 * @version 1.0
 */
public class WorldConfig extends YamlConfig {

    @Comment( "Name of the world this affects" )
    private String name = "world";

    @Comment( "Name of the chunk generator for this world" )
    private String chunkGenerator = NormalGenerator.NAME;

    @Comment( "How many blocks should we update per tick using random reasons" )
    private int randomUpdatesPerTick = 3;

    @Comment( "Amount of Chunks which will always be loaded and stay loaded around the spawn area.\n" +
        "You can set this to 0 if you don't want to hold any Chunks in Memory but this also means\n" +
        "that you have to load the Chunks from disk everytime someone joins and the Chunk GC has cleared\n" +
        "the chunks. USE 0 WITH CAUTION!!!" )
    private int amountOfChunksForSpawnArea = 8;

    @Comment( "View distance of a player. This defines the amount of chunks sent to a player when he moves or spawns.\n" +
        "This is measured in Chunks." )
    private int viewDistance = 8;

    @Comment( "After how many seconds should a chunk be marked to be gced after the last player left the chunk." )
    private int secondsUntilGCAfterLastPlayerLeft = 300;

    @Comment( "Amount of seconds to wait after the chunk has come into the cache before we can select it for gc." )
    private int waitAfterLoadForGCSeconds = 120;

    @Comment( "Whether or not the world should be saved automatically on a regular basis" )
    private boolean autoSave = true;

    @Comment( "Save the world when it or its chunks get unloaded or the server shuts down?")
    private boolean saveOnUnload = true;

    @Comment( "The auto-save interval in which chunks should be saved automatically in milliseconds" )
    private int autoSaveInterval = 600000;

    @Comment( "Disabled random ticking?" )
    private boolean disableRandomTicking = false;

    @Comment( "Disabled chunk gc?" )
    private boolean disableChunkGC = false;

    public String name() {
        return this.name;
    }

    public String chunkGenerator() {
        return this.chunkGenerator;
    }

    public int randomUpdatesPerTick() {
        return this.randomUpdatesPerTick;
    }

    public int amountOfChunksForSpawnArea() {
        return this.amountOfChunksForSpawnArea;
    }

    public int viewDistance() {
        return this.viewDistance;
    }

    public int secondsUntilGCAfterLastPlayerLeft() {
        return this.secondsUntilGCAfterLastPlayerLeft;
    }

    public int waitAfterLoadForGCSeconds() {
        return this.waitAfterLoadForGCSeconds;
    }

    public boolean autoSave() {
        return this.autoSave;
    }

    public boolean saveOnUnload() {
        return this.saveOnUnload;
    }

    public int autoSaveInterval() {
        return this.autoSaveInterval;
    }

    public boolean disableRandomTicking() {
        return this.disableRandomTicking;
    }

    public boolean disableChunkGC() {
        return this.disableChunkGC;
    }

    @Override
    public String toString() {
        return "WorldConfig{" +
            "name='" + this.name + '\'' +
            ", chunkGenerator='" + this.chunkGenerator + '\'' +
            ", randomUpdatesPerTick=" + this.randomUpdatesPerTick +
            ", amountOfChunksForSpawnArea=" + this.amountOfChunksForSpawnArea +
            ", viewDistance=" + this.viewDistance +
            ", secondsUntilGCAfterLastPlayerLeft=" + this.secondsUntilGCAfterLastPlayerLeft +
            ", waitAfterLoadForGCSeconds=" + this.waitAfterLoadForGCSeconds +
            ", autoSave=" + this.autoSave +
            ", saveOnUnload=" + this.saveOnUnload +
            ", autoSaveInterval=" + this.autoSaveInterval +
            ", disableRandomTicking=" + this.disableRandomTicking +
            ", disableChunkGC=" + this.disableChunkGC +
            '}';
    }

}
