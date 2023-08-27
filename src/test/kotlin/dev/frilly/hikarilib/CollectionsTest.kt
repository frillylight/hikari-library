package dev.frilly.hikarilib

import dev.frilly.hikarilib.collections.LateInitReference
import dev.frilly.hikarilib.collections.LazyReference
import dev.frilly.hikarilib.exceptions.LateInitAccessException
import dev.frilly.hikarilib.exceptions.LateInitAlreadyInitializedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.longs.shouldBeLessThan
import io.kotest.matchers.shouldBe
import java.lang.Exception
import kotlin.system.measureTimeMillis

class CollectionsTest : FunSpec({

    fun expensiveCalculation(): Int {
        Thread.sleep(1000)
        return 20
    }

    val lazyReference = LazyReference.of { expensiveCalculation() }

    test("First-time access to LazyReference") {
        var value: Int?
        val time = measureTimeMillis {
            value = lazyReference.get()
        }

        time.shouldBeGreaterThanOrEqual(1000)
        value.shouldBe(20)
    }

    test("Second-time access to LazyReference") {
        var value: Int? = null
        val time = measureTimeMillis {
            repeat(1000) {
                value = lazyReference.get()
            }
        }

        time.shouldBeLessThan(20) // To spare time a little.
        value.shouldBe(20)
    }

    test("Proper use of LateInit") {
        val lateInit = LateInitReference.empty<Int>()
        shouldThrow<LateInitAccessException> { lateInit.get() }
        lateInit.set(10)
        lateInit.get().shouldBe(10)
        shouldThrow<LateInitAlreadyInitializedException> { lateInit.set(20) }
        lateInit.get().shouldBe(10)
    }

})
