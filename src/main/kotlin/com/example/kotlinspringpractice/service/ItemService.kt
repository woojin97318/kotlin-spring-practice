package com.example.kotlinspringpractice.service

import com.example.kotlinspringpractice.global.exception.CustomException
import com.example.kotlinspringpractice.global.exception.ErrorMessage
import com.example.kotlinspringpractice.domain.item.Item
import com.example.kotlinspringpractice.web.dto.ItemSaveDto
import com.example.kotlinspringpractice.domain.item.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {
    fun findAll(): List<Item> =
        itemRepository.findAll()

    fun findById(id: Long): Item =
        itemRepository.findById(id).orElseThrow { CustomException(ErrorMessage.NOT_FOUND) }

    @Transactional
    fun save(itemSaveDto: ItemSaveDto): Item {
        val newItem: Item = itemSaveDto.toEntity()
        return itemRepository.save(newItem)
    }

    @Transactional
    fun update(id: Long, itemSaveDto: ItemSaveDto): Item {
        val existingItem = itemRepository.findById(id).orElseThrow { CustomException(ErrorMessage.NOT_FOUND) }
        existingItem.updateFromDto(itemSaveDto)

        // 더티 체킹으로 인해 Entity의 변경사항이 DB에 반영되지만 명확성을 위해 save() 호출
        return itemRepository.save(existingItem)
    }

    @Transactional
    fun deleteById(id: Long)  {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id)
        } else {
            throw CustomException(ErrorMessage.NOT_FOUND)
        }
    }
}