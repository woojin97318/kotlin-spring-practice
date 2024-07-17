package com.example.kotlinspringpractice.service

import com.example.kotlinspringpractice.domain.Item
import com.example.kotlinspringpractice.dto.ItemSaveDto
import com.example.kotlinspringpractice.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService (
    private val itemRepository: ItemRepository
) {
    fun findAll(): List<Item> =
        itemRepository.findAll()

    fun findById(id: Long): Item? =
        itemRepository.findById(id).orElse(null)

    @Transactional
    fun save(itemSaveDto: ItemSaveDto): Item {
        val newItem = itemSaveDto.toEntity()
        return itemRepository.save(newItem)
    }

    @Transactional
    fun update(id: Long, updatedItem: Item): Item? {
        val existingItem = itemRepository.findById(id).orElse(null)

        return if (existingItem != null) {
            existingItem.name = updatedItem.name
            existingItem.description = updatedItem.description
            itemRepository.save(existingItem)
        } else {
            null
        }
    }

    @Transactional
    fun deleteById(id: Long): Boolean {
        return if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}