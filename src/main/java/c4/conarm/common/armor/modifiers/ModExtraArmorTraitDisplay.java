/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.common.armor.modifiers;

import c4.conarm.common.ConstructsRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.tools.modifiers.ModExtraTrait;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ModExtraArmorTraitDisplay extends Modifier implements IModifierDisplay {

  public ModExtraArmorTraitDisplay() {
    super(ModExtraTrait.EXTRA_TRAIT_IDENTIFIER + "_armor");
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
    return ConstructsRegistry.chestplate.getRequiredComponents().stream()
                              .map(PartMaterialType::getPossibleParts)
                              .flatMap(Collection::stream)
                              .map(this::getItems)
                              .collect(Collectors.toList());
  }

  private List<ItemStack> getItems(IToolPart part) {
    List<Material> possibleMaterials = TinkerRegistry.getAllMaterials().stream()
                                                     .filter(part::canUseMaterial)
                                                     .collect(Collectors.toList());
    Material material = possibleMaterials.get(new Random().nextInt(possibleMaterials.size()));

    return ImmutableList.<ItemStack>builder()
        .add(part.getItemstackWithMaterial(material))
        .addAll(ModExtraTrait.EMBOSSMENT_ITEMS)
        .build();
  }
}