package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;

public class RPMMixingRecipe
        extends BasinRecipe
        implements RPMRequiredRecipe {

    private final RPMProcessingRecipeParams rpmParams;

    public RPMMixingRecipe(RPMProcessingRecipeParams params) {
        super(ModRecipeTypes.RPM_MIXING, params);
        this.rpmParams = params;
    }

    @Override
    public float getMinRPM() {
        return rpmParams.getMinRPM();
    }

    public RPMProcessingRecipeParams getRPMParams() {
        return rpmParams;
    }
}