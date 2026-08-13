package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RPMPressingRecipe
        extends PressingRecipe
        implements RPMRequiredRecipe {

    private final RPMProcessingRecipeParams rpmParams;

    public RPMPressingRecipe(RPMProcessingRecipeParams params) {
        super(params);
        this.rpmParams = params;
    }

    @Override
    public float getMinRPM() {
        return rpmParams.getMinRPM();
    }

    public RPMProcessingRecipeParams getRPMParams() {
        return rpmParams;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.RPM_PRESSING.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.RPM_PRESSING.getType();
    }

    @Override
    public IRecipeTypeInfo getTypeInfo() {
        return ModRecipeTypes.RPM_PRESSING;
    }
}