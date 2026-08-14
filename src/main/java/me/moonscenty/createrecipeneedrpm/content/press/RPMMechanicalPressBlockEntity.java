package me.moonscenty.createrecipeneedrpm.content.press;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.moonscenty.createrecipeneedrpm.foundation.utility.RPMGoggleTooltip;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCompactingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipe;
import me.moonscenty.createrecipeneedrpm.recipe.RPMRecipeSelector;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class RPMMechanicalPressBlockEntity
        extends MechanicalPressBlockEntity {

    private static final Object RPM_COMPACTING_RECIPES_KEY = new Object();

    public RPMMechanicalPressBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.RPM_MECHANICAL_PRESS.get(),
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

        Optional<BasinBlockEntity> basin =
                getBasin().filter(blockEntity -> !blockEntity.isEmpty());

        if (basin.isPresent()) {
            addCompactingDiagnostics(tooltip);
            return true;
        }

        Optional<ItemStack> input = findPressingInput();
        addPressingDiagnostics(tooltip, input);
        return true;
    }

    private void addCompactingDiagnostics(List<Component> tooltip) {
        RecipeType<RPMCompactingRecipe> type =
                ModRecipeTypes.RPM_COMPACTING.getType();

        Optional<RPMCompactingRecipe> current =
                RPMRecipeSelector.findBestMatching(
                        type,
                        level,
                        getSpeed(),
                        this::matchBasinRecipe
                ).map(RecipeHolder::value);

        Optional<RPMCompactingRecipe> minimum =
                RPMRecipeSelector.findMinimumMatching(
                        type,
                        level,
                        this::matchBasinRecipe
                ).map(RecipeHolder::value);

        Optional<RPMCompactingRecipe> next =
                RPMRecipeSelector.findNextMatching(
                        type,
                        level,
                        getSpeed(),
                        this::matchBasinRecipe
                ).map(RecipeHolder::value);

        RPMGoggleTooltip.add(
                tooltip,
                getSpeed(),
                true,
                current,
                minimum,
                next
        );
    }

    private void addPressingDiagnostics(
            List<Component> tooltip,
            Optional<ItemStack> inputStack
    ) {
        if (inputStack.isEmpty()) {
            RPMGoggleTooltip.add(
                    tooltip,
                    getSpeed(),
                    false,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
            return;
        }

        ItemStack stack = inputStack.get();
        RecipeType<RPMPressingRecipe> type =
                ModRecipeTypes.RPM_PRESSING.getType();

        Optional<RPMPressingRecipe> sequenced =
                SequencedAssemblyRecipe.getRecipe(
                        level,
                        stack,
                        type,
                        RPMPressingRecipe.class
                ).map(RecipeHolder::value);

        if (sequenced.isPresent()) {
            RPMPressingRecipe recipe = sequenced.get();
            Optional<RPMPressingRecipe> current =
                    Math.abs(getSpeed()) >= recipe.getMinRPM()
                            ? sequenced
                            : Optional.empty();

            RPMGoggleTooltip.add(
                    tooltip,
                    getSpeed(),
                    true,
                    current,
                    sequenced,
                    Optional.empty()
            );
            return;
        }

        SingleRecipeInput input = new SingleRecipeInput(stack);
        Optional<RPMPressingRecipe> current =
                RPMRecipeSelector.findBest(
                        type,
                        input,
                        level,
                        getSpeed()
                ).map(RecipeHolder::value);
        Optional<RPMPressingRecipe> minimum =
                RPMRecipeSelector.findMinimum(
                        type,
                        input,
                        level
                ).map(RecipeHolder::value);
        Optional<RPMPressingRecipe> next =
                RPMRecipeSelector.findNext(
                        type,
                        input,
                        level,
                        getSpeed()
                ).map(RecipeHolder::value);

        RPMGoggleTooltip.add(
                tooltip,
                getSpeed(),
                true,
                current,
                minimum,
                next
        );
    }

    private Optional<ItemStack> findPressingInput() {
        TransportedItemStackHandlerBehaviour transportedItems =
                BlockEntityBehaviour.get(
                        level,
                        worldPosition.below(2),
                        TransportedItemStackHandlerBehaviour.TYPE
                );

        if (transportedItems != null) {
            AtomicReference<ItemStack> found =
                    new AtomicReference<>(ItemStack.EMPTY);

            transportedItems.handleProcessingOnAllItems(transported -> {
                if (found.get().isEmpty()) {
                    found.set(transported.stack.copy());
                }
                return TransportedItemStackHandlerBehaviour.TransportedResult
                        .doNothing();
            });

            if (!found.get().isEmpty()) {
                return Optional.of(found.get());
            }
        }

        return level.getEntitiesOfClass(
                        ItemEntity.class,
                        new AABB(worldPosition.below()).deflate(.125F),
                        entity -> entity.isAlive() && entity.onGround()
                ).stream()
                .map(ItemEntity::getItem)
                .filter(stack -> !stack.isEmpty())
                .findFirst();
    }

    @Override
    public Optional<RecipeHolder<PressingRecipe>> getRecipe(ItemStack item) {

        if (level == null || item.isEmpty()) {
            return Optional.empty();
        }

        RecipeType<RPMPressingRecipe> type =
                ModRecipeTypes.RPM_PRESSING.getType();

        // Sequenced Assembly의 rpm_pressing step 먼저 확인
        Optional<RecipeHolder<RPMPressingRecipe>> sequencedRecipe =
                SequencedAssemblyRecipe.getRecipe(
                        level,
                        item,
                        type,
                        RPMPressingRecipe.class
                );

        if (sequencedRecipe.isPresent()) {

            RPMPressingRecipe recipe =
                    sequencedRecipe.get().value();

            if (Math.abs(getSpeed()) < recipe.getMinRPM()) {
                return Optional.empty();
            }

            return sequencedRecipe.map(holder ->
                    new RecipeHolder<PressingRecipe>(
                            holder.id(),
                            holder.value()
                    )
            );
        }

        // 일반 rpm_pressing 레시피
        SingleRecipeInput input =
                new SingleRecipeInput(item);

        return RPMRecipeSelector
                .findBest(
                        type,
                        input,
                        level,
                        getSpeed()
                )
                .map(holder ->
                        new RecipeHolder<PressingRecipe>(
                                holder.id(),
                                holder.value()
                        )
                );
    }

    @Override
    protected boolean matchStaticFilters(
            RecipeHolder<? extends Recipe<?>> recipe
    ) {
        Recipe<?> value = recipe.value();

        boolean automaticPacking =
                value instanceof CraftingRecipe
                        && !(value instanceof MechanicalCraftingRecipe)
                        && MechanicalPressBlockEntity.canCompress(value)
                        && !AllRecipeTypes.shouldIgnoreInAutomation(recipe);

        boolean rpmCompacting =
                value.getType()
                        == ModRecipeTypes.RPM_COMPACTING.getType();

        return automaticPacking || rpmCompacting;
    }

    @Override
    protected List<Recipe<?>> getMatchingRecipes() {

        List<Recipe<?>> recipes =
                super.getMatchingRecipes();

        float speed = Math.abs(getSpeed());

        recipes.removeIf(recipe ->
                recipe instanceof RPMCompactingRecipe rpmRecipe
                        && speed < rpmRecipe.getMinRPM()
        );

        recipes.sort(
                Comparator
                        .comparingInt(
                                (Recipe<?> recipe) ->
                                        recipe.getIngredients().size()
                        )
                        .reversed()
                        .thenComparing(
                                Comparator.comparingDouble(
                                        recipe ->
                                                recipe instanceof RPMCompactingRecipe rpmRecipe
                                                        ? rpmRecipe.getMinRPM()
                                                        : -1
                                ).reversed()
                        )
        );

        return recipes;
    }

    @Override
    public boolean tryProcessInBasin(boolean simulate) {
        List<Recipe<?>> recipes =
                getMatchingRecipes();

        if (recipes.isEmpty()) {
            return false;
        }

        currentRecipe = recipes.getFirst();

        return super.tryProcessInBasin(simulate);
    }

    @Override
    public void onPressingCompleted() {

        if (pressingBehaviour.onBasin()
                && currentRecipe instanceof RPMCompactingRecipe) {

            basinChecker.scheduleUpdate();
            return;
        }

        super.onPressingCompleted();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return RPM_COMPACTING_RECIPES_KEY;
    }
}
