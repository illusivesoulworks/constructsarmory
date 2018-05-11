package c4.conarm.lib.materials;

import c4.conarm.armor.ArmorTraits;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.tools.TinkerMaterials;
import slimeknights.tconstruct.tools.TinkerTraits;

public class ArmorMaterials {

    public static void setupArmorMaterials() {

        //Natural
        addArmorTrait(TinkerMaterials.wood, ArmorTraits.ecological);
        addArmorTrait(TinkerMaterials.stone, ArmorTraits.cheapskate, ArmorTraits.cheap);
        addArmorTrait(TinkerMaterials.flint, ArmorTraits.mundane);
        addArmorTrait(TinkerMaterials.obsidian, ArmorTraits.duritae);
        addArmorTrait(TinkerMaterials.cactus, ArmorTraits.spiny);
        addArmorTrait(TinkerMaterials.prismarine, ArmorTraits.aquaspeed);
        addArmorTrait(TinkerMaterials.prismarine, ArmorTraits.rough, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.netherrack, ArmorTraits.aridiculous, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.netherrack, ArmorTraits.infernal);
        addArmorTrait(TinkerMaterials.endstone, ArmorTraits.alien, ArmorTraits.enderport);

        //Item/Special Resources
        addArmorTrait(TinkerMaterials.bone, ArmorTraits.calcic, ArmorTraits.skeletal);
        addArmorTrait(TinkerMaterials.paper, TinkerTraits.writable2, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.sponge, ArmorTraits.absorbent);
        addArmorTrait(TinkerMaterials.firewood, ArmorTraits.combustible);
        addArmorTrait(TinkerMaterials.slime, ArmorTraits.slimeyGreen);
        addArmorTrait(TinkerMaterials.slime, ArmorTraits.bouncy, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.blueslime, ArmorTraits.slimeyBlue);
        addArmorTrait(TinkerMaterials.blueslime, ArmorTraits.bouncy, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.knightslime, ArmorTraits.dramatic, ArmorTraits.invigorating);
        addArmorTrait(TinkerMaterials.magmaslime, ArmorTraits.autoforge, ArmorTraits.superhot);

        //Metals
        addArmorTrait(TinkerMaterials.iron, ArmorTraits.magnetic);
        addArmorTrait(TinkerMaterials.pigiron, ArmorTraits.baconlicious, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.pigiron, ArmorTraits.tasty);
        addArmorTrait(TinkerMaterials.cobalt, ArmorTraits.lightweight);
        addArmorTrait(TinkerMaterials.ardite, ArmorTraits.subterranean, ArmorTraits.petravidity);
        addArmorTrait(TinkerMaterials.manyullyn, ArmorTraits.vengeful, ArmorTraits.prideful);

        //Mod Integrations
        addArmorTrait(TinkerMaterials.copper, ArmorTraits.ambitious);
        addArmorTrait(TinkerMaterials.bronze, ArmorTraits.dense);
        addArmorTrait(TinkerMaterials.lead, ArmorTraits.heavy);
        addArmorTrait(TinkerMaterials.lead, ArmorTraits.shielding, ArmorMaterialType.CORE);
        addArmorTrait(TinkerMaterials.silver, ArmorTraits.blessed);
        addArmorTrait(TinkerMaterials.electrum, ArmorTraits.voltaic);
        addArmorTrait(TinkerMaterials.steel, ArmorTraits.steady, ArmorTraits.indomitable);
    }

    private static void addArmorTrait(Material material, ITrait trait) {
        addArmorTrait(material, trait, (ITrait) null);
    }

    private static void addArmorTrait(Material material, ITrait trait, ITrait secondTrait) {
        material.addTrait(trait, ArmorMaterialType.CORE);
        if (secondTrait != null) {
            material.addTrait(secondTrait, ArmorMaterialType.PLATES);
            material.addTrait(secondTrait, ArmorMaterialType.TRIM);
        } else {
            material.addTrait(trait, ArmorMaterialType.PLATES);
            material.addTrait(trait, ArmorMaterialType.TRIM);
        }
    }

    private static void addArmorTrait(Material material, ITrait trait, String type) {
        material.addTrait(trait, type);
    }

