package c4.conarm.lib.client;

import c4.conarm.lib.tinkering.TinkersArmor;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import org.lwjgl.util.Point;

import javax.annotation.Nonnull;
import java.util.List;

/*This class is a re-implementation of the
ToolBuildGuiInfo class from Tinkers' Construct
Tinkers' Construct is licensed under the MIT License
Find the source here: https://github.com/SlimeKnights/TinkersConstruct
 */
public class ArmorBuildGuiInfo {

    @Nonnull
    public final ItemStack armor;
    public final List<Point> positions = Lists.newArrayList();

    public ArmorBuildGuiInfo() {
        this.armor = ItemStack.EMPTY;
    }

    public ArmorBuildGuiInfo(@Nonnull TinkersArmor armor) {
        this.armor = armor.buildItemForRenderingInGui();
    }

    public static ArmorBuildGuiInfo default3Part(@Nonnull TinkersArmor armor) {
        ArmorBuildGuiInfo info = new ArmorBuildGuiInfo(armor);
        info.addSlotPosition(33 - 20, 42 + 20);
        info.addSlotPosition(33 + 20, 42 - 20);
        info.addSlotPosition(33, 42);
        return info;
    }

    public void addSlotPosition(int x, int y) {
        positions.add(new Point(x, y));
    }

}
