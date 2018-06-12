/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.lib.book;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.book.sectiontransformers.ArmorAccessorySectionTransformer;
import c4.conarm.lib.utils.ConstructUtils;
import c4.conarm.lib.book.content.ContentArmor;
import c4.conarm.lib.book.content.ContentArmorMaterial;
import c4.conarm.lib.book.content.ContentArmorModifier;
import c4.conarm.lib.book.content.ContentArmorModifierPolished;
import c4.conarm.lib.book.sectiontransformers.ArmorMaterialSectionTransformer;
import c4.conarm.lib.book.sectiontransformers.ArmorModifierSectionTransformer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.BookTransformer;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;

@SideOnly(Side.CLIENT)
public class ArmoryBook extends BookData {

    public final static BookData INSTANCE = BookLoader.registerBook(ConstructsArmory.MODID, false, false);

    public static void init() {
        BookLoader.registerPageType(ContentArmorMaterial.ID, ContentArmorMaterial.class);
        BookLoader.registerPageType(ContentArmorModifier.ID, ContentArmorModifier.class);
        BookLoader.registerPageType(ContentArmorModifierPolished.ID, ContentArmorModifierPolished.class);
        BookLoader.registerPageType(ContentArmor.ID, ContentArmor.class);
        INSTANCE.addRepository(new FileRepository(ConstructUtils.getResource("book").toString()));
        INSTANCE.addTransformer(new ArmorMaterialSectionTransformer());
        INSTANCE.addTransformer(new ArmorModifierSectionTransformer());
        INSTANCE.addTransformer(new ArmorAccessorySectionTransformer());
        INSTANCE.addTransformer(BookTransformer.IndexTranformer());
    }
}
