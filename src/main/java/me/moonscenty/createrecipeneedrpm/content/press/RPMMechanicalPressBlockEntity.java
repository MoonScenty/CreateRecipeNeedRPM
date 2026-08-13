package me.moonscenty.createrecipeneedrpm.content.press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCompactingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeSelector;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RPMMechanicalPressBlockEntity
        extends MechanicalPressBlockEntity {

    private static final Object RPM_COMPACTING_RECIPES_KEY = new Object();

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

    @Override
    protected boolean matchStaticFilters(
            RecipeHolder<? extends Recipe<?>> recipe
    ) {
        return recipe.value().getType()
                == ModRecipeTypes.RPM_COMPACTING.getType();
    }

    @Override
    protected List<Recipe<?>> getMatchingRecipes() {

        List<Recipe<?>> recipes =
                super.getMatchingRecipes();

        float speed = Math.abs(getSpeed());

        recipes.removeIf(recipe ->
                !(recipe instanceof RPMCompactingRecipe rpmRecipe)
                        || speed < rpmRecipe.getMinRPM()
        );

        recipes.sort(
                Comparator
                        .comparingInt(
                                (Recipe<?> recipe) ->
                                        recipe.getIngredients().size()
                        )
                        .reversed()
                        .thenComparing(
                                Comparator
                                        .comparingDouble(
                                                (Recipe<?> recipe) ->
                                                        ((RPMCompactingRecipe) recipe)
                                                                .getMinRPM()
                                        )
                                        .reversed()
                        )
        );

        return recipes;
    }

    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        List<Recipe<?>> recipes =
                getMatchingRecipes();

        if (recipes.isEmpty()) {
            return false;
        }

        currentRecipe = recipes.getFirst();

        return super.tryProcessInBasin(simulate);
    }

    @Override
    public void onPressingCompleted() {

        if (pressingBehaviour.onBasin()
                && currentRecipe instanceof RPMCompactingRecipe) {

            basinChecker.scheduleUpdate();
            return;
        }

        super.onPressingCompleted();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return RPM_COMPACTING_RECIPES_KEY;
    }
}