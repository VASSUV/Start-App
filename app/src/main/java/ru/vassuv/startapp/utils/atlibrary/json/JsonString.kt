package ru.vassuv.startapp.utils.atlibrary.json

import java.io.IOException

internal class JsonString(private val string: String) : JsonValue() {

    @Throws(IOException::class)
    override fun write(writer: JsonWriter) {
        writer.writeString(string)
    }

    override val isString: Boolean
        get() = true

    override fun asString() = string

    override fun hashCode() = string.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val other = other as JsonString?
        return string == other!!.string
    }

}
