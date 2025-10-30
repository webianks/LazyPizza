package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.Category
import com.webianks.lazypizza.data.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun items(): Flow<List<MenuItem>>
    fun search(query: String): Flow<List<MenuItem>>
    fun byCategory(category: Category): Flow<List<MenuItem>>
    suspend fun getItem(id: String): MenuItem?

    /** Server-driven categories used to build add-on recommendations. */
    fun recommendedCategories(): Flow<Set<String>>
}