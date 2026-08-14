package me.moonscenty.createrecipeneedrpm.content.millstone;

import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.foundation.utility.RPMGoggleTooltip;
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

        boolean hasInput = !inputInv.getStackInSlot(0).isEmpty();
        if (!hasInput) {
            RPMGoggleTooltip.add(
                    tooltip,
                    getSpeed(),
                    false,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
            return true;
        }

        float rpm = Math.abs(getSpeed());
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

        RPMGoggleTooltip.add(
                tooltip,
                rpm,
                true,
                currentRecipe,
                minimumRecipe,
                nextRecipe
        );

        return true;
    }
}
