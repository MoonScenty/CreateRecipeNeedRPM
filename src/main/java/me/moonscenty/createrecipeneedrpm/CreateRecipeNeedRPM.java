package me.moonscenty.createrecipeneedrpm;

import com.simibubi.create.api.stress.BlockStressValues;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import me.moonscenty.createrecipeneedrpm.registry.ModItems;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
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
        });
    }
}