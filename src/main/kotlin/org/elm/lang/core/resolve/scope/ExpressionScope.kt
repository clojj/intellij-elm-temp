package org.elm.lang.core.resolve.scope

import com.intellij.psi.PsiElement
import org.elm.lang.core.psi.ElmFile
import org.elm.lang.core.psi.ElmNamedElement
import org.elm.lang.core.psi.elements.ElmCaseOfBranch
import org.elm.lang.core.psi.elements.ElmLetIn
import org.elm.lang.core.psi.elements.ElmValueDeclaration


class ExpressionScope(val element: PsiElement) {

    fun getVisibleValues(): List<ElmNamedElement> {

        val results = mutableListOf<ElmNamedElement>()

        treeWalkUp(element) {
            if (it is ElmFile) {
                results.addAll(ModuleScope(it).getVisibleValues())
                return@treeWalkUp false // stop
            }

            if (it is ElmNamedElement) {
                results.add(it)
            }

            // TODO [kl] might need to de-dupe because an [ElmValueDeclaration] is also a named element
            if (it is ElmValueDeclaration) {
                results.addAll(it.declaredNames())
            }

            if (it is ElmLetIn) {
                for (innerDecl in it.innerValueDeclarationList) {
                    results.addAll(innerDecl.declaredNames())
                }
            }

            if (it is ElmCaseOfBranch) {
                results.addAll(it.destructuredNames)
            }

            return@treeWalkUp true // keep going
        }

        return results
    }
}

fun treeWalkUp(start: PsiElement, callback: (PsiElement) -> Boolean) {
    var current: PsiElement? = start
    while (current != null) {
        val keepGoing = callback(current)
        if (!keepGoing) break
        current = current.parent
    }
}
