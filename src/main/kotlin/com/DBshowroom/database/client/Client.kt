package com.DBshowroom.database.client

import com.DBshowroom.database.client.Client.update
import com.DBshowroom.database.showroom.Showroom.autoIncrement
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Client: Table("client") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 100)
    val phone = varchar("phone", length = 20)
    val password = varchar("password", length = 20)

    override val primaryKey = PrimaryKey(id)

    fun create(client: ClientRegisterReceiveRemote): Int {
        return transaction {
            Client.insert {
                it[name] = client.name
                it[phone] = client.phone
                it[password] = client.password
            } get Client.id
        }
    }

    fun read(id: Int): ClientDTO? {
        return transaction {
            Client.select { Client.id eq id }
                .mapNotNull { toClientDTO(it) }
                .singleOrNull()
        }
    }

    fun findByPhoneAndPassword(phone: String, password: String): ClientDTO?{
        return transaction {
            Client.select { (Client.phone eq phone) and (Client.password eq password) }
                .mapNotNull { toClientDTO(it) }
                .singleOrNull()
        }
    }

    fun readAll(): List<ClientDTO> {
        return transaction {
            Client.selectAll()
                .mapNotNull { toClientDTO(it) }
        }
    }

    fun update(id: Int, name: String, phone: String, password: String): Boolean {
        return transaction {
            Client.update({ Client.id eq id }) {
                it[Client.name] = name
                it[Client.phone] = phone
                it[Client.password] = password
            } > 0
        }
    }

    fun delete(id: Int): Boolean {
        return transaction {
            Client.deleteWhere { Client.id eq id } > 0
        }
    }

    private fun toClientDTO(row: ResultRow): ClientDTO {
        return ClientDTO(
            id = row[Client.id],
            name = row[Client.name],
            phone = row[Client.phone],
            password = row[Client.password]
        )
    }
}