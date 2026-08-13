package me.moonscenty.createrecipeneedrpm.content.press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RPMMechanicalPressBlock extends MechanicalPressBlock {

    public RPMMechanicalPressBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends MechanicalPressBlockEntity> getBlockEntityType() {
        return ModBlockEntities.RPM_MECHANICAL_PRESS.get();
    }
}