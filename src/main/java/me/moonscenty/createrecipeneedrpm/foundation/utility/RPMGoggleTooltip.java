package me.moonscenty.createrecipeneedrpm.foundation.utility;

import me.moonscenty.createrecipeneedrpm.recipe.RPMRequiredRecipe;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

import static net.minecraft.ChatFormatting.AQUA;
import static net.minecraft.ChatFormatting.DARK_GRAY;
import static net.minecraft.ChatFormatting.GOLD;
import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.GREEN;
import static net.minecraft.ChatFormatting.RED;
import static net.minecraft.ChatFormatting.YELLOW;

public final class RPMGoggleTooltip {

    private RPMGoggleTooltip() {
    }

    public static void add(
            List<Component> tooltip,
            float rpm,
            boolean hasInput,
            Optional<? extends RPMRequiredRecipe> currentRecipe,
            Optional<? extends RPMRequiredRecipe> minimumRecipe,
            Optional<? extends RPMRequiredRecipe> nextRecipe
    ) {
        CreateRecipeNeedRPMLang
                .translate("gui.goggles.rpm_recipe")
                .style(GOLD)
                .forGoggles(tooltip);

        CreateRecipeNeedRPMLang
                .translate("tooltip.current_rpm")
                .style(GRAY)
                .forGoggles(tooltip);

        CreateRecipeNeedRPMLang
                .number(Math.abs(rpm))
                .translate("generic.unit.rpm")
                .style(AQUA)
                .forGoggles(tooltip, 1);

        if (!hasInput) {
            CreateRecipeNeedRPMLang
                    .translate("gui.goggles.no_recipe_input")
                    .style(DARK_GRAY)
                    .forGoggles(tooltip, 1);
            return;
        }

        if (currentRecipe.isPresent()) {
            CreateRecipeNeedRPMLang
                    .translate("tooltip.current_tier")
                    .style(GRAY)
                    .forGoggles(tooltip);

            addRPM(tooltip, currentRecipe.get().getMinRPM(), GREEN);

            if (nextRecipe.isPresent()) {
                CreateRecipeNeedRPMLang
                        .translate("tooltip.next_tier")
                        .style(GRAY)
                        .forGoggles(tooltip);

                addRPM(tooltip, nextRecipe.get().getMinRPM(), YELLOW);
            } else {
                CreateRecipeNeedRPMLang
                        .translate("tooltip.highest_tier")
                        .style(GOLD)
                        .forGoggles(tooltip, 1);
            }
            return;
        }

        if (minimumRecipe.isPresent()) {
            CreateRecipeNeedRPMLang
                    .translate("tooltip.minimum_required_rpm")
                    .style(GRAY)
                    .forGoggles(tooltip);

            addRPM(tooltip, minimumRecipe.get().getMinRPM(), RED);

            CreateRecipeNeedRPMLang
                    .translate("gui.goggles.not_fast_enough")
                    .style(RED)
                    .forGoggles(tooltip, 1);
        }
    }

    private static void addRPM(
            List<Component> tooltip,
            float rpm,
            net.minecraft.ChatFormatting color
    ) {
        CreateRecipeNeedRPMLang
                .number(rpm)
                .translate("generic.unit.rpm")
                .style(color)
                .forGoggles(tooltip, 1);
    }
}
