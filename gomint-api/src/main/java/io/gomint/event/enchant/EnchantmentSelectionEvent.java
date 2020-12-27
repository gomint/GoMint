package io.gomint.event.enchant;

import io.gomint.enchant.Enchantment;
import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerEvent;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 *
 * This event gets fired when a enchantment table needs to select new enchants (due to a player
 * putting a item in it for example). You can modify the list of enchants, this also alters the
 * output of the enchantment being applied later when the {@link ItemEnchantmentEvent} gets fired.
 */
public class EnchantmentSelectionEvent extends PlayerEvent {

    private final List<List<Enchantment>> enchantments;
    private final int[] requiredLevels;

    public EnchantmentSelectionEvent(EntityPlayer player,
                                     List<List<Enchantment>> enchantments,
                                     int[] requiredLevels) {
        super(player);
        this.enchantments = enchantments;
        this.requiredLevels = requiredLevels;
    }

    /**
     * The preselected list of enchantments. This list is unmodifiable in itself since it needs to be
     * exactly the same length as the client displays options (currently three). You can modify the enchantments
     * in the option itself (.get(0).add(
     *
     * @return
     */
    public List<List<Enchantment>> getEnchantments() {
        return Collections.unmodifiableList(enchantments);
    }

}
