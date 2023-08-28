package dev.frilly.hikarilib.collections

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class PropertyTest : FunSpec({

    test("Empty Property") {
        val empty = Property.empty<Int>()

        shouldThrow<NullPointerException> {
            empty.get()
        }
        empty.isPresent shouldBe false
        empty.isEmpty shouldBe true
        empty.orNull shouldBe null
        empty.equals(null) shouldBe true
    }

    test("Property with value") {
        val number = 10
        val property = Property.of(number)

        property.get() shouldBe number
        property.orNull shouldBe number
        property.isPresent shouldBe true
        property.isEmpty shouldBe false
        property.map { it + 10 } shouldNotBe property
        shouldThrow<RuntimeException> {
            property.ifPresent { throw RuntimeException() }
        }
        property.equals(null) shouldBe false
        property.equals(Property.empty<Int>()) shouldBe false
        property.equals(Property.of(20)) shouldBe false
        property.equals(Property.of(number)) shouldBe true
        property.equals(number) shouldBe true
    }
    
    test("Property and MutableProperty") {
        val prop = Property.of(42)
        val mutableProp = prop.toMutable()
        
        mutableProp.get() shouldBe 42
        mutableProp.set(10)
        mutableProp.get() shouldBe 10
        prop.get() shouldBe 42
    }

})
