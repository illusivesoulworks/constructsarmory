/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 */

package c4.conarm.lib.book.sectiontransformers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.book.content.ContentArmorModifier;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.ContentListingSectionTransformer;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class ArmorAccessorySectionTransformer extends ContentListingSectionTransformer {

    public ArmorAccessorySectionTransformer() {
        super("accessories");
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
