package dev.frilly.hikarilib.misc

import dev.frilly.hikarilib.collections.Pair
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StringsTest : FunSpec({

    test("Remove Prefix test") {
        Strings.removePrefix("hello", "he") shouldBe "llo"
        Strings.removePrefix("hello", "hello") shouldBe ""
        Strings.removePrefix("hello", "helloo") shouldBe "hello"
    }

    test("Remove Suffix test") {
        Strings.removeSuffix("hello", "lo") shouldBe "hel"
        Strings.removeSuffix("hello", "hello") shouldBe ""
        Strings.removeSuffix("hello", "helloo") shouldBe "hello"
    }

    test("Trim Left test") {
        Strings.trimLeft("hello") shouldBe "hello"
        Strings.trimLeft(" hello") shouldBe "hello"
        Strings.trimLeft("  hello") shouldBe "hello"
        Strings.trimLeft("hello ") shouldBe "hello "
        Strings.trimLeft("hello  ") shouldBe "hello  "
    }

    test("Trim Right test") {
        Strings.trimRight("hello") shouldBe "hello"
        Strings.trimRight("hello ") shouldBe "hello"
        Strings.trimRight("hello  ") shouldBe "hello"
        Strings.trimRight(" hello") shouldBe " hello"
        Strings.trimRight("  hello") shouldBe "  hello"
    }

    test("Color String") {
        Strings.color("&aHello") shouldBe "\u00a7aHello"
        Strings.color("Hello") shouldBe "Hello"
        Strings.color("&aHello &bWorld") shouldBe "\u00a7aHello \u00a7bWorld"
    }

    test("Replace Placeholders test") {
        Strings.replacePlaceholders("Hello %name%", "name", "World") shouldBe "Hello World"
        shouldThrow<IllegalArgumentException> {
            Strings.replacePlaceholders("Hello %name%", "name")
        }
        Strings.replacePlaceholders("Hello %name% %name%", "name", "World") shouldBe "Hello World World"
        Strings.replacePlaceholders("Hello", "name", "World", "name", "Foo") shouldBe "Hello"
        Strings.replacePlaceholders("Goodbye %name%", Pair.of("name", "Kazuha")) shouldBe "Goodbye Kazuha"
        Strings.replacePlaceholders("Goodbye %name%", null, null) shouldBe "Goodbye %name%"
    }

})
