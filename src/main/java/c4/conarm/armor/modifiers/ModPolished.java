package c4.conarm.armor.modifiers;

import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.client.IArmorMaterialTexture;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.modifiers.ArmorModifier;
import c4.conarm.lib.tinkering.TinkersArmor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import javax.tools.Tool;

public class ModPolished extends ToolModifier implements IArmorMaterialTexture {

    public final Material material;

    public ModPolished(Material material) {
        super("polished_armor" + material.getIdentifier(), material.materialTextColor);

        if(!material.hasStats(ArmorMaterialType.PLATES)) {
            throw new TinkerAPIException(String.format("Trying to add a polished-modifier for a material without armor stats: %s", material.getIdentifier()));
        }

        this.material = material;
        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this));

        ItemStack kit = ConstructsArmor.polishingKit.getItemstackWithMaterial(material);
        ItemStack sand = new ItemStack(Blocks.SAND);
        addRecipeMatch(new RecipeMatch.ItemCombination(1, kit, sand));
    }

    @Override
    public String getBaseTexture() {
        return "conarm:models/modifiers/mod_polished_armor_" + material.getIdentifier();
    }

    @Override
    public String getLocalizedName() {
        return Util.translate(LOC_Name, "polished_armor") + " (" + material.getLocalizedName() + ")";
    }

    @Override
    public String getLocalizedDesc() {
        return Util.translateFormatted(String.format(LOC_Desc, "polished_armor"), material.getLocalizedName());
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
        PlatesMaterialStats stats = material.getStats(ArmorMaterialType.PLATES);
        tag.setFloat(ArmorTagUtil.TOUGHNESS, stats.toughness);

        NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound mod = tagList.getCompoundTagAt(i);
            ModifierNBT data = ModifierNBT.readTag(mod);

            if(data.identifier.equals(this.identifier)) {
                break;
            }

            if(data.identifier.startsWith("polished_armor")) {
                tagList.removeTag(i);
                i--;
            }
        }

        TagUtil.setModifiersTagList(rootCompound, tagList);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {

        return stack.getItem() instanceof TinkersArmor;
    }

    @Override
    public boolean hasTexturePerMaterial() {
        return true;
    }

}
