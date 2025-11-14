package com.imobly.imobly.persistences.category.repositories

import com.imobly.imobly.persistences.category.entities.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<CategoryEntity, String> {
    fun findByTitleContainingAllIgnoreCase(title: String): List<CategoryEntity>
}