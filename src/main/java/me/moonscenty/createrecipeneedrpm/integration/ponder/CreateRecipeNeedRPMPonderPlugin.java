package me.moonscenty.createrecipeneedrpm.integration.ponder;

import com.simibubi.create.Create;
import com.simibubi.create.infrastructure.ponder.scenes.ProcessingScenes;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class CreateRecipeNeedRPMPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return Create.ID;
    }

    @Override
    public void registerScenes(
            PonderSceneRegistrationHelper<ResourceLocation> helper
    ) {
        // RPM Millstone -> Create 기본 Millstone Ponder
        helper.addStoryBoard(
                BuiltInRegistries.BLOCK.getKey(
                        ModBlocks.RPM_MILLSTONE.get()
                ),
                Create.asResource("millstone"),
                ProcessingScenes::millstone
        );

        // RPM Mechanical Press -> Create 기본 Pressing Ponder
        helper.addStoryBoard(
                BuiltInRegistries.BLOCK.getKey(
                        ModBlocks.RPM_MECHANICAL_PRESS.get()
                ),
                Create.asResource("mechanical_press/pressing"),
                ProcessingScenes::pressing
        );

        helper.addStoryBoard(
                BuiltInRegistries.BLOCK.getKey(
                        ModBlocks.RPM_MECHANICAL_PRESS.get()
                ),
                Create.asResource("mechanical_press/compacting"),
                ProcessingScenes::compacting
        );

        helper.addStoryBoard(
                BuiltInRegistries.BLOCK.getKey(
                        ModBlocks.RPM_MECHANICAL_MIXER.get()
                ),
                Create.asResource("mechanical_mixer/mixing"),
                ProcessingScenes::mixing
        );
    }
}