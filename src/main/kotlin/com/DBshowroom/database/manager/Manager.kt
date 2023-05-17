package com.DBshowroom.database.manager

import com.DBshowroom.database.showroom.Showroom
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Manager: Table("manager") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 20)
    val password = varchar("password", length = 20)
    val showroomId = integer("id_showroom").references(Showroom.id)

    override val primaryKey = PrimaryKey(id)

    fun create(manager: ManagerRegisterReceiveRemote): Int {
        return transaction {
            Manager.insert {
                it[name] = manager.name
                it[phone] = manager.phone
                it[password] = manager.password
                it[showroomId] = manager.showroomId
            } get Manager.id
        }
    }

    fun read(id: Int): ManagerDTO? {
        return transaction {
            Manager.select { Manager.id eq id }
                .mapNotNull { toManagerDTO(it) }
                .singleOrNull()
        }
    }

    fun findByPhoneAndPassword(manager: ManagerLoginReceiveRemote): ManagerDTO?{
        return transaction {
            Manager.select { (Manager.phone eq manager.phone) and (Manager.password eq manager.password) }
                .mapNotNull { toManagerDTO(it) }
                .singleOrNull()
        }
    }

    fun readAll(): List<ManagerDTO> {
        return transaction {
            Manager.selectAll()
                .mapNotNull { toManagerDTO(it) }
        }
    }

    fun update(manager: ManagerDTO): Boolean {
        return transaction {
            Manager.update({ Manager.id eq manager.id }) {
                it[name] = manager.name
                it[phone] = manager.phone
                it[password] = manager.password
                it[showroomId] = manager.showroomId
            } > 0
        }
    }

    fun delete(id: Int): Boolean {
        return transaction {
            Manager.deleteWhere { Manager.id eq id } > 0
        }
    }

    private fun toManagerDTO(row: ResultRow): ManagerDTO {
        return ManagerDTO(
            id = row[Manager.id],
            name = row[Manager.name],
            phone = row[Manager.phone],
            password = row[Manager.password],
            showroomId = row[Manager.showroomId]
        )
    }
}