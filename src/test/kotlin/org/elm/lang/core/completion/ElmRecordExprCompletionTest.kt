package org.elm.lang.core.completion

import org.junit.Test

class ElmRecordExprCompletionTest : ElmCompletionTestBase() {

    @Test
    fun `test blank`() = doSingleCompletion(
            """
f : { foo : () }
f =
    { {-caret-} }
""", """
f : { foo : () }
f =
    { foo = {-caret-} }
""")

    @Test
    fun `test one letter`() = doSingleCompletion(
            """
f : { foo : (), other: () }
f =
    { f{-caret-} }
""", """
f : { foo : (), other: () }
f =
    { foo = {-caret-} }
""")

    @Test
    fun `test no completions when all fields already defined`() = checkNoCompletion(
            """
f : { foo : () }
f =
    { foo = "", f{-caret-} }
""")

    @Test
    fun `test no completions when type cannot be inferred`() = checkNoCompletion(
            """
f =
    { foo = "", f{-caret-} }
""")

    @Test
    fun `test middle of record missing trailing comma`() = doSingleCompletion(
            """
f : { foo : (), bar : (), baz : () }
f =
    { foo = ()
    , b{-caret-}
      baz = ()
    }
""", """
f : { foo : (), bar : (), baz : () }
f =
    { foo = ()
    , bar = {-caret-}
      baz = ()
    }
""")

    @Test
    fun `test record update`() = doSingleCompletion(
            """
type alias Foo = { foo : () }

f : Foo -> Foo
f foo =
    { foo | f{-caret-} }
""", """
type alias Foo = { foo : () }

f : Foo -> Foo
f foo =
    { foo | foo = {-caret-} }
""")

    @Test
    fun `test root of nested record`() = doSingleCompletion(
            """
f : { foo : { bar: () } }
f =
    { f{-caret-} }
""", """
f : { foo : { bar: () } }
f =
    { foo = {-caret-} }
""")

    @Test
    fun `test custom type declaration`() = doSingleCompletion(
            """
type Bar = Baz { foo : () }
type alias Foo = { bar : Bar }

f : () -> Foo
f str =
    { bar = Baz { {-caret-} } }
""", """
type Bar = Baz { foo : () }
type alias Foo = { bar : Bar }

f : () -> Foo
f str =
    { bar = Baz { foo = {-caret-} } }
""")

    @Test
    fun `test let block`() = doSingleCompletion(
            """
f =
  let
    f2 : { foo : () }
    f2 =
        { f{-caret-} }
  in
  f2
""", """
f =
  let
    f2 : { foo : () }
    f2 =
        { foo = {-caret-} }
  in
  f2
""")

    @Test
    fun `test case expression without type annotation`() = doSingleCompletion(
            """
f b =
  case b of 
    () ->
        { foo = "foo" }
    _ ->
        { f{-caret-} }
""", """
f b =
  case b of 
    () ->
        { foo = "foo" }
    _ ->
        { foo = {-caret-} }
""")

    @Test
    fun `test blank with field value set`() = doSingleCompletion(
            """
f : { foo : () }
f =
    { {-caret-} = "" }
""", """
f : { foo : () }
f =
    { foo{-caret-} = "" }
""")

    @Test
    fun `test one letter with field value set`() = doSingleCompletion(
            """
f : { foo : () }
f =
    { f{-caret-} = "" }
""", """
f : { foo : () }
f =
    { foo{-caret-} = "" }
""")

    @Test
    fun `test extensible record`() = doSingleCompletion(
            """
f : { a | foo : () }
f =
    { f{-caret-} }
""", """
f : { a | foo : () }
f =
    { foo = {-caret-} }
""")

    @Test
    fun `test custom type declaration nested in incomplete record`() = doSingleCompletion(
            """
type Bar = Baz { foo : () }
type alias Foo = { first : (), second : { third : Bar } }

f : Foo
f =
    { second = { third = Baz { {-caret-} } } }
""", """
type Bar = Baz { foo : () }
type alias Foo = { first : (), second : { third : Bar } }

f : Foo
f =
    { second = { third = Baz { foo = {-caret-} } } }
"""
    )

    @Test
    fun `test inside tuple`() = doSingleCompletion("""
f : ({ foo : () }, ())
f =
    ({ f{-caret-} }, ())
""", """
f : ({ foo : () }, ())
f =
    ({ foo = {-caret-} }, ())
""")
}
