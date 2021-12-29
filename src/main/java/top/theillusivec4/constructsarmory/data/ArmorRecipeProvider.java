package top.theillusivec4.constructsarmory.data;

import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.tools.item.ModifiableArmorItem;
import top.theillusivec4.constructsarmory.common.ConstructsArmoryItems;

public class ArmorRecipeProvider extends BaseRecipeProvider implements IMaterialRecipeHelper,
    IToolRecipeHelper {

  public ArmorRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
    String folder = "tools/building/";

    for (ModifiableArmorItem item : ConstructsArmoryItems.MATERIAL_ARMOR.values()) {
      toolBuilding(consumer, item, folder);
    }
    String partFolder = "tools/parts/";
    String castFolder = "smeltery/casts/";
    partRecipes(consumer, ConstructsArmoryItems.HEAD_PLATE, ConstructsArmoryItems.HEAD_PLATE_CAST,
        4, partFolder, castFolder);
    partRecipes(consumer, ConstructsArmoryItems.CHEST_PLATE, ConstructsArmoryItems.CHEST_PLATE_CAST,
        6, partFolder, castFolder);
    partRecipes(consumer, ConstructsArmoryItems.LEGS_PLATE, ConstructsArmoryItems.LEGS_PLATE_CAST,
        5, partFolder, castFolder);
    partRecipes(consumer, ConstructsArmoryItems.FEET_PLATE, ConstructsArmoryItems.FEET_PLATE_CAST,
        3, partFolder, castFolder);
    partRecipes(consumer, ConstructsArmoryItems.MAIL, ConstructsArmoryItems.MAIL_CAST, 2,
        partFolder, castFolder);
    partRecipes(consumer, ConstructsArmoryItems.TRIM, ConstructsArmoryItems.TRIM_CAST, 1,
        partFolder, castFolder);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Armor Recipes";
  }
}
