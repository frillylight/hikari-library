package dev.frilly.hikarilib.collections

import dev.frilly.hikarilib.exceptions.FailedAccessException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AttemptTest : FunSpec({

    // Throws IllegalArgumentException if str is empty
    fun readLength(str: String): Attempt<Int> {
        if(str.isEmpty())
            return Failure.of(IllegalArgumentException("Empty String"))
        return Success.of(str.length)
    }

    /**
     * Gives 0 if str is empty.
     */
    fun readLengthSafe(str: String): Attempt<Int> {
        if(str.isEmpty())
            return Failure.of(0, IllegalArgumentException("Empty String"))
        return Success.of(str.length)
    }

    test("Successful Attempt") {
        val attempt = readLength("Hello")
        attempt.isSuccess shouldBe true
        attempt.isFailure shouldBe false
        attempt.value shouldBe 5
        attempt.exception shouldBe null
    }

    test("Failed Attempt with no value") {
        val attempt = readLength("")
        attempt.isSuccess shouldBe false
        attempt.isFailure shouldBe true
        shouldThrow<FailedAccessException> {
            attempt.value
        }
        attempt.exception shouldBe IllegalArgumentException("Empty String")
    }

    test("Failed Attempt with value") {
        val attempt = readLengthSafe("")
        attempt.isSuccess shouldBe false
        attempt.isFailure shouldBe true
        attempt.value shouldBe 0
        attempt.exception shouldBe IllegalArgumentException("Empty String")
    }

})
