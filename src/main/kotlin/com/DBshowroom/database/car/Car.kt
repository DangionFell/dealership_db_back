package com.DBshowroom.database.car

import com.DBshowroom.database.showroom.Showroom
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Car: Table("car") {
    val id = integer("id").autoIncrement()
    val model = varchar("model", 30)
    val config = varchar("config", 30)
    val yearOfManufacture = varchar("year_of_manufacture", 10)
    val horsepower = integer("horsepower")
    val price = integer("price").check { it greater 0 }
    val color = varchar("color", 30)
    val state = varchar("state", 30)
    val showroomId = integer("id_showroom").references(Showroom.id)

    override val primaryKey = PrimaryKey(Showroom.id)


    fun create(car: CarReceiveRemote): Int {
        return transaction {
            Car.insert {
                it[model] = car.model
                it[config] = car.config
                it[yearOfManufacture] = car.yearOfManufacture
                it[horsepower] = car.horsepower
                it[price] = car.price
                it[color] = car.color
                it[state] = car.state
                it[showroomId] = car.showroomId
            } get Car.id
        }
    }

    fun read(id: Int): CarDTO? {
        return transaction {
            Car.select { Car.id eq id }
                .mapNotNull { toCarDTO(it) }
                .singleOrNull()
        }
    }

    fun readAll(): List<CarDTO> {
        return transaction {
            Car.selectAll()
                .mapNotNull { toCarDTO(it) }
        }
    }

    fun update(car: CarDTO): Boolean {
        return transaction {
            Car.update({ Car.id eq car.id }) {
                it[model] = car.model
                it[config] = car.config
                it[yearOfManufacture] = car.yearOfManufacture
                it[horsepower] = car.horsepower
                it[price] = car.price
                it[color] = car.color
                it[state] = car.state
                it[showroomId] = car.showroomId
            } > 0
        }
    }

    fun delete(id: Int): Boolean {
        return transaction {
            Car.deleteWhere { Car.id eq id } > 0
        }
    }

    fun getCarsByShowroomId(showroomId: Int): List<CarDTO> {
        return transaction {
            Car.select { Car.showroomId eq showroomId }
                .mapNotNull { toCarDTO(it) }
        }
    }

    private fun toCarDTO(row: ResultRow): CarDTO {
        return CarDTO(
            id = row[Car.id],
            model = row[Car.model],
            config = row[Car.config],
            yearOfManufacture = row[Car.yearOfManufacture],
            horsepower = row[Car.horsepower],
            price = row[Car.price],
            color = row[Car.color],
            state = row[Car.state],
            showroomId = row[Car.showroomId]
        )
    }
}