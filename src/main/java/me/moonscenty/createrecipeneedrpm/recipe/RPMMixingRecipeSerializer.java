package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMMixingRecipeSerializer
        implements RecipeSerializer<RPMMixingRecipe> {

    private static final MapCodec<RPMMixingRecipe> CODEC =
            RPMProcessingRecipeParams.CODEC.xmap(
                    RPMMixingRecipe::new,
                    RPMMixingRecipe::getRPMParams
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, RPMMixingRecipe>
            STREAM_CODEC =
            RPMProcessingRecipeParams.STREAM_CODEC.map(
                    RPMMixingRecipe::new,
                    RPMMixingRecipe::getRPMParams
            );

    @Override
    public MapCodec<RPMMixingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RPMMixingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}