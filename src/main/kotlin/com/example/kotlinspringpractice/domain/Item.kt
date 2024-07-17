package com.example.kotlinspringpractice.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Item(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false

        if (id != null && id == other.id) return true

        return false
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Item(id=$id, name='$name', description='$description')"
    }
}