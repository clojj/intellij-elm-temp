
package org.elm.lang.core.psi.elements

import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil
import org.elm.lang.core.psi.ElmPsiElementImpl


class ElmTypeRef(node: ASTNode) : ElmPsiElementImpl(node) {

    val upperPathTypeRefList: List<ElmUpperPathTypeRef>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmUpperPathTypeRef::class.java)

    val typeVariableRefList: List<ElmTypeVariableRef>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmTypeVariableRef::class.java)

    val recordTypeList: List<ElmRecordType>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmRecordType::class.java)

    val tupleTypeList: List<ElmTupleType>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmTupleType::class.java)

    val parametricTypeRefList: List<ElmParametricTypeRef>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmParametricTypeRef::class.java)

    val typeRefList: List<ElmTypeRef>
        get() = PsiTreeUtil.getChildrenOfTypeAsList(this, ElmTypeRef::class.java)


    // TODO [kl] this will be wrong in the case of a function type ref
    // such as `Int -> { foo: String }`. We want to know that it is exclusively a record.
    // We should see if we can get GrammarKit to parse this into a better data structure
    // than what Kamil had.
    val isRecord: Boolean
        get() = recordTypeList.isNotEmpty()
}
