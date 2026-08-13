package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RPMMillstoneBlock extends MillstoneBlock {

    public RPMMillstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends MillstoneBlockEntity> getBlockEntityType() {
        return ModBlockEntities.RPM_MILLSTONE.get();
    }
}