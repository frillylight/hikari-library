package dev.frilly.hikarilib.properties

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.File

class PropertiesTest : FunSpec({

    val file = File("./src/test/resources/lang/messages/messages.properties")

    fun checkMap(map: Map<String, PropertyKey>) {
        map["wikipedia"]?.value shouldBe "wikipedia.com"
        map["wikipedia"]?.comments shouldBe listOf("# Comments before everything")

        map["google"]?.value shouldBe "google.com"
        map["google"]?.comments shouldBe listOf("! Another comment before, test with spaces")

        map["yahoo"]?.value shouldBe "yahoo.com"
        map["yahoo"]?.comments?.isEmpty() shouldBe true

        map["pinterest"]?.value shouldBe "pinterest.com"
        map["pinterest"]?.comments?.isEmpty() shouldBe true

        map["facebook"]?.value shouldBe "\nfacebook.com \\\\"
        map["facebook"]?.comments shouldBe listOf("# Multi-line setting")

        map["a-we:ird=key"]?.value shouldBe "\na-we:ird=key.com"
        map["a-we:ird=key"]?.comments shouldBe listOf("# Very weird formatted key")

        map["move-setting"]?.value shouldBe "移動設定"
        map["move-setting"]?.comments shouldBe listOf("# Other languages text")

        map["hello"]?.value shouldBe "こんにちは"
        map["hello"]?.comments?.isEmpty() shouldBe true
    }

    test("Read properties") {
        file.exists() shouldBe true
        val map = PropertiesReader.read(file).associateBy { it.key }
        checkMap(map)
    }

    test("Write properties") {
        file.exists() shouldBe true

        // Temporary file
        val output = File("./src/test/resources/lang/messages/messages2.properties")
        output.createNewFile()

        val list = PropertiesReader.read(file)
        PropertiesWriter.write(output, list)
        checkMap(PropertiesReader.read(file).associateBy { it.key })
    }

    test("With PropertiesFile") {
        val props = PropertiesFile(file)
        props.reload()

        props.exists() shouldBe true
        props.create() shouldBe false
        props.get("wikipedia").get().value shouldBe "wikipedia.com"
        shouldThrow<NullPointerException> {
            props.getRaw("not-exists")
        }
        props.addKey("test", "example.com", listOf("Test comment"))
        props.save()
        props.get("test").get().value shouldBe "example.com"
    }

})
