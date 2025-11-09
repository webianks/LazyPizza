package com.webianks.lazypizza.data.repository

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.webianks.lazypizza.data.Category
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.data.dto.CatalogSettingsDto
import com.webianks.lazypizza.data.dto.MenuItemDto
import com.webianks.lazypizza.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreMenuRepository(
    private val db: FirebaseFirestore = Firebase.firestore("lazy-pizza")
) : MenuRepository {

    private val activeDoc get() = db.collection("catalogs").document("active")
    private val itemsCol get() = activeDoc.collection("items")

    override fun items(): Flow<List<MenuItem>> = callbackFlow {
        val reg = itemsCol.orderBy("sortIndex", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    Log.e("WEBIANKS", "Error fetching menu items", err)
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val out = snap?.documents.orEmpty().mapNotNull {
                    it.toObject(MenuItemDto::class.java)?.copy(id = it.id)?.toDomain()
                }
                Log.d("WEBIANKS", "Fetched ${out.size} menu items")
                trySend(out)
            }
        awaitClose { reg.remove() }
    }.distinctUntilChanged()

    override fun search(query: String): Flow<List<MenuItem>> = items().map { list ->
        val q = query.trim().lowercase()
        if (q.isEmpty()) list else list.filter { it.name.lowercase().contains(q) }
    }

    override fun byCategory(category: Category): Flow<List<MenuItem>> = items().map { list ->
        list.filter { it.category.equals(category, ignoreCase = true) }
    }

    override suspend fun getItem(id: String): MenuItem? = withContext(Dispatchers.IO) {
        val doc = itemsCol.document(id).get().await()
        doc.toObject(MenuItemDto::class.java)?.copy(id = doc.id)?.toDomain()
    }

    override fun recommendedCategories(): Flow<Set<String>> = callbackFlow {
        val reg = activeDoc.addSnapshotListener { snap, err ->
            if (err != null) {
                trySend(setOf("drink", "sauce"))
                return@addSnapshotListener
            }
            val dto = snap?.toObject(CatalogSettingsDto::class.java)
            val cats = dto?.recommendedCategories?.toSet().orEmpty()
            trySend(cats.ifEmpty { setOf("drink", "sauce") })
        }
        awaitClose { reg.remove() }
    }.distinctUntilChanged()
}
