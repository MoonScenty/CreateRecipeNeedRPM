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

public class RPMPressingCategory extends PressingCategory {

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

        var font = Minecraft.getInstance().font;

        Component text = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(rpmRecipe.getMinRPM())
        ).withStyle(ChatFormatting.GOLD);

        int x = (177 - font.width(text)) / 2;
        int y = 66;

        graphics.drawString(
                font,
                text,
                x,
                y,
                0xFFFFFF,
                false
        );
    }

    private static String formatRPM(float rpm) {
        if (rpm == (int) rpm) {
            return Integer.toString((int) rpm);
        }

        return String.format("%.1f", rpm);
    }
}