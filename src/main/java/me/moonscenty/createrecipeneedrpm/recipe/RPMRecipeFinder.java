package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public final class RPMRecipeFinder {

    private RPMRecipeFinder() {
    }

    private static RecipeType<RPMMillingRecipe> getType() {
        return ModRecipeTypes.RPM_MILLING.getType();
    }

    public static Optional<RecipeHolder<MillingRecipe>> find(
            RecipeInput input,
            Level level,
            float rpm
    ) {
        return RPMRecipeSelector
                .findBest(getType(), input, level, rpm)
                .map(holder ->
                        new RecipeHolder<MillingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }

    public static Optional<RecipeHolder<MillingRecipe>> findAny(
            RecipeInput input,
            Level level
    ) {
        return RPMRecipeSelector
                .findAny(getType(), input, level)
                .map(holder ->
                        new RecipeHolder<MillingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }

    public static Optional<RPMMillingRecipe> findMinimum(
            RecipeInput input,
            Level level
    ) {
        return RPMRecipeSelector
                .findMinimum(getType(), input, level)
                .map(RecipeHolder::value);
    }

    public static Optional<RPMMillingRecipe> findNext(
            RecipeInput input,
            Level level,
            float rpm
    ) {
        return RPMRecipeSelector
                .findNext(getType(), input, level, rpm)
                .map(RecipeHolder::value);
    }

    public static boolean isBestRecipe(
            MillingRecipe recipe,
            RecipeInput input,
            Level level,
            float rpm
    ) {
        if (!(recipe instanceof RPMMillingRecipe rpmRecipe)) {
            return false;
        }

        return RPMRecipeSelector.isBest(
                rpmRecipe,
                getType(),
                input,
                level,
                rpm
        );
    }
}