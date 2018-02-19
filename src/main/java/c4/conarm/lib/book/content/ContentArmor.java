package c4.conarm.lib.book.content;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.materials.ArmorMaterialType;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.element.ImageData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementImage;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.tconstruct.common.ClientProxy;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.TinkerPage;
import slimeknights.tconstruct.library.book.content.ContentModifier;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static slimeknights.tconstruct.library.book.content.ContentTool.*;

public class ContentArmor extends TinkerPage {

    public static final transient String ID = "armor";

    private transient ArmorCore armor;
    private transient List<Collection<IToolPart>> parts;

    public TextData[] text = new TextData[0];
    public String[] properties = new String[0];

    @SerializedName("armor")
    public String armorName;

    public ContentArmor() {
    }

    public ContentArmor(ArmorCore armor) {
        this.armor = armor;
        this.armorName = armor.getIdentifier();
    }

    @Override
    public void load() {
        if(armorName == null) {
            armorName = parent.name;
        }
        if(armor == null) {
            armor = ArmoryRegistry.getArmor().stream()
                    .filter(armorCore -> armorName.equals(armorCore.getIdentifier()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Unknown armor " + armorName));
        }
        if(parts == null) {
            parts = armor.getArmorBuildComponents().stream()
                    .map(PartMaterialType::getPossibleParts)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
        addTitle(list, armor.getLocalizedName());

        int padding = 5;

        int h = GuiBook.PAGE_WIDTH / 3 - 10;
        int y = 16;
        list.add(new ElementText(padding, y, GuiBook.PAGE_WIDTH - padding*2, h, text));

        ImageData img = IMG_SLOTS;
        int imgX = GuiBook.PAGE_WIDTH - img.width - 8;
        int imgY = GuiBook.PAGE_HEIGHT - img.height - 16;

        int armorX = imgX + (img.width - 16) / 2;
        int armorY = imgY + 28;

        y = imgY - 6;

        if(properties.length > 0) {
            TextData head = new TextData(parent.translate("tool.properties"));
            head.underlined = true;
            list.add(new ElementText(padding, y, 86 - padding, GuiBook.PAGE_HEIGHT - h - 20, head));

            List<TextData> effectData = Lists.newArrayList();
            for(String e : properties) {
                effectData.add(new TextData("\u25CF "));
                effectData.add(new TextData(e));
                effectData.add(new TextData("\n"));
            }

            y += 10;
            list.add(new ElementText(padding, y, GuiBook.PAGE_WIDTH / 2 + 5, GuiBook.PAGE_HEIGHT - h - 20, effectData));
        }



        int[] slotX = new int[]{-21, -25,   0, 25, 21};
        int[] slotY = new int[]{ 22,  -4, -25, -4, 22};

        list.add(new ElementImage(imgX + (img.width - IMG_TABLE.width) / 2, imgY + 28, -1, -1, IMG_TABLE));
        list.add(new ElementImage(imgX, imgY, -1, -1, img, book.appearance.slotColor));

        ItemStack demo = armor.buildItemForRenderingInGui();

        ElementTinkerItem toolItem = new ElementTinkerItem(armorX, armorY, 1f, demo);
        toolItem.noTooltip = true;

        list.add(toolItem);
        list.add(new ElementImage(armorX - 3, armorY - 3, -1, -1, IMG_SLOT_1, 0xffffff));

        for(int i = 0; i < parts.size(); i++) {
            Collection<IToolPart> items = parts.get(i);

            Material material = armor.getMaterialForPartForGuiRendering(i);

            ItemStack[] stacks = items.stream().map(part -> part.getItemstackWithMaterial(material)).toArray(ItemStack[]::new);

            ElementTinkerItem partItem = new ElementTinkerItem(armorX + slotX[i], armorY + slotY[i], 1f, stacks);
            partItem.noTooltip = true;

            list.add(partItem);
        }
    }
}
