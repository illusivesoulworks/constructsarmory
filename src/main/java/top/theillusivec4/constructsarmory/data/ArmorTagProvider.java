package top.theillusivec4.constructsarmory.data;

import java.util.function.Consumer;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.mantle.registration.object.EnumObject;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.common.registration.CastItemObject;
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
    this.getOrCreateBuilder(TinkerTags.Items.TOOL_PARTS)
        .add(ConstructsArmoryItems.HEAD_PLATE.get(), ConstructsArmoryItems.CHEST_PLATE.get(),
            ConstructsArmoryItems.LEGS_PLATE.get(), ConstructsArmoryItems.FEET_PLATE.get(),
            ConstructsArmoryItems.MAIL.get(), ConstructsArmoryItems.TRIM.get());
    TagsProvider.Builder<Item> goldCasts = this.getOrCreateBuilder(TinkerTags.Items.GOLD_CASTS);
    TagsProvider.Builder<Item> sandCasts = this.getOrCreateBuilder(TinkerTags.Items.SAND_CASTS);
    TagsProvider.Builder<Item> redSandCasts =
        this.getOrCreateBuilder(TinkerTags.Items.RED_SAND_CASTS);
    TagsProvider.Builder<Item> singleUseCasts =
        this.getOrCreateBuilder(TinkerTags.Items.SINGLE_USE_CASTS);
    TagsProvider.Builder<Item> multiUseCasts =
        this.getOrCreateBuilder(TinkerTags.Items.MULTI_USE_CASTS);
    Consumer<CastItemObject> addCast = cast -> {
      // tag based on material
      goldCasts.add(cast.get());
      sandCasts.add(cast.getSand());
      redSandCasts.add(cast.getRedSand());
      // tag based on usage
      singleUseCasts.addTag(cast.getSingleUseTag());
      this.getOrCreateBuilder(cast.getSingleUseTag()).add(cast.getSand(), cast.getRedSand());
      multiUseCasts.addTag(cast.getMultiUseTag());
      this.getOrCreateBuilder(cast.getMultiUseTag()).add(cast.get());
    };
    addCast.accept(ConstructsArmoryItems.HEAD_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.CHEST_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.LEGS_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.FEET_PLATE_CAST);
    addCast.accept(ConstructsArmoryItems.MAIL_CAST);
    addCast.accept(ConstructsArmoryItems.TRIM_CAST);
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
