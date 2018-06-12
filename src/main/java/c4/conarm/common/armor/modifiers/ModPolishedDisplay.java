/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
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