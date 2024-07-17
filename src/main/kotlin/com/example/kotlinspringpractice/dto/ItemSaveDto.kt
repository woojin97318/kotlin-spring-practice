package com.example.kotlinspringpractice.dto

import com.example.kotlinspringpractice.domain.Item

data class ItemSaveDto(
    val name: String,
    val description: String
) {
    fun toEntity(): Item {
        return Item(
            name = this.name,
            description = this.description
        )
    }
}
