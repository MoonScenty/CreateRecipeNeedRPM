package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RPMMillstoneBlockEntity extends MillstoneBlockEntity {

    public RPMMillstoneBlockEntity(BlockPos pos, BlockState state) {
        super(
                ModBlockEntities.RPM_MILLSTONE.get(),
                pos,
                state
        );
    }
}