package me.moonscenty.createrecipeneedrpm.content.millstone;

import static net.minecraft.ChatFormatting.AQUA;
import static net.minecraft.ChatFormatting.DARK_GRAY;
import static net.minecraft.ChatFormatting.GOLD;
import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.GREEN;
import static net.minecraft.ChatFormatting.RED;
import static net.minecraft.ChatFormatting.YELLOW;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.foundation.utility.CreateRecipeNeedRPMLang;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMillingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeFinder;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Optional;

public class RPMMillstoneBlockEntity extends MillstoneBlockEntity {

    public RPMMillstoneBlockEntity(BlockPos pos, BlockState state) {
        super(
                ModBlockEntities.RPM_MILLSTONE.get(),
                pos,
                state
        );
    }

    @Override
    public boolean addToGoggleTooltip(
            List<Component> tooltip,
            boolean isPlayerSneaking
    ) {
        boolean parentAdded =
                super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        if (level == null) {
            return parentAdded;
        }

        float rpm = Math.abs(getSpeed());

        // ─────────────────────────────
        // RPM Recipe
        // ─────────────────────────────

        CreateRecipeNeedRPMLang
                .translate("gui.goggles.rpm_recipe")
                .style(GOLD)
                .forGoggles(tooltip);

        // Current RPM
        CreateRecipeNeedRPMLang
                .translate("tooltip.current_rpm")
                .style(GRAY)
                .forGoggles(tooltip);

        CreateRecipeNeedRPMLang
                .number(rpm)
                .translate("generic.unit.rpm")
                .style(AQUA)
                .forGoggles(tooltip, 1);

        if (inputInv.getStackInSlot(0).isEmpty()) {
            CreateRecipeNeedRPMLang
                    .translate("gui.goggles.no_recipe_input")
                    .style(DARK_GRAY)
                    .forGoggles(tooltip, 1);

            return true;
        }

        RecipeWrapper input = new RecipeWrapper(inputInv);

        Optional<RPMMillingRecipe> currentRecipe =
                RPMRecipeFinder.find(input, level, rpm)
                        .map(RecipeHolder::value)
                        .filter(RPMMillingRecipe.class::isInstance)
                        .map(RPMMillingRecipe.class::cast);

        Optional<RPMMillingRecipe> minimumRecipe =
                RPMRecipeFinder.findMinimum(input, level);

        Optional<RPMMillingRecipe> nextRecipe =
                RPMRecipeFinder.findNext(input, level, rpm);

        if (currentRecipe.isPresent()) {

            CreateRecipeNeedRPMLang
                    .translate("tooltip.current_tier")
                    .style(GRAY)
                    .forGoggles(tooltip);

            CreateRecipeNeedRPMLang
                    .number(currentRecipe.get().getMinRPM())
                    .translate("generic.unit.rpm")
                    .style(GREEN)
                    .forGoggles(tooltip, 1);

        } else if (minimumRecipe.isPresent()) {

            CreateRecipeNeedRPMLang
                    .translate("tooltip.minimum_required_rpm")
                    .style(GRAY)
                    .forGoggles(tooltip);

            CreateRecipeNeedRPMLang
                    .number(minimumRecipe.get().getMinRPM())
                    .translate("generic.unit.rpm")
                    .style(RED)
                    .forGoggles(tooltip, 1);

            CreateRecipeNeedRPMLang
                    .translate("gui.goggles.not_fast_enough")
                    .style(RED)
                    .forGoggles(tooltip, 1);
        }

        nextRecipe.ifPresent(recipe -> {

            CreateRecipeNeedRPMLang
                    .translate("tooltip.next_tier")
                    .style(GRAY)
                    .forGoggles(tooltip);

            CreateRecipeNeedRPMLang
                    .number(recipe.getMinRPM())
                    .translate("generic.unit.rpm")
                    .style(YELLOW)
                    .forGoggles(tooltip, 1);
        });

        return true;
    }

    private static String formatRPM(float rpm) {

        if (rpm == (int) rpm)
            return Integer.toString((int) rpm);

        return String.format("%.1f", rpm);
    }
}