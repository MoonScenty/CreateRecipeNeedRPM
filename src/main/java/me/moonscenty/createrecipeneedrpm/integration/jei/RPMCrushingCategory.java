package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.CrushingCategory;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCrushingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMCrushingCategory extends CrushingCategory implements RPMCategoryHelper{

    public RPMCrushingCategory(
            CreateRecipeCategory.Info<AbstractCrushingRecipe> info
    ) {
        super(info);
    }

    @Override
    public void draw(
            AbstractCrushingRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics graphics,
            double mouseX,
            double mouseY
    ) {
        // Create 기본 Crushing Wheel JEI 애니메이션
        super.draw(
                recipe,
                recipeSlotsView,
                graphics,
                mouseX,
                mouseY
        );

        if (!(recipe instanceof RPMCrushingRecipe rpmRecipe)) {
            return;
        }

        drawRPM(
                graphics,
                rpmRecipe.getMinRPM(),
                95,
                6
        );

    }

}