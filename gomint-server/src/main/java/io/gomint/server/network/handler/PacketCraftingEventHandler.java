package io.gomint.server.network.handler;

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
            for ( ItemStack itemStack : connection.getEntity().getCraftingInputInventory().getContents() ) {
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
            return;
        }

        // Now we have to look if we have the correct items
        ItemStack[] inputItems = connection.getEntity().getCraftingInputInventory().getContents();
        boolean craftable = true;
        for ( ItemStack recipeWanted : recipe.getIngredients() ) {
            boolean found = false;
            for ( ItemStack input : inputItems ) {
                int recipeMaterial = ( (io.gomint.server.inventory.item.ItemStack) recipeWanted ).getMaterial();
                int inputMaterial = ( (io.gomint.server.inventory.item.ItemStack) input ).getMaterial();

                if ( recipeMaterial == inputMaterial &&
                        ( recipeWanted.getData() == -1 || recipeWanted.getData() == input.getData() ) ) {
                    found = true;
                    break;
                }
            }

            if ( !found ) {
                craftable = false;
                break;
            }
        }

        // Calculate the items we need to consume
        List<ItemStack> available = new ArrayList<>( Arrays.asList( inputItems ) );
        List<ItemStack> consume = new ArrayList<>();

        // We need to consume every item in there
        for ( ItemStack neededToConsume : packet.getInput() ) {
            boolean didConsume = false;
            for ( ItemStack canConsume : available ) {
                int needToConsumeMaterial = ( (io.gomint.server.inventory.item.ItemStack) neededToConsume ).getMaterial();
                int canConsumeMaterial = ( (io.gomint.server.inventory.item.ItemStack) canConsume ).getMaterial();

                if ( needToConsumeMaterial == canConsumeMaterial &&
                        ( neededToConsume.getData() == -1 || neededToConsume.getData() == canConsume.getData() ) ) {
                    if ( canConsume.getAmount() > 0 ) {
                        canConsume.setAmount( canConsume.getAmount() - 1 );

                        boolean didFindAlreadyConsumed = false;
                        for ( ItemStack alreadyConsumed : consume ) {
                            if ( alreadyConsumed.equals( canConsume ) ) {
                                alreadyConsumed.setAmount( alreadyConsumed.getAmount() + 1 );
                                didFindAlreadyConsumed = true;
                                break;
                            }
                        }

                        if ( !didFindAlreadyConsumed ) {
                            ItemStack canConsumeClone = ( (io.gomint.server.inventory.item.ItemStack) canConsume ).clone();
                            canConsumeClone.setAmount( 1 );
                            consume.add( canConsumeClone );
                        }

                        didConsume = true;
                        break;
                    }
                }
            }

            if ( !didConsume ) {
                craftable = false;
                break;
            }
        }

        // TODO: Event to cancel the crafting if needed


        // We can craft this
        if ( craftable ) {
            for ( ItemStack itemStack : output ) {
                connection.getEntity().getCraftingResultInventory().addItem( itemStack );
            }

            // Reset the inventory
            connection.getEntity().getCraftingInputInventory().clear();
        } else {
            // We can't craft => reset inventory
            for ( ItemStack inputItem : inputItems ) {
                connection.getEntity().getInventory().addItem( inputItem );
            }

            connection.getEntity().getInventory().sendContents( connection );
            connection.getEntity().getCraftingInputInventory().clear();
        }
    }

}
