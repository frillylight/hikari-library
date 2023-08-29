package dev.frilly.hikarilib.misc

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class RecurseTest : FunSpec({

    fun sumFirst(n: Int, acc: Int): Recurse<Int> {
        if (n <= 0) return Recurse.finish(acc)
        return Recurse.suspend() { sumFirst(n - 1, acc + n) }
    }

    fun sumFirstRecursive(n: Int): Int {
        return if (n <= 0) 0
        else n + sumFirstRecursive(n - 1)
    }

    fun sumIterative(n: Int) = (1..n).sum()

    test("Recursive Summation") {
        sumFirst(1000000, 0).get() shouldBe sumIterative(1000000)
        shouldThrow<StackOverflowError> { sumFirstRecursive(1000000) }
    }

})
