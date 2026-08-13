package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.Optional;

public final class RPMRecipeFinder {

    private RPMRecipeFinder() {
    }

    /**
     * 현재 RPM에서 실행 가능한 레시피 중
     * minRPM이 가장 높은 레시피를 반환한다.
     */
    public static Optional<RecipeHolder<MillingRecipe>> find(
            RecipeInput input,
            Level level,
            float rpm
    ) {
        float speed = Math.abs(rpm);

        RecipeType<RPMMillingRecipe> type =
                ModRecipeTypes.RPM_MILLING.getType();

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder -> holder.value().matches(input, level))
                .filter(holder -> speed >= holder.value().getMinRPM())
                .max(Comparator.comparingDouble(
                        holder -> holder.value().getMinRPM()
                ))
                .map(holder ->
                        new RecipeHolder<MillingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }

    /**
     * RPM은 무시하고 해당 아이템을 처리할 수 있는
     * rpm_milling 레시피가 존재하는지만 검사한다.
     *
     * 아이템 투입 가능 여부 판단용.
     */
    public static Optional<RecipeHolder<MillingRecipe>> findAny(
            RecipeInput input,
            Level level
    ) {
        RecipeType<RPMMillingRecipe> type =
                ModRecipeTypes.RPM_MILLING.getType();

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(holder -> holder.value().matches(input, level))
                .findFirst()
                .map(holder ->
                        new RecipeHolder<MillingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }

    /**
     * 현재 캐시된 레시피가 현재 RPM에서
     * 가장 적절한 레시피인지 검사한다.
     */
    public static boolean isBestRecipe(
            MillingRecipe recipe,
            RecipeInput input,
            Level level,
            float rpm
    ) {
        if (!(recipe instanceof RPMMillingRecipe)) {
            return false;
        }

        return find(input, level, rpm)
                .map(holder -> holder.value() == recipe)
                .orElse(false);
    }

    public static Optional<RPMMillingRecipe> findMinimum(
            RecipeInput input,
            Level level
    ) {
        RecipeType<RPMMillingRecipe> type =
                ModRecipeTypes.RPM_MILLING.getType();

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.matches(input, level))
                .min(Comparator.comparingDouble(
                        RPMMillingRecipe::getMinRPM
                ));
    }

    public static Optional<RPMMillingRecipe> findNext(
            RecipeInput input,
            Level level,
            float rpm
    ) {
        float speed = Math.abs(rpm);

        RecipeType<RPMMillingRecipe> type =
                ModRecipeTypes.RPM_MILLING.getType();

        return level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.matches(input, level))
                .filter(recipe -> recipe.getMinRPM() > speed)
                .min(Comparator.comparingDouble(
                        RPMMillingRecipe::getMinRPM
                ));
    }
}