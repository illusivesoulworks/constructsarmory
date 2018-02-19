package c4.conarm.armor.traits;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.armor.ArmorTagUtil;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.utils.TagUtil;

public abstract class TraitProgressiveArmorStats extends AbstractArmorTrait {

    protected final String pool_key;
    protected final String applied_key;

    public TraitProgressiveArmorStats(String identifier, TextFormatting color) {
        super(identifier, color);

        pool_key = identifier + "StatPool";
        applied_key = identifier + "StatBonus";
    }

    public TraitProgressiveArmorStats(String identifier, int color) {
        super(identifier, color);

        pool_key = identifier + "StatPool";
        applied_key = identifier + "StatBonus";
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        StatNBT bonus = getBonus(rootCompound);

        data.durability += bonus.durability;
        data.armor += bonus.armor;
        data.toughness += bonus.toughness;

        TagUtil.setToolTag(rootCompound, data.get());
    }

    protected boolean hasPool(NBTTagCompound root) {
        return TagUtil.getExtraTag(root).hasKey(pool_key);
    }

    protected StatNBT getPool(NBTTagCompound root) {
        return getStats(root, pool_key);
    }

    protected void setPool(NBTTagCompound root, StatNBT data) {
        setStats(root, data, pool_key);
    }

    protected StatNBT getBonus(NBTTagCompound root) {
        return getStats(root, applied_key);
    }

    protected void setBonus(NBTTagCompound root, StatNBT data) {
        setStats(root, data, applied_key);
    }

    protected static StatNBT getStats(NBTTagCompound root, String key) {
        return ModifierNBT.readTag(TagUtil.getTagSafe(TagUtil.getExtraTag(root), key), StatNBT.class);
    }

    protected static void setStats(NBTTagCompound root, StatNBT data, String key) {
        NBTTagCompound extra = TagUtil.getExtraTag(root);
        NBTTagCompound tag = new NBTTagCompound();
        data.write(tag);
        extra.setTag(key, tag);
        TagUtil.setExtraTag(root, extra);
    }

    public static class StatNBT extends ModifierNBT {

        public int durability;
        public float armor;
        public float toughness;

        @Override
        public void read(NBTTagCompound tag) {
            super.read(tag);
            durability = tag.getInteger("durability");
            armor = tag.getFloat("armor");
            toughness = tag.getFloat("toughness");
        }

        @Override
        public void write(NBTTagCompound tag) {
            super.write(tag);
            tag.setInteger("durability", durability);
            tag.setFloat("armor", armor);
            tag.setFloat("toughness", toughness);
        }
    }
}
