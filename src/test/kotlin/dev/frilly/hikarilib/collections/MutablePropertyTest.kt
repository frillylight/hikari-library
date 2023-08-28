package dev.frilly.hikarilib.collections

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MutablePropertyTest : FunSpec({

    test("MutableProperty") {
        val prop = MutableProperty.of(42)

        prop.get() shouldBe 42
        prop.set(10)
        prop.get() shouldBe 10
        (prop.with(20) === prop) shouldBe true
        prop.get() shouldBe 20
    }

    test("MutableProperty and Property") {
        val mutableProp = MutableProperty.of(42)
        val prop = mutableProp.toImmutable()

        prop.equals(mutableProp) shouldBe true
        mutableProp.equals(prop) shouldBe true
        mutableProp.set(10)
        prop.equals(mutableProp) shouldBe false
        mutableProp.equals(prop) shouldBe false
    }

    test("MutableProperty as pass-by-reference") {
        fun countBits(arr: CharArray, zero: MutableProperty<Int>, one: MutableProperty<Int>) {
            zero.set(0)
            one.set(0)
            arr.forEach {
                when(it) {
                    '0' -> zero.set(zero.get() + 1)
                    '1' -> one.set(one.get() + 1)
                }
            }
        }

        val zero = MutableProperty.empty<Int>()
        val one = MutableProperty.empty<Int>()

        countBits("01010101010".toCharArray(), zero, one)
        zero.equals(6) shouldBe true
        one.equals(5) shouldBe true
    }

})
