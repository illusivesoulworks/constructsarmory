/*
 * Copyright (c) 2018-2019 <C4>
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

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;

public class ModConcealed extends ArmorModifierTrait {

    public ModConcealed() {
        super("concealed", 0xfff5cc);
        this.aspects.clear();
        addAspects(new ModifierAspect.DataAspect(this), new ModifierAspect.SingleAspect(this));
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return otherModifier != ArmorModifiers.modTravelSneak;
    }

    @Override
    public boolean disableRendering(ItemStack armor, EntityLivingBase entityLivingBase) {
        return true;
    }
}
