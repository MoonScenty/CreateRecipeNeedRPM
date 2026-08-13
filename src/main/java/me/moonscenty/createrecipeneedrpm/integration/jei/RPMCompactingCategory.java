package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCompactingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMCompactingCategory extends BasinCategory {

    private final AnimatedPress press = new AnimatedPress(true);
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

    public RPMCompactingCategory(CreateRecipeCategory.Info<BasinRecipe> info) {
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
        // Create 기본 Basin JEI
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);

        // Create PackingCategory.standard와 동일한 렌더링
        HeatCondition requiredHeat = recipe.getRequiredHeat();

        if (requiredHeat != HeatCondition.NONE) {
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
                    .draw(
                            graphics,
                            getBackground().getWidth() / 2 + 3,
                            55
                    );
        }

        press.draw(
                graphics,
                getBackground().getWidth() / 2 + 3,
                34
        );

        // 우리 모드 추가 부분
        if (recipe instanceof RPMCompactingRecipe rpmRecipe) {
            drawMinimumRPM(graphics, rpmRecipe.getMinRPM());
        }
    }

    private void drawMinimumRPM(GuiGraphics graphics, float minRPM) {
        var font = Minecraft.getInstance().font;

        Component text = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(minRPM)
        ).withStyle(ChatFormatting.GOLD);

        int x = 5;
        int y = 6;

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