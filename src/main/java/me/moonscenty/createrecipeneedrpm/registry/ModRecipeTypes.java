package me.moonscenty.createrecipeneedrpm.registry;

import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCompactingRecipeSerializer;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMillingRecipeSerializer;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipeSerializer;
import me.moonscenty.createrecipeneedrpm.recipe.RPMMixingRecipeSerializer;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCrushingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

public enum ModRecipeTypes implements IRecipeTypeInfo {

    RPM_MILLING(RPMMillingRecipeSerializer::new),
    RPM_PRESSING(RPMPressingRecipeSerializer::new),
    RPM_COMPACTING(RPMCompactingRecipeSerializer::new),
    RPM_MIXING(RPMMixingRecipeSerializer::new),
    RPM_CRUSHING(RPMCrushingRecipeSerializer::new);

    private final ResourceLocation id;
    private final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializer;
    private final DeferredHolder<RecipeType<?>, RecipeType<?>> type;

    ModRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = name().toLowerCase(Locale.ROOT);

        this.id = ResourceLocation.fromNamespaceAndPath(
                CreateRecipeNeedRPM.MOD_ID,
                name
        );

        this.serializer = Registers.SERIALIZERS.register(
                name,
                serializerSupplier
        );

        this.type = Registers.TYPES.register(
                name,
                () -> RecipeType.simple(id)
        );
    }

    public static void register(IEventBus modEventBus) {
        Registers.SERIALIZERS.register(modEventBus);
        Registers.TYPES.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializer.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I extends RecipeInput, R extends Recipe<I>> RecipeType<R> getType() {
        return (RecipeType<R>) type.get();
    }

    private static class Registers {

        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
                DeferredRegister.create(
                        Registries.RECIPE_SERIALIZER,
                        CreateRecipeNeedRPM.MOD_ID
                );

        private static final DeferredRegister<RecipeType<?>> TYPES =
                DeferredRegister.create(
                        Registries.RECIPE_TYPE,
                        CreateRecipeNeedRPM.MOD_ID
                );
    }
}