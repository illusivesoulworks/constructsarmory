/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Construct's Armory, a mod made for Minecraft.
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package c4.conarm.lib.book;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.book.content.ContentArmor;
import c4.conarm.lib.book.content.ContentArmorMaterial;
import c4.conarm.lib.book.content.ContentArmorModifier;
import c4.conarm.lib.book.content.ContentArmorModifierPolished;
import c4.conarm.lib.book.sectiontransformers.ArmorAccessorySectionTransformer;
import c4.conarm.lib.book.sectiontransformers.ArmorMaterialSectionTransformer;
import c4.conarm.lib.book.sectiontransformers.ArmorModifierSectionTransformer;
import c4.conarm.lib.utils.ConstructUtils;
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
