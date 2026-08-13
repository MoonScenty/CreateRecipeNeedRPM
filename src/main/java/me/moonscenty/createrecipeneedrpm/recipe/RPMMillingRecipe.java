package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;

public class RPMMillingRecipe
        extends ProcessingRecipe<RecipeInput, RPMMillingRecipeParams> {

    public RPMMillingRecipe(RPMMillingRecipeParams params) {
        super(ModRecipeTypes.RPM_MILLING, params);
    }

    public float getMinRPM() {
        return params.getMinRPM();
    }

    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.isEmpty()) {
            return false;
        }

        return ingredients.getFirst().test(input.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 4;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return true;
    }
}