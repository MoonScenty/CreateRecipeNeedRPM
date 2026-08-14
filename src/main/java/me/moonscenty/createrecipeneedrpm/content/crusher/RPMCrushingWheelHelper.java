package me.moonscenty.createrecipeneedrpm.content.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public final class RPMCrushingWheelHelper {

    private RPMCrushingWheelHelper() {
    }

    public static Optional<RPMCrushingWheelBlockEntity> findWheel(
            LevelAccessor level,
            BlockPos controllerPos
    ) {
        for (Direction direction : Direction.values()) {

            BlockPos wheelPos =
                    controllerPos.relative(direction);

            BlockState state =
                    level.getBlockState(wheelPos);

            if (!state.is(ModBlocks.RPM_CRUSHING_WHEEL.get())) {
                continue;
            }

            if (state.getValue(BlockStateProperties.AXIS)
                    == direction.getAxis()) {
                continue;
            }

            BlockEntity blockEntity =
                    level.getBlockEntity(wheelPos);

            if (blockEntity
                    instanceof RPMCrushingWheelBlockEntity wheel) {

                return Optional.of(wheel);
            }
        }

        return Optional.empty();
    }

    public static float getRPM(
            LevelAccessor level,
            BlockPos controllerPos
    ) {
        return findWheel(level, controllerPos)
                .map(wheel -> Math.abs(wheel.getSpeed()))
                .orElse(0.0F);
    }

    public static Optional<CrushingWheelControllerBlockEntity> findController(
            LevelAccessor level,
            BlockPos wheelPos
    ) {
        for (Direction direction : Direction.values()) {
            BlockPos controllerPos = wheelPos.relative(direction);
            BlockEntity blockEntity = level.getBlockEntity(controllerPos);

            if (!(blockEntity
                    instanceof CrushingWheelControllerBlockEntity controller)) {
                continue;
            }

            if (findWheel(level, controllerPos).isPresent()) {
                return Optional.of(controller);
            }
        }

        return Optional.empty();
    }

    public static void updateControllerSpeed(
            LevelAccessor level,
            BlockPos controllerPos
    ) {
        BlockEntity blockEntity =
                level.getBlockEntity(controllerPos);

        if (!(blockEntity
                instanceof CrushingWheelControllerBlockEntity controller)) {
            return;
        }

        float rpm = getRPM(
                level,
                controllerPos
        );

        float crushingSpeed =
                Math.abs(rpm / 50.0F);

        if (controller.crushingspeed == crushingSpeed) {
            return;
        }

        controller.crushingspeed =
                crushingSpeed;

        controller.sendData();
    }
}
