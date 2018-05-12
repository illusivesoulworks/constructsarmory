package c4.conarm.lib.tinkering;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.ArmoryRegistry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import org.apache.logging.log4j.Level;
import slimeknights.mantle.util.ItemStackList;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.events.TinkerEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*This class is a re-implementation of some methods of the
ToolBuilder class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ArmorBuilder {

    @Nonnull
    public static ItemStack tryBuildArmor(final NonNullList<ItemStack> stacks, final String name, final Collection<ArmorCore> possibleArmor) {
        int length = -1;
        for (int i = 0; i < stacks.size(); ++i) {
            if ((stacks.get(i)).isEmpty()) {
                if (length < 0) {
                    length = i;
                }
            }
            else if (length >= 0) {
                return ItemStack.EMPTY;
            }
        }
        if (length < 0) {
            return ItemStack.EMPTY;
        }
        final NonNullList<ItemStack> input = ItemStackList.of(stacks);
        for (final Item item : possibleArmor) {
            if (!(item instanceof ArmorCore)) {
                continue;
            }
            final ItemStack output = ((ArmorCore)item).buildItemFromStacks(input);
            if (!output.isEmpty()) {
                if (name != null && !name.isEmpty()) {
                    output.setStackDisplayName(name);
                }
                return output;
            }
        }
        return ItemStack.EMPTY;
    }

    public static void rebuildArmor(NBTTagCompound rootNBT, TinkersArmor tinkersArmor) throws TinkerGuiException {

        boolean broken = TagUtil.getToolTag(rootNBT).getBoolean(Tags.BROKEN);
        NBTTagList materialTag = TagUtil.getBaseMaterialsTagList(rootNBT);
        List<Material> materials = TinkerUtil.getMaterialsFromTagList(materialTag);
        List<PartMaterialType> pms = tinkersArmor.getRequiredComponents();

        while(materials.size() < pms.size()) {
            materials.add(Material.UNKNOWN);
        }
        for(int i = 0; i < pms.size(); i++) {
            if(!pms.get(i).isValidMaterial(materials.get(i))) {
                materials.set(i, Material.UNKNOWN);
            }
        }

        NBTTagCompound toolTag = tinkersArmor.buildTag(materials);
        TagUtil.setToolTag(rootNBT, toolTag);
        rootNBT.setTag(Tags.TOOL_DATA_ORIG, toolTag.copy());

        NBTTagList modifiersTagOld = TagUtil.getModifiersTagList(rootNBT);
        rootNBT.removeTag(Tags.TOOL_MODIFIERS);
        rootNBT.setTag(Tags.TOOL_MODIFIERS, new NBTTagList());
        rootNBT.removeTag("ench");
        rootNBT.removeTag(Tags.ENCHANT_EFFECT);

        rootNBT.removeTag(Tags.TOOL_TRAITS);
        tinkersArmor.addMaterialTraits(rootNBT, materials);

        ArmoryEvent.OnItemBuilding.fireEvent(rootNBT, ImmutableList.copyOf(materials), tinkersArmor);

        NBTTagList modifiers = TagUtil.getBaseModifiersTagList(rootNBT);
        NBTTagList modifiersTag = TagUtil.getModifiersTagList(rootNBT);

        for (int i = 0; i < modifiers.tagCount(); i++) {
            String identifier = modifiers.getStringTagAt(i);
            IModifier modifier = TinkerRegistry.getModifier(identifier);
            if(modifier == null) {
                ConstructsArmory.logger.log(Level.ERROR, "Missing modifier: " + identifier);
                continue;
            }

            NBTTagCompound tag;
            int index = TinkerUtil.getIndexInList(modifiersTagOld, modifier.getIdentifier());

            if(index >= 0) {
                tag = modifiersTagOld.getCompoundTagAt(index);
            }
            else {
                tag = new NBTTagCompound();
            }

            modifier.applyEffect(rootNBT, tag);

            if(!tag.hasNoTags()) {

                int indexNew = TinkerUtil.getIndexInList(modifiersTag, modifier.getIdentifier());

                if(indexNew >= 0) {
                    modifiersTag.set(indexNew, tag);
                } else {
                    modifiersTag.appendTag(tag);
                }
            }

            if (modifier instanceof AccessoryModifier) {
                int indexAcc = TinkerUtil.getIndexInList(modifiers, modifier.getIdentifier());
                if (indexAcc >= 0) {
                    NBTTagList newList = new NBTTagList();
                    for (int s = 0; s < modifiers.tagCount(); s++) {
                        if (s != indexAcc) {
                            newList.appendTag(modifiers.get(s));
                        }
                    }
                    newList.appendTag(modifiers.get(indexAcc));
                    TagUtil.setBaseModifiersTagList(rootNBT, newList);
                }
            }
        }

        toolTag = TagUtil.getToolTag(rootNBT);
        int freeModifiers = toolTag.getInteger(Tags.FREE_MODIFIERS);
        freeModifiers -= TagUtil.getBaseModifiersUsed(rootNBT);
        toolTag.setInteger(Tags.FREE_MODIFIERS, Math.max(0, freeModifiers));

        if(broken) {
            toolTag.setBoolean(Tags.BROKEN, true);
        }

        TagUtil.setToolTag(rootNBT, toolTag);

        if(freeModifiers < 0) {
            throw new TinkerGuiException(Util.translateFormatted("gui.error.not_enough_modifiers", -freeModifiers));
        }
    }

    @Nonnull
    public static ItemStack tryModifyArmor(NonNullList<ItemStack> input, ItemStack toolStack, boolean removeItems)
            throws TinkerGuiException {
        ItemStack copy = toolStack.copy();

        NonNullList<ItemStack> stacks = Util.deepCopyFixedNonNullList(input);
        NonNullList<ItemStack> usedStacks = Util.deepCopyFixedNonNullList(input);

        Set<IModifier> appliedModifiers = Sets.newHashSet();
        for(IModifier modifier : ArmoryRegistry.getAllArmorModifiers()) {
            Optional<RecipeMatch.Match> matchOptional;
            do {
                matchOptional = modifier.matches(stacks);
                ItemStack backup = copy.copy();

                if(matchOptional.isPresent()) {
                    RecipeMatch.Match match = matchOptional.get();
                    while(match.amount > 0) {
                        TinkerGuiException caughtException = null;
                        boolean canApply = false;
                        try {
                            canApply = modifier.canApply(copy, toolStack);
                        } catch(TinkerGuiException e) {
                            caughtException = e;
                        }

                        if(canApply) {
                            modifier.apply(copy);

                            appliedModifiers.add(modifier);
                            match.amount--;
                        }
                        else {
                            if(caughtException != null && !appliedModifiers.contains(modifier)) {
                                throw caughtException;
                            }

                            copy = backup;
                            RecipeMatch.removeMatch(stacks, match);
                            break;
                        }
                    }

                    if(match.amount == 0) {
                        RecipeMatch.removeMatch(stacks, match);
                        RecipeMatch.removeMatch(usedStacks, match);
                    }
                }
            } while(matchOptional.isPresent());
        }

        for(int i = 0; i < input.size(); i++) {
            if(!input.get(i).isEmpty() && ItemStack.areItemStacksEqual(input.get(i), stacks.get(i))) {
                if(!appliedModifiers.isEmpty()) {
                    throw new TinkerGuiException(Util.translateFormatted("gui.error.no_modifier_for_item", input.get(i).getDisplayName()));
                }
                return ItemStack.EMPTY;
            }
        }

        if(removeItems) {
            for(int i = 0; i < input.size(); i++) {
                if(usedStacks.get(i).isEmpty()) {
                    input.get(i).setCount(0);
                }
                else {
                    input.get(i).setCount(usedStacks.get(i).getCount());
                }
            }
        }

        if(!appliedModifiers.isEmpty()) {
            if(copy.getItem() instanceof TinkersArmor) {
                NBTTagCompound root = TagUtil.getTagSafe(copy);
                rebuildArmor(root, (TinkersArmor) copy.getItem());
                copy.setTagCompound(root);
            }
            return copy;
        }

        return ItemStack.EMPTY;
    }

    /**
     * Takes an armor and its parts and replaces the parts inside the armor with the given ones.
     * Armor parts have to be applicable to the armor. Armor parts must not be duplicates of currently used parts.
     *
     * @param armorStack   The armor to replace the parts in
     * @param partsIn The armor parts.
     * @param removeItems If true the applied items will be removed from the array
     * @return The armor with the replaced parts or null if the conditions have not been met.
     */
    @Nonnull
    public static ItemStack tryReplaceArmorParts(ItemStack armorStack, final NonNullList<ItemStack> partsIn, final boolean removeItems)
            throws TinkerGuiException {
        if(armorStack == null || !(armorStack.getItem() instanceof TinkersArmor)) {
            return ItemStack.EMPTY;
        }

        NonNullList<ItemStack> inputItems = ItemStackList.of(Util.deepCopyFixedNonNullList(partsIn));
        if(!TinkerEvent.OnToolPartReplacement.fireEvent(inputItems, armorStack)) {
            return ItemStack.EMPTY;
        }

        final NonNullList<ItemStack> armorParts = Util.deepCopyFixedNonNullList(inputItems);

        TIntIntMap assigned = new TIntIntHashMap();
        TinkersArmor armor = (TinkersArmor) armorStack.getItem();

        final NBTTagList materialList = TagUtil.getBaseMaterialsTagList(armorStack).copy();

        for(int i = 0; i < armorParts.size(); i++) {
            ItemStack part = armorParts.get(i);
            if(part.isEmpty()) {
                continue;
            }
            if(!(part.getItem() instanceof IToolPart)) {
                return ItemStack.EMPTY;
            }

            int candidate = -1;

            List<PartMaterialType> pms = armor.getRequiredComponents();
            for(int j = 0; j < pms.size(); j++) {
                PartMaterialType pmt = pms.get(j);
                String partMat = ((IToolPart) part.getItem()).getMaterial(part).getIdentifier();
                String currentMat = materialList.getStringTagAt(j);
                if(pmt.isValid(part) && !partMat.equals(currentMat)) {
                    if(!assigned.valueCollection().contains(j)) {
                        candidate = j;
                        if(i <= j) {
                            break;
                        }
                    }
                }
            }

            if(candidate < 0) {
                return ItemStack.EMPTY;
            }
            assigned.put(i, candidate);
        }

        if(assigned.isEmpty()) {
            return ItemStack.EMPTY;
        }

        assigned.forEachEntry((i, j) -> {
            String mat = ((IToolPart) armorParts.get(i).getItem()).getMaterial(armorParts.get(i)).getIdentifier();
            materialList.set(j, new NBTTagString(mat));
            if(removeItems) {
                if(i < partsIn.size() && !partsIn.get(i).isEmpty()) {
                    partsIn.get(i).shrink(1);
                }
            }
            return true;
        });

        TinkersArmor tinkersArmor = (TinkersArmor) armorStack.getItem();
        ItemStack copyToCheck = tinkersArmor.buildItem(TinkerUtil.getMaterialsFromTagList(materialList));
        NBTTagList modifiers = TagUtil.getBaseModifiersTagList(armorStack);
        for(int i = 0; i < modifiers.tagCount(); i++) {
            String id = modifiers.getStringTagAt(i);
            IModifier mod = TinkerRegistry.getModifier(id);

            if(mod != null && !mod.canApply(copyToCheck, copyToCheck)) {
                throw new TinkerGuiException();
            }
        }

        ItemStack output = armorStack.copy();
        TagUtil.setBaseMaterialsTagList(output, materialList);
        NBTTagCompound tag = TagUtil.getTagSafe(output);
        rebuildArmor(tag, (TinkersArmor) output.getItem());
        output.setTagCompound(tag);

        if(output.getItemDamage() > output.getMaxDamage()) {
            String error = I18n.translateToLocalFormatted("gui.error.not_enough_durability", output.getItemDamage() - output.getMaxDamage());
            throw new TinkerGuiException(error);
        }

        return output;
    }
}
