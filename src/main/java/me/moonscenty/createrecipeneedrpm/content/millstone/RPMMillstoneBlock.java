package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class RPMMillstoneBlock extends KineticBlock implements ICogWheel {

    public RPMMillstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(
            LevelReader level,
            BlockPos pos,
            BlockState state,
            Direction face
    ) {
        return face == Direction.DOWN;
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }
}