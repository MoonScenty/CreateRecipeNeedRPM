package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMMillingRecipeSerializer
        implements RecipeSerializer<RPMMillingRecipe> {

    private static final MapCodec<RPMMillingRecipe> CODEC =
            ProcessingRecipe.codec(
                    RPMMillingRecipe::new,
                    RPMMillingRecipeParams.CODEC
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, RPMMillingRecipe> STREAM_CODEC =
            ProcessingRecipe.streamCodec(
                    RPMMillingRecipe::new,
                    RPMMillingRecipeParams.STREAM_CODEC
            );

    @Override
    public MapCodec<RPMMillingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RPMMillingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}