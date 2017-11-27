package org.elm.lang.core.parser.manual

import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase


/**
 * Entry-points for manual/external parse rules to be called by the parser
 * generated by GrammarKit.
 *
 * In general, the reason we need manual parse rules is to handle cases
 * where Elm requires that there be no whitespace between certain tokens
 * (e.g. a "dotted" module name such as `Json.Encode`)
 */
class ElmParserUtil : GeneratedParserUtilBase() {
    companion object {

        @JvmStatic fun parseUpperCaseQID(builder: PsiBuilder, level: Int) =
                UpperCaseQIDParser().parse(builder, level)

        @JvmStatic fun parseValueQID(builder: PsiBuilder, level: Int) =
                ValueQIDParser().parse(builder, level)

        @JvmStatic fun parseFieldAccess(builder: PsiBuilder, level: Int) =
                FieldAccessParser().parse(builder, level)

        @JvmStatic fun parseFieldAccessorFunction(builder: PsiBuilder, level: Int) =
                FieldAccessorFunctionParser().parse(builder, level)

        @JvmStatic fun parseEffect(builder: PsiBuilder, level: Int) =
                EffectParser().parse(builder, level)
    }
}
