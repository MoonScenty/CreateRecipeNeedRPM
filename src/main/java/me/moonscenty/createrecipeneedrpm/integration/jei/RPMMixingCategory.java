package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedMixer;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMixingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMMixingCategory extends BasinCategory implements RPMCategoryHelper {

    private final AnimatedMixer mixer = new AnimatedMixer();
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

    public RPMMixingCategory(
            CreateRecipeCategory.Info<BasinRecipe> info
    ) {
        super(info, true);
    }

    @Override
    public void draw(
            BasinRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics graphics,
            double mouseX,
            double mouseY
    ) {
        // Create 기본 Basin UI
        super.draw(
                recipe,
                recipeSlotsView,
                graphics,
                mouseX,
                mouseY
        );

        // Create MixingCategory의 MIXING 렌더링과 동일
        HeatCondition requiredHeat = recipe.getRequiredHeat();

        if (requiredHeat != HeatCondition.NONE) {
            heater.withHeat(
                    requiredHeat.visualizeAsBlazeBurner()
            ).draw(
                    graphics,
                    getBackground().getWidth() / 2 + 3,
                    55
            );
        }

        mixer.draw(
                graphics,
                getBackground().getWidth() / 2 + 3,
                34
        );

        // RPM 전용 표시
        if (!(recipe instanceof RPMMixingRecipe rpmRecipe)) {
            return;
        }

        drawRPM(
                graphics,
                rpmRecipe.getMinRPM(),
                5,
                6
        );
    }
}