package me.moonscenty.createrecipeneedrpm;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.stress.BlockStressValues;
import me.moonscenty.createrecipeneedrpm.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

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

            // 원본 Create 블록의 스트레스 값을 그대로 따르도록 위임한다.
            // supplier는 매번 평가되므로 Create 서버 config(stressValues) 변경이 즉시 반영된다.
            copyImpact(ModBlocks.RPM_MILLSTONE.get(), AllBlocks.MILLSTONE::get);
            copyImpact(ModBlocks.RPM_MECHANICAL_PRESS.get(), AllBlocks.MECHANICAL_PRESS::get);
            copyImpact(ModBlocks.RPM_MECHANICAL_MIXER.get(), AllBlocks.MECHANICAL_MIXER::get);
            copyImpact(ModBlocks.RPM_CRUSHING_WHEEL.get(), AllBlocks.CRUSHING_WHEEL::get);
        });
    }

    private static void copyImpact(Block block, Supplier<? extends Block> original) {
        BlockStressValues.IMPACTS.register(
                block,
                () -> BlockStressValues.getImpact(original.get())
        );
    }
}
