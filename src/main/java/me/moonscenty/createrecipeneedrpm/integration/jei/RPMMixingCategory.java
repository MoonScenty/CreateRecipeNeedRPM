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

public class RPMMixingCategory extends BasinCategory {

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

        var font = Minecraft.getInstance().font;

        Component text = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(rpmRecipe.getMinRPM())
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