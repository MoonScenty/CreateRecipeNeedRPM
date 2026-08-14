package me.moonscenty.createrecipeneedrpm.content.mixer;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.infrastructure.config.AllConfigs;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMixingRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Comparator;
import java.util.List;

public class RPMMechanicalMixerBlockEntity
        extends MechanicalMixerBlockEntity {

    private static final Object RPM_MIXING_RECIPES_KEY =
            new Object();

    public RPMMechanicalMixerBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.RPM_MECHANICAL_MIXER.get(),
                pos,
                state
        );
    }

    @Override
    protected boolean matchStaticFilters(
            RecipeHolder<? extends Recipe<?>> recipe
    ) {
        Recipe<?> value = recipe.value();

        boolean automaticShapeless =
                value instanceof CraftingRecipe
                        && !(value instanceof ShapedRecipe)
                        && AllConfigs.server()
                        .recipes
                        .allowShapelessInMixer
                        .get()
                        && value.getIngredients().size() > 1
                        && !MechanicalPressBlockEntity.canCompress(value)
                        && !AllRecipeTypes.shouldIgnoreInAutomation(recipe);

        boolean rpmMixing =
                value.getType()
                        == ModRecipeTypes.RPM_MIXING.getType();

        return automaticShapeless || rpmMixing;
    }

    @Override
    protected List<Recipe<?>> getMatchingRecipes() {

        List<Recipe<?>> recipes = super.getMatchingRecipes();

        float speed = Math.abs(getSpeed());

        recipes.removeIf(recipe ->
                recipe instanceof RPMMixingRecipe rpmRecipe
                        && speed < rpmRecipe.getMinRPM()
        );

        List<RPMMixingRecipe> rpmRecipes = recipes.stream()
                .filter(RPMMixingRecipe.class::isInstance)
                .map(RPMMixingRecipe.class::cast)
                .sorted(
                        Comparator.comparingDouble(
                                RPMMixingRecipe::getMinRPM
                        ).reversed()
                )
                .toList();

        if (rpmRecipes.size() > 1) {
            int index = 0;

            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i) instanceof RPMMixingRecipe) {
                    recipes.set(i, rpmRecipes.get(index++));
                }
            }
        }

        return recipes;
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {

        if (!super.isSpeedRequirementFulfilled()) {
            return false;
        }

        if (running
                && currentRecipe instanceof RPMMixingRecipe rpmRecipe) {

            return Math.abs(getSpeed())
                    >= rpmRecipe.getMinRPM();
        }

        return true;
    }

    @Override
    public boolean continueWithPreviousRecipe() {

        /*
         * RPM Mixing은 다음 batch에서
         * 현재 RPM에 맞는 tier를 다시 선택해야 한다.
         *
         * Shapeless / Potion Mixing은
         * Create 원본의 연속 처리 동작을 유지한다.
         */
        if (currentRecipe instanceof RPMMixingRecipe) {
            return false;
        }

        return super.continueWithPreviousRecipe();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return RPM_MIXING_RECIPES_KEY;
    }
}