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
        Component text = Component.translatable(
                "createrecipeneedrpm.recipe.minimum_rpm",
                formatRPM(minRPM)
        ).withStyle(ChatFormatting.GOLD);

        drawText(
                graphics,
                text,
                x,
                y,
                isCenter
        );
    }

    /**
     * Sequenced Assembly 등 좁은 영역에서
     * "64 RPM" 형태로 표시한다.
     */
    default void drawRPMValue(
            GuiGraphics graphics,
            float minRPM,
            int x,
            int y
    ) {
        drawRPMValue(
                graphics,
                minRPM,
                x,
                y,
                true
        );
    }

    default void drawRPMValue(
            GuiGraphics graphics,
            float minRPM,
            int x,
            int y,
            boolean isCenter
    ) {
        Component text = Component.literal(
                formatRPM(minRPM) + " RPM"
        ).withStyle(ChatFormatting.GOLD);

        drawText(
                graphics,
                text,
                x,
                y,
                isCenter
        );
    }

    default void drawText(
            GuiGraphics graphics,
            Component text,
            int x,
            int y,
            boolean isCenter
    ) {
        var font = Minecraft.getInstance().font;

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

    default void drawRPMStacked(
            GuiGraphics graphics,
            float minRPM,
            int x,
            int y
    ) {
        var font = Minecraft.getInstance().font;

        Component rpmValue = Component.literal(
                formatRPM(minRPM)
        ).withStyle(ChatFormatting.GOLD);

        Component rpmUnit = Component.literal(
                "RPM"
        ).withStyle(ChatFormatting.GOLD);

        int valueX = x - font.width(rpmValue) / 2;
        int unitX = x - font.width(rpmUnit) / 2;

        graphics.drawString(
                font,
                rpmValue,
                valueX,
                y,
                0xFFFFFF,
                false
        );

        graphics.drawString(
                font,
                rpmUnit,
                unitX,
                y + font.lineHeight,
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