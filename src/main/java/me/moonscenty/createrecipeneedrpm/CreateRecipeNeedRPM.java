package me.moonscenty.createrecipeneedrpm;

import com.simibubi.create.api.stress.BlockStressValues;
import me.moonscenty.createrecipeneedrpm.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(CreateRecipeNeedRPM.MOD_ID)
public class CreateRecipeNeedRPM {

    public static final String MOD_ID = "createrecipeneedrpm";

    public CreateRecipeNeedRPM(IEventBus modEventBus) {

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {

            BlockStressValues.IMPACTS.register(
                    ModBlocks.RPM_MILLSTONE.get(),
                    () -> 4.0
            );

            BlockStressValues.IMPACTS.register(
                    ModBlocks.RPM_MECHANICAL_PRESS.get(),
                    () -> 8.0
            );

            BlockStressValues.IMPACTS.register(
                    ModBlocks.RPM_MECHANICAL_MIXER.get(),
                    () -> 4.0
            );

            BlockStressValues.IMPACTS.register(
                    ModBlocks.RPM_CRUSHING_WHEEL.get(),
                    () -> 8.0
            );
        });
    }
}