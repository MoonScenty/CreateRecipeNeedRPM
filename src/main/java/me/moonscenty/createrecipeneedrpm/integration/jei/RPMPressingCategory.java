package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.PressingCategory;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMPressingCategory extends PressingCategory implements RPMCategoryHelper {

    public RPMPressingCategory(
            CreateRecipeCategory.Info<PressingRecipe> info
    ) {
        super(info);
    }

    @Override
    public void draw(
            PressingRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics graphics,
            double mouseX,
            double mouseY
    ) {
        super.draw(
                recipe,
                recipeSlotsView,
                graphics,
                mouseX,
                mouseY
        );

        if (!(recipe instanceof RPMPressingRecipe rpmRecipe)) {
            return;
        }


        drawRPM(
                graphics,
                rpmRecipe.getMinRPM(),
                88,
                65,
                true
        );
    }
}