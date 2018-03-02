package c4.conarm.lib.modifiers;

import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public abstract class ArmorModifier extends ToolModifier {

    public ArmorModifier(String identifier, int color) {
        super(identifier + "_armor", color);
    }
}
