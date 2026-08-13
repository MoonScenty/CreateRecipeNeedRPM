package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RPMMillstoneBlockEntity extends KineticBlockEntity {

    public RPMMillstoneBlockEntity(BlockPos pos, BlockState state) {
        super(
                ModBlockEntities.RPM_MILLSTONE.get(),
                pos,
                state
        );
    }

    @Override
    public void tick() {
        super.tick();

        System.out.println("RPM = " + getSpeed());
    }
}