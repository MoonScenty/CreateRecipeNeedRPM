package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMPressingRecipeSerializer
        implements RecipeSerializer<RPMPressingRecipe> {

    private static final MapCodec<RPMPressingRecipe> CODEC =
            RPMProcessingRecipeParams.CODEC.xmap(
                    RPMPressingRecipe::new,
                    RPMPressingRecipe::getRPMParams
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, RPMPressingRecipe>
            STREAM_CODEC =
            RPMProcessingRecipeParams.STREAM_CODEC.map(
                    RPMPressingRecipe::new,
                    RPMPressingRecipe::getRPMParams
            );

    @Override
    public MapCodec<RPMPressingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RPMPressingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}