package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

public final class RPMCrushingRecipeFinder {

    private RPMCrushingRecipeFinder() {
    }

    public static Optional<
            RecipeHolder<StandardProcessingRecipe<RecipeWrapper>>
            > find(
            RecipeWrapper input,
            Level level,
            float rpm
    ) {
        Optional<RecipeHolder<RPMCrushingRecipe>> crushing =
                RPMRecipeSelector.findBest(
                        ModRecipeTypes.RPM_CRUSHING.getType(),
                        input,
                        level,
                        rpm
                );

        return crushing.map(
                RPMCrushingRecipeFinder::toControllerRecipe
        );
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static RecipeHolder<
            StandardProcessingRecipe<RecipeWrapper>
            > toControllerRecipe(
            RecipeHolder<? extends StandardProcessingRecipe<?>> holder
    ) {
        return (RecipeHolder) holder;
    }
}