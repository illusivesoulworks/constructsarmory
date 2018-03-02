package c4.conarm.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
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
