package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RPMMillingRecipe
        extends MillingRecipe
        implements RPMRequiredRecipe {

    private final RPMMillingRecipeParams rpmParams;

    public RPMMillingRecipe(RPMMillingRecipeParams params) {
        super(params);
        this.rpmParams = params;
    }

    @Override
    public float getMinRPM() {
        return rpmParams.getMinRPM();
    }

    public RPMMillingRecipeParams getRPMParams() {
        return rpmParams;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.RPM_MILLING.getSerializer();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.RPM_MILLING.getType();
    }

    @Override
    public IRecipeTypeInfo getTypeInfo() {
        return ModRecipeTypes.RPM_MILLING;
    }
}