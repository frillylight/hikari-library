package dev.frilly.hikarilib.misc

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

})
