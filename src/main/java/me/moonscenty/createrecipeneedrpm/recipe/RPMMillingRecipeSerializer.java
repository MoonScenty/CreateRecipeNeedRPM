package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMMillingRecipeSerializer
        implements RecipeSerializer<RPMMillingRecipe> {

    private static final MapCodec<RPMMillingRecipe> CODEC =
            RPMProcessingRecipeParams.CODEC.xmap(
                    RPMMillingRecipe::new,
                    RPMMillingRecipe::getRPMParams
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, RPMMillingRecipe> STREAM_CODEC =
            RPMProcessingRecipeParams.STREAM_CODEC.map(
                    RPMMillingRecipe::new,
                    RPMMillingRecipe::getRPMParams
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