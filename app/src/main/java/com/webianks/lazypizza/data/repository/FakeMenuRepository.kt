package com.webianks.lazypizza.data.repository

import com.webianks.lazypizza.data.Category
import com.webianks.lazypizza.data.MenuItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMenuRepository : MenuRepository {

    override fun items(): Flow<List<MenuItem>> {
        TODO("Not yet implemented")
    }

    override fun search(query: String): Flow<List<MenuItem>> {
        TODO("Not yet implemented")
    }

    override fun byCategory(category: Category): Flow<List<MenuItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getItem(id: String): MenuItem? {
        TODO("Not yet implemented")
    }

    override fun recommendedCategories(): Flow<Set<String>> {
        TODO("Not yet implemented")
    }
}
