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

package c4.conarm.common.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class TraitIndomitable extends AbstractArmorTrait {

    private static final float MULTIPLIER = 0.2F;

    public TraitIndomitable() {
        super("indomitable", 0xffffff);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        if(!TinkerUtil.hasTrait(rootCompound, identifier)) {
            ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
            ArmorNBT original = ArmorTagUtil.getOriginalArmorStats(rootCompound);
            data.defense += original.defense * MULTIPLIER;
            data.toughness += original.toughness * MULTIPLIER;
            TagUtil.setToolTag(rootCompound, data.get());
        }
        super.applyEffect(rootCompound, modifierTag);
    }
}
