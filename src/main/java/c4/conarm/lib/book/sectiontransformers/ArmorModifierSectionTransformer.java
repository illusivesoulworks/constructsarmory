package c4.conarm.lib.book.sectiontransformers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.book.content.ContentArmorModifier;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.content.ContentModifier;
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
