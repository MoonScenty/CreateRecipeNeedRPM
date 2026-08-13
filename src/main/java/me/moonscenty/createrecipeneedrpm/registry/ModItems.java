package me.moonscenty.createrecipeneedrpm.registry;

import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CreateRecipeNeedRPM.MOD_ID);

    public static final DeferredItem<BlockItem> RPM_MILLSTONE =
            ITEMS.registerSimpleBlockItem(ModBlocks.RPM_MILLSTONE);
    public static final DeferredItem<AssemblyOperatorBlockItem>
            RPM_MECHANICAL_PRESS =
            ITEMS.register(
                    "rpm_mechanical_press",
                    () -> new AssemblyOperatorBlockItem(
                            ModBlocks.RPM_MECHANICAL_PRESS.get(),
                            new Item.Properties()
                    )
            );
    private ModItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}