/*
 * Copyright (C) 2018-2022 Illusive Soulworks
 *
 * Construct's Armory is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Construct's Armory is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Construct's Armory.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.constructsarmory.data;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import com.illusivesoulworks.constructsarmory.api.ArmorMaterialStatsIdentifiers;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.RepairKitStats;

/**
 * Modified copy of {@link TinkerMaterialSpriteProvider} from Tinkers' Construct
 * MIT License (c) SlimeKnights
 */
public class ArmorMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

  @Nonnull
  @Override
  public String getName() {
    return "Construct's Armory Material Textures";
  }

  @Override
  protected void addAllMaterials() {
    // tier 1
    buildArmorMaterial(MaterialIds.flint)
            .meleeHarvest()
            .fallbacks("crystal", "rock", "stick")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF0E0E0E).addARGB(102, 0xFF222020).addARGB(140, 0xFF2E2D2D).addARGB(178, 0xFF3D3C3C).addARGB(216, 0xFF565656).addARGB(255, 0xFF7F7F7F).build());
    buildArmorMaterial(MaterialIds.basalt)
            .meleeHarvest()
            .fallbacks("crystal", "rock", "stick")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1B2632).addARGB(102, 0xFF353641).addARGB(140, 0xFF3D3E4C).addARGB(178, 0xFF5C5C5C).addARGB(216, 0xFF747474).addARGB(255, 0xFF898989).build());
    buildArmorMaterial(MaterialIds.bone)
            .meleeHarvest()
            .fallbacks("bone", "rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF7B7E6B).addARGB(102, 0xFFA09F86).addARGB(140, 0xFFCBC6A5).addARGB(178, 0xFFDAD6BC).addARGB(216, 0xFFE8E5D2).addARGB(255, 0xFFFCFBED).build());
    buildArmorMaterial(MaterialIds.necroticBone)
            .meleeHarvest()
            .fallbacks("bone", "rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF050505).addARGB(102, 0xFF0E0E0E).addARGB(140, 0xFF151515).addARGB(178, 0xFF1F1F1F).addARGB(216, 0xFF292929).addARGB(255, 0xFF343434).build());
    buildArmorMaterial(MaterialIds.leather)
            .statType(RepairKitStats.ID, ExtraMaterialStats.ID)
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3D1C10).addARGB(102, 0xFF542716).addARGB(140, 0xFF893B25).addARGB(178, 0xFF9E492A).addARGB(216, 0xFFC65C35).addARGB(255, 0xFFD76B43).build());
    buildArmorMaterial(MaterialIds.string)
            .statType(ExtraMaterialStats.ID, RepairKitStats.ID)
            .fallbacks("primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2F4547).addARGB(102, 0xFF4D5859).addARGB(140, 0xFF838B8C).addARGB(178, 0xFFAFB3B5).addARGB(216, 0xFFDBDBDB).addARGB(255, 0xFFF7F7F7).build());
    buildArmorMaterial(MaterialIds.vine)
            .statType(ExtraMaterialStats.ID)
            .fallbacks("primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF143306).addARGB(102, 0xFF183D08).addARGB(140, 0xFF1F4E0A).addARGB(178, 0xFF265F0D).addARGB(216, 0xFF2E730F).addARGB(255, 0xFF3A9313).build());
    // tier 1 - wood
    buildArmorMaterial(MaterialIds.wood)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF281E0B).addARGB(102, 0xFF493615).addARGB(140, 0xFF584014).addARGB(178, 0xFF684E1E).addARGB(216, 0xFF785A22).addARGB(255, 0xFF896727).build());
    buildArmorMaterial(MaterialIds.oak)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF382B18).addARGB(102, 0xFF4C3D26).addARGB(140, 0xFF5F4A2B).addARGB(178, 0xFF745A36).addARGB(216, 0xFF917142).addARGB(255, 0xFF9F844D).build());
    buildArmorMaterial(MaterialIds.spruce)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF311E0B).addARGB(102, 0xFF3B2713).addARGB(140, 0xFF4D3317).addARGB(178, 0xFF553A1F).addARGB(216, 0xFF5A4424).addARGB(255, 0xFF685032).build());
    buildArmorMaterial(MaterialIds.birch)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF36342A).addARGB(102, 0xFF605E54).addARGB(140, 0xFFBEBEAE).addARGB(178, 0xFFDED3D5).addARGB(216, 0xFFF0EEEB).addARGB(255, 0xFFFFFFFF).build());
    buildArmorMaterial(MaterialIds.jungle)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF403612).addARGB(102, 0xFF503F16).addARGB(140, 0xFF59461A).addARGB(178, 0xFF59511B).addARGB(216, 0xFF635820).addARGB(255, 0xFF766626).build());
    buildArmorMaterial(MaterialIds.darkOak)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF292011).addARGB(102, 0xFF302513).addARGB(140, 0xFF3F311D).addARGB(178, 0xFF4A381E).addARGB(216, 0xFF584428).addARGB(255, 0xFF664F2E).build());
    buildArmorMaterial(MaterialIds.acacia)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF504B40).addARGB(102, 0xFF585349).addARGB(140, 0xFF696259).addARGB(178, 0xFF7B7368).addARGB(216, 0xFF8D8477).addARGB(255, 0xFF998F82).build());
    buildArmorMaterial(MaterialIds.crimson)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF442131).addARGB(102, 0xFF4B2737).addARGB(140, 0xFF521810).addARGB(178, 0xFF7B0000).addARGB(216, 0xFF961515).addARGB(255, 0xFFB12727).build());
    buildArmorMaterial(MaterialIds.warped)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF38254C).addARGB(102, 0xFF452D5C).addARGB(140, 0xFF16615B).addARGB(178, 0xFF136C68).addARGB(216, 0xFF118066).addARGB(255, 0xFF14956F).build());
    buildArmorMaterial(MaterialIds.bamboo)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3D5A1F).addARGB(102, 0xFF456317).addARGB(140, 0xFF507516).addARGB(178, 0xFF5E8A24).addARGB(216, 0xFF8AAE60).addARGB(255, 0xFF97C463).build());
    // tier 1 - stone
    buildArmorMaterial(MaterialIds.stone)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF181818).addARGB(102, 0xFF494949).addARGB(140, 0xFF5A5A5A).addARGB(178, 0xFF787777).addARGB(216, 0xFF95918D).addARGB(255, 0xFFB3B1AF).build());
    buildArmorMaterial(MaterialIds.andesite)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF181818).addARGB(102, 0xFF494949).addARGB(140, 0xFF686868).addARGB(178, 0xFF8A8A8E).addARGB(216, 0xFF9C9C9C).addARGB(255, 0xFFA8AA9A).build());
    buildArmorMaterial(MaterialIds.diorite)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4C4C4C).addARGB(102, 0xFF666666).addARGB(140, 0xFFA4A2A2).addARGB(178, 0xFFBEBFC1).addARGB(216, 0xFFCECECF).addARGB(255, 0xFFE9E9E9).build());
    buildArmorMaterial(MaterialIds.granite)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3F2C24).addARGB(102, 0xFF54382E).addARGB(140, 0xFF724C3F).addARGB(178, 0xFF926251).addARGB(216, 0xFFA97764).addARGB(255, 0xFFD3B3A5).build());
    buildArmorMaterial(MaterialIds.deepslate)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF131316).addARGB(102, 0xFF2F2F37).addARGB(140, 0xFF3D3D43).addARGB(178, 0xFF515151).addARGB(216, 0xFF646464).addARGB(255, 0xFF797979).build());
    buildArmorMaterial(MaterialIds.blackstone)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF160F10).addARGB(102, 0xFF27221C).addARGB(140, 0xFF27221C).addARGB(178, 0xFF312C36).addARGB(216, 0xFF3C3947).addARGB(255, 0xFF4E4B54).build());

    // tier 2
    buildArmorMaterial(MaterialIds.iron)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF353535).addARGB(102, 0xFF5E5E5E).addARGB(140, 0xFF828282).addARGB(178, 0xFFA8A8A8).addARGB(216, 0xFFD8D8D8).addARGB(255, 0xFFFFFFFF).build());
    buildArmorMaterial(MaterialIds.oxidizedIron)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF574D39).addARGB(102, 0xFF887455).addARGB(140, 0xFFAF8E77).addARGB(178, 0xFFD8AF93).addARGB(216, 0xFFFEDEC8).addARGB(255, 0xFFFEF4ED).build());
    buildArmorMaterial(MaterialIds.copper)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF6D3421).addARGB(102, 0xFF8A4129).addARGB(140, 0xFF9C4E31).addARGB(178, 0xFFC15A36).addARGB(216, 0xFFE77C56).addARGB(255, 0xFFFC9982).build());
    buildArmorMaterial(MaterialIds.oxidizedCopper)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2D5646).addARGB(102, 0xFF396E59).addARGB(140, 0xFF43897A).addARGB(178, 0xFF48966D).addARGB(216, 0xFF4FAB90).addARGB(255, 0xFF73CEA6).build());
    buildArmorMaterial(MaterialIds.searedStone)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF242021).addARGB(102, 0xFF2F2C2A).addARGB(140, 0xFF383433).addARGB(178, 0xFF3F3C39).addARGB(216, 0xFF4F4A47).addARGB(255, 0xFF625B57).build());
    buildArmorMaterial(MaterialIds.bloodbone)
            .meleeHarvest()
            .fallbacks("bone", "rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4A0000).addARGB(102, 0xFF5D0000).addARGB(140, 0xFF820000).addARGB(178, 0xFFA00000).addARGB(216, 0xFFB80000).addARGB(255, 0xFFE82323).build());
    buildArmorMaterial(MaterialIds.osmium)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF51586D).addARGB(102, 0xFF707C92).addARGB(140, 0xFF7C8BA6).addARGB(178, 0xFF9AACC1).addARGB(216, 0xFFBBCEDD).addARGB(255, 0xFFE1F1F7).build());
    buildArmorMaterial(MaterialIds.platinum)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1B4A54).addARGB(102, 0xFF286B77).addARGB(140, 0xFF5093A0).addARGB(178, 0xFF79BCC6).addARGB(216, 0xFFA6D7DD).addARGB(255, 0xFFD5EAEF).build());
    buildArmorMaterial(MaterialIds.tungsten)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF211F19).addARGB(102, 0xFF33312C).addARGB(140, 0xFF424039).addARGB(178, 0xFF514F46).addARGB(216, 0xFF5B5950).addARGB(255, 0xFF707063).build());
    buildArmorMaterial(MaterialIds.lead)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1C182C).addARGB(102, 0xFF262236).addARGB(140, 0xFF2E2A40).addARGB(178, 0xFF423E52).addARGB(216, 0xFF59556A).addARGB(255, 0xFF6A667A).build());
    buildArmorMaterial(MaterialIds.silver)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4F6770).addARGB(102, 0xFF65858D).addARGB(140, 0xFF83A4AA).addARGB(178, 0xFF97C1C5).addARGB(216, 0xFFB7DFDD).addARGB(255, 0xFFDDF6F0).build());
    buildArmorMaterial(MaterialIds.whitestone)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF717275).addARGB(102, 0xFF7F8084).addARGB(140, 0xFF989A9E).addARGB(178, 0xFFA0A1A1).addARGB(216, 0xFFB4B9BE).addARGB(255, 0xFFCFD2D4).build());
    buildArmorMaterial(MaterialIds.scorchedStone)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF241D19).addARGB(102, 0xFF2D231D).addARGB(140, 0xFF3E3029).addARGB(178, 0xFF483A32).addARGB(216, 0xFF53453C).addARGB(255, 0xFF66554A).build());
    // tier 2 - slimewood
    buildArmorMaterial(MaterialIds.greenheart)
            .meleeHarvest()
            .fallbacks("wood", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF34532F).addARGB(102, 0xFF477A42).addARGB(140, 0xFF5E9854).addARGB(178, 0xFF71AC63).addARGB(216, 0xFF76BE6D).addARGB(255, 0xFF96DD8F).build());
    buildArmorMaterial(MaterialIds.skyroot)
            .meleeHarvest()
            .fallbacks("wood", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF00507C).addARGB(102, 0xFF00628A).addARGB(140, 0xFF007F9A).addARGB(178, 0xFF1D96A6).addARGB(216, 0xFF17B2B7).addARGB(255, 0xFF19D4D6).build());
    buildArmorMaterial(MaterialIds.bloodshroom)
            .meleeHarvest()
            .fallbacks("wood", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF7F3500).addARGB(102, 0xFF954200).addARGB(140, 0xFFB24100).addARGB(178, 0xFFCB4B00).addARGB(216, 0xFFEF6F05).addARGB(255, 0xFFFF9826).build());

    // tier 3
    buildArmorMaterial(MaterialIds.chain)
            .statType(ExtraMaterialStats.ID)
            .fallbacks("chain", "metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1B202D).addARGB(102, 0xFF252C3D).addARGB(140, 0xFF323847).addARGB(178, 0xFF3E4453).addARGB(216, 0xFF495065).addARGB(255, 0xFF545C72).build());
    buildArmorMaterial(MaterialIds.skyslimeVine)
            .statType(ExtraMaterialStats.ID)
            .fallbacks("primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2F5351).addARGB(102, 0xFF3C6A68).addARGB(140, 0xFF4F918F).addARGB(178, 0xFF63ACAB).addARGB(216, 0xFF6DBEBD).addARGB(255, 0xFF82D7D5).build());
    buildArmorMaterial(MaterialIds.slimesteel)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("slime_metal", "metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF157891).addARGB(102, 0xFF2094A4).addARGB(140, 0xFF2DB3B9).addARGB(178, 0xFF35D3D6).addARGB(216, 0xFF47EFEA).addARGB(255, 0xFFAAFFFF).build());
    buildArmorMaterial(MaterialIds.amethystBronze)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF583757).addARGB(102, 0xFF754D73).addARGB(140, 0xFFB174A9).addARGB(178, 0xFFC687BD).addARGB(216, 0xFFD9A2D0).addARGB(255, 0xFFE6BFDD).build());
    buildArmorMaterial(MaterialIds.nahuatl)
            .meleeHarvest()
            .fallbacks("wood", "stick")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF100C1C).addARGB(102, 0xFF271E3D).addARGB(140, 0xFF49332E).addARGB(178, 0xFF543D30).addARGB(216, 0xFF664B2D).addARGB(255, 0xFF7A5C2D).build());
    buildArmorMaterial(MaterialIds.pigIron)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF69363A).addARGB(102, 0xFF7E484C).addARGB(140, 0xFFAF666C).addARGB(178, 0xFFC87D82).addARGB(216, 0xFFF0A8A4).addARGB(255, 0xFFFBCECC).build());
    buildArmorMaterial(MaterialIds.roseGold)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFFAD685B).addARGB(102, 0xFFBF8070).addARGB(140, 0xFFDB9A87).addARGB(178, 0xFFE8B3A0).addARGB(216, 0xFFF7CDBB).addARGB(255, 0xFFFFE7DB).build());
    buildArmorMaterial(MaterialIds.steel)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF222626).addARGB(102, 0xFF393D3D).addARGB(140, 0xFF515454).addARGB(178, 0xFF6A6D6D).addARGB(216, 0xFF898C8C).addARGB(255, 0xFFADAFAF).build());
    buildArmorMaterial(MaterialIds.bronze)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3F261B).addARGB(102, 0xFF563221).addARGB(140, 0xFF7A4C35).addARGB(178, 0xFFA06A48).addARGB(216, 0xFFC48C5E).addARGB(255, 0xFFDDAF73).build());
    buildArmorMaterial(MaterialIds.constantan)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF5B3027).addARGB(102, 0xFF723E31).addARGB(140, 0xFFA35647).addARGB(178, 0xFFCD6E59).addARGB(216, 0xFFF6866C).addARGB(255, 0xFFFFA986).build());
    buildArmorMaterial(MaterialIds.invar)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4A666A).addARGB(102, 0xFF5C7C7B).addARGB(140, 0xFF859792).addARGB(178, 0xFFA3B1A8).addARGB(216, 0xFFC5CCC3).addARGB(255, 0xFFE1E7E5).build());
    IColorMapping uraniumPalette = GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF363D2F).addARGB(102, 0xFF48543F).addARGB(140, 0xFF5F7050).addARGB(178, 0xFF728762).addARGB(216, 0xFF88A075).addARGB(255, 0xFF9EBF8B).build();
    buildArmorMaterial(MaterialIds.necronium)
            .meleeHarvest()
            .fallbacks("bone", "metal")
            .colorMapper(uraniumPalette);
    buildArmorMaterial(MaterialIds.electrum)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF605626).addARGB(102, 0xFF7C7031).addARGB(140, 0xFFB2A03E).addARGB(178, 0xFFD6C044).addARGB(216, 0xFFF2DE60).addARGB(255, 0xFFFFF2A3).build());
    IColorMapping brassPalette = GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF563B1F).addARGB(102, 0xFF775633).addARGB(140, 0xFFA0763E).addARGB(178, 0xFFCCA353).addARGB(216, 0xFFEDD578).addARGB(255, 0xFFFCF7AE).build();
    buildArmorMaterial(MaterialIds.platedSlimewood)
            .meleeHarvest()
            .fallbacks("slime_metal", "metal")
            .colorMapper(brassPalette);
    buildArmorMaterial(MaterialIds.cobalt)
            .meleeHarvest()
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF001944).addARGB(102, 0xFF00296D).addARGB(140, 0xFF0043A5).addARGB(178, 0xFF186ACE).addARGB(216, 0xFF338FEA).addARGB(255, 0xFF59A6EF).build());
    IColorMapping obsidianPalette = GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF000001).addARGB(102, 0xFF06030B).addARGB(140, 0xFF100C1C).addARGB(178, 0xFF271E3D).addARGB(216, 0xFF3B2754).addARGB(255, 0xFF503572).build();
    buildArmorMaterial(MaterialIds.darkthread)
            .statType(ExtraMaterialStats.ID, RepairKitStats.ID)
            .fallbacks("primitive")
            .colorMapper(obsidianPalette);

    // tier 4
    ResourceLocation baseTexture = TConstruct.getResource("item/materials/generator/queens_slime");
    ResourceLocation highlightTexture = TConstruct.getResource("item/materials/generator/queens_slime_highlight");
    ResourceLocation borderTexture = TConstruct.getResource("item/materials/generator/queens_slime_border");
    buildArmorMaterial(MaterialIds.queensSlime)
            .meleeHarvest()
            .fallbacks("slime_metal", "metal")
            .transformer(GreyToSpriteTransformer.builderFromBlack()
                    .addTexture( 63, borderTexture,    0xFFC8C8C8).addTexture(102, borderTexture)
                    .addTexture(140, baseTexture,      0xFFE1E1E1).addTexture(178, baseTexture)
                    .addTexture(216, highlightTexture, 0xFFE1E1E1).addTexture(255, highlightTexture)
                    .build());
    buildArmorMaterial(MaterialIds.hepatizon)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1D0628).addARGB(102, 0xFF281133).addARGB(140, 0xFF311A3C).addARGB(178, 0xFF452E50).addARGB(216, 0xFF5F486A).addARGB(255, 0xFF725B7D).build());
    buildArmorMaterial(MaterialIds.manyullyn)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF350C57).addARGB(102, 0xFF481D6D).addARGB(140, 0xFF5C268A).addARGB(178, 0xFF7141AC).addARGB(216, 0xFF9261CC).addARGB(255, 0xFFC299F3).build());
    buildArmorMaterial(MaterialIds.blazingBone)
            .meleeHarvest()
            .fallbacks("bone", "rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF7F3611).addARGB(102, 0xFF934114).addARGB(140, 0xFFB5671E).addARGB(178, 0xFFE28F28).addARGB(216, 0xFFEFC62F).addARGB(255, 0xFFF4EA5A).build());
    IColorMapping ancientDebrisPalette = GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF24110B).addARGB(102, 0xFF411E15).addARGB(140, 0xFF4A281D).addARGB(178, 0xFF654740).addARGB(216, 0xFF7E6059).addARGB(255, 0xFF95867E).build();
    buildArmorMaterial(MaterialIds.ancientHide)
            .statType(ExtraMaterialStats.ID)
            .colorMapper(ancientDebrisPalette);
    buildArmorMaterial(MaterialIds.enderslimeVine)
            .statType(ExtraMaterialStats.ID)
            .fallbacks("primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF6300B0).addARGB(102, 0xFF790CC9).addARGB(140, 0xFF9727DD).addARGB(178, 0xFFA936ED).addARGB(216, 0xFFBF58F7).addARGB(255, 0xFFD37CFF).build());
  }

  protected MaterialSpriteInfoBuilder buildArmorMaterial(MaterialVariantId name) {
    return buildMaterial(name).statType(ArmorMaterialStatsIdentifiers.PLATE)
        .statType(ArmorMaterialStatsIdentifiers.MAIL);
  }
}
