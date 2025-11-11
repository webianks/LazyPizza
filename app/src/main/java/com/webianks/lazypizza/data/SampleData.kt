package com.webianks.lazypizza.data

/*
data class MenuItem(
    var id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    var imageUrl: String = "",
    val category: String = ""
)*/

/*data class Topping(
    var id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    var imageUrl: String = "",
    val category: String = "Topping"
)*/


val sampleItem = MenuItem.Configurable(
    id = "sample_configurable_id",
    name = "Custom Pizza",
    basePrice = Money(7.99),
    imageUrl = "https://example.com/sample_pizza_image.png",
    category = "Pizza",
    description = "Tomato sauce, mozzarella, fresh basil, olive oil",
    groups = listOf(
        ModifierGroup(
            id = "toppings_group_id",
            options = listOf(
                ModifierOption(
                    id = "topping_bacon_id",
                    name = "Bacon",
                    price = Money(1.00),
                    imageUrl = "https://example.com/sample_topping_image.png"
                ),
                ModifierOption(
                    id = "topping_cheese_id",
                    name = "Extra Cheese",
                    price = Money(1.00),
                    imageUrl = "https://example.com/sample_topping_image.png"
                ),
                ModifierOption(
                    id = "topping_mushrooms_id",
                    name = "Mushrooms",
                    price = Money(0.50),
                    imageUrl = "https://example.com/sample_topping_image.png"
                )
            ),
            title = "Toppings",
            min = 1,
            max = 3
        )
    )
)

val sampleTopping = MenuItem.Simple(
    id = "topping_sample_id",
    name = "Bacon",
    basePrice = Money(1.00),
    imageUrl = "https://example.com/sample_topping_image.png",
    category = "Toppings"
)

/*val sampleToppings = mutableListOf(
    Topping(
        name = "Bacon",
        price = 1.00,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbacon.png?alt=media&token=ee1dcddf-274b-4830-8d88-257ff4925d48"
    ),
    Topping(
        name = "Extra Cheese",
        price = 1.00,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcheese.png?alt=media&token=40046f9c-71c0-4bce-b839-9b68b5798094"
    ),
    Topping(
        name = "Corn",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcorn.png?alt=media&token=03ddd0f8-d514-459c-a322-c2b507a2eb76"
    ),
    Topping(
        name = "Tomato",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Ftomato.png?alt=media&token=f0121ceb-bb50-4c3e-b8a6-f0023c932d21"
    ),
    Topping(
        name = "Olives",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Folive.png?alt=media&token=74c961bc-83ae-4d16-ad58-7c6dabbe9b0a"
    ),
    Topping(
        name = "Pepperoni",
        price = 1.00,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpepperoni.png?alt=media&token=e91bd839-6d45-4aba-9fd9-1d5fef7eb52c"
    ),
    Topping(
        name = "Mushrooms",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fmashroom.png?alt=media&token=d0f765d3-7380-46e1-95ce-5db8943dff7a"
    ),
    Topping(
        name = "Basil",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbasil.png?alt=media&token=4e57b896-a529-4ae4-aa5b-2e4616f317e9"
    ),
    Topping(
        name = "Pineapple",
        price = 1.00,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpineapple.png?alt=media&token=9d491809-94a2-4e4c-9ada-ff9f11acaf18"
    ),
    Topping(
        name = "Onion",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fonion.png?alt=media&token=478ce024-02a0-494d-b5cb-6eb2dd908cac"
    ),
    Topping(
        name = "Chili Peppers",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fchilli.png?alt=media&token=1ababda4-a97b-43b8-bd06-4bc0305c9f79"
    ),
    Topping(
        name = "Spinach",
        price = 0.50,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fspinach.png?alt=media&token=44937b97-f236-4ed9-961a-0e7982e52800"
    )
)*/

val simpleItem = MenuItem.Simple(
    id = "sample_id",
    name = "Sample Item",
    basePrice = Money(5.99),
    imageUrl = "https://example.com/sample_image.png",
    category = "Sauces"
)

