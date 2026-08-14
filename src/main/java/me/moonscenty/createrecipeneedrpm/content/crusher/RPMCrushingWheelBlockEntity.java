package me.moonscenty.createrecipeneedrpm.content.crusher;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import me.moonscenty.createrecipeneedrpm.foundation.utility.RPMGoggleTooltip;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCrushingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeSelector;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean addToGoggleTooltip(
            List<Component> tooltip,
            boolean isPlayerSneaking
    ) {
        boolean parentAdded =
                super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        if (level == null) {
            return parentAdded;
        }

        Optional<CrushingWheelControllerBlockEntity> controller =
                RPMCrushingWheelHelper.findController(
                        level,
                        worldPosition
                );

        boolean hasInput = controller
                .map(value -> !value.inventory.getStackInSlot(0).isEmpty())
                .orElse(false);

        if (!hasInput) {
            RPMGoggleTooltip.add(
                    tooltip,
                    getSpeed(),
                    false,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
            return true;
        }

        RecipeWrapper input =
                new RecipeWrapper(controller.get().inventory);
        RecipeType<RPMCrushingRecipe> type =
                ModRecipeTypes.RPM_CRUSHING.getType();

        Optional<RPMCrushingRecipe> current =
                RPMRecipeSelector.findBest(
                        type,
                        input,
                        level,
                        getSpeed()
                ).map(RecipeHolder::value);
        Optional<RPMCrushingRecipe> minimum =
                RPMRecipeSelector.findMinimum(
                        type,
                        input,
                        level
                ).map(RecipeHolder::value);
        Optional<RPMCrushingRecipe> next =
                RPMRecipeSelector.findNext(
                        type,
                        input,
                        level,
                        getSpeed()
                ).map(RecipeHolder::value);

        RPMGoggleTooltip.add(
                tooltip,
                getSpeed(),
                true,
                current,
                minimum,
                next
        );
        return true;
    }
}
