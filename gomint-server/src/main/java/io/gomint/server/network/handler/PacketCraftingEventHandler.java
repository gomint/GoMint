package io.gomint.server.network.handler;

import io.gomint.event.player.PlayerCraftingEvent;
import io.gomint.inventory.item.ItemAir;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.crafting.Recipe;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketCraftingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketCraftingEventHandler implements PacketHandler<PacketCraftingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger( PacketCraftingEventHandler.class );

    @Override
    public void handle( PacketCraftingEvent packet, long currentTimeMillis, PlayerConnection connection ) {
        // Get the recipe based on its id
        Recipe recipe = connection.getEntity().getWorld().getServer().getRecipeManager().getRecipe( packet.getRecipeId() );
        if ( recipe == null ) {
            // Resend inventory and call it a day
            for ( ItemStack itemStack : connection.getEntity().getCraftingInputInventory().getContentsArray() ) {
                connection.getEntity().getInventory().addItem( itemStack );
            }

            connection.getEntity().getInventory().sendContents( connection );
            return;
        }

        // Generate lists of output and input
        List<ItemStack> packetOutput = new ArrayList<>( Arrays.asList( packet.getOutput() ) );

        // Generate a output stack for compare
        Collection<ItemStack> output = recipe.createResult();

        // Due to a bug in MC:PE it can happen that the recipe id is shit
        if ( output.size() != packet.getOutput().length ) {
            LOGGER.debug( "Output size does not match up" );
            recipe = connection.getEntity().getWorld().getServer().getRecipeManager().getRecipe( packetOutput );
        } else {
            Iterator<ItemStack> recipeSide = output.iterator();
            Iterator<ItemStack> packetSide = packetOutput.iterator();

            while ( recipeSide.hasNext() ) {
                ItemStack recipeOutput = recipeSide.next();
                ItemStack packetSingleOutput = packetSide.next();

                if ( !recipeOutput.equals( packetSingleOutput ) ) {
                    LOGGER.debug( "Packet wanted to get a recipe with different output" );
                    recipe = connection.getEntity().getWorld().getServer().getRecipeManager().getRecipe( packetOutput );
                    output = recipe.createResult();
                    break;
                }
            }
        }

        // Crafting types:
        // 0 => Small crafting window inside of the player inventory

        // If the crafting window is small you can't craft bigger recipes
        if ( packet.getRecipeType() == 0 && connection.getEntity().getCraftingInputInventory().size() > 4 ) {
            // Resend inventory and call it a day
            connection.getEntity().getInventory().sendContents( connection );
            LOGGER.debug( "Did not craft due to wrong inventory size" );
            return;
        }

        // Let the recipe check if it can complete
        int[] consumeSlots = recipe.isCraftable( connection.getEntity().getCraftingInputInventory() );
        boolean craftable = consumeSlots != null;
        if ( !craftable ) {
            // We can't craft => reset inventory
            for ( ItemStack inputItem : connection.getEntity().getCraftingInputInventory().getContentsArray() ) {
                connection.getEntity().getInventory().addItem( inputItem );
            }

            connection.getEntity().getInventory().sendContents( connection );
            connection.getEntity().getCraftingInputInventory().clear();

            LOGGER.debug( "Could not craft, recipe denied: {} -> {}", recipe.getClass().getName(), recipe.getIngredients() );
            return;
        }

        PlayerCraftingEvent event = new PlayerCraftingEvent( connection.getEntity(), recipe );
        connection.getEntity().getWorld().getServer().getPluginManager().callEvent( event );

        if ( event.isCancelled() ) {
            // We can't craft => reset inventory
            for ( ItemStack inputItem : connection.getEntity().getCraftingInputInventory().getContentsArray() ) {
                connection.getEntity().getInventory().addItem( inputItem );
            }

            connection.getEntity().getInventory().sendContents( connection );
            connection.getEntity().getCraftingInputInventory().clear();
            return;
        }

        // Consume items
        for ( int slot : consumeSlots ) {
            LOGGER.debug( "Consuming slot {}", slot );

            io.gomint.server.inventory.item.ItemStack itemStack = (io.gomint.server.inventory.item.ItemStack) connection.getEntity().getCraftingInputInventory().getItem( slot );
            LOGGER.debug( "ItemStack before {}", itemStack );
            if ( itemStack.afterPlacement() ) {
                LOGGER.debug( "Removing slot from crafting input" );
                connection.getEntity().getCraftingInputInventory().setItem( slot, ItemAir.create( 0 ) );
            } else {
                LOGGER.debug( "Remaining input at slot: {}", itemStack.getAmount() );
                connection.getEntity().getCraftingInputInventory().setItem( slot, itemStack );
            }
        }

        // We can craft this
        for ( ItemStack itemStack : output ) {
            connection.getEntity().getCraftingResultInventory().addItem( itemStack );
        }
    }

}
