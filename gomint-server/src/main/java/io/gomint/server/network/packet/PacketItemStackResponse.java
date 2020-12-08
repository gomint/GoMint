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
import io.gomint.server.network.packet.types.InventoryMoveAction;
import io.gomint.server.network.packet.types.InventoryPlaceAction;
import io.gomint.server.network.packet.types.InventorySwapAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacketItemStackResponse extends Packet {

    public enum ResponseResult {
        Success( (byte) 0 ),
        Error( (byte) 1 ),
        InvalidRequestActionType( (byte) 2 ),
        ActionRequestNotAllowed( (byte) 3 ),
        ScreenHandlerEndRequestFailed( (byte) 4 ),
        ItemRequestActionHandlerCommitFailed( (byte) 5 ),
        InvalidRequestCraftActionType( (byte) 6 ),
        InvalidCraftRequest( (byte) 7 ),
        InvalidCraftRequestScreen( (byte) 8 ),
        InvalidCraftResult( (byte) 9 ),
        InvalidCraftResultIndex( (byte) 10 ),
        InvalidCraftResultItem( (byte) 11 ),
        InvalidItemNetId( (byte) 12 ),
        MissingCreatedOutputContainer( (byte) 13 ),
        FailedToSetCreatedItemOutputSlot( (byte) 14 ),
        RequestAlreadyInProgress( (byte) 15 ),
        FailedToInitSparseContainer( (byte) 16 ),
        ResultTransferFailed( (byte) 17 ),
        ExpectedItemSlotNotFullyConsumed( (byte) 18 ),
        ExpectedAnywhereItemNotFullyConsumed( (byte) 19 ),
        ItemAlreadyConsumedFromSlot( (byte) 20 ),
        ConsumedTooMuchFromSlot( (byte) 21 ),
        MismatchSlotExpectedConsumedItem( (byte) 22 ),
        MismatchSlotExpectedConsumedItemNetIdVariant( (byte) 23 ),
        FailedToMatchExpectedSlotConsumedItem( (byte) 24 ),
        FailedToMatchExpectedAllowedAnywhereConsumedItem( (byte) 25 ),
        ConsumedItemOutOfAllowedSlotRange( (byte) 26 ),
        ConsumedItemNotAllowed( (byte) 27 ),
        PlayerNotInCreativeMode( (byte) 28 ),
        InvalidExperimentalRecipeRequest( (byte) 29 ),
        FailedToCraftCreative( (byte) 30 ),
        FailedToGetLevelRecipe( (byte) 31 ),
        FailedToFindReceiptByNetId( (byte) 32 ),
        MismatchedCraftingSize( (byte) 33 ),
        MissingInputSparseContainer( (byte) 34 ),
        MismatchedRecipeForInputGridItems( (byte) 35 ),
        EmptyCraftResults( (byte) 36 ),
        FailedToEnchant( (byte) 37 ),
        MissingInputItem( (byte) 38 ),
        InsufficientPlayerLevelToEnchant( (byte) 39 ),
        MissingMaterialItem( (byte) 40 ),
        MissingActor( (byte) 41 ),
        UnknownPrimaryEffect( (byte) 42 ),
        PrimaryEffectOutOfRange( (byte) 43 ),
        PrimaryEffectUnavailable( (byte) 44 ),
        SecondaryEffectOutOfRange( (byte) 45 ),
        SecondaryEffectUnavailable( (byte) 46 ),
        DstContainerEqualToCreatedOutputContainer( (byte) 47 ),
        DstContainerAndSlotEqualToSrcContainerAndSlot( (byte) 48 ),
        FailedToValidateSrcSlot( (byte) 49 ),
        FailedToValidateDstSlot( (byte) 50 ),
        InvalidAdjustedAmount( (byte) 51 ),
        InvalidItemSetType( (byte) 52 ),
        InvalidTransferAmount( (byte) 53 ),
        CannotSwapItem( (byte) 54 ),
        CannotPlaceItem( (byte) 55 ),
        UnhandledItemSetType( (byte) 56 ),
        InvalidRemovedAmount( (byte) 57 ),
        InvalidRegion( (byte) 58 ),
        CannotDropItem( (byte) 59 ),
        CannotDestroyItem( (byte) 60 ),
        InvalidSourceContainer( (byte) 61 ),
        ItemNotConsumed( (byte) 62 ),
        InvalidNumCrafts( (byte) 63 ),
        InvalidCraftResultStackSize( (byte) 64 ),
        CannotRemoveItem( (byte) 65 ),
        CannotConsumeItem( (byte) 66 );

        private final byte result;
        ResponseResult(byte result) {
            this.result = result;
        }
    }

    public static class StackResponseSlotInfo {
        private byte slot;
        private byte count;
        private int itemStackId;

        public StackResponseSlotInfo(byte slot, byte count, int itemStackId) {
            this.slot = slot;
            this.count = count;
            this.itemStackId = itemStackId;
        }

        @Override
        public String toString() {
            return "{\"_class\":\"StackResponseSlotInfo\", " +
                "\"slot\":\"" + slot + "\"" + ", " +
                "\"count\":\"" + count + "\"" + ", " +
                "\"itemStackId\":\"" + itemStackId + "\"" +
                "}";
        }
    }

    public static class StackResponseContainerInfo {
        private byte windowId;
        private List<StackResponseSlotInfo> slotInfos;

        public StackResponseContainerInfo(byte windowId, List<StackResponseSlotInfo> slotInfos) {
            this.windowId = windowId;
            this.slotInfos = slotInfos;
        }

        @Override
        public String toString() {
            return "{\"_class\":\"StackResponseContainerInfo\", " +
                "\"windowId\":\"" + windowId + "\"" + ", " +
                "\"slotInfos\":" + (slotInfos == null ? "null" : Arrays.toString(slotInfos.toArray())) +
                "}";
        }
    }
    
    public static class Response {
        private ResponseResult result;
        private int requestId;
        private List<StackResponseContainerInfo> containerInfos;

        public Response(ResponseResult result, int requestId, List<StackResponseContainerInfo> containerInfos) {
            this.result = result;
            this.requestId = requestId;
            this.containerInfos = containerInfos;
        }

        @Override
        public String toString() {
            return "{\"_class\":\"Response\", " +
                "\"result\":" + (result == null ? "null" : result) + ", " +
                "\"requestId\":\"" + requestId + "\"" + ", " +
                "\"containerInfos\":" + (containerInfos == null ? "null" : Arrays.toString(containerInfos.toArray())) +
                "}";
        }
    }

    private List<Response> responses;

    /**
     * Construct a new packet
     */
    public PacketItemStackResponse() {
        super(Protocol.PACKET_ITEM_STACK_RESPONSE);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        buffer.writeUnsignedVarInt(this.responses.size());
        for (Response response : this.responses) {
            buffer.writeByte(response.result.result);
            buffer.writeUnsignedVarInt(response.requestId);

            if (response.containerInfos != null) {
                buffer.writeUnsignedVarInt(response.containerInfos.size());
                for (StackResponseContainerInfo info : response.containerInfos) {
                    buffer.writeByte(info.windowId);
                    buffer.writeUnsignedVarInt(info.slotInfos.size());
                    for (StackResponseSlotInfo slotInfo : info.slotInfos) {
                        buffer.writeByte(slotInfo.slot);
                        buffer.writeByte(slotInfo.slot);
                        buffer.writeByte(slotInfo.count);
                        buffer.writeSignedVarInt(slotInfo.itemStackId);
                        buffer.writeString("");
                    }
                }
            } else {
                buffer.writeUnsignedVarInt(0);
            }
        }
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {

    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"PacketItemStackResponse\", " +
            "\"responses\":" + (responses == null ? "null" : Arrays.toString(responses.toArray())) +
            "}";
    }
}
