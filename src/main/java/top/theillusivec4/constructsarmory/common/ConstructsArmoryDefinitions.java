package top.theillusivec4.constructsarmory.common;

import java.util.EnumMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.Util;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.tools.definition.IToolStatProvider;
import slimeknights.tconstruct.library.tools.definition.ModifiableArmorMaterial;
import slimeknights.tconstruct.tools.item.ArmorSlotType;
import top.theillusivec4.constructsarmory.ConstructsArmoryMod;
import top.theillusivec4.constructsarmory.common.stat.ArmorStatProvider;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstructsArmoryDefinitions {

  public static final Map<ArmorSlotType, IToolStatProvider> ARMOR_STAT_PROVIDERS =
      Util.make(new EnumMap<>(ArmorSlotType.class), map -> {
        for (ArmorSlotType type : ArmorSlotType.values()) {
          map.put(type, new ArmorStatProvider(type));
        }
      });

  public static final ModifiableArmorMaterial MATERIAL_ARMOR =
      ModifiableArmorMaterial.builder(ConstructsArmoryMod.getResource("material_armor"))
          .setStatsProvider(ArmorSlotType.HELMET, ARMOR_STAT_PROVIDERS.get(ArmorSlotType.HELMET))
          .setStatsProvider(ArmorSlotType.CHESTPLATE,
              ARMOR_STAT_PROVIDERS.get(ArmorSlotType.CHESTPLATE))
          .setStatsProvider(ArmorSlotType.LEGGINGS,
              ARMOR_STAT_PROVIDERS.get(ArmorSlotType.LEGGINGS))
          .setStatsProvider(ArmorSlotType.BOOTS, ARMOR_STAT_PROVIDERS.get(ArmorSlotType.BOOTS))
          .setSoundEvent(Sounds.EQUIP_PLATE.getSound())
          .build();
}
