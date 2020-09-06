package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.Axis;
import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = -212, sId = "minecraft:wood")
@RegisterInfo(sId = "minecraft:log", id = 17, def = true)
@RegisterInfo(sId = "minecraft:log2", id = 162)
@RegisterInfo(sId = "minecraft:crimson_stem", id = -225)
@RegisterInfo(sId = "minecraft:warped_stem", id = -226)
@RegisterInfo(sId = "minecraft:stripped_oak_log", id = -10)
@RegisterInfo(sId = "minecraft:stripped_spruce_log", id = -5)
@RegisterInfo(sId = "minecraft:stripped_birch_log", id = -6)
@RegisterInfo(sId = "minecraft:stripped_jungle_log", id = -7)
@RegisterInfo(sId = "minecraft:stripped_dark_oak_log", id = -9)
@RegisterInfo(sId = "minecraft:stripped_acacia_log", id = -8)
@RegisterInfo(sId = "minecraft:stripped_crimson_stem", id = -240)
@RegisterInfo(sId = "minecraft:stripped_warped_stem", id = -241)
@RegisterInfo(sId = "minecraft:warped_hyphae", id = -298)
@RegisterInfo(sId = "minecraft:crimson_hyphae", id = -299)
public class ItemLog extends ItemStack implements io.gomint.inventory.item.ItemLog {

    private enum LogTypeMagic {
        OAK("minecraft:log", "minecraft:stripped_oak_log", (short) 0, "minecraft:wood", (short) 0, (short) 8),
        SPRUCE("minecraft:log", "minecraft:stripped_spruce_log", (short) 1, "minecraft:wood", (short) 1, (short) 9),
        BIRCH("minecraft:log", "minecraft:stripped_birch_log", (short) 2, "minecraft:wood", (short) 2, (short) 10),
        JUNGLE("minecraft:log", "minecraft:stripped_jungle_log", (short) 3, "minecraft:wood", (short) 3, (short) 11),
        ACACIA("minecraft:log2", "minecraft:stripped_acacia_log", (short) 0, "minecraft:wood", (short) 4, (short) 12),
        DARK_OAK("minecraft:log2", "minecraft:stripped_dark_oak_log", (short) 1, "minecraft:wood", (short) 5, (short) 13),
        CRIMSON("minecraft:crimson_stem", "minecraft:stripped_crimson_stem", (short) 0, "minecraft:crimson_hyphae", "minecraft:stripped_crimson_hyphae"),
        WARPED("minecraft:warped_stem", "minecraft:stripped_warped_stem", (short) 0, "minecraft:warped_hyphae", "minecraft:stripped_warped_hyphae");

        private final String logBlockId;
        private final String strippedLogBlockId;
        private final short dataValue;
        private final String woodBlockId;

        private String strippedWoodBlockId;
        private short woodDataValue = -1;
        private short strippedWoodDataValue = -1;

        LogTypeMagic(String logBlockId, String strippedLogBlockId, short dataValue, String woodBlockId, String strippedWoodBlockId) {
            this.logBlockId = logBlockId;
            this.strippedLogBlockId = strippedLogBlockId;
            this.dataValue = dataValue;
            this.woodBlockId = woodBlockId;

            this.strippedWoodBlockId = strippedWoodBlockId;
        }

        LogTypeMagic(String logBlockId, String strippedLogBlockId, short dataValue, String woodBlockId, short woodDataValue, short strippedWoodDataValue) {
            this.logBlockId = logBlockId;
            this.strippedLogBlockId = strippedLogBlockId;
            this.dataValue = dataValue;
            this.woodBlockId = woodBlockId;

            this.woodDataValue = woodDataValue;
            this.strippedWoodDataValue = strippedWoodDataValue;
        }
    }

    @Override
    public long getBurnTime() {
        return 15000;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.LOG;
    }

    @Override
    public boolean isStripped() {
        return this.getMaterial().startsWith("minecraft:stripped_");
    }

    @Override
    public void setStripped(boolean stripped) {

    }

    @Override
    public void setLogType(LogType type) {

    }

    @Override
    public LogType getLogType() {
        return null;
    }

    @Override
    public void setBarkOnAllSides(boolean allSides) {

    }

    @Override
    public boolean isBarkOnAllSides() {
        return false;
    }

    @Override
    public void setAxis(Axis axis) {

    }

    @Override
    public Axis getAxis() {
        return null;
    }

    private void calculate(LogTypeMagic type, Axis axis, boolean stripped, boolean bark) {
        if (stripped) {
            if (bark) {
                if (type.strippedWoodDataValue > -1) {
                    this.setMaterial(type.woodBlockId);
                    this.setData(type.strippedWoodDataValue);
                } else {
                    this.setMaterial(type.strippedWoodBlockId);
                    this.setData((short) 0);
                }
            } else {
                this.setMaterial(type.strippedLogBlockId);
                this.setData((short) 0);
            }
        } else {
            if (bark) {
                this.setMaterial(type.woodBlockId);

                if (type.woodDataValue > -1) {
                    this.setData(type.woodDataValue);
                } else {
                    this.setData((short) 0);
                }
            } else {
                this.setMaterial(type.logBlockId);
                this.setData(type.dataValue);
            }
        }

        if (!bark) {
            if (stripped) {
                switch (axis) {
                    case X:
                        this.setData((short) 1);
                        break;
                    case Z:
                        this.setData((short) 2);
                        break;
                }
            } else {
                switch (axis) {
                    case X:
                        this.setData((short) (this.getData() + 4));
                        break;
                    case Z:
                        this.setData((short) (this.getData() + 5));
                        break;
                }
            }
        }
    }

}
