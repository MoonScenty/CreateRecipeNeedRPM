package me.moonscenty.createrecipeneedrpm.registry;

import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.content.millstone.RPMMillstoneBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateRecipeNeedRPM.MOD_ID);

    public static final DeferredBlock<RPMMillstoneBlock> RPM_MILLSTONE =
            BLOCKS.register(
                    "rpm_millstone",
                    () -> new RPMMillstoneBlock(
                            BlockBehaviour.Properties.of()
                                    .strength(3.0F)
                    )
            );

    private ModBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}