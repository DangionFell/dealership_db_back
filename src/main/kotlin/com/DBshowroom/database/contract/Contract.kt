package com.DBshowroom.database.contract

import com.DBshowroom.database.car.Car
import com.DBshowroom.database.client.Client
import com.DBshowroom.database.contract.Contract.update
import com.DBshowroom.database.manager.Manager
import com.DBshowroom.database.showroom.Showroom
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Contract: Table("contract") {
    val id = integer("id").autoIncrement()
    val date = varchar("date_of_conclusion", 20)
    val showroomId = integer("id_showroom").references(Showroom.id)
    val carId = integer("id_car").references(Car.id)
    val clientId = integer("id_client").references(Client.id)
    val managerId = integer("id_manager").references(Manager.id)

    override val primaryKey = PrimaryKey(Showroom.id)

    fun create(contract: ContractReceiveRemote): Int {
        return transaction {
            Car.update({ Car.id eq carId }) {
                it[Car.state] = "продано"
            }

            Contract.insert {
                it[date] = contract.date
                it[showroomId] = contract.showroomId
                it[carId] = contract.carId
                it[clientId] = contract.clientId
                it[managerId] = contract.managerId
            } get Car.id
        }
    }

    fun read(id: Int): ContractDTO? {
        return transaction {
            Contract.select { Contract.id eq id }
                .mapNotNull { toContractDTO(it) }
                .singleOrNull()
        }
    }

    fun readAll(): List<ContractDTO> {
        return transaction {
            Contract.selectAll()
                .mapNotNull { toContractDTO(it) }
        }
    }

    fun update(contract: ContractDTO): Boolean {
        return transaction {
            Contract.update({ Contract.id eq contract.id }) {
                it[date] = contract.date
                it[showroomId] = contract.showroomId
                it[carId] = contract.carId
                it[clientId] = contract.clientId
                it[managerId] = contract.managerId
            } > 0
        }
    }

    fun delete(id: Int): Boolean {
        return transaction {
            Contract.deleteWhere { Contract.id eq id } > 0
        }
    }

    private fun toContractDTO(row: ResultRow): ContractDTO {
        return ContractDTO(
            id = row[Contract.id],
            date = row[Contract.date],
            showroomId = row[Contract.showroomId],
            carId = row[Contract.carId],
            clientId = row[Contract.clientId],
            managerId = row[Contract.managerId]
        )
    }
}