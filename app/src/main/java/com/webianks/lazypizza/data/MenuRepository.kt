/*
package com.webianks.lazypizza.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.webianks.lazypizza.ui.screens.MenuItem
import com.webianks.lazypizza.ui.screens.Topping
import com.webianks.lazypizza.ui.screens.sampleMenu
import com.webianks.lazypizza.ui.screens.sampleToppings
import kotlinx.coroutines.tasks.await

class MenuRepository {

    private val db = Firebase.firestore("lazy-pizza")

    suspend fun getMenu(): List<MenuItem> {
        return try {
            val menuItems = db.collection("menu").get().await().map {
                val menuItem = it.toObject(MenuItem::class.java)
                menuItem.id = it.id
                menuItem
            }
            val categoryOrder = listOf("Pizza", "Drinks", "Sauces", "Ice Cream")
            menuItems.sortedWith(compareBy { categoryOrder.indexOf(it.category) })
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error getting menu", e)
            emptyList()
        }
    }

    suspend fun getToppings(): List<Topping> {
        return try {
            db.collection("toppings").get().await().map {
                val topping = it.toObject(Topping::class.java)
                topping.id = it.id
                topping
            }
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error getting toppings", e)
            emptyList()
        }
    }

    suspend fun uploadSampleData() {
        val menuCollection = db.collection("menu")
        val toppingsCollection = db.collection("toppings")

        val batch = db.batch()

        sampleMenu.forEach { menuItem ->
            val docRef = menuCollection.document()
            menuItem.id = docRef.id
            batch.set(docRef, menuItem)
        }

        sampleToppings.forEach { topping ->
            val docRef = toppingsCollection.document()
            topping.id = docRef.id
            batch.set(docRef, topping)
        }

        try {
            batch.commit().await()
            Log.d("MenuRepository", "Sample data uploaded successfully.")
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error uploading sample data.", e)
        }
    }
}*/
