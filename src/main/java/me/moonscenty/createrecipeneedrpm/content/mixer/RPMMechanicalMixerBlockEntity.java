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

        /*
         * MechanicalMixerBlockEntity의 기존 로직을 그대로 사용.
         *
         * 여기에는:
         * - Shapeless Crafting
         * - RPM Mixing (matchStaticFilters에 의해)
         * - Potion Mixing
         * 이 들어오게 된다.
         */
        List<Recipe<?>> recipes =
                super.getMatchingRecipes();

        float speed = Math.abs(getSpeed());

        /*
         * RPMMixingRecipe에만 RPM 제한 적용.
         *
         * Shapeless Crafting / Potion Mixing은
         * Create 기본 동작 그대로 유지한다.
         */
        recipes.removeIf(recipe ->
                recipe instanceof RPMMixingRecipe rpmRecipe
                        && speed < rpmRecipe.getMinRPM()
        );

        /*
         * Create 기본 우선순위:
         * 재료가 많은 레시피 우선
         *
         * 동일 조건의 RPM Mixing끼리는
         * min_rpm이 높은 것을 우선한다.
         */
        recipes.sort((first, second) -> {

            int ingredientCompare =
                    Integer.compare(
                            second.getIngredients().size(),
                            first.getIngredients().size()
                    );

            if (ingredientCompare != 0) {
                return ingredientCompare;
            }

            if (first instanceof RPMMixingRecipe firstRPM
                    && second instanceof RPMMixingRecipe secondRPM) {

                return Float.compare(
                        secondRPM.getMinRPM(),
                        firstRPM.getMinRPM()
                );
            }

            return 0;
        });

        return recipes;
    }

    @Override
    public boolean isSpeedRequirementFulfilled() {

        /*
         * Mixer 자체의 Create 기본 최소 속도 조건부터 확인.
         */
        if (!super.isSpeedRequirementFulfilled()) {
            return false;
        }

        /*
         * 이미 RPM 레시피를 가공 중이라면
         * 해당 레시피의 min_rpm도 만족해야 한다.
         */
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