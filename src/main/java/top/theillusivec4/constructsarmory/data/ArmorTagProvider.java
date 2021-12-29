package top.theillusivec4.constructsarmory.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ArmorTagProvider extends ItemTagProvider {

  public ArmorTagProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider,
                          ExistingFileHelper existingFileHelper) {
    super(generatorIn, blockTagProvider, existingFileHelper);
  }

  @Override
  protected void registerTags() {
    addArmorTags(ConstructsArmoryItems.MATERIAL_ARMOR, TinkerTags.Items.DURABILITY,
        TinkerTags.Items.MULTIPART_TOOL);
  }

  private ITag.INamedTag<Item> getArmorTag(ArmorSlotType slotType) {
    switch (slotType) {
      case BOOTS:
        return TinkerTags.Items.BOOTS;
      case LEGGINGS:
        return TinkerTags.Items.LEGGINGS;
      case CHESTPLATE:
        return TinkerTags.Items.CHESTPLATES;
      case HELMET:
        return TinkerTags.Items.HELMETS;
    }
    return TinkerTags.Items.ARMOR;
  }

  @SafeVarargs
  private final void addArmorTags(EnumObject<ArmorSlotType, ? extends Item> armor,
                                  ITag.INamedTag<Item>... tags) {
    armor.forEach((type, item) -> {
      for (ITag.INamedTag<Item> tag : tags) {
        this.getOrCreateBuilder(tag).add(item);
      }
      this.getOrCreateBuilder(getArmorTag(type)).add(item);
    });
  }
}
