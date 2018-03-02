package c4.conarm.integrations.crafttweaker.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.conarm.IConArmMatDefinition")
@ZenRegister
public interface IConArmMatDefinition {

    @ZenGetter("stack")
    IConArmMaterial asMaterial();

    @ZenGetter("name")
    String getName();

    @ZenGetter("displayName")
    String getDisplayName();
}
