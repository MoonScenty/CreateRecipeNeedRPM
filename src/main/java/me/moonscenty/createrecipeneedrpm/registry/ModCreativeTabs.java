package me.moonscenty.createrecipeneedrpm.registry;

import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(
                    Registries.CREATIVE_MODE_TAB,
                    CreateRecipeNeedRPM.MOD_ID
            );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
            CREATIVE_MODE_TABS.register(
                    "main",
                    () -> CreativeModeTab.builder()
                            .title(
                                    Component.translatable(
                                            "itemGroup.createrecipeneedrpm.main"
                                    )
                            )
                            .icon(
                                    () -> new ItemStack(
                                            ModItems.RPM_MILLSTONE.get()
                                    )
                            )
                            .displayItems((parameters, output) -> {

                                output.accept(
                                        ModItems.RPM_MILLSTONE.get()
                                );

                                output.accept(
                                        ModItems.RPM_MECHANICAL_PRESS.get()
                                );

                                output.accept(ModItems.RPM_MECHANICAL_MIXER.get());
                            })
                            .build()
            );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}