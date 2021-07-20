package top.theillusivec4.constructsarmory.common.modifier;

import java.util.function.Supplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.modifiers.EmptyModifier;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;

public final class ConstructsArmoryModifiers extends TinkerModule {

  protected static final Supplier<IForgeRegistry<Modifier>> MODIFIER_REGISTRY = MODIFIERS
      .makeRegistry("modifiers", () -> new RegistryBuilder<Modifier>().setType(Modifier.class)
          .setDefaultKey(ConstructsArmoryMod.getResource("empty")));

  /*
   * Modifiers
   */
  public static final RegistryObject<EmptyModifier> empty =
      MODIFIERS.register("empty", EmptyModifier::new);
}
