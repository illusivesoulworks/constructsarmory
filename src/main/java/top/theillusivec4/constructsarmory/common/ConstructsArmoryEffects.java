package top.theillusivec4.constructsarmory.common;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public class ConstructsArmoryEffects {

  protected static final DeferredRegister<Effect> POTIONS =
      DeferredRegister.create(ForgeRegistries.POTIONS, ConstructsArmoryMod.MOD_ID);

  private static final IntFunction<Supplier<TinkerEffect>> MARKER_EFFECT =
      color -> () -> new NoMilkEffect(EffectType.BENEFICIAL, color, true);

  public static final RegistryObject<TinkerEffect> VENGEFUL =
      POTIONS.register("vengeful", MARKER_EFFECT.apply(0x9261cc));
  public static final RegistryObject<TinkerEffect> ACCELERATION =
      POTIONS.register("acceleration", MARKER_EFFECT.apply(0x60496b));
  public static final RegistryObject<TinkerEffect> MALIGNANT =
      POTIONS.register("malignant", MARKER_EFFECT.apply(0x4d4d4d));
  public static final RegistryObject<TinkerEffect> BLOODLETTING =
      POTIONS.register("bloodletting", MARKER_EFFECT.apply(0xb30000));

  public static void init() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    POTIONS.register(bus);
  }
}
