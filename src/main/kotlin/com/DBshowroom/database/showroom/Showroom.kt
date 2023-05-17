package com.DBshowroom.database.showroom

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Showroom: Table("car_showroom") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 100)
    val address = varchar("address", length = 100)
    val phone = varchar("phone", length = 20)

    override val primaryKey = PrimaryKey(id)

    fun create(showroom: ShowroomReceiveRemote): Int{
        return transaction {
            Showroom.insert {
                it[name] = showroom.name
                it[address] = showroom.address
                it[phone] = showroom.phone
            } get Showroom.id
        }
    }

    fun read(id: Int): ShowroomDTO? {
        return transaction {
            Showroom.select { Showroom.id eq id }
                .mapNotNull { toShowroomDTO(it) }
                .singleOrNull()
        }
    }

    fun getShowroomByAddress(address: String): ShowroomDTO? {
        return transaction {
            Showroom.select { Showroom.address eq address }
                .mapNotNull { toShowroomDTO(it) }
                .singleOrNull()
        }
    }

    fun readAll(): List<ShowroomDTO> {
        return transaction {
            Showroom.selectAll()
                .mapNotNull{ toShowroomDTO(it) }
        }
    }

    fun update(showroomDTO: ShowroomDTO): Boolean {
        return transaction {
            Showroom.update({ Showroom.id eq showroomDTO.id }) {
                it[Showroom.name] = showroomDTO.name
                it[Showroom.address] = showroomDTO.address
                it[Showroom.phone] = showroomDTO.phone
            } > 0
        }
    }

    fun delete(id: Int): Boolean {
        return transaction {
            Showroom.deleteWhere { Showroom.id eq id } > 0
        }
    }

    fun deleteAll(){
        transaction {
            Showroom.deleteAll()
        }
    }

    private fun toShowroomDTO(row: ResultRow): ShowroomDTO {
        return ShowroomDTO(
            id = row[Showroom.id],
            name = row[Showroom.name],
            address = row[Showroom.address],
            phone = row[Showroom.phone]
        )
    }
}