package com.example.kotlinspringpractice.domain.item

import com.example.kotlinspringpractice.web.dto.ItemSaveDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Item(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String?,
    var description: String?
) {
    fun update(dto: ItemSaveDto) {
        // 필드 값이 null인 경우에도 실행 -> nullable인 경우
        this.name = dto.name
        this.description = dto.description

        // 필드 값이 null이 아닌 경우에만 실행 -> NOT NULL인 경우 (예외 발생 안함)
//        dto.name?.let { this.name = it }
//        dto.description?.let { this.description = it }
    }
}