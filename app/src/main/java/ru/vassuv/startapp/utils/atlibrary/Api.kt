package ru.vassuv.startapp.utils.atlibrary

enum class Api(override val host: String) : ApiMethod {
    AUTH("https://vassuv.ru/api/v1/login/");
}

