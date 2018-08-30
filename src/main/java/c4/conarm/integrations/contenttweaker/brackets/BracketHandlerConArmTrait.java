/*
 * Copyright (c) 2018 <C4>
 *
 * This Java class is distributed as a part of the Construct's Armory mod.
 * Construct's Armory is open source and distributed under the GNU Lesser General Public License v3.
 * View the source code and license file on github: https://github.com/TheIllusiveC4/ConstructsArmory
 *
 * Some classes and assets are taken and modified from the parent mod, Tinkers' Construct.
 * Tinkers' Construct is open source and distributed under the MIT License.
 * View the source code on github: https://github.com/SlimeKnights/TinkersConstruct/
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */

package c4.conarm.integrations.contenttweaker.brackets;

import c4.conarm.integrations.contenttweaker.traits.ConArmTraitRepresentation;
import com.teamacronymcoders.contenttweaker.modules.tinkers.traits.TConTraitRepresentation;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;

import java.util.List;
import java.util.stream.Collectors;

/*
 * Base code is from ContentTweaker by The-Acronym-Coders
 * ContentTweaker is open source and distributed under the MIT License
 * View the source code on github: https://github.com/The-Acronym-Coders/ContentTweaker
 * View the MIT License here: https://tldrlegal.com/license/mit-license
 */
@BracketHandler
@ZenRegister
@ModOnly("contenttweaker")
public class BracketHandlerConArmTrait implements IBracketHandler {
    public final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), ConArmTraitRepresentation.class,
            "getFromString", String.class);

    public BracketHandlerConArmTrait() {
    }

    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        return tokens != null && tokens.size() >= 3 && (tokens.get(0)).getValue().equalsIgnoreCase("conarmtrait") ?
                (position) -> new ExpressionCallStatic(position, environment, this.method, new ExpressionString(position, String.join("", tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.toList())) + "_armor")) : null;
    }
}
