package me.moonscenty.createrecipeneedrpm.content.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RPMCrushingWheelBlockEntity extends CrushingWheelBlockEntity {

    public RPMCrushingWheelBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.RPM_CRUSHING_WHEEL.get(),
                pos,
                state
        );
    }
}