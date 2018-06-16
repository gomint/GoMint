package io.gomint.event.player;

import io.gomint.crafting.Recipe;
import io.gomint.entity.EntityPlayer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PlayerCraftingEvent extends CancellablePlayerEvent {

    private final Recipe recipe;

    public PlayerCraftingEvent( EntityPlayer player, Recipe recipe ) {
        super( player );
        this.recipe = recipe;
    }

    /**
     * The recipe the player crafted
     *
     * @return recipe which has been crafted
     */
    public Recipe getRecipe() {
        return this.recipe;
    }

}
