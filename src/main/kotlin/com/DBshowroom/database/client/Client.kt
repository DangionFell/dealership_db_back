package com.DBshowroom.database.client

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

    fun findByPhoneAndPassword(client: ClientLoginReceiveRemote): ClientDTO?{
        return transaction {
            Client.select { (Client.phone eq client.phone) and (Client.password eq client.password) }
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

    fun update(client: ClientDTO): Boolean {
        return transaction {
            Client.update({ Client.id eq client.id }) {
                it[Client.name] = client.name
                it[Client.phone] = client.phone
                it[Client.password] = client.password
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