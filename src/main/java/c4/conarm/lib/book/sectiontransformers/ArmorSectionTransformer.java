package c4.conarm.lib.book.sectiontransformers;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.book.content.ContentArmor;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.content.ContentTool;
import slimeknights.tconstruct.library.book.sectiontransformer.ContentListingSectionTransformer;
import slimeknights.tconstruct.library.tools.ToolCore;

import java.util.Optional;

public class ArmorSectionTransformer extends ContentListingSectionTransformer {

    public ArmorSectionTransformer() {
        super("armory");
    }

    @Override
    protected void processPage(BookData book, ContentListing listing, PageData page) {
        if(page.content instanceof ContentArmor) {
            String armorName = ((ContentArmor) page.content).armorName;
            Optional<ArmorCore> armor = ArmoryRegistry.getArmor().stream()
                    .filter(armorCore -> armorName.equals(armorCore.getIdentifier()))
                    .findFirst();

            armor.ifPresent(armorCore -> listing.addEntry(armorCore.getLocalizedName(), page));
        }
        else {
            super.processPage(book, listing, page);
        }
    }
}
