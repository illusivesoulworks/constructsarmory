package c4.conarm.lib.book;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.ConstructUtils;
import c4.conarm.lib.book.content.ContentArmor;
import c4.conarm.lib.book.content.ContentArmorMaterial;
import c4.conarm.lib.book.content.ContentArmorModifier;
import c4.conarm.lib.book.content.ContentArmorModifierPolished;
import c4.conarm.lib.book.sectiontransformers.ArmorMaterialSectionTransformer;
import c4.conarm.lib.book.sectiontransformers.ArmorModifierSectionTransformer;
import c4.conarm.lib.book.sectiontransformers.ArmorSectionTransformer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.BookTransformer;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.tconstruct.library.book.sectiontransformer.BowMaterialSectionTransformer;
import slimeknights.tconstruct.library.book.sectiontransformer.MaterialSectionTransformer;
import slimeknights.tconstruct.library.book.sectiontransformer.ModifierSectionTransformer;
import slimeknights.tconstruct.library.book.sectiontransformer.ToolSectionTransformer;

@SideOnly(Side.CLIENT)
public class ArmoryBook extends BookData {

    public final static BookData INSTANCE = BookLoader.registerBook(ConstructsArmory.MODID, false, false);

    public static void init() {
        BookLoader.registerPageType(ContentArmorMaterial.ID, ContentArmorMaterial.class);
        BookLoader.registerPageType(ContentArmorModifier.ID, ContentArmorModifier.class);
        BookLoader.registerPageType(ContentArmorModifierPolished.ID, ContentArmorModifierPolished.class);
        BookLoader.registerPageType(ContentArmor.ID, ContentArmor.class);
        INSTANCE.addRepository(new FileRepository(ConstructUtils.getResource("book").toString()));
        INSTANCE.addTransformer(new ArmorSectionTransformer());
        INSTANCE.addTransformer(new ArmorMaterialSectionTransformer());
        INSTANCE.addTransformer(new ArmorModifierSectionTransformer());
        INSTANCE.addTransformer(BookTransformer.IndexTranformer());
    }
}
