package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class RPMProcessingRecipeParams extends ProcessingRecipeParams {

    public static final MapCodec<RPMProcessingRecipeParams> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(

                    codec(RPMProcessingRecipeParams::new)
                            .forGetter(Function.identity()),

                    Codec.FLOAT
                            .fieldOf("min_rpm")
                            .forGetter(RPMProcessingRecipeParams::getMinRPM)

            ).apply(instance, (params, minRPM) -> {
                params.minRPM = minRPM;
                return params;
            }));

    public static final StreamCodec<RegistryFriendlyByteBuf, RPMProcessingRecipeParams>
            STREAM_CODEC =
            streamCodec(RPMProcessingRecipeParams::new);

    private float minRPM;

    public RPMProcessingRecipeParams() {
        super();
    }

    public float getMinRPM() {
        return minRPM;
    }

    @Override
    protected void encode(RegistryFriendlyByteBuf buffer) {
        super.encode(buffer);
        ByteBufCodecs.FLOAT.encode(buffer, minRPM);
    }

    @Override
    protected void decode(RegistryFriendlyByteBuf buffer) {
        super.decode(buffer);
        minRPM = ByteBufCodecs.FLOAT.decode(buffer);
    }
}