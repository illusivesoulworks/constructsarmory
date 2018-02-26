package c4.conarm.lib.book.content;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.armor.ArmorCore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.element.ImageData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementImage;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.mantle.util.ItemStackList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.book.TinkerPage;
import slimeknights.tconstruct.library.book.content.ContentModifier;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.client.CustomFontColor;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.ArrayList;
import java.util.List;

import static slimeknights.tconstruct.library.book.content.ContentModifier.*;

public class ContentArmorModifier extends TinkerPage {

    public static final transient String ID = "armormodifier";

    private transient IModifier modifier;
    private transient List<Item> armor;

    public TextData[] text;
    public String[] effects;

    @SerializedName("armormodifier")
    public String modifierName;
    public String[] demoArmor = new String[]{ConstructUtils.getResource("chestplate").toString()};

    public ContentArmorModifier() {}

    public ContentArmorModifier(IModifier modifier) {
        this.modifier = modifier;
        this.modifierName = modifier.getIdentifier();
    }

    @Override
    public void load() {
        if(modifierName == null) {
            modifierName = parent.name;
        }
        if(modifier == null) {
            modifier = TinkerRegistry.getModifier(modifierName);
        }
        if(armor == null) {
            armor = Lists.newArrayList();
            for(String entry : demoArmor) {
                Item item = Item.REGISTRY.getObject(new ResourceLocation(entry));
                if(item != null) {
                    armor.add(item);
                }
            }
        }
    }

    @Override
    public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
        if(modifier == null) {
            TinkerModifiers.log.error("Modifier " + modifierName + " not found");
            return;
        }
        int color = 0xdddddd;
        int inCount = 1;
        ItemStack[][] inputItems = null;
        if(modifier instanceof IModifierDisplay) {
            IModifierDisplay modifierDisplay = (IModifierDisplay) modifier;
            color = modifierDisplay.getColor();

            List<List<ItemStack>> inputList = modifierDisplay.getItems();
            inputItems = new ItemStack[5][];

            for(int i = 0; i < 5; i++) {
                inputItems[i] = new ItemStack[inputList.size()];
                for(int j = 0; j < inputItems[i].length; j++) {
                    inputItems[i][j] = ItemStack.EMPTY;
                }
            }

            for(int i = 0; i < inputList.size(); i++) {
                List<ItemStack> inputs = new ArrayList<>(inputList.get(i));
                if(inputs.size() > inCount) {
                    inCount = inputs.size();
                }

                for(int j = 0; j < inputs.size() && j < 5; j++) {
                    ItemStack stack = inputs.get(j);
                    if(!stack.isEmpty() && stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
                        stack = stack.copy();
                        stack.setItemDamage(0);
                    }
                    inputItems[j][i] = stack;
                }
            }
        }

        IModifier actualModifier = modifier;
        if (ArmoryRegistry.getArmorModifier(modifierName) != null) {
            actualModifier = ArmoryRegistry.getArmorModifier(modifierName);
        }
        addTitle(list, CustomFontColor.encodeColor(color) + actualModifier.getLocalizedName(), true);

        // description
        int h = GuiBook.PAGE_WIDTH / 3 - 10;
        list.add(new ElementText(10, 20, GuiBook.PAGE_WIDTH - 20, h, text));

        if(effects.length > 0) {
            TextData head = new TextData(parent.translate("modifier.effect"));
            head.underlined = true;
            list.add(new ElementText(10, 20 + h, GuiBook.PAGE_WIDTH / 2 - 5, GuiBook.PAGE_HEIGHT - h - 20, head));

            List<TextData> effectData = Lists.newArrayList();
            for(String e : effects) {
                effectData.add(new TextData("\u25CF "));
                effectData.add(new TextData(e));
                effectData.add(new TextData("\n"));
            }

            list.add(new ElementText(10, 30 + h, GuiBook.PAGE_WIDTH / 2 + 5, GuiBook.PAGE_HEIGHT - h - 20, effectData));
        }

        ImageData img;
        switch(inCount) {
            case 1:
                img = IMG_SLOT_1;
                break;
            case 2:
                img = IMG_SLOT_2;
                break;
            case 3:
                img = IMG_SLOT_3;
                break;
            default:
                img = IMG_SLOT_5;
        }

        int imgX = GuiBook.PAGE_WIDTH / 2 + 20;
        int imgY = GuiBook.PAGE_HEIGHT / 2 + 30;

        imgX = imgX + 29 - img.width / 2;
        imgY = imgY + 20 - img.height / 2;

        int[] slotX = new int[]{3, 21, 39, 12, 30};
        int[] slotY = new int[]{3, 3, 3, 22, 22};

        list.add(new ElementImage(imgX + (img.width - IMG_TABLE.width) / 2, imgY - 24, -1, -1, IMG_TABLE));
        list.add(new ElementImage(imgX, imgY, -1, -1, img, book.appearance.slotColor));

        ItemStackList demo = getDemoArmor(inputItems);

        ElementTinkerItem toolItem = new ElementTinkerItem(imgX + (img.width - 16) / 2, imgY - 24, 1f, demo);
        toolItem.noTooltip = true;

        list.add(toolItem);
        list.add(new ElementImage(imgX + (img.width - 22) / 2, imgY - 27, -1, -1, IMG_SLOT_1, 0xffffff));

        if(inputItems != null) {
            for(int i = 0; i < inCount && i < 5; i++) {
                list.add(new ElementTinkerItem(imgX + slotX[i], imgY + slotY[i], 1f, inputItems[i]));
            }
        }
    }

    protected ItemStackList getDemoArmor(ItemStack[][] inputItems) {
        ItemStackList demo = ItemStackList.withSize(armor.size());

        for(int i = 0; i < armor.size(); i++) {
            if(armor.get(i) instanceof ArmorCore) {
                ArmorCore core = (ArmorCore) armor.get(i);
                List<Material> mats = ImmutableList.of(TinkerMaterials.wood, TinkerMaterials.cobalt, TinkerMaterials.ardite, TinkerMaterials.manyullyn);
                mats = mats.subList(0, core.getRequiredComponents().size());
                demo.set(i, ((ArmorCore) armor.get(i)).buildItemForRendering(mats));
            }
            else if(armor != null) {
                demo.set(i, new ItemStack(armor.get(i)));
            }

            if(!demo.get(i).isEmpty()) {
                modifier.apply(demo.get(i));
            }
        }

        return demo;
    }
}
