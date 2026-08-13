package me.moonscenty.createrecipeneedrpm.content.press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeSelector;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class RPMMechanicalPressBlockEntity
        extends MechanicalPressBlockEntity {

    public RPMMechanicalPressBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.RPM_MECHANICAL_PRESS.get(),
                pos,
                state
        );
    }

    @Override
    public Optional<RecipeHolder<PressingRecipe>> getRecipe(ItemStack item) {

        if (level == null || item.isEmpty()) {
            return Optional.empty();
        }

        RecipeType<RPMPressingRecipe> type =
                ModRecipeTypes.RPM_PRESSING.getType();

        SingleRecipeInput input =
                new SingleRecipeInput(item);

        return RPMRecipeSelector
                .findBest(
                        type,
                        input,
                        level,
                        getSpeed()
                )
                .map(holder ->
                        new RecipeHolder<PressingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }
}