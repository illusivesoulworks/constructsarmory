package top.theillusivec4.constructsarmory.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.Modifier;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public abstract class ConstructsArmoryModule {

  protected static final ItemDeferredRegisterExtension
      ITEMS = new ItemDeferredRegisterExtension(ConstructsArmoryMod.MOD_ID);
  protected static final DeferredRegister<Modifier>
      MODIFIERS = DeferredRegister.create(Modifier.class, ConstructsArmoryMod.MOD_ID);

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    ITEMS.register(bus);
    MODIFIERS.register(bus);
  }
}
