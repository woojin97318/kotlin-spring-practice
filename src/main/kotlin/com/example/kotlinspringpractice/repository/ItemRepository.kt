package com.example.kotlinspringpractice.repository

import com.example.kotlinspringpractice.domain.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long>