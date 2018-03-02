package c4.conarm.integrations.crafttweaker.materials;

import slimeknights.tconstruct.library.materials.Material;

public class ConArmMatDefinition implements IConArmMatDefinition {

    private final Material material;

    public ConArmMatDefinition(Material material) {
        this.material = material;
    }

    @Override
    public IConArmMaterial asMaterial() {
        return new ConArmMaterial(material);
    }

    @Override
    public String getName() {
        return material.getLocalizedName();
    }

    @Override
    public String getDisplayName() {
        return material.getLocalizedName();
    }
}
