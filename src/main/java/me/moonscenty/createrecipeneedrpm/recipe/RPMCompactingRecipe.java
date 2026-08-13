package me.moonscenty.createrecipeneedrpm.recipe;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;

public class RPMCompactingRecipe
        extends BasinRecipe
        implements RPMRequiredRecipe {

    private final RPMProcessingRecipeParams rpmParams;

    public RPMCompactingRecipe(RPMProcessingRecipeParams params) {
        super(ModRecipeTypes.RPM_COMPACTING, params);
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