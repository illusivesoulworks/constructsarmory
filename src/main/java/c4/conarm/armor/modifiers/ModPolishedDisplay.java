package c4.conarm.armor.modifiers;

import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.ArmoryRegistry;
import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.modifiers.ModFortify;

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

      ItemStack kit = ConstructsArmor.polishingKit.getItemstackWithMaterial(((ModPolished) modifier).material);
      ItemStack sand = new ItemStack(Blocks.SAND);

      builder.add(ImmutableList.of(kit, sand));
    }

    return builder.build();
  }
}