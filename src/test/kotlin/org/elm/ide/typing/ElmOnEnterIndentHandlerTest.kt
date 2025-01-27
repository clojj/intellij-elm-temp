package org.elm.ide.typing

import org.elm.lang.core.psi.indentStyle
import org.intellij.lang.annotations.Language
import org.junit.Test


class ElmOnEnterIndentHandlerTest : ElmTypingTestBase() {


    @Test
    fun `test after function equals at EOF`() = doTest("""
f ={-caret-}
""", """
f =
    {-caret-}
""")


    @Test
    fun `test after function equals before blank line`() = doTest("""
f ={-caret-}

""", """
f =
    {-caret-}

""")

    @Test
    fun `test after function equals before another function`() = doTest("""
f ={-caret-}
g = 0
""", """
f =
    {-caret-}
g = 0
""")

    @Test
    fun `test after let beginning`() = doTest("""
f =
    let{-caret-}
""", """
f =
    let
        {-caret-}
""")


    @Test
    fun `test after let partial`() = doTest("""
f =
    let{-caret-}
        x = 0
    in
        x
""", """
f =
    let
        {-caret-}
        x = 0
    in
        x
""")


    @Test
    fun `test after equals in let declaration beginning`() = doTest("""
f =
    let
        x ={-caret-}
""", """
f =
    let
        x =
            {-caret-}
""")


    @Test
    fun `test after equals in let declaration partial`() = doTest("""
f =
    let
        x ={-caret-}
    in
        x
""", """
f =
    let
        x =
            {-caret-}
    in
        x
""")


    @Test
    fun `test after case-of beginning`() = doTest("""
f x =
    case x of{-caret-}
""", """
f x =
    case x of
        {-caret-}
""")


    @Test
    fun `test after case branch`() = doTest("""
f x =
    case x of
        Nothing ->{-caret-}
""", """
f x =
    case x of
        Nothing ->
            {-caret-}
""")


    @Test
    fun `test after equals in a type alias declaration`() = doTest("""
type alias Foo ={-caret-}
""", """
type alias Foo =
    {-caret-}
""")


    @Test
    fun `test no smart indent BEFORE equals`() = doTest("""
f{-caret-} =
""", """
f
 {-caret-}=
""")


    @Test
    fun `test no smart indent after equals in a record value expression`() = doTest("""
f =
    { foo ={-caret-}
""", """
f =
    { foo =
    {-caret-}
""")


    @Test
    fun `test no smart indent after arrow in type annotation`() = doTest("""
f : Int ->{-caret-}
""", """
f : Int ->
{-caret-}
""")


    @Test
    fun `test no smart indent after equals in a union type declaration`() = doTest("""
type Foo ={-caret-}
""", """
type Foo =
{-caret-}
""")

    @Test
    fun `test indentation code style settings`() {
        checkByText("f ={-caret-}", """
            |f =
            |  {-caret-}
            """.trimMargin()) {
            myFixture.file.indentStyle.INDENT_SIZE = 2
            myFixture.type('\n')
        }
    }

    fun doTest(@Language("Elm") before: String, @Language("Elm") after: String) {
        checkByText(before, after) {
            myFixture.type('\n')
        }
    }
}
