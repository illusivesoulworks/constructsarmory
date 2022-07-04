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
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;
import slimeknights.tconstruct.library.client.data.spritetransformer.IColorMapping;
import slimeknights.tconstruct.tools.data.material.MaterialIds;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;
import com.illusivesoulworks.constructsarmory.api.ArmorMaterialStatsIdentifiers;

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
    // Tier 1
    buildArmorMaterial(MaterialIds.wood)
        .fallbacks("wood", "stick", "primitive")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF281E0B).addARGB(102, 0xFF493615)
                .addARGB(140, 0xFF584014).addARGB(178, 0xFF684E1E).addARGB(216, 0xFF785A22)
                .addARGB(255, 0xFF896727).build());
    buildArmorMaterial(MaterialIds.stone)
        .fallbacks("rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF181818).addARGB(102, 0xFF494949)
                .addARGB(140, 0xFF5A5A5A).addARGB(178, 0xFF787777).addARGB(216, 0xFF95918D)
                .addARGB(255, 0xFFB3B1AF).build());
    buildArmorMaterial(MaterialIds.bone)
        .fallbacks("bone", "rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF7B7E6B).addARGB(102, 0xFFA09F86)
                .addARGB(140, 0xFFCBC6A5).addARGB(178, 0xFFDAD6BC).addARGB(216, 0xFFE8E5D2)
                .addARGB(255, 0xFFFCFBED).build());
    buildArmorMaterial(MaterialIds.leather)
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3D1C10).addARGB(102, 0xFF542716)
                .addARGB(140, 0xFF893B25).addARGB(178, 0xFF9E492A).addARGB(216, 0xFFC65C35)
                .addARGB(255, 0xFFD76B43).build());
    buildArmorMaterial(MaterialIds.vine)
        .fallbacks("primitive")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF143306).addARGB(102, 0xFF183D08)
                .addARGB(140, 0xFF1F4E0A).addARGB(178, 0xFF265F0D).addARGB(216, 0xFF2E730F)
                .addARGB(255, 0xFF3A9313).build());

    // Tier 2
    buildArmorMaterial(MaterialIds.iron)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF353535).addARGB(102, 0xFF5E5E5E)
                .addARGB(140, 0xFF828282).addARGB(178, 0xFFA8A8A8).addARGB(216, 0xFFD8D8D8)
                .addARGB(255, 0xFFFFFFFF).build());
    buildArmorMaterial(MaterialIds.copper)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF72341A).addARGB(102, 0xFF934828)
                .addARGB(140, 0xFFD87236).addARGB(178, 0xFFEF8345).addARGB(216, 0xFFFBA165)
                .addARGB(255, 0xFFFAC493).build());
    buildArmorMaterial(MaterialIds.searedStone)
        .fallbacks("rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF242021).addARGB(102, 0xFF2F2C2A)
                .addARGB(140, 0xFF383433).addARGB(178, 0xFF3F3C39).addARGB(216, 0xFF4F4A47)
                .addARGB(255, 0xFF625B57).build());
    buildArmorMaterial(MaterialIds.bloodbone)
        .fallbacks("bone", "rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4A0000).addARGB(102, 0xFF5D0000)
                .addARGB(140, 0xFF820000).addARGB(178, 0xFFA00000).addARGB(216, 0xFFB80000)
                .addARGB(255, 0xFFE82323).build());
    buildArmorMaterial(MaterialIds.slimewood)
        .fallbacks("wood", "primitive")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF34532F).addARGB(102, 0xFF477A42)
                .addARGB(140, 0xFF5E9854).addARGB(178, 0xFF71AC63).addARGB(216, 0xFF76BE6D)
                .addARGB(255, 0xFF96DD8F).build());
    buildArmorMaterial(MaterialIds.osmium)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF51586D).addARGB(102, 0xFF707C92)
                .addARGB(140, 0xFF7C8BA6).addARGB(178, 0xFF9AACC1).addARGB(216, 0xFFBBCEDD)
                .addARGB(255, 0xFFE1F1F7).build());
    buildArmorMaterial(MaterialIds.platinum)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1B4A54).addARGB(102, 0xFF286B77)
                .addARGB(140, 0xFF5093A0).addARGB(178, 0xFF79BCC6).addARGB(216, 0xFFA6D7DD)
                .addARGB(255, 0xFFD5EAEF).build());
    buildArmorMaterial(MaterialIds.tungsten)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF211F19).addARGB(102, 0xFF33312C)
                .addARGB(140, 0xFF424039).addARGB(178, 0xFF514F46).addARGB(216, 0xFF5B5950)
                .addARGB(255, 0xFF707063).build());
    buildArmorMaterial(MaterialIds.lead)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1C182C).addARGB(102, 0xFF262236)
                .addARGB(140, 0xFF2E2A40).addARGB(178, 0xFF423E52).addARGB(216, 0xFF59556A)
                .addARGB(255, 0xFF6A667A).build());
    buildArmorMaterial(MaterialIds.silver)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4F6770).addARGB(102, 0xFF65858D)
                .addARGB(140, 0xFF83A4AA).addARGB(178, 0xFF97C1C5).addARGB(216, 0xFFB7DFDD)
                .addARGB(255, 0xFFDDF6F0).build());
    buildArmorMaterial(MaterialIds.whitestone)
        .meleeHarvest()
        .fallbacks("rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF717275).addARGB(102, 0xFF7F8084)
                .addARGB(140, 0xFF989A9E).addARGB(178, 0xFFA0A1A1).addARGB(216, 0xFFB4B9BE)
                .addARGB(255, 0xFFCFD2D4).build());
    buildArmorMaterial(MaterialIds.necroticBone)
        .fallbacks("bone", "rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF050505).addARGB(102, 0xFF0E0E0E)
                .addARGB(140, 0xFF151515).addARGB(178, 0xFF1F1F1F).addARGB(216, 0xFF292929)
                .addARGB(255, 0xFF343434).build());

    // Tier 3
    buildArmorMaterial(MaterialIds.skyslimeVine)
        .fallbacks("primitive")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2F5351).addARGB(102, 0xFF3C6A68)
                .addARGB(140, 0xFF4F918F).addARGB(178, 0xFF63ACAB).addARGB(216, 0xFF6DBEBD)
                .addARGB(255, 0xFF82D7D5).build());
    buildArmorMaterial(MaterialIds.slimesteel)
        .fallbacks("slime_metal", "metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF157891).addARGB(102, 0xFF2094A4)
                .addARGB(140, 0xFF2DB3B9).addARGB(178, 0xFF35D3D6).addARGB(216, 0xFF47EFEA)
                .addARGB(255, 0xFFAAFFFF).build());
    buildArmorMaterial(MaterialIds.tinkersBronze)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF684420).addARGB(102, 0xFF895D31)
                .addARGB(140, 0xFFB98541).addARGB(178, 0xFFD79D4D).addARGB(216, 0xFFEFC275)
                .addARGB(255, 0xFFF4D99F).build());
    buildArmorMaterial(MaterialIds.nahuatl)
        .fallbacks("wood", "stick")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF100C1C).addARGB(102, 0xFF271E3D)
                .addARGB(140, 0xFF49332E).addARGB(178, 0xFF543D30).addARGB(216, 0xFF664B2D)
                .addARGB(255, 0xFF7A5C2D).build());
    buildArmorMaterial(MaterialIds.pigIron)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF69363A).addARGB(102, 0xFF7E484C)
                .addARGB(140, 0xFFAF666C).addARGB(178, 0xFFC87D82).addARGB(216, 0xFFF0A8A4)
                .addARGB(255, 0xFFFBCECC).build());
    buildArmorMaterial(MaterialIds.roseGold)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFFAD685B).addARGB(102, 0xFFBF8070)
                .addARGB(140, 0xFFDB9A87).addARGB(178, 0xFFE8B3A0).addARGB(216, 0xFFF7CDBB)
                .addARGB(255, 0xFFFFE7DB).build());
    buildArmorMaterial(MaterialIds.steel)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF222626).addARGB(102, 0xFF393D3D)
                .addARGB(140, 0xFF515454).addARGB(178, 0xFF6A6D6D).addARGB(216, 0xFF898C8C)
                .addARGB(255, 0xFFADAFAF).build());
    buildArmorMaterial(MaterialIds.bronze)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF3F261B).addARGB(102, 0xFF563221)
                .addARGB(140, 0xFF7A4C35).addARGB(178, 0xFFA06A48).addARGB(216, 0xFFC48C5E)
                .addARGB(255, 0xFFDDAF73).build());
    buildArmorMaterial(MaterialIds.constantan)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF5B3027).addARGB(102, 0xFF723E31)
                .addARGB(140, 0xFFA35647).addARGB(178, 0xFFCD6E59).addARGB(216, 0xFFF6866C)
                .addARGB(255, 0xFFFFA986).build());
    buildArmorMaterial(MaterialIds.invar)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF4A666A).addARGB(102, 0xFF5C7C7B)
                .addARGB(140, 0xFF859792).addARGB(178, 0xFFA3B1A8).addARGB(216, 0xFFC5CCC3)
                .addARGB(255, 0xFFE1E7E5).build());
    IColorMapping uraniumPalette =
        GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF363D2F).addARGB(102, 0xFF48543F)
            .addARGB(140, 0xFF5F7050).addARGB(178, 0xFF728762).addARGB(216, 0xFF88A075)
            .addARGB(255, 0xFF9EBF8B).build();
    buildArmorMaterial(MaterialIds.necronium)
        .fallbacks("bone", "metal")
        .colorMapper(uraniumPalette);
    buildArmorMaterial(MaterialIds.electrum)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF605626).addARGB(102, 0xFF7C7031)
                .addARGB(140, 0xFFB2A03E).addARGB(178, 0xFFD6C044).addARGB(216, 0xFFF2DE60)
                .addARGB(255, 0xFFFFF2A3).build());
    IColorMapping brassPalette =
        GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF563B1F).addARGB(102, 0xFF775633)
            .addARGB(140, 0xFFA0763E).addARGB(178, 0xFFCCA353).addARGB(216, 0xFFEDD578)
            .addARGB(255, 0xFFFCF7AE).build();
    buildArmorMaterial(MaterialIds.platedSlimewood)
        .fallbacks("slime_metal", "metal")
        .colorMapper(brassPalette);
    buildArmorMaterial(MaterialIds.cobalt)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF001944).addARGB(102, 0xFF00296D)
                .addARGB(140, 0xFF0043A5).addARGB(178, 0xFF186ACE).addARGB(216, 0xFF338FEA)
                .addARGB(255, 0xFF59A6EF).build());
    IColorMapping obsidianPalette =
        GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF000001).addARGB(102, 0xFF06030B)
            .addARGB(140, 0xFF100C1C).addARGB(178, 0xFF271E3D).addARGB(216, 0xFF3B2754)
            .addARGB(255, 0xFF503572).build();
    buildArmorMaterial(MaterialIds.darkthread)
        .fallbacks("primitive")
        .colorMapper(obsidianPalette);

    // Tier 4
    ResourceLocation baseTexture = TConstruct.getResource("item/materials/generator/queens_slime");
    ResourceLocation highlightTexture =
        TConstruct.getResource("item/materials/generator/queens_slime_highlight");
    ResourceLocation borderTexture =
        TConstruct.getResource("item/materials/generator/queens_slime_border");
    buildArmorMaterial(MaterialIds.queensSlime)
        .fallbacks("slime_metal", "metal")
        .transformer(GreyToSpriteTransformer.builderFromBlack()
            .addTexture(63, borderTexture, 0xFFC8C8C8).addTexture(102, borderTexture)
            .addTexture(140, baseTexture, 0xFFE1E1E1).addTexture(178, baseTexture)
            .addTexture(216, highlightTexture, 0xFFE1E1E1).addTexture(255, highlightTexture)
            .build());
    buildArmorMaterial(MaterialIds.hepatizon)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF1D0628).addARGB(102, 0xFF281133)
                .addARGB(140, 0xFF311A3C).addARGB(178, 0xFF452E50).addARGB(216, 0xFF5F486A)
                .addARGB(255, 0xFF725B7D).build());
    buildArmorMaterial(MaterialIds.manyullyn)
        .fallbacks("metal")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF350C57).addARGB(102, 0xFF481D6D)
                .addARGB(140, 0xFF5C268A).addARGB(178, 0xFF7141AC).addARGB(216, 0xFF9261CC)
                .addARGB(255, 0xFFC299F3).build());
    buildArmorMaterial(MaterialIds.blazingBone)
        .fallbacks("bone", "rock")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF7F3611).addARGB(102, 0xFF934114)
                .addARGB(140, 0xFFB5671E).addARGB(178, 0xFFE28F28).addARGB(216, 0xFFEFC62F)
                .addARGB(255, 0xFFF4EA5A).build());
    IColorMapping ancientDebrisPalette =
        GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF24110B).addARGB(102, 0xFF411E15)
            .addARGB(140, 0xFF4A281D).addARGB(178, 0xFF654740).addARGB(216, 0xFF7E6059)
            .addARGB(255, 0xFF95867E).build();
    buildArmorMaterial(MaterialIds.ancientHide)
        .colorMapper(ancientDebrisPalette);

    // Tier 5
    buildArmorMaterial(MaterialIds.enderslimeVine)
        .fallbacks("primitive")
        .colorMapper(
            GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF6300B0).addARGB(102, 0xFF790CC9)
                .addARGB(140, 0xFF9727DD).addARGB(178, 0xFFA936ED).addARGB(216, 0xFFBF58F7)
                .addARGB(255, 0xFFD37CFF).build());
  }

  protected MaterialSpriteInfoBuilder buildArmorMaterial(ResourceLocation name) {
    return buildMaterial(name).statType(ArmorMaterialStatsIdentifiers.PLATE)
        .statType(ArmorMaterialStatsIdentifiers.MAIL);
  }
}
