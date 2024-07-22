package com.example.kotlinspringpractice.web.controller

import com.example.kotlinspringpractice.domain.item.Item
import com.example.kotlinspringpractice.web.dto.ItemSaveDto
import com.example.kotlinspringpractice.service.ItemService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/item")
class ItemController(
    private val itemService: ItemService
) {
    /**
     * 모든 Item 조회
     */
    @GetMapping
    fun findAll(): List<Item> =
        itemService.findAll()

    /**
     * ID로 Item 조회
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Item =
        itemService.findById(id)

    /**
     * Item 저장
     */
    @PostMapping
    fun save(@RequestBody itemSaveDto: ItemSaveDto): Item =
        itemService.save(itemSaveDto)

    /**
     * Item 수정
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody itemSaveDto: ItemSaveDto): Item =
        itemService.update(id, itemSaveDto)

    /**
     * Item 삭제
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Unit =
        itemService.deleteById(id)
}