package ru.vassuv.startapp.repository

import ru.vassuv.startapp.repository.dataclass.User

class DemoData : IData {
    override var user: User? = User("Tom", 20)
}
