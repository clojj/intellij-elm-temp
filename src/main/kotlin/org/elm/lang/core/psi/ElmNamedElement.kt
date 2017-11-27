package org.elm.lang.core.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement
import org.elm.lang.core.psi.ElmTypes.LOWER_CASE_IDENTIFIER
import org.elm.lang.core.psi.ElmTypes.UPPER_CASE_IDENTIFIER
import org.elm.lang.core.psi.IdentifierCase.LOWER
import org.elm.lang.core.psi.IdentifierCase.UPPER


interface ElmNamedElement : ElmPsiElement, PsiNamedElement


interface ElmNameIdentifierOwner : ElmNamedElement, PsiNameIdentifierOwner {
    override fun getNameIdentifier(): PsiElement
    override fun getName(): String
}


open class ElmNamedElementImpl(node: ASTNode, val case: IdentifierCase) : ElmPsiElementImpl(node), ElmNameIdentifierOwner {

    override fun getNameIdentifier(): PsiElement =
            when (case) {
                UPPER -> findNotNullChildByType(UPPER_CASE_IDENTIFIER)
                LOWER -> findNotNullChildByType(LOWER_CASE_IDENTIFIER)
            }

    override fun getName(): String =
            nameIdentifier.text


    override fun setName(name: String): PsiElement {
        val newIdentifier = when (nameIdentifier.elementType) {
            UPPER_CASE_IDENTIFIER -> ElmPsiFactory(project).createUpperCaseIdentifier(name)
            LOWER_CASE_IDENTIFIER -> ElmPsiFactory(project).createLowerCaseIdentifier(name)
            else -> error("unexpected name identifier type: ${nameIdentifier.elementType}")
        }
        nameIdentifier.replace(newIdentifier)
        return this
    }

    override fun getTextOffset(): Int =
            nameIdentifier.textOffset
}


enum class IdentifierCase {
    UPPER, LOWER
}
