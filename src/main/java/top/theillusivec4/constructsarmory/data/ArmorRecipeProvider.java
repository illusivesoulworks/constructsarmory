package top.theillusivec4.constructsarmory.data;

import java.util.function.Consumer;
import javax.annotation.Nonnull;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import top.theillusivec4.constructsarmory.common.ArmorItems;
import top.theillusivec4.constructsarmory.common.ArmorParts;
import top.theillusivec4.constructsarmory.common.ArmorSmeltery;

public class ArmorRecipeProvider extends BaseRecipeProvider implements IMaterialRecipeHelper,
    IToolRecipeHelper {

  public ArmorRecipeProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
    String folder = "tools/building/";
    toolBuilding(consumer, ArmorItems.helmet, folder);
    toolBuilding(consumer, ArmorItems.chestplate, folder);
    toolBuilding(consumer, ArmorItems.leggings, folder);
    toolBuilding(consumer, ArmorItems.boots, folder);

    String partFolder = "tools/parts/";
    String castFolder = "smeltery/casts/";
    partRecipes(consumer, ArmorParts.helmetCore, ArmorSmeltery.helmetCoreCast, 4, partFolder,
        castFolder);
    partRecipes(consumer, ArmorParts.chestplateCore, ArmorSmeltery.chestplateCoreCast, 6,
        partFolder, castFolder);
    partRecipes(consumer, ArmorParts.leggingsCore, ArmorSmeltery.leggingsCoreCast, 5, partFolder,
        castFolder);
    partRecipes(consumer, ArmorParts.bootsCore, ArmorSmeltery.bootsCoreCast, 3, partFolder,
        castFolder);
    partRecipes(consumer, ArmorParts.armorPlate, ArmorSmeltery.armorPlateCast, 2, partFolder,
        castFolder);
    partRecipes(consumer, ArmorParts.armorTrim, ArmorSmeltery.armorTrimCast, 1, partFolder,
        castFolder);
  }

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Armor Recipes";
  }
}
