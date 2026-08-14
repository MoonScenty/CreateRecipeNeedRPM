package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RPMCrushingRecipe
        extends CrushingRecipe
        implements RPMRequiredRecipe {

    private final RPMProcessingRecipeParams rpmParams;

    public RPMCrushingRecipe(RPMProcessingRecipeParams params) {
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
        return ModRecipeTypes.RPM_CRUSHING.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.RPM_CRUSHING.getType();
    }

    @Override
    public IRecipeTypeInfo getTypeInfo() {
        return ModRecipeTypes.RPM_CRUSHING;
    }
}