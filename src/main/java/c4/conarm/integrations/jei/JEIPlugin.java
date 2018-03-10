package c4.conarm.integrations.jei;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.ISubtypeRegistry;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistry registry) {

        ArmorSubtypeInterpreter armorInterpreter = new ArmorSubtypeInterpreter();
        for (ArmorCore armor : ArmoryRegistry.getArmor()) {
            registry.registerSubtypeInterpreter(armor, armorInterpreter);
        }
    }
}
