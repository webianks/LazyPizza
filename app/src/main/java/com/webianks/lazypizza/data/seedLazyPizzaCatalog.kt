@file:Suppress("FunctionName")

package com.webianks.lazypizza.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.webianks.lazypizza.data.dto.CatalogSettingsDto
import com.webianks.lazypizza.data.dto.MenuItemDto
import com.webianks.lazypizza.data.dto.ModifierGroupDto
import com.webianks.lazypizza.data.dto.ModifierOptionDto
import kotlinx.coroutines.tasks.await

/** Seed with full image URLs and Firestore auto-generated item IDs. */
suspend fun seedGenericCatalog(db: FirebaseFirestore = Firebase.firestore("lazy-pizza")) {
    val activeDoc = db.collection("catalogs").document("active")
    val itemsCol = activeDoc.collection("items")

    // ---- settings on catalogs/active ----
    val settings = CatalogSettingsDto(recommendedCategories = listOf("drinks", "sauces"))
    val meta = mapOf("version" to 1, "isActive" to true)
    activeDoc.set(meta + mapOf("recommendedCategories" to settings.recommendedCategories)).await()

    // ---- topping image URLs (from your sample) ----
    val toppingImage = mapOf(
        "Bacon" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbacon.png?alt=media&token=ee1dcddf-274b-4830-8d88-257ff4925d48",
        "Extra Cheese" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcheese.png?alt=media&token=40046f9c-71c0-4bce-b839-9b68b5798094",
        "Corn" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcorn.png?alt=media&token=03ddd0f8-d514-459c-a322-c2b507a2eb76",
        "Tomato" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Ftomato.png?alt=media&token=f0121ceb-bb50-4c3e-b8a6-f0023c932d21",
        "Olives" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Folive.png?alt=media&token=74c961bc-83ae-4d16-ad58-7c6dabbe9b0a",
        "Pepperoni" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpepperoni.png?alt=media&token=e91bd839-6d45-4aba-9fd9-1d5fef7eb52c",
        "Mushrooms" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fmashroom.png?alt=media&token=d0f765d3-7380-46e1-95ce-5db8943dff7a",
        "Basil" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbasil.png?alt=media&token=4e57b896-a529-4ae4-aa5b-2e4616f317e9",
        "Pineapple" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpineapple.png?alt=media&token=9d491809-94a2-4e4c-9ada-ff9f11acaf18",
        "Onion" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fonion.png?alt=media&token=478ce024-02a0-494d-b5cb-6eb2dd908cac",
        "Chili Peppers" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fchilli.png?alt=media&token=1ababda4-a97b-43b8-bd06-4bc0305c9f79",
        "Spinach" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fspinach.png?alt=media&token=44937b97-f236-4ed9-961a-0e7982e52800",
    )

    // ---- menu item image URLs (from your sample) ----
    val itemImage = mapOf(
        // pizzas
        "Margherita" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmargherita.png?alt=media&token=ef4b15d1-de0f-46d1-a036-644e193f5bbc",
        "Pepperoni" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fpepperoni.png?alt=media&token=eafb8f6d-3cf7-491e-85e8-0258eaf21c1c",
        "Hawaiian" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fhawaiian.png?alt=media&token=6f1ab78f-aa88-4cce-9dc7-f80c01b5120c",
        "BBQ Chicken" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fbbq_chicken.png?alt=media&token=ec59a3b6-ceec-45da-8a4b-87f37c957226",
        "Four Cheese" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ffour_cheese.png?alt=media&token=446bff1e-41a0-4218-a82b-5ffcc1903974",
        "Veggie Delight" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fveggie_delight.png?alt=media&token=5b7c2d80-9524-4f9c-afed-b91fa7bddde4",
        "Meat Lovers" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmeat_lovers.png?alt=media&token=b8f567a0-d2e9-4eb3-9b82-550086039654",
        "Spicy Inferno" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fspicy_inferno.png?alt=media&token=b355067e-0867-4091-a15e-03f16655a604",
        "Seafood Special" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fseafood_special.png?alt=media&token=10a690d0-558c-460d-ae17-9d7e5cf43670",
        "Truffle Mushroom" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ftruffle_mushroom.png?alt=media&token=559f4708-49d6-43d8-b59c-447d5669c015",
        // drinks
        "Mineral Water" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fmineral_water.png?alt=media&token=3e5621a4-5de4-45ec-a988-e6136c11a9d5",
        "7-Up" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fseven_up.png?alt=media&token=74d4bbf6-677a-4633-aede-1e85a2ac84c7",
        "Pepsi" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fpepsi.png?alt=media&token=7d2ac61c-ca29-4d55-bbae-4cce56849b14",
        "Orange Juice" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Forange_juice.png?alt=media&token=3b530bda-9f4d-4078-acc8-570f3352cc06",
        "Apple Juice" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fapple_juice.png?alt=media&token=11d81306-8e6a-40ba-b78d-48d338bd3399",
        "Iced Tea (Lemon)" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Ficed_tea.png?alt=media&token=e77c2a96-c63b-468c-8cfd-66f0d1cd21dd",
        // sauces
        "Garlic Sauce" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fgarlic_sauce.png?alt=media&token=543594b1-177b-4f81-a0b6-59a42ee68c24",
        "BBQ Sauce" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fbbq_sauce.png?alt=media&token=7dc571ab-59d1-456c-9aa9-f7c5fd293d80",
        "Cheese Sauce" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fcheese_sauce.png?alt=media&token=a048a10b-3c62-4064-ada6-013d2377c00b",
        "Spicy Chili Sauce" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fspicy_chili_sauce.png?alt=media&token=66ac6dfc-5273-4f06-bda2-c9a8e3633383",
        // ice cream
        "Vanilla Ice Cream" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fvanilla.png?alt=media&token=f62c9fd7-0086-49ae-bd6b-a5cab4ed2a0d",
        "Chocolate Ice Cream" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fchocolate.png?alt=media&token=bd5d4dd2-a9ac-4229-9db4-c843f9eed021",
        "Strawberry Ice Cream" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fstrawberry.png?alt=media&token=7a2cf52c-eee3-457d-86f1-72b3c7cfc33f",
        "Cookies Ice Cream" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fcookies.png?alt=media&token=a42b67b4-9c8e-4c06-acee-61b582ea1fb2",
        "Pistachio Ice Cream" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fpistachio.png?alt=media&token=a25de1f8-30f7-4f65-b66e-a5395362cb9b",
        "Mango Sorbet" to "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fmango_sorbet.png?alt=media&token=0c26ec24-528a-43e8-8b89-a8c77ab1efda",
    )

    // ---- pizzas (configurable) ----
    data class Pizza(val name: String, val price: Double, val ingredients: List<String>)

    val pizzas = listOf(
        Pizza("Margherita", 8.99, listOf("Tomato sauce", "mozzarella", "fresh basil", "olive oil")),
        Pizza("Pepperoni", 9.99, listOf("Tomato sauce", "mozzarella", "pepperoni")),
        Pizza("Hawaiian", 10.49, listOf("Tomato sauce", "mozzarella", "ham", "pineapple")),
        Pizza(
            "BBQ Chicken",
            11.49,
            listOf("BBQ sauce", "mozzarella", "grilled chicken", "onion", "corn")
        ),
        Pizza("Four Cheese", 11.99, listOf("Mozzarella", "gorgonzola", "parmesan", "ricotta")),
        Pizza(
            "Veggie Delight",
            9.79,
            listOf(
                "Tomato sauce",
                "mozzarella",
                "mushrooms",
                "olives",
                "bell pepper",
                "onion",
                "corn"
            )
        ),
        Pizza(
            "Meat Lovers",
            12.49,
            listOf("Tomato sauce", "mozzarella", "pepperoni", "ham", "bacon", "sausage")
        ),
        Pizza(
            "Spicy Inferno",
            11.29,
            listOf(
                "Tomato sauce",
                "mozzarella",
                "spicy salami",
                "jalapeños",
                "red chili pepper",
                "garlic"
            )
        ),
        Pizza(
            "Seafood Special",
            13.99,
            listOf("Tomato sauce", "mozzarella", "shrimp", "mussels", "squid", "parsley")
        ),
        Pizza(
            "Truffle Mushroom",
            12.99,
            listOf("Cream sauce", "mozzarella", "mushrooms", "truffle oil", "parmesan")
        ),
    )

    // ---- simple items ----
    data class Simple(val name: String, val price: Double, val category: String)

    val drinks = listOf(
        Simple("Mineral Water", 1.49, "drinks"),
        Simple("7-Up", 1.89, "drinks"),
        Simple("Pepsi", 1.99, "drinks"),
        Simple("Orange Juice", 2.49, "drinks"),
        Simple("Apple Juice", 2.29, "drinks"),
        Simple("Iced Tea (Lemon)", 2.19, "drinks"),
    )
    val sauces = listOf(
        Simple("Garlic Sauce", 0.59, "sauces"),
        Simple("BBQ Sauce", 0.59, "sauces"),
        Simple("Cheese Sauce", 0.89, "sauces"),
        Simple("Spicy Chili Sauce", 0.59, "sauces"),
    )
    val iceCreams = listOf(
        Simple("Vanilla Ice Cream", 2.49, "ice cream"),
        Simple("Chocolate Ice Cream", 2.49, "ice cream"),
        Simple("Strawberry Ice Cream", 2.49, "ice cream"),
        Simple("Cookies Ice Cream", 2.79, "ice cream"),
        Simple("Pistachio Ice Cream", 2.99, "ice cream"),
        Simple("Mango Sorbet", 2.69, "ice cream"),
    )

    // ---- toppings (12) ----
    data class Opt(val id: String, val name: String, val price: Double)

    val toppingOptions = listOf(
        Opt("bacon", "Bacon", 1.00),
        Opt("extra_cheese", "Extra Cheese", 1.00),
        Opt("corn", "Corn", 0.50),
        Opt("tomato", "Tomato", 0.50),
        Opt("olives", "Olives", 0.50),
        Opt("pepperoni", "Pepperoni", 1.00),
        Opt("mushrooms", "Mushrooms", 0.50),
        Opt("basil", "Basil", 0.50),
        Opt("pineapple", "Pineapple", 1.00),
        Opt("onion", "Onion", 0.50),
        Opt("chili_peppers", "Chili Peppers", 0.50),
        Opt("spinach", "Spinach", 0.50),
    )

    val toppingsGroup = ModifierGroupDto(
        id = "toppings",
        title = "Toppings",
        min = 1,
        max = 3,
        options = toppingOptions.map { o ->
            ModifierOptionDto(
                id = o.id,
                name = o.name,
                price = o.price,
                imageUrl = toppingImage[o.name]
            )
        }
    )

    var sort = 0

    // ---- PIZZAS: configurable, auto IDs ----
    for (p in pizzas) {
        val dto = MenuItemDto(
            name = p.name,
            imageUrl = itemImage[p.name] ?: "",
            category = "pizza",
            price = p.price,
            type = "configurable",
            description = p.ingredients.joinToString(),
            groups = listOf(toppingsGroup),
            sortIndex = sort++
        )
        itemsCol.add(dto).await() // auto-generated document ID
    }

    // ---- SIMPLE items: drinks/sauces/ice_cream, auto IDs ----
    suspend fun addSimple(name: String, price: Double, cat: String) {
        val dto = MenuItemDto(
            name = name,
            imageUrl = itemImage[name] ?: "",
            category = cat, // "drink" | "sauce" | "ice_cream"
            price = price,
            type = "simple",
            sortIndex = sort++
        )
        itemsCol.add(dto).await() // auto-generated document ID
    }

    for (d in drinks) addSimple(d.name, d.price, d.category)
    for (s in sauces) addSimple(s.name, s.price, s.category)
    for (i in iceCreams) addSimple(i.name, i.price, i.category)
}

