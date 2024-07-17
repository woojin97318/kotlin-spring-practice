package com.example.kotlinspringpractice.controller

import com.example.kotlinspringpractice.domain.Item
import com.example.kotlinspringpractice.dto.ItemSaveDto
import com.example.kotlinspringpractice.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun findAll(): List<Item> = itemService.findAll()

    /**
     * item select by id
     */
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Item> =
        itemService.findById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()

    /**
     * Item 저장
     */
    @PostMapping
    fun save(@RequestBody itemSaveDto: ItemSaveDto): Item =
        itemService.save(itemSaveDto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updatedItem: Item): ResponseEntity<Item> =
        itemService.update(id, updatedItem)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()

    /**
     * Item 삭제
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        if (itemService.deleteById(id)) {
            ResponseEntity<Void>(HttpStatus.OK)
        } else {
            ResponseEntity.notFound().build()
        }
}