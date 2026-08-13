package me.moonscenty.createrecipeneedrpm;

import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(CreateRecipeNeedRPM.MOD_ID)
public class CreateRecipeNeedRPM {

    public static final String MOD_ID = "createrecipeneedrpm";

    public CreateRecipeNeedRPM(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
    }
}