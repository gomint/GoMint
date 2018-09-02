package io.gomint.server.entity.passive;

import io.gomint.GoMint;
import io.gomint.entity.passive.EntityItemDrop;
import io.gomint.event.entity.EntityDamageEvent;
import io.gomint.event.player.PlayerPickupItemEvent;
import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.GoMintServer;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.entity.EntityType;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketAddItemEntity;
import io.gomint.server.network.packet.PacketPickupItemEntity;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.util.DumpUtil;
import io.gomint.server.util.Values;
import io.gomint.server.world.WorldAdapter;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Gamemode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 64 )
@ToString
public class EntityItem extends Entity implements EntityItemDrop {

    private ItemStack itemStack;
    @Getter
    private long pickupTime;
    private boolean isReset;

    private float lastUpdateDt;

    /**
     * Construct a new Entity
     *
     * @param itemStack The itemstack which should be dropped
     * @param world     The world in which this entity is in
     */
    public EntityItem( ItemStack itemStack, WorldAdapter world ) {
        super( EntityType.ITEM_DROP, world );
        this.itemStack = itemStack;
        this.initEntity();
    }

    /**
     * Create new entity item for API
     */
    public EntityItem() {
        super( EntityType.ITEM_DROP, null );
        this.initEntity();
    }

    private void initEntity() {
        this.setSize( 0.25f, 0.25f );
        setPickupDelay( 1000, TimeUnit.MILLISECONDS );


        GRAVITY = 0.04f;
        this.offsetY = 0.125f;
    }

    @Override
    public <T extends ItemStack> T getItemStack() {
        return (T) ( (io.gomint.server.inventory.item.ItemStack) this.itemStack ).clone();
    }

    @Override
    public void setItemStack( ItemStack itemStack ) {
        if ( this.world == null ) {
            this.itemStack = itemStack.clone();
        }
    }

    @Override
    public void setPickupDelay( long duration, TimeUnit timeUnit ) {
        this.pickupTime = ( (GoMintServer) GoMint.instance() ).getCurrentTickTime() + timeUnit.toMillis( duration );
    }

    @Override
    public void update( long currentTimeMS, float dT ) {
        // Entity base tick (movement)
        super.update( currentTimeMS, dT );

        this.lastUpdateDt += dT;
        if ( this.lastUpdateDt >= Values.CLIENT_TICK_RATE ) {
            if ( this.isCollided && !this.isReset && this.getVelocity().length() < 0.01f ) {
                this.setVelocity( Vector.ZERO ); // Reset velocity
                this.isReset = true;
            }

            if ( this.age > 6000 ) { // 5 Minutes
                this.despawn();
            }

            this.lastUpdateDt = 0;
        }
    }

    @Override
    protected void fall() {

    }

    @Override
    public boolean damage( EntityDamageEvent damageEvent ) {
        if ( damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FALL ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.FIRE ||
            damageEvent.getDamageSource() == EntityDamageEvent.DamageSource.ENTITY_EXPLODE ) {
            this.despawn();
            return true;
        }

        return false;
    }

    @Override
    public Packet createSpawnPacket( EntityPlayer receiver ) {
        PacketAddItemEntity packetAddItemEntity = new PacketAddItemEntity();
        packetAddItemEntity.setEntityId( this.getEntityId() );
        packetAddItemEntity.setItemStack( this.itemStack );
        packetAddItemEntity.setX( this.getPositionX() );
        packetAddItemEntity.setY( this.getPositionY() );
        packetAddItemEntity.setZ( this.getPositionZ() );
        packetAddItemEntity.setMotionX( this.getMotionX() );
        packetAddItemEntity.setMotionY( this.getMotionY() );
        packetAddItemEntity.setMotionZ( this.getMotionZ() );
        return packetAddItemEntity;
    }

    @Override
    public void onCollideWithPlayer( EntityPlayer player ) {
        // Check if we can pick it up
        if ( this.world.getServer().getCurrentTickTime() > this.getPickupTime() && !this.isDead() ) {
            // Check if we have place in out inventory to store this item
            if ( !player.getInventory().hasPlaceFor( this.getItemStack() ) ) {
                return;
            }

            // Ask the API is we can pickup
            PlayerPickupItemEvent event = new PlayerPickupItemEvent( player, this, this.getItemStack() );
            if ( player.getGamemode() == Gamemode.SPECTATOR ) {
                event.setCancelled( true );
            }

            this.world.getServer().getPluginManager().callEvent( event );

            if ( !event.isCancelled() ) {
                // Consume the item
                PacketPickupItemEntity packet = new PacketPickupItemEntity();
                packet.setItemEntityId( this.getEntityId() );
                packet.setPlayerEntityId( player.getEntityId() );

                for ( io.gomint.entity.EntityPlayer announcePlayer : this.world.getPlayers() ) {
                    if ( announcePlayer instanceof EntityPlayer ) {
                        ( (EntityPlayer) announcePlayer ).getConnection().addToSendQueue( packet );
                    }
                }

                // Manipulate inventory
                player.getInventory().addItem( this.getItemStack() );
                this.despawn();
            }
        }
    }

    @Override
    public boolean isMotionSendingEnabled() {
        return true;
    }

    @Override
    public void initFromNBT( NBTTagCompound compound ) {
        super.initFromNBT( compound );


        DumpUtil.dumpNBTCompund( compound );
    }

}
