package me.moonscenty.createrecipeneedrpm.content.mixer;

import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlock;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class RPMMechanicalMixerBlock extends MechanicalMixerBlock {

    public RPMMechanicalMixerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<RPMMechanicalMixerBlockEntity> getBlockEntityType() {
        return ModBlockEntities.RPM_MECHANICAL_MIXER.get();
    }
}