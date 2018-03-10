package c4.conarm.lib.book.content;

import c4.conarm.armor.ConstructsArmor;
import c4.conarm.lib.ArmoryRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.ItemStackList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentModifier;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.tools.TinkerTools;

@SideOnly(Side.CLIENT)
public class ContentArmorModifierPolished extends ContentArmorModifier {

    public static final transient String ID = "armormodifier_polished";

    public ContentArmorModifierPolished() {
    }

    public ContentArmorModifierPolished(IModifier modifier) {
        super(modifier);
    }

    @Override
    protected ItemStackList getDemoArmor(ItemStack[][] inputItems) {
        if(inputItems.length == 0) {
            return ItemStackList.create();
        }

        ItemStackList demo = super.getDemoArmor(inputItems);

        ItemStackList out = ItemStackList.create();

        for(int i = 0; i < inputItems[0].length; i++) {
            if(inputItems[0][i].getItem() == ConstructsArmor.polishingKit) {
                Material material = ConstructsArmor.polishingKit.getMaterial(inputItems[0][i]);
                IModifier modifier = ArmoryRegistry.getArmorModifier("polished" + material.getIdentifier() + "_armor");
                if(modifier != null) {
                    ItemStack stack = demo.get(i % demo.size()).copy();
                    modifier.apply(stack);
                    out.add(stack);
                }
            }
        }

        return out;
    }
}