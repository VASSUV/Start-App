package ru.vassuv.startapp.utils.atlibrary.json

class ParseException
internal constructor(message: String, val offset: Int, val line: Int, val column: Int)
    : RuntimeException("$message at $line:$column")
