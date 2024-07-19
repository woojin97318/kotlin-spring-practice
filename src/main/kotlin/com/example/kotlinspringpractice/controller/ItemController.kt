package com.example.kotlinspringpractice.controller

import com.example.kotlinspringpractice.common.annotation.ApiResponseWrapper
import com.example.kotlinspringpractice.dto.response.ApiResponse
import com.example.kotlinspringpractice.common.util.ResponseUtil
import com.example.kotlinspringpractice.domain.Item
import com.example.kotlinspringpractice.dto.ItemSaveDto
import com.example.kotlinspringpractice.service.ItemService
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
//    @ApiResponseWrapper
    @GetMapping
    fun findAll(): List<Item>? = itemService.findAll()
//    fun findAll(): ResponseEntity<ApiResponse<List<Item>>> =
//        ResponseUtil.successResponse(itemService.findAll())

    /**
     * ID로 Item 조회
     */
    @ApiResponseWrapper
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): Item? = itemService.findById(id)
//    fun findById(@PathVariable id: Long): ResponseEntity<ApiResponse<Item>> =
//        itemService.findById(id)?.let {
//            ResponseUtil.successResponse(it)
//        } ?: ResponseUtil.notFoundResponse()

    /**
     * Item 저장
     */
    @PostMapping
    fun save(@RequestBody itemSaveDto: ItemSaveDto): ResponseEntity<ApiResponse<Item>> =
        ResponseUtil.successResponse(itemService.save(itemSaveDto))

    /**
     * Item 수정
     */
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updatedItem: Item): ResponseEntity<ApiResponse<Item>> =
        itemService.update(id, updatedItem)?.let {
            ResponseUtil.successResponse(it)
        } ?: ResponseUtil.notFoundResponse()

    /**
     * Item 삭제
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Void>> =
        if (itemService.deleteById(id)) {
            ResponseUtil.successResponseWithoutData()
        } else {
            ResponseUtil.notFoundResponse()
        }
}