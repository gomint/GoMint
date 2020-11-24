/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.enchant;

import io.gomint.event.player.PlayerEnchantItemEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemType;
import io.gomint.math.BlockPosition;
import io.gomint.server.entity.EntityPlayer;
import io.gomint.server.inventory.item.ItemStack;
import io.gomint.server.network.packet.PacketInventoryTransaction;
import io.gomint.taglib.NBTTagCompound;
import io.gomint.world.Gamemode;
import io.gomint.world.World;
import io.gomint.world.block.BlockType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * <p>
 * This class handles some safety for enchantment tables. Since 1.2+ the server doesn't get data for the
 * enchanter (no enchantment list) or can't set those we need to collect all data around that and check if it
 * is somewhat in bounds.
 * <p>
 * For that i know that the client sends a PlayerAction with ID 20 first (which sets the new seed for the randomizer we don't have),
 * after that we get some inventory transactions with windowId -15 and -17 (those contain the enchantment items) and the
 * last packet is a EntityEvent with ID 34 (enchanted) with data of the missing levels (-1,-2,-3)
 */
public class EnchantmentProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger( EnchantmentProcessor.class );

    private final EntityPlayer player;
    private ItemStack startItem;
    private ItemStack lapisItem;
    private List<Enchantment> enchantments;
    private short data;

    public EnchantmentProcessor(EntityPlayer player) {
        this.player = player;
    }

    private void check() {
        if ( this.player.getGamemode() != Gamemode.CREATIVE && ( this.data < 1 || this.data > 3 ) ) {
            this.reset();
            return;
        }

        if ( this.lapisItem == null || this.startItem.getEnchantAbility() == 0 ||
            this.lapisItem.getItemType() != ItemType.DYE || this.lapisItem.getData() != 4 ) {
            this.reset();
            return;
        }

        if ( this.player.getGamemode() != Gamemode.CREATIVE ) {
            BlockPosition position = this.player.getEnchantmentInputInventory().getContainerPosition();
            int bookShelves = this.findBookshelves( position );

            // Calculate enchant ability of the item
            int itemEnchantable = 3 + ( ( this.startItem.getEnchantAbility() >> 1 ) * 2 );
            int itemEnchantableMin = 1;
            int bookEnchantable = 25 + ( bookShelves >> 1 );
            int bookEnchantableMin = 1 + ( bookShelves >> 1 );

            /* Check for iteration differences:
             * 0: book / 3; max 1
             * 1: book * 2 / 3 + 1
             * 2: book; max bookShelves * 2
             */
            switch ( this.data - 1 ) {
                case 0:
                    bookEnchantable = Math.max( bookEnchantable / 3, 1 );
                    bookEnchantableMin = Math.max( bookEnchantableMin / 3, 1 );
                    break;
                case 1:
                    bookEnchantable = bookShelves * 2 / 3 + 1;
                    bookEnchantableMin = bookShelves * 2 / 3 + 1;
                    break;
                case 2:
                    bookEnchantable = Math.max( bookEnchantable, bookShelves * 2 );
                    bookEnchantableMin = Math.max( bookEnchantableMin, bookShelves * 2 );
                    break;
                default:
                    this.reset();
                    return;
            }

            int totalEnchantAbility = itemEnchantable + bookEnchantable;
            int totalEnchantAbilityMin = itemEnchantableMin + bookEnchantableMin;

            // Calculate needed enchant ability of this enchantments
            for ( Enchantment enchantment : this.enchantments ) {
                if ( !enchantment.canBeApplied( this.startItem ) ) {
                    this.reset();
                    return;
                }

                byte min = enchantment.getMinEnchantAbility( enchantment.getLevel() );
                byte max = enchantment.getMaxEnchantAbility( enchantment.getLevel() );
                if ( min > totalEnchantAbility ) {
                    this.reset();
                    return;
                }

                LOGGER.debug( "Needed ability: {} -> {}; having: {} -> {}", min, max, totalEnchantAbilityMin, totalEnchantAbility );
            }
        }

        // Remap
        List<io.gomint.enchant.Enchantment> apiEnchantments = new ArrayList<>( this.enchantments );

        // Event for enchantment
        PlayerEnchantItemEvent event = new PlayerEnchantItemEvent( this.player, this.startItem, apiEnchantments, this.data, this.data );
        this.player.getWorld().getServer().getPluginManager().callEvent( event );

        if ( event.isCancelled() ) {
            this.reset();
        } else {
            // Remove amount of lapis and level
            if ( this.lapisItem.getAmount() < this.data && this.player.getGamemode() != Gamemode.CREATIVE ) {
                this.reset();
            } else {
                if ( this.player.getLevel() < this.data && this.player.getGamemode() != Gamemode.CREATIVE ) {
                    this.reset();
                } else {
                    if ( this.player.getGamemode() != Gamemode.CREATIVE ) {
                        this.lapisItem.setAmount( this.lapisItem.getAmount() - this.data );
                        if ( this.lapisItem.getAmount() == 0 ) {
                            this.player.getEnchantmentInputInventory().setItem( 1, ItemAir.create( 0 ) );
                        } else {
                            this.player.getEnchantmentInputInventory().setItem( 1, this.lapisItem );
                        }

                        this.player.setLevel( this.player.getLevel() - this.data );
                    }

                    // Enchant the start item
                    for ( Enchantment enchantment : this.enchantments ) {
                        LOGGER.debug( "Adding enchant: {} lvl: {} to item", enchantment.getClass().getName(), enchantment.getLevel() );
                        this.startItem.addEnchantment( enchantment.getClass(), enchantment.getLevel() );
                    }

                    this.player.getEnchantmentInputInventory().setItem( 0, this.startItem );

                    // Remove the processor
                    this.player.setEnchantmentProcessor( null );
                }
            }
        }
    }

    private int findBookshelves( BlockPosition position ) {
        int foundShelves = 0;

        World world = this.player.getWorld();
        for ( int z = -1; z <= 1; ++z ) {
            for ( int x = -1; x <= 1; ++x ) {
                if ( ( z != 0 || x != 0 ) &&
                    world.getBlockAt( position.add( x, 0, z ) ).getBlockType() == BlockType.AIR &&
                    world.getBlockAt( position.add( x, 1, z ) ).getBlockType() == BlockType.AIR ) {
                    if ( world.getBlockAt( position.add( x * 2, 0, z * 2 ) ).getBlockType() == BlockType.BOOKSHELF ) {
                        foundShelves++;
                    }

                    if ( world.getBlockAt( position.add( x * 2, 1, z * 2 ) ).getBlockType() == BlockType.BOOKSHELF ) {
                        foundShelves++;
                    }

                    if ( x != 0 && z != 0 ) {
                        if ( world.getBlockAt( position.add( x * 2, 0, z ) ).getBlockType() == BlockType.BOOKSHELF ) {
                            foundShelves++;
                        }

                        if ( world.getBlockAt( position.add( x * 2, 1, z ) ).getBlockType() == BlockType.BOOKSHELF ) {
                            foundShelves++;
                        }

                        if ( world.getBlockAt( position.add( x, 0, z * 2 ) ).getBlockType() == BlockType.BOOKSHELF ) {
                            foundShelves++;
                        }

                        if ( world.getBlockAt( position.add( x, 1, z * 2 ) ).getBlockType() == BlockType.BOOKSHELF ) {
                            foundShelves++;
                        }
                    }
                }
            }
        }

        return foundShelves;
    }

    /**
     * Entity event data
     *
     * @param data level of enchantment
     */
    public void checkEntityEvent( short data ) {
        this.data = data;
    }

    private void reset() {
        // Reset inventory
        this.player.getEnchantmentInputInventory().setItem( 0, this.startItem );
        this.player.getEnchantmentInputInventory().setItem( 1, this.lapisItem );

        // Reset level
        this.player.sendData( this.player );
    }

    /**
     * Add a new inventory transaction
     *
     * @param inventoryTransaction to add
     */
    public void addTransaction( PacketInventoryTransaction inventoryTransaction ) {
        // We should already have lapis in the enchanter
        this.lapisItem = (ItemStack) this.player.getEnchantmentInputInventory().getItem( 1 );

        for ( PacketInventoryTransaction.NetworkTransaction networkTransaction : inventoryTransaction.getActions() ) {
            if ( networkTransaction.getWindowId() == -15 ) {
                if ( networkTransaction.getOldItem().getItemType() != ItemType.AIR &&
                    !isEnchanted( (ItemStack) networkTransaction.getOldItem() ) ) {
                    this.startItem = (ItemStack) networkTransaction.getOldItem();
                } else if ( networkTransaction.getNewItem().getItemType() != ItemType.AIR &&
                    isEnchanted( (ItemStack) networkTransaction.getNewItem() ) ) {
                    io.gomint.server.inventory.item.ItemStack serverItemStack = (io.gomint.server.inventory.item.ItemStack) networkTransaction.getNewItem();

                    // Extract enchantments
                    List<Object> enchants = serverItemStack.getNbtData().getList( "ench", false );
                    this.enchantments = new ArrayList<>();
                    for ( Object enchant : enchants ) {
                        NBTTagCompound enchantCompound = (NBTTagCompound) enchant;
                        Enchantment enchantment = this.player.getWorld().getServer().getEnchantments().create(
                            enchantCompound.getShort( "id", (short) 0 ),
                            enchantCompound.getShort( "lvl", (short) 0 )
                        );

                        this.enchantments.add( enchantment );
                    }
                }
            }
        }

        this.check();
    }

    private boolean isEnchanted( ItemStack itemStack ) {
        // We need a NBT tag
        if ( itemStack.getNbtData() == null ) {
            return false;
        }

        // Check if there is a ench list compound
        return itemStack.getNbtData().getList( "ench", false ) != null;
    }

}
