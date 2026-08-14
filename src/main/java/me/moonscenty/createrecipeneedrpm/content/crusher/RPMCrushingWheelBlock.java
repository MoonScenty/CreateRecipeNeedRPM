package me.moonscenty.createrecipeneedrpm.content.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RPMCrushingWheelBlock extends CrushingWheelBlock {

    public RPMCrushingWheelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.RPM_CRUSHING_WHEEL.get();
    }
}