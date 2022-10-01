/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.common;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.TinkerEffect;
import slimeknights.tconstruct.tools.modifiers.effect.NoMilkEffect;
import com.illusivesoulworks.constructsarmory.ConstructsArmoryMod;

public class ConstructsArmoryEffects {

  protected static final DeferredRegister<MobEffect> POTIONS =
      DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ConstructsArmoryMod.MOD_ID);

  private static final IntFunction<Supplier<TinkerEffect>> MARKER_EFFECT =
      color -> () -> new NoMilkEffect(MobEffectCategory.BENEFICIAL, color, true);

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
