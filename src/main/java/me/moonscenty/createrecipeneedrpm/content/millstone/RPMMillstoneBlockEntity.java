package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMillingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeFinder;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

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

        if (level == null)
            return parentAdded;

        float rpm = Math.abs(getSpeed());

        tooltip.add(
                Component.literal("RPM Milling")
                        .withStyle(ChatFormatting.GOLD)
        );

        tooltip.add(
                Component.literal(" Current RPM: ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(
                                Component.literal(formatRPM(rpm) + " RPM")
                                        .withStyle(ChatFormatting.AQUA)
                        )
        );

        if (inputInv.getStackInSlot(0).isEmpty()) {
            tooltip.add(
                    Component.literal(" No recipe input")
                            .withStyle(ChatFormatting.DARK_GRAY)
            );

            return true;
        }

        RecipeWrapper input =
                new RecipeWrapper(inputInv);

        Optional<RPMMillingRecipe> currentRecipe =
                RPMRecipeFinder.find(input, level, rpm)
                        .map(holder ->
                                (RPMMillingRecipe) holder.value()
                        );

        Optional<RPMMillingRecipe> minimumRecipe =
                RPMRecipeFinder.findMinimum(input, level);

        Optional<RPMMillingRecipe> nextRecipe =
                RPMRecipeFinder.findNext(input, level, rpm);

        if (currentRecipe.isPresent()) {

            float required =
                    currentRecipe.get().getMinRPM();

            tooltip.add(
                    Component.literal(" Current Tier: ")
                            .withStyle(ChatFormatting.GRAY)
                            .append(
                                    Component.literal(
                                                    formatRPM(required)
                                                            + " RPM"
                                            )
                                            .withStyle(
                                                    ChatFormatting.GREEN
                                            )
                            )
            );

        } else if (minimumRecipe.isPresent()) {

            float required =
                    minimumRecipe.get().getMinRPM();

            tooltip.add(
                    Component.literal(" Minimum Required: ")
                            .withStyle(ChatFormatting.GRAY)
                            .append(
                                    Component.literal(
                                                    formatRPM(required)
                                                            + " RPM"
                                            )
                                            .withStyle(
                                                    ChatFormatting.RED
                                            )
                            )
            );

            tooltip.add(
                    Component.literal(" Not Fast Enough")
                            .withStyle(ChatFormatting.RED)
            );
        }

        nextRecipe.ifPresent(recipe -> {
            tooltip.add(
                    Component.literal(" Next Tier: ")
                            .withStyle(ChatFormatting.GRAY)
                            .append(
                                    Component.literal(
                                                    formatRPM(
                                                            recipe.getMinRPM()
                                                    )
                                                            + " RPM"
                                            )
                                            .withStyle(
                                                    ChatFormatting.YELLOW
                                            )
                            )
            );
        });

        return true;
    }

    private static String formatRPM(float rpm) {

        if (rpm == (int) rpm)
            return Integer.toString((int) rpm);

        return String.format("%.1f", rpm);
    }
}