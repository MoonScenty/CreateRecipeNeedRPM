package me.moonscenty.createrecipeneedrpm.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.Optional;

public final class RPMRecipeSelector {

    private RPMRecipeSelector() {
    }

    /**
     * 입력 아이템과 현재 RPM을 만족하는 레시피 중
     * 가장 높은 minRPM을 가진 레시피를 선택한다.
     */
    public static <
            I extends RecipeInput,
            R extends Recipe<I> & RPMRequiredRecipe
            >
    Optional<RecipeHolder<R>> findBest(
            RecipeType<R> type,
            I input,
            Level level,
            float rpm
    ) {
        float speed = Math.abs(rpm);

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder ->
                        holder.value().matches(input, level)
                )
                .filter(holder ->
                        speed >= holder.value().getMinRPM()
                )
                .max(Comparator.comparingDouble(
                        holder -> holder.value().getMinRPM()
                ));
    }

    /**
     * RPM 조건은 무시하고 입력에 맞는 레시피가
     * 하나라도 존재하는지 확인한다.
     */
    public static <
            I extends RecipeInput,
            R extends Recipe<I> & RPMRequiredRecipe
            >
    Optional<RecipeHolder<R>> findAny(
            RecipeType<R> type,
            I input,
            Level level
    ) {
        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder ->
                        holder.value().matches(input, level)
                )
                .findFirst();
    }

    /**
     * 해당 입력에서 가장 낮은 minRPM을 반환한다.
     */
    public static <
            I extends RecipeInput,
            R extends Recipe<I> & RPMRequiredRecipe
            >
    Optional<RecipeHolder<R>> findMinimum(
            RecipeType<R> type,
            I input,
            Level level
    ) {
        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder ->
                        holder.value().matches(input, level)
                )
                .min(Comparator.comparingDouble(
                        holder -> holder.value().getMinRPM()
                ));
    }

    /**
     * 현재 RPM보다 높은 레시피 중
     * 가장 가까운 다음 RPM 레시피를 반환한다.
     */
    public static <
            I extends RecipeInput,
            R extends Recipe<I> & RPMRequiredRecipe
            >
    Optional<RecipeHolder<R>> findNext(
            RecipeType<R> type,
            I input,
            Level level,
            float rpm
    ) {
        float speed = Math.abs(rpm);

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder ->
                        holder.value().matches(input, level)
                )
                .filter(holder ->
                        holder.value().getMinRPM() > speed
                )
                .min(Comparator.comparingDouble(
                        holder -> holder.value().getMinRPM()
                ));
    }

    /**
     * 캐시된 레시피가 현재 RPM 기준으로
     * 여전히 최적 레시피인지 확인한다.
     */
    public static <
            I extends RecipeInput,
            R extends Recipe<I> & RPMRequiredRecipe
            >
    boolean isBest(
            R recipe,
            RecipeType<R> type,
            I input,
            Level level,
            float rpm
    ) {
        return findBest(type, input, level, rpm)
                .map(holder -> holder.value() == recipe)
                .orElse(false);
    }
}