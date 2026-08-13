package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.MillingCategory;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMillingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMMillingCategory extends MillingCategory {

    public RPMMillingCategory(
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
        super.draw(
                recipe,
                recipeSlotsView,
                graphics,
                mouseX,
                mouseY
        );

        if (!(recipe instanceof RPMMillingRecipe rpmRecipe)) {
            return;
        }

        Component rpmText = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(rpmRecipe.getMinRPM())
        ).withStyle(ChatFormatting.GOLD);

        graphics.drawString(
                Minecraft.getInstance().font,
                rpmText,
                82,
                8,
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