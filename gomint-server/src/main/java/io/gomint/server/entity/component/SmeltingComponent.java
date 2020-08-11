/*
 * Copyright (c) 2018 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.entity.component;

import io.gomint.GoMint;
import io.gomint.entity.Entity;
import io.gomint.server.GoMintServer;
import io.gomint.server.crafting.SmeltingRecipe;
import io.gomint.server.entity.tileentity.SerializationReason;
import io.gomint.server.inventory.ContainerInventory;
import io.gomint.server.inventory.Inventory;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.packet.PacketSetContainerData;
import io.gomint.taglib.NBTTagCompound;

import java.util.List;

public class SmeltingComponent extends AbstractTileEntityComponent {

    private static final int CONTAINER_PROPERTY_TICK_COUNT = 0;
    private static final int CONTAINER_PROPERTY_LIT_TIME = 1;
    private static final int CONTAINER_PROPERTY_LIT_DURATION = 2;

    private ContainerInventory inventory;

    private short cookTime;
    private short burnTime;
    private short burnDuration;

    private io.gomint.inventory.item.ItemStack output;

    public SmeltingComponent(ContainerInventory inventory, Items items) {
        super(items);

        this.inventory = inventory;
        this.inventory.addObserver( pair -> {
            if ( pair.getFirst() == 0 ) {
                // Input slot has changed
                onInputChanged( pair.getSecond() );
            }
        } );
    }

    private void onInputChanged( io.gomint.inventory.item.ItemStack input ) {
        // If we currently smelt reset progress
        if ( this.cookTime > 0 ) {
            this.cookTime = 0;

            for ( Entity viewer : this.inventory.getViewers() ) {
                if ( viewer instanceof io.gomint.server.entity.EntityPlayer ) {
                    this.sendTickProgress( (io.gomint.server.entity.EntityPlayer) viewer );
                }
            }
        }

        // Check for new recipe
        this.checkForRecipe( input );
    }

    private void sendTickProgress( io.gomint.server.entity.EntityPlayer player ) {
        byte windowId = player.getWindowId( this.inventory );

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_TICK_COUNT );
        containerData.setValue( this.cookTime );
        player.getConnection().addToSendQueue( containerData );
    }

    private void sendFuelInfo( io.gomint.server.entity.EntityPlayer player ) {
        byte windowId = player.getWindowId( this.inventory );

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_LIT_TIME );
        containerData.setValue( this.burnTime );
        player.getConnection().addToSendQueue( containerData );

        containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_LIT_DURATION );
        containerData.setValue( this.burnDuration );
        player.getConnection().addToSendQueue( containerData );
    }

    private void sendDataProperties( io.gomint.server.entity.EntityPlayer player ) {
        byte windowId = player.getWindowId( this.inventory );

        PacketSetContainerData containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_TICK_COUNT );
        containerData.setValue( this.cookTime );
        player.getConnection().addToSendQueue( containerData );

        containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_LIT_TIME );
        containerData.setValue( this.burnTime );
        player.getConnection().addToSendQueue( containerData );

        containerData = new PacketSetContainerData();
        containerData.setWindowId( windowId );
        containerData.setKey( CONTAINER_PROPERTY_LIT_DURATION );
        containerData.setValue( this.burnDuration );
        player.getConnection().addToSendQueue( containerData );
    }

    @Override
    public void toCompound(NBTTagCompound compound, SerializationReason reason) {
        List<Object> itemCompounds = compound.getList( "Items", false );
        if ( itemCompounds != null ) {
            for ( Object itemCompound : itemCompounds ) {
                NBTTagCompound cd = (NBTTagCompound) itemCompound;

                byte slot = cd.getByte( "Slot", (byte) -1 );
                if ( slot == -1 ) {
                    this.inventory.addItem( getItemStack( cd ) );
                } else {
                    this.inventory.setItem( slot, getItemStack( cd ) );

                    if ( slot == 0 ) {
                        checkForRecipe( this.inventory.getItem( 0 ) );
                    }
                }
            }
        }

        this.cookTime = compound.getShort( "CookTime", (short) 0 );
        this.burnTime = compound.getShort( "BurnTime", (short) 0 );
        this.burnDuration = compound.getShort( "BurnDuration", (short) 0 );
    }

    private void checkForRecipe( io.gomint.inventory.item.ItemStack input ) {
        // Reset just to be sure that the new item needs to have a new recipe
        this.output = null;

        // Check if there is a smelting recipe present
        GoMintServer server = (GoMintServer) GoMint.instance();
        SmeltingRecipe recipe = server.getRecipeManager().getSmeltingRecipe( input );
        if ( recipe != null ) {
            for ( io.gomint.inventory.item.ItemStack stack : recipe.createResult() ) {
                this.output = stack; // Smelting only has one result
            }
        }
    }

    @Override
    public void fromCompound(NBTTagCompound compound) {

    }

    @Override
    public void update(long currentTimeMS, float dT) {

    }

}
