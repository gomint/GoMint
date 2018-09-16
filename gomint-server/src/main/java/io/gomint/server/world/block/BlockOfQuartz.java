package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.server.world.block.state.EnumBlockState;
import io.gomint.world.block.BlockType;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:quartz_block" )
@EqualsAndHashCode( callSuper = true )
public class BlockOfQuartz extends Block implements io.gomint.world.block.BlockBlockOfQuartz {

    private EnumBlockState<Variant> variant = new EnumBlockState<>( Variant.values() );

    @Override
    public String getBlockId() {
        return "minecraft:quartz_block";
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_QUARTZ;
    }

    @Override
    public Variant getVariant() {
        return this.variant.getState();
    }

    @Override
    public void setVariant( Variant variant ) {
        this.variant.setState( variant );
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public List<ItemStack> getDrops( ItemStack itemInHand ) {
        if ( isCorrectTool( itemInHand ) ) {
            return super.getDrops( itemInHand );
        }

        return Collections.emptyList();
    }

    @Override
    public void generateBlockStates() {
        this.variant.fromData( this.getBlockData() );
    }

    @Override
    public void calculateBlockData() {
        this.resetBlockData();
        this.addToBlockData( this.variant.toData() );
    }

}
