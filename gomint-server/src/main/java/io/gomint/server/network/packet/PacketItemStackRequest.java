/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.types.InventoryAction;
import io.gomint.server.network.packet.types.InventoryConsumeAction;
import io.gomint.server.network.packet.types.InventoryCraftAction;
import io.gomint.server.network.packet.types.InventoryCraftingResultAction;
import io.gomint.server.network.packet.types.InventoryDestroyCreativeAction;
import io.gomint.server.network.packet.types.InventoryDropAction;
import io.gomint.server.network.packet.types.InventoryGetCreativeAction;
import io.gomint.server.network.packet.types.InventoryMoveAction;
import io.gomint.server.network.packet.types.InventoryPlaceAction;
import io.gomint.server.network.packet.types.InventorySwapAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PacketItemStackRequest extends Packet {

    public static class Request {
        private int requestId;
        private List<InventoryAction> actions;

        public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
            this.requestId = buffer.readUnsignedVarInt();

            int amountOfActions = buffer.readUnsignedVarInt();
            this.actions = new ArrayList<>(amountOfActions);
            for (int j = 0; j < amountOfActions; j++) {
                // Read type
                InventoryAction action;
                byte type = buffer.readByte();
                switch (type) {
                    case 0:
                        action = new InventoryMoveAction();
                        break;
                    case 1:
                        action = new InventoryPlaceAction();
                        break;
                    case 2:
                        action = new InventorySwapAction();
                        break;
                    case 3:
                        action = new InventoryDropAction();
                        break;
                    case 4:
                        action = new InventoryDestroyCreativeAction();
                        break;
                    case 5:
                        action = new InventoryConsumeAction();
                        break;
                    case 9:
                    case 10:
                        action = new InventoryCraftAction();
                        break;
                    case 11:
                        action = new InventoryGetCreativeAction();
                        break;
                    case 13:
                        action = new InventoryCraftingResultAction();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }

                action.deserialize(buffer, protocolID);
                this.actions.add(action);
            }

            int amountOfWords = buffer.readUnsignedVarInt();
            List<String> wordToFilter = new ArrayList<>(amountOfWords);
            for (int i = 0; i < amountOfWords; i++) {
                wordToFilter.add(buffer.readString());
            }
        }

        public int getRequestId() {
            return requestId;
        }

        public List<InventoryAction> getActions() {
            return actions;
        }

        @Override
        public String toString() {
            return "Request{" +
                "requestId=" + requestId +
                ", actions=" + actions +
                '}';
        }
    }

    private List<Request> requests;

    /**
     * Construct a new packet
     */
    public PacketItemStackRequest() {
        super(Protocol.PACKET_ITEM_STACK_REQUEST);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        int amountOfRequests = buffer.readUnsignedVarInt();
        this.requests = new ArrayList<>(amountOfRequests);
        for (int i = 0; i < amountOfRequests; i++) {
            Request request = new Request();
            request.deserialize(buffer, protocolID);
            this.requests.add(request);
        }
    }

    public List<Request> getRequests() {
        return requests;
    }

    @Override
    public String toString() {
        return "PacketItemStackRequest{" +
            "requests=" + requests +
            '}';
    }

}
