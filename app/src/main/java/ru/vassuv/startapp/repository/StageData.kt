package ru.vassuv.startapp.repository

import ru.vassuv.startapp.repository.dataclass.User
import ru.vassuv.startapp.repository.db.delegate.UserDelegate

class StageData: IData {
    override var user: User? by UserDelegate()
}