    public static void registerArmorMaterialStats() {
        //Stats: Core - base durability, base armor
        //       Plating - modifier, base durability, toughness
        //       Trim - extra base durability

        //Default stats
        Material.UNKNOWN.addStats(new TrimMaterialStats(0));
        Material.UNKNOWN.addStats(new CoreMaterialStats(0, 0));
        Material.UNKNOWN.addStats(new PlatesMaterialStats(1, 0, 0));

        //Durability ranges from 1-40 since this number is multiplied later on
        //Ref: 5 for leather, 15 for chain/iron, 7 for gold, 33 for diamond
        //Defense ranges from 0-20
        //Toughness ranges from 0-5

        //Natural
        TinkerRegistry.addMaterialStats(TinkerMaterials.wood,
                                                        new CoreMaterialStats(2.5F, 3),
                                                        new PlatesMaterialStats(1, 1, 0),
                                                        new TrimMaterialStats(0.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.stone,
                                                        new CoreMaterialStats(8.7F, 5),
                                                        new PlatesMaterialStats(0.5F, -3.5F, 0),
                                                        new TrimMaterialStats(0.75F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.flint,
                                                        new CoreMaterialStats(10, 4.5F),
                                                        new PlatesMaterialStats(0.6F, -5, 1),
                                                        new TrimMaterialStats(2.6F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.cactus,
                                                        new CoreMaterialStats(12.5F, 10),
                                                        new PlatesMaterialStats(0.85F, 0.75F, 0),
                                                        new TrimMaterialStats(3.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.bone,
                                                        new CoreMaterialStats(12, 4),
                                                        new PlatesMaterialStats(1.10F, 3.5F, 1),
                                                        new TrimMaterialStats(5.3F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.obsidian,
                                                        new CoreMaterialStats(9.5F, 16.2F),
                                                        new PlatesMaterialStats(0.9F, -8, 3.5F),
                                                        new TrimMaterialStats(7));

        TinkerRegistry.addMaterialStats(TinkerMaterials.prismarine,
                                                        new CoreMaterialStats(16, 18.8F),
                                                        new PlatesMaterialStats(0.6F, -10, 2),
                                                        new TrimMaterialStats(8));

        TinkerRegistry.addMaterialStats(TinkerMaterials.endstone,
                                                        new CoreMaterialStats(15.5F, 9),
                                                        new PlatesMaterialStats(0.85F, 0, 1),
                                                        new TrimMaterialStats(2.7F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.paper,
                                                        new CoreMaterialStats(0.4F, 0.5F),
                                                        new PlatesMaterialStats(0.1F, 0.3F, 0),
                                                        new TrimMaterialStats(0.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.sponge,
                                                        new CoreMaterialStats(21, 1),
                                                        new PlatesMaterialStats(1.2F, 13, 5),
                                                        new TrimMaterialStats(13));

        //Slime
        TinkerRegistry.addMaterialStats(TinkerMaterials.slime,
                                                        new CoreMaterialStats(20.7F, 2),
                                                        new PlatesMaterialStats(0.7F, 0, 2),
                                                        new TrimMaterialStats(14.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.blueslime,
                                                        new CoreMaterialStats(19.5F, 1.8F),
                                                        new PlatesMaterialStats(1.3F, -3.5F, 2.25F),
                                                        new TrimMaterialStats(12));

        TinkerRegistry.addMaterialStats(TinkerMaterials.knightslime,
                                                        new CoreMaterialStats(20, 17.1F),
                                                        new PlatesMaterialStats(0.5F, 16.7F, 1),
                                                        new TrimMaterialStats(9));

        TinkerRegistry.addMaterialStats(TinkerMaterials.magmaslime,
                                                        new CoreMaterialStats(18, 19.4F),
                                                        new PlatesMaterialStats(0.85F, -12, 2.5F),
                                                        new TrimMaterialStats(10));

        //Nether
        TinkerRegistry.addMaterialStats(TinkerMaterials.netherrack,
                                                        new CoreMaterialStats(13.5F, 5.3F),
                                                        new PlatesMaterialStats(0.85F, -10, 0),
                                                        new TrimMaterialStats(5.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.cobalt,
                                                        new CoreMaterialStats(19.5F, 15.6F),
                                                        new PlatesMaterialStats(0.9F, 8, 0),
                                                        new TrimMaterialStats(14));

        TinkerRegistry.addMaterialStats(TinkerMaterials.ardite,
                                                        new CoreMaterialStats(20.5F, 13),
                                                        new PlatesMaterialStats(1.4F, -12, 4),
                                                        new TrimMaterialStats(16.2F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.manyullyn,
                                                        new CoreMaterialStats(19.7F, 20),
                                                        new PlatesMaterialStats(1.0F, 13, 3),
                                                        new TrimMaterialStats(3.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.firewood,
                                                        new CoreMaterialStats(17, 17.5F),
                                                        new PlatesMaterialStats(1.0F, -12, 0.5F),
                                                        new TrimMaterialStats(10));

        //Metals
        TinkerRegistry.addMaterialStats(TinkerMaterials.iron,
                                                        new CoreMaterialStats(12, 15),
                                                        new PlatesMaterialStats(0.85F, 5, 0),
                                                        new TrimMaterialStats(3.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.pigiron,
                                                        new CoreMaterialStats(15, 16.7F),
                                                        new PlatesMaterialStats(1.2F, 0, 1),
                                                        new TrimMaterialStats(10.5F));

        //Mod Integration
        TinkerRegistry.addMaterialStats(TinkerMaterials.copper,
                                                        new CoreMaterialStats(12, 8),
                                                        new PlatesMaterialStats(1.05F, 2, 0),
                                                        new TrimMaterialStats(8));

        TinkerRegistry.addMaterialStats(TinkerMaterials.bronze,
                                                        new CoreMaterialStats(16, 12),
                                                        new PlatesMaterialStats(1.10F, 5.5F, 1.25F),
                                                        new TrimMaterialStats(6.5F));

        TinkerRegistry.addMaterialStats(TinkerMaterials.lead,
                                                        new CoreMaterialStats(16, 11),
                                                        new PlatesMaterialStats(0.7F, -3.5F, 2),
                                                        new TrimMaterialStats(8));

        TinkerRegistry.addMaterialStats(TinkerMaterials.silver,
                                                        new CoreMaterialStats(13,17),
                                                        new PlatesMaterialStats(0.95F, 3.5F, 2),
                                                        new TrimMaterialStats(10));

        TinkerRegistry.addMaterialStats(TinkerMaterials.electrum,
                                                        new CoreMaterialStats(3.5F, 8.1F),
                                                        new PlatesMaterialStats(1.1F, -1, 0),
                                                        new TrimMaterialStats(13));

        TinkerRegistry.addMaterialStats(TinkerMaterials.steel,
                                                        new CoreMaterialStats(17, 18.4F),
                                                        new PlatesMaterialStats(0.9F, 10, 4.5F),
                                                        new TrimMaterialStats(1));
    }
}
