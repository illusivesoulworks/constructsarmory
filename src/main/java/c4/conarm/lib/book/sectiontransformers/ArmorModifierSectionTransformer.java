/*
 * Copyright (c) 2018-2019 <C4>
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

package c4.conarm.lib.book.sectiontransformers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.book.content.ContentArmorModifier;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.ContentListingSectionTransformer;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class ArmorModifierSectionTransformer extends ContentListingSectionTransformer {

    public ArmorModifierSectionTransformer() {
        super("modifiers");
    }


    @Override
    protected void processPage(BookData book, ContentListing listing, PageData page) {
        if(page.content instanceof ContentArmorModifier) {
            IModifier modifier = ArmoryRegistry.getArmorModifier(((ContentArmorModifier) page.content).modifierName);
            if(modifier != null) {
                listing.addEntry(modifier.getLocalizedName(), page);
            }
        }
    }
}
