/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockElement;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.ElementType;

@RegisterInfo(sId = "minecraft:element_118")
@RegisterInfo(sId = "minecraft:element_112")
@RegisterInfo(sId = "minecraft:element_113")
@RegisterInfo(sId = "minecraft:element_110")
@RegisterInfo(sId = "minecraft:element_111")
@RegisterInfo(sId = "minecraft:element_116")
@RegisterInfo(sId = "minecraft:element_117")
@RegisterInfo(sId = "minecraft:element_114")
@RegisterInfo(sId = "minecraft:element_115")
@RegisterInfo(sId = "minecraft:element_99")
@RegisterInfo(sId = "minecraft:element_96")
@RegisterInfo(sId = "minecraft:element_95")
@RegisterInfo(sId = "minecraft:element_98")
@RegisterInfo(sId = "minecraft:element_97")
@RegisterInfo(sId = "minecraft:element_92")
@RegisterInfo(sId = "minecraft:element_91")
@RegisterInfo(sId = "minecraft:element_94")
@RegisterInfo(sId = "minecraft:element_93")
@RegisterInfo(sId = "minecraft:element_90")
@RegisterInfo(sId = "minecraft:element_89")
@RegisterInfo(sId = "minecraft:element_88")
@RegisterInfo(sId = "minecraft:element_85")
@RegisterInfo(sId = "minecraft:element_84")
@RegisterInfo(sId = "minecraft:element_87")
@RegisterInfo(sId = "minecraft:element_86")
@RegisterInfo(sId = "minecraft:element_81")
@RegisterInfo(sId = "minecraft:element_80")
@RegisterInfo(sId = "minecraft:element_83")
@RegisterInfo(sId = "minecraft:element_82")
@RegisterInfo(sId = "minecraft:element_2")
@RegisterInfo(sId = "minecraft:element_3")
@RegisterInfo(sId = "minecraft:element_0", def = true)
@RegisterInfo(sId = "minecraft:element_1")
@RegisterInfo(sId = "minecraft:element_6")
@RegisterInfo(sId = "minecraft:element_7")
@RegisterInfo(sId = "minecraft:element_4")
@RegisterInfo(sId = "minecraft:element_5")
@RegisterInfo(sId = "minecraft:element_8")
@RegisterInfo(sId = "minecraft:element_9")
@RegisterInfo(sId = "minecraft:element_38")
@RegisterInfo(sId = "minecraft:element_37")
@RegisterInfo(sId = "minecraft:element_39")
@RegisterInfo(sId = "minecraft:element_34")
@RegisterInfo(sId = "minecraft:element_33")
@RegisterInfo(sId = "minecraft:element_36")
@RegisterInfo(sId = "minecraft:element_35")
@RegisterInfo(sId = "minecraft:element_30")
@RegisterInfo(sId = "minecraft:element_32")
@RegisterInfo(sId = "minecraft:element_31")
@RegisterInfo(sId = "minecraft:element_27")
@RegisterInfo(sId = "minecraft:element_26")
@RegisterInfo(sId = "minecraft:element_29")
@RegisterInfo(sId = "minecraft:element_28")
@RegisterInfo(sId = "minecraft:element_23")
@RegisterInfo(sId = "minecraft:element_22")
@RegisterInfo(sId = "minecraft:element_25")
@RegisterInfo(sId = "minecraft:element_24")
@RegisterInfo(sId = "minecraft:element_21")
@RegisterInfo(sId = "minecraft:element_20")
@RegisterInfo(sId = "minecraft:element_19")
@RegisterInfo(sId = "minecraft:element_16")
@RegisterInfo(sId = "minecraft:element_15")
@RegisterInfo(sId = "minecraft:element_18")
@RegisterInfo(sId = "minecraft:element_17")
@RegisterInfo(sId = "minecraft:element_12")
@RegisterInfo(sId = "minecraft:element_11")
@RegisterInfo(sId = "minecraft:element_14")
@RegisterInfo(sId = "minecraft:element_13")
@RegisterInfo(sId = "minecraft:element_10")
@RegisterInfo(sId = "minecraft:element_78")
@RegisterInfo(sId = "minecraft:element_77")
@RegisterInfo(sId = "minecraft:element_79")
@RegisterInfo(sId = "minecraft:element_74")
@RegisterInfo(sId = "minecraft:element_73")
@RegisterInfo(sId = "minecraft:element_76")
@RegisterInfo(sId = "minecraft:element_75")
@RegisterInfo(sId = "minecraft:element_70")
@RegisterInfo(sId = "minecraft:element_72")
@RegisterInfo(sId = "minecraft:element_71")
@RegisterInfo(sId = "minecraft:element_67")
@RegisterInfo(sId = "minecraft:element_66")
@RegisterInfo(sId = "minecraft:element_69")
@RegisterInfo(sId = "minecraft:element_68")
@RegisterInfo(sId = "minecraft:element_63")
@RegisterInfo(sId = "minecraft:element_62")
@RegisterInfo(sId = "minecraft:element_65")
@RegisterInfo(sId = "minecraft:element_64")
@RegisterInfo(sId = "minecraft:element_61")
@RegisterInfo(sId = "minecraft:element_60")
@RegisterInfo(sId = "minecraft:element_59")
@RegisterInfo(sId = "minecraft:element_56")
@RegisterInfo(sId = "minecraft:element_55")
@RegisterInfo(sId = "minecraft:element_58")
@RegisterInfo(sId = "minecraft:element_57")
@RegisterInfo(sId = "minecraft:element_52")
@RegisterInfo(sId = "minecraft:element_51")
@RegisterInfo(sId = "minecraft:element_54")
@RegisterInfo(sId = "minecraft:element_53")
@RegisterInfo(sId = "minecraft:element_50")
@RegisterInfo(sId = "minecraft:element_49")
@RegisterInfo(sId = "minecraft:element_48")
@RegisterInfo(sId = "minecraft:element_45")
@RegisterInfo(sId = "minecraft:element_44")
@RegisterInfo(sId = "minecraft:element_47")
@RegisterInfo(sId = "minecraft:element_46")
@RegisterInfo(sId = "minecraft:element_41")
@RegisterInfo(sId = "minecraft:element_40")
@RegisterInfo(sId = "minecraft:element_43")
@RegisterInfo(sId = "minecraft:element_42")
@RegisterInfo(sId = "minecraft:element_109")
@RegisterInfo(sId = "minecraft:element_107")
@RegisterInfo(sId = "minecraft:element_108")
@RegisterInfo(sId = "minecraft:element_101")
@RegisterInfo(sId = "minecraft:element_102")
@RegisterInfo(sId = "minecraft:element_100")
@RegisterInfo(sId = "minecraft:element_105")
@RegisterInfo(sId = "minecraft:element_106")
@RegisterInfo(sId = "minecraft:element_103")
@RegisterInfo(sId = "minecraft:element_104")
public class Element extends Block implements BlockElement {

    private static final int BLOCK_PREFIX_LENGTH = 18; // minecraft:element_

    @Override
    public long breakTime() {
        return 0;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float blastResistance() {
        return 0;
    }

    @Override
    public BlockType blockType() {
        return BlockType.ELEMENT;
    }

    @Override
    public ElementType type() {
        int ord = Integer.parseInt(this.blockId().substring(BLOCK_PREFIX_LENGTH));
        return ElementType.values()[ord];
    }

    @Override
    public BlockElement type(ElementType type) {
        this.blockId("minecraft:element_" + type.ordinal());
        return this;
    }

}
