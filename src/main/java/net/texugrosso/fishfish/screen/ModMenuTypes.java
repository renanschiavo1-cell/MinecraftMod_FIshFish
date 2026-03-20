package net.texugrosso.fishfish.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.texugrosso.fishfish.FishFish;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, FishFish.MOD_ID);

    // Registra o Menu do nosso Altar
    public static final Supplier<MenuType<ManaAltarMenu>> MANA_ALTAR_MENU =
            MENUS.register("mana_altar_menu", () -> IMenuTypeExtension.create(ManaAltarMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}