package me.moonscenty.createrecipeneedrpm.integration.jei;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public interface RPMCategoryHelper {

    default void drawRPM(
            GuiGraphics graphics,
            float minRPM,
            int x,
            int y
    ) {
        drawRPM(
                graphics,
                minRPM,
                x,
                y,
                false
        );
    }

    default void drawRPM(
            GuiGraphics graphics,
            float minRPM,
            int x,
            int y,
            boolean isCenter
    ) {
        var font = Minecraft.getInstance().font;

        Component text = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(minRPM)
        ).withStyle(ChatFormatting.GOLD);

        int drawX = x;

        if (isCenter) {
            drawX -= font.width(text) / 2;
        }

        graphics.drawString(
                font,
                text,
                drawX,
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