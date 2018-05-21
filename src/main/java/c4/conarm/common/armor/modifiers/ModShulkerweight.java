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

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingEvent;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.List;

public class ModShulkerweight extends ArmorModifierTrait {

    protected int max;

    public ModShulkerweight(int count) {
        super("shulkerweight", 0xaaccff, 1, count);
        this.max = count;
    }

    protected float getJumpBonus(ItemStack stack) {
        NBTTagCompound modifierTag = new NBTTagCompound();
        NBTTagList tagList = TagUtil.getModifiersTagList(TagUtil.getTagSafe(stack));
        int index = TinkerUtil.getIndexInList(tagList, identifier);
        if(index >= 0) {
            modifierTag = tagList.getCompoundTagAt(index);
        }
        ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);
        return getJumpBonus(modData);
    }

    protected float getJumpBonus(ModifierNBT.IntegerNBT modData) {
        return 0.4F * modData.current / this.max;
    }

    @Override
    public void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt) {
        player.motionY += getJumpBonus(armor);
    }
}