/*val sampleMenu = mutableListOf(
    // Pizzas
    MenuItem(
        name = "Margherita",
        description = "Tomato sauce, mozzarella, fresh basil, olive oil",
        price = 8.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmargherita.png?alt=media&token=ef4b15d1-de0f-46d1-a036-644e193f5bbc",
        category = "Pizza"
    ),
    MenuItem(
        name = "Pepperoni",
        description = "Tomato sauce, mozzarella, pepperoni",
        price = 9.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fpepperoni.png?alt=media&token=eafb8f6d-3cf7-491e-85e8-0258eaf21c1c",
        category = "Pizza"
    ),
    MenuItem(
        name = "Hawaiian",
        description = "Tomato sauce, mozzarella, ham, pineapple",
        price = 10.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fhawaiian.png?alt=media&token=6f1ab78f-aa88-4cce-9dc7-f80c01b5120c",
        category = "Pizza"
    ),
    MenuItem(
        name = "BBQ Chicken",
        description = "BBQ sauce, mozzarella, grilled chicken, onion, corn",
        price = 11.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fbbq_chicken.png?alt=media&token=ec59a3b6-ceec-45da-8a4b-87f37c957226",
        category = "Pizza"
    ),
    MenuItem(
        name = "Four Cheese",
        description = "Mozzarella, gorgonzola, parmesan, ricotta",
        price = 11.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ffour_cheese.png?alt=media&token=446bff1e-41a0-4218-a82b-5ffcc1903974",
        category = "Pizza"
    ),
    MenuItem(
        name = "Veggie Delight",
        description = "Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
        price = 9.79,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fveggie_delight.png?alt=media&token=5b7c2d80-9524-4f9c-afed-b91fa7bddde4",
        category = "Pizza"
    ),
    MenuItem(
        name = "Meat Lovers",
        description = "Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
        price = 12.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmeat_lovers.png?alt=media&token=b8f567a0-d2e9-4eb3-9b82-550086039654",
        category = "Pizza"
    ),
    MenuItem(
        name = "Spicy Inferno",
        description = "Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
        price = 11.29,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fspicy_inferno.png?alt=media&token=b355067e-0867-4091-a15e-03f16655a604",
        category = "Pizza"
    ),
    MenuItem(
        name = "Seafood Special",
        description = "Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
        price = 13.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fseafood_special.png?alt=media&token=10a690d0-558c-460d-ae17-9d7e5cf43670",
        category = "Pizza"
    ),
    MenuItem(
        name = "Truffle Mushroom",
        description = "Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
        price = 12.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ftruffle_mushroom.png?alt=media&token=559f4708-49d6-43d8-b59c-447d5669c015",
        category = "Pizza"
    ),

    // Drinks
    MenuItem(
        name = "Mineral Water",
        description = "Still or sparkling",
        price = 1.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fmineral_water.png?alt=media&token=3e5621a4-5de4-45ec-a988-e6136c11a9d5",
        category = "Drinks"
    ),
    MenuItem(
        name = "7-Up",
        description = "Lemon-lime soda",
        price = 1.89,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fseven_up.png?alt=media&token=74d4bbf6-677a-4633-aede-1e85a2ac84c7",
        category = "Drinks"
    ),
    MenuItem(
        name = "Pepsi",
        description = "Classic cola",
        price = 1.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fpepsi.png?alt=media&token=7d2ac61c-ca29-4d55-bbae-4cce56849b14",
        category = "Drinks"
    ),
    MenuItem(
        name = "Orange Juice",
        description = "Freshly squeezed",
        price = 2.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Forange_juice.png?alt=media&token=3b530bda-9f4d-4078-acc8-570f3352cc06",
        category = "Drinks"
    ),
    MenuItem(
        name = "Apple Juice",
        description = "Sweet and refreshing",
        price = 2.29,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fapple_juice.png?alt=media&token=11d81306-8e6a-40ba-b78d-48d338bd3399",
        category = "Drinks"
    ),
    MenuItem(
        name = "Iced Tea (Lemon)",
        description = "Chilled with a hint of lemon",
        price = 2.19,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Ficed_tea.png?alt=media&token=e77c2a96-c63b-468c-8cfd-66f0d1cd21dd",
        category = "Drinks"
    ),

    // Sauces
    MenuItem(
        name = "Garlic Sauce",
        description = "Creamy garlic dip",
        price = 0.59,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fgarlic_sauce.png?alt=media&token=543594b1-177b-4f81-a0b6-59a42ee68c24",
        category = "Sauces"
    ),
    MenuItem(
        name = "BBQ Sauce",
        description = "Smoky barbecue dip",
        price = 0.59,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fbbq_sauce.png?alt=media&token=7dc571ab-59d1-456c-9aa9-f7c5fd293d80",
        category = "Sauces"
    ),
    MenuItem(
        name = "Cheese Sauce",
        description = "Rich cheddar cheese dip",
        price = 0.89,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fcheese_sauce.png?alt=media&token=a048a10b-3c62-4064-ada6-013d2377c00b",
        category = "Sauces"
    ),
    MenuItem(
        name = "Spicy Chili Sauce",
        description = "Hot and tangy dip",
        price = 0.59,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fspicy_chili_sauce.png?alt=media&token=66ac6dfc-5273-4f06-bda2-c9a8e3633383",
        category = "Sauces"
    ),

    // Ice Cream
    MenuItem(
        name = "Vanilla Ice Cream",
        description = "Classic vanilla bean",
        price = 2.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fvanilla.png?alt=media&token=f62c9fd7-0086-49ae-bd6b-a5cab4ed2a0d",
        category = "Ice Cream"
    ),
    MenuItem(
        name = "Chocolate Ice Cream",
        description = "Rich dark chocolate",
        price = 2.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fchocolate.png?alt=media&token=bd5d4dd2-a9ac-4229-9db4-c843f9eed021",
        category = "Ice Cream"
    ),
    MenuItem(
        name = "Strawberry Ice Cream",
        description = "Made with real strawberries",
        price = 2.49,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fstrawberry.png?alt=media&token=7a2cf52c-eee3-457d-86f1-72b3c7cfc33f",
        category = "Ice Cream"
    ),
    MenuItem(
        name = "Cookies Ice Cream",
        description = "Vanilla with cookie chunks",
        price = 2.79,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fcookies.png?alt=media&token=a42b67b4-9c8e-4c06-acee-61b582ea1fb2",
        category = "Ice Cream"
    ),
    MenuItem(
        name = "Pistachio Ice Cream",
        description = "Nutty and creamy",
        price = 2.99,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fpistachio.png?alt=media&token=a25de1f8-30f7-4f65-b66e-a5395362cb9b",
        category = "Ice Cream"
    ),
    MenuItem(
        name = "Mango Sorbet",
        description = "Dairy-free mango sorbet",
        price = 2.69,
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fmango_sorbet.png?alt=media&token=0c26ec24-528a-43e8-8b89-a8c77ab1efda",
        category = "Ice Cream"
    )
)*/
