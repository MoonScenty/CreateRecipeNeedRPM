package me.moonscenty.createrecipeneedrpm.content.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RPMCrushingWheelBlock extends CrushingWheelBlock {

    public RPMCrushingWheelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends CrushingWheelBlockEntity> getBlockEntityType() {
        return ModBlockEntities.RPM_CRUSHING_WHEEL.get();
    }

    @Override
    public void updateControllers(
            BlockState state,
            Level world,
            BlockPos pos,
            Direction side
    ) {
        if (side.getAxis() == state.getValue(AXIS)) {
            return;
        }

        if (world == null) {
            return;
        }

        BlockPos controllerPos = pos.relative(side);
        BlockPos otherWheelPos = pos.relative(side, 2);

        boolean controllerExists =
                AllBlocks.CRUSHING_WHEEL_CONTROLLER.has(
                        world.getBlockState(controllerPos)
                );

        boolean controllerIsValid =
                controllerExists
                        && world.getBlockState(controllerPos)
                        .getValue(CrushingWheelControllerBlock.VALID);

        Direction controllerOldDirection =
                controllerExists
                        ? world.getBlockState(controllerPos)
                        .getValue(CrushingWheelControllerBlock.FACING)
                        : null;

        boolean controllerShouldExist = false;
        boolean controllerShouldBeValid = false;

        Direction controllerNewDirection = Direction.DOWN;

        BlockState otherState =
                world.getBlockState(otherWheelPos);

        // Create 원본과 다른 핵심 부분
        if (otherState.is(ModBlocks.RPM_CRUSHING_WHEEL.get())) {

            controllerShouldExist = true;

            CrushingWheelBlockEntity be =
                    getBlockEntity(world, pos);

            CrushingWheelBlockEntity otherBE =
                    getBlockEntity(world, otherWheelPos);

            if (be != null
                    && otherBE != null
                    && (be.getSpeed() > 0) != (otherBE.getSpeed() > 0)
                    && be.getSpeed() != 0
                    && otherBE.getSpeed() != 0) {

                Direction.Axis wheelAxis =
                        state.getValue(AXIS);

                Direction.Axis sideAxis =
                        side.getAxis();

                int controllerADO =
                        Math.round(Math.signum(be.getSpeed()))
                                * side.getAxisDirection().getStep();

                Vec3 controllerDirVec =
                        new Vec3(
                                wheelAxis == Direction.Axis.X ? 1 : 0,
                                wheelAxis == Direction.Axis.Y ? 1 : 0,
                                wheelAxis == Direction.Axis.Z ? 1 : 0
                        ).cross(
                                new Vec3(
                                        sideAxis == Direction.Axis.X ? 1 : 0,
                                        sideAxis == Direction.Axis.Y ? 1 : 0,
                                        sideAxis == Direction.Axis.Z ? 1 : 0
                                )
                        );

                controllerNewDirection =
                        Direction.getNearest(
                                controllerDirVec.x * controllerADO,
                                controllerDirVec.y * controllerADO,
                                controllerDirVec.z * controllerADO
                        );

                controllerShouldBeValid = true;
            }

            if (otherState.getValue(AXIS)
                    != state.getValue(AXIS)) {

                controllerShouldExist = false;
            }
        }

        if (!controllerShouldExist) {

            if (controllerExists) {
                world.setBlockAndUpdate(
                        controllerPos,
                        Blocks.AIR.defaultBlockState()
                );
            }

            return;
        }

        if (!controllerExists) {

            if (!world.getBlockState(controllerPos)
                    .canBeReplaced()) {
                return;
            }

            world.setBlockAndUpdate(
                    controllerPos,
                    AllBlocks.CRUSHING_WHEEL_CONTROLLER
                            .getDefaultState()
                            .setValue(
                                    CrushingWheelControllerBlock.VALID,
                                    controllerShouldBeValid
                            )
                            .setValue(
                                    CrushingWheelControllerBlock.FACING,
                                    controllerNewDirection
                            )
            );

        } else if (controllerIsValid != controllerShouldBeValid
                || controllerOldDirection != controllerNewDirection) {

            world.setBlockAndUpdate(
                    controllerPos,
                    world.getBlockState(controllerPos)
                            .setValue(
                                    CrushingWheelControllerBlock.VALID,
                                    controllerShouldBeValid
                            )
                            .setValue(
                                    CrushingWheelControllerBlock.FACING,
                                    controllerNewDirection
                            )
            );
        }

        RPMCrushingWheelHelper.updateControllerSpeed(
                world,
                controllerPos
        );
    }
}