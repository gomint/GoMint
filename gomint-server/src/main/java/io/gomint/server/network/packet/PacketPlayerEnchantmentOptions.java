/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketPlayerEnchantmentOptions extends Packet {

    public static class Enchantment {
        private byte type;
        private byte level;

        public Enchantment(byte type, byte level) {
            this.type = type;
            this.level = level;
        }
    }

    public static class EnchantmentActivation {
        private List<Enchantment> enchantments;

        public EnchantmentActivation(List<Enchantment> enchantments) {
            this.enchantments = enchantments;
        }
    }

    public static class EnchantmentItem {
        private int slot;
        private List<EnchantmentActivation> enchantmentActivations;

        public EnchantmentItem(int slot, List<EnchantmentActivation> enchantmentActivations) {
            this.slot = slot;
            this.enchantmentActivations = enchantmentActivations;
        }
    }

    public static class EnchantmentOption {
        private int cost;
        private EnchantmentItem enchantmentItem;
        private String name;
        private int id;

        public EnchantmentOption(int cost, EnchantmentItem enchantmentItem, String name, int id) {
            this.cost = cost;
            this.enchantmentItem = enchantmentItem;
            this.name = name;
            this.id = id;
        }
    }

    private List<EnchantmentOption> options;

    /**
     * Construct a new packet
     */
    public PacketPlayerEnchantmentOptions() {
        super(Protocol.PACKET_PLAYER_ENCHANT_OPTIONS);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        if (this.options == null) {
            buffer.writeUnsignedVarInt(0);
            return;
        }

        buffer.writeUnsignedVarInt(this.options.size());
        for (EnchantmentOption option : this.options) {
            buffer.writeUnsignedVarInt(option.cost);
            buffer.writeInt(option.enchantmentItem.slot);

            for (EnchantmentActivation activation : option.enchantmentItem.enchantmentActivations) {
                if (activation.enchantments == null) {
                    buffer.writeUnsignedVarInt(0);
                    continue;
                }

                buffer.writeUnsignedVarInt(activation.enchantments.size());
                for (Enchantment enchantment : activation.enchantments) {
                    buffer.writeByte(enchantment.type);
                    buffer.writeByte(enchantment.level);
                }
            }

            buffer.writeString(option.name);
            buffer.writeUnsignedVarInt(option.id);
        }
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    public List<EnchantmentOption> getOptions() {
        return options;
    }

    public void setOptions(List<EnchantmentOption> options) {
        this.options = options;
    }

}
