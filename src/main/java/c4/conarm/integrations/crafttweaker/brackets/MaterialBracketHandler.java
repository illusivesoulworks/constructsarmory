package c4.conarm.integrations.crafttweaker.brackets;

import c4.conarm.integrations.crafttweaker.materials.ConArmMaterial;
import c4.conarm.integrations.crafttweaker.materials.IConArmMaterial;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.zenscript.IBracketHandler;
import slimeknights.tconstruct.library.TinkerRegistry;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.List;

/*
    This class is a copy of the MaterialBracketHandler.java class from ModTweaker by jaredlll08
    ModTweaker is licensed under the MIT License
    Find the source here: https://github.com/jaredlll08/ModTweaker
 */
@BracketHandler(priority = 100)
@ZenRegister
public class MaterialBracketHandler implements IBracketHandler {

    public static IConArmMaterial getMaterial(String name) {
        return new ConArmMaterial(TinkerRegistry.getMaterial(name));
    }

    private final IZenSymbol symbolAny;
    private final IJavaMethod method;

    public MaterialBracketHandler() {

        symbolAny = CraftTweakerAPI.getJavaStaticFieldSymbol(IngredientAny.class, "INSTANCE");
        method = CraftTweakerAPI.getJavaMethod(MaterialBracketHandler.class, "getMaterial", String.class);
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        // any symbol
        if(tokens.size() == 1 && tokens.get(0).getValue().equals("*")) {
            return symbolAny;
        }

        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equals("conmat") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }

        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for(int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        IConArmMaterial material = getMaterial(valueBuilder.toString());
        if(!material.getName().equals("unknown")) {
            CraftTweakerAPI.logInfo("Material wasn't null");
            return new MaterialReferenceSymbol(environment, valueBuilder.toString());
        }
        CraftTweakerAPI.logInfo("Material was null");

        return null;
    }

    private class MaterialReferenceSymbol implements IZenSymbol {

        private final IEnvironmentGlobal environment;
        private final String name;

        MaterialReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
        }
    }
}
