/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet.types;

import io.gomint.jraknet.PacketBuffer;

/**
 * @author geNAZt
 */
public class InventoryCraftAction implements InventoryAction {

    private int recipeId;

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.recipeId = buffer.readUnsignedVarInt();
    }

    @Override
    public int weight() {
        return 10;
    }

    public int getRecipeId() {
        return recipeId;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"InventoryCraftAction\", " +
            "\"recipeId\":\"" + recipeId + "\"" +
            "}";
    }
}
