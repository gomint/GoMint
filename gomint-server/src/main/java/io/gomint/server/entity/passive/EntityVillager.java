package io.gomint.server.entity.passive;

import io.gomint.entity.EntityPlayer;
import io.gomint.math.Vector;
import io.gomint.server.entity.Attribute;
import io.gomint.server.entity.EntityAgeable;
import io.gomint.server.entity.EntityTags;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;

import java.util.Set;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:villager" )
public class EntityVillager extends EntityAgeable<io.gomint.entity.passive.EntityVillager> implements io.gomint.entity.passive.EntityVillager {

    /**
     * Constructs a new EntityLiving
     *
     * @param world The world in which this entity is in
     */
    public EntityVillager( WorldAdapter world ) {
        super( EntityType.VILLAGER, world );
        this.initEntity();
    }

    /**
     * Create new entity villager for API
     */
    public EntityVillager() {
        super( EntityType.VILLAGER, null );
        this.initEntity();
    }

    private void initEntity() {
        this.eyeHeight = 1.62f;
        this.attribute(Attribute.HEALTH);
        this.maxHealth(20);
        this.health(20);
        this.profession(Profession.FARMER);
        if(this.baby()) {
            this.size(0.3f, 0.975f);
        }else{
            this.size(0.6f, 1.95f);
        }
    }

    @Override
    public void initFromNBT( NBTTagCompound compound ) {
        super.initFromNBT( compound );

        this.metadataContainer.putInt( MetadataContainer.DATA_VARIANT, compound.getInteger( "Variant", 0 ) );
    }

    @Override
    public NBTTagCompound persistToNBT() {
        return super.persistToNBT();
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        super.update( currentTimeMS, dT );
    }

    @Override
    public EntityVillager interact( EntityPlayer player, Vector clickVector ) {
        // TODO: Adding the ability of open the villager shop inventory for the player
        return this;
    }

    @Override
    public EntityVillager profession(Profession profession ) {
        int variant = 0;
        switch ( profession ) {
            case BUTCHER:
                variant = 4;
                break;
            case BLACKSMITH:
                variant = 3;
                break;
            case PRIEST:
                variant = 2;
                break;
            case LIBRARIAN:
                variant = 1;
                break;
            case FARMER:
            default:
                variant = 0;
        }

        this.metadataContainer.putInt( MetadataContainer.DATA_VARIANT, variant );
        return this;
    }

    @Override
    public Profession profession() {
        int variant = this.metadataContainer.getInt( MetadataContainer.DATA_VARIANT );
        switch ( variant ) {
            case 4:
                return Profession.BUTCHER;
            case 3:
                return Profession.BLACKSMITH;
            case 2:
                return Profession.PRIEST;
            case 1:
                return Profession.LIBRARIAN;
            case 0:
            default:
                return Profession.FARMER;
        }
    }

    @Override
    public Set<String> tags() {
        return EntityTags.CREATURE;
    }

}
