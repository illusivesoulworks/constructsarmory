package c4.conarm.armor.traits;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.potion.TinkerPotion;

public class TraitSuperhot extends AbstractArmorTrait {

    public static TinkerPotion superhotPotion = new SuperhotPotion();

    public TraitSuperhot() {
        super("superhot", 0xffffff);
    }

    @Override
    public void onAbilityTick(ArmorAbilityHandler.IArmorAbilities abilities, World world, EntityPlayer player) {
        if (player.isBurning()) {
            superhotPotion.apply(player, 25, ArmorHelper.getArmorAbilityLevel(player, this.getIdentifier()));
        }
    }

    private static class SuperhotPotion extends TinkerPotion {

        public static final String UUID = "da1c3163-029c-4b7f-974b-de9a6dcf3c00";

        public SuperhotPotion() {
            super(ConstructUtils.getResource("superhotPotion"), false, false);
            this.registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, UUID, 0.15D, 2);
        }

        public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier)
        {
            return modifier.getAmount() * (double)(amplifier);
        }
    }
}
