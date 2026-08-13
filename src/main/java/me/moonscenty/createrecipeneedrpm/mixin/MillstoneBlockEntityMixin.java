package me.moonscenty.createrecipeneedrpm.mixin;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.content.millstone.RPMMillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeFinder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(MillstoneBlockEntity.class)
public abstract class MillstoneBlockEntityMixin {

    /**
     * tick() / process()에서 Create의 milling 레시피 검색을
     * RPM 전용 레시피 검색으로 교체한다.
     */
    @Redirect(
            method = {
                    "tick",
                    "process"
            },
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lcom/simibubi/create/AllRecipeTypes;" +
                                    "find(" +
                                    "Lnet/minecraft/world/item/crafting/RecipeInput;" +
                                    "Lnet/minecraft/world/level/Level;" +
                                    ")Ljava/util/Optional;"
            )
    )
    private Optional<RecipeHolder<MillingRecipe>>
    createrecipeneedrpm$findRPMRecipe(
            AllRecipeTypes recipeType,
            RecipeInput input,
            Level level
    ) {
        MillstoneBlockEntity self =
                (MillstoneBlockEntity) (Object) this;

        if (self instanceof RPMMillstoneBlockEntity) {
            return RPMRecipeFinder.find(
                    input,
                    level,
                    self.getSpeed()
            );
        }

        return recipeType.find(input, level);
    }

    /**
     * canProcess()에서는 RPM을 무시한다.
     * 레시피 존재 여부만 확인한다.
     */
    @Redirect(
            method = "canProcess",
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lcom/simibubi/create/AllRecipeTypes;" +
                                    "find(" +
                                    "Lnet/minecraft/world/item/crafting/RecipeInput;" +
                                    "Lnet/minecraft/world/level/Level;" +
                                    ")Ljava/util/Optional;"
            )
    )
    private Optional<RecipeHolder<MillingRecipe>>
    createrecipeneedrpm$findAnyRPMRecipe(
            AllRecipeTypes recipeType,
            RecipeInput input,
            Level level
    ) {
        MillstoneBlockEntity self =
                (MillstoneBlockEntity) (Object) this;

        if (self instanceof RPMMillstoneBlockEntity) {
            return RPMRecipeFinder.findAny(input, level);
        }

        return recipeType.find(input, level);
    }

    /**
     * lastRecipe 캐시가 현재 RPM에서도 최적의 레시피인지 검사한다.
     *
     * 예:
     * 32 RPM → minRPM 32
     * 128 RPM으로 변경 → 기존 32 RPM 레시피 캐시 무효화
     * → minRPM 128 레시피 재검색
     */
    @Redirect(
            method = {
                    "tick",
                    "process"
            },
            at = @At(
                    value = "INVOKE",
                    target =
                            "Lcom/simibubi/create/content/kinetics/millstone/MillingRecipe;" +
                                    "matches(" +
                                    "Lnet/minecraft/world/item/crafting/RecipeInput;" +
                                    "Lnet/minecraft/world/level/Level;" +
                                    ")Z"
            )
    )
    private boolean createrecipeneedrpm$checkCachedRecipe(
            MillingRecipe recipe,
            RecipeInput input,
            Level level
    ) {
        MillstoneBlockEntity self =
                (MillstoneBlockEntity) (Object) this;

        if (self instanceof RPMMillstoneBlockEntity) {
            return RPMRecipeFinder.isBestRecipe(
                    recipe,
                    input,
                    level,
                    self.getSpeed()
            );
        }

        return recipe.matches(input, level);
    }
}