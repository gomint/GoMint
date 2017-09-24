package io.gomint.server.inventory.item.generator;

import io.gomint.inventory.item.ItemStack;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface ItemGenerator {

    /**
     * Generate a new item of given type
     *
     * @param data   value of the items in this stack
     * @param amount of items in stack
     * @param nbt    additional data for this stack
     * @param <T>    generic type of item stack
     * @return generated item stack with all arguments used
     */
    <T extends ItemStack> T generate( short data, byte amount, NBTTagCompound nbt );

    /**
     * Generate a new item of given type
     *
     * @param data   value of the items in this stack
     * @param amount of items in stack
     * @param <T>    generic type of item stack
     * @return generated item stack with all arguments used
     */
    <T extends ItemStack> T generate( short data, byte amount );

}
