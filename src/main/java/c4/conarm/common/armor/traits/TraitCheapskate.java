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
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

public class TraitCheapskate extends AbstractArmorTrait {

    public TraitCheapskate() {
        super("cheapskate", TextFormatting.GRAY);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onArmorBuilding(ArmoryEvent.OnItemBuilding evt) {
        if(TinkerUtil.hasTrait(evt.tag, this.getIdentifier())) {
            ArmorNBT data = ArmorTagUtil.getArmorStats(evt.tag);
            data.durability = Math.max(1, (data.durability * 80) / 100);
            TagUtil.setToolTag(evt.tag, data.get());
        }
    }
}
