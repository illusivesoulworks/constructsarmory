/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import c4.conarm.lib.ArmoryRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.util.List;

public class ModPolishedDisplay extends Modifier implements IModifierDisplay {

  public ModPolishedDisplay() {
    super("polished_armor");
  }

  @Override
  public boolean hasTexturePerMaterial() {
    return true;
  }

  @Override
  public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
    //NO-OP
  }

  @Override
  public int getColor() {
    return 0xdddddd;
  }

  @Override
  public List<List<ItemStack>> getItems() {
    ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
    for(IModifier modifier : ArmoryRegistry.getAllArmorModifiers()) {
      if(!(modifier instanceof ModPolished)) {
        continue;
      }

      ItemStack kit = ConstructsRegistry.polishingKit.getItemstackWithMaterial(((ModPolished) modifier).material);
      ItemStack sand = new ItemStack(Blocks.SAND);

      builder.add(ImmutableList.of(kit, sand));
    }

    return builder.build();
  }
}