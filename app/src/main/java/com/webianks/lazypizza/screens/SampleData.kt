package com.webianks.lazypizza.screens

data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    var imageUrl: String,
    val category: String
)

data class Topping(
    val id: String,
    val name: String,
    val price: String,
    var imageUrl: String,
    val category: String = "Topping"
)

val sampleToppings = mutableListOf(
    Topping(
        "27",
        "Bacon",
        "$1.00",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbacon.png?alt=media&token=ee1dcddf-274b-4830-8d88-257ff4925d48"
    ),
    Topping(
        "28",
        "Extra Cheese",
        "$1.00",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcheese.png?alt=media&token=40046f9c-71c0-4bce-b839-9b68b5798094"
    ),
    Topping(
        "29",
        "Corn",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fcorn.png?alt=media&token=03ddd0f8-d514-459c-a322-c2b507a2eb76"
    ),
    Topping(
        "30",
        "Tomato",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Ftomato.png?alt=media&token=f0121ceb-bb50-4c3e-b8a6-f0023c932d21"
    ),
    Topping(
        "31",
        "Olives",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Folive.png?alt=media&token=74c961bc-83ae-4d16-ad58-7c6dabbe9b0a"
    ),
    Topping(
        "32",
        "Pepperoni",
        "$1.00",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpepperoni.png?alt=media&token=e91bd839-6d45-4aba-9fd9-1d5fef7eb52c"
    ),
    Topping(
        "33",
        "Mushrooms",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fmashroom.png?alt=media&token=d0f765d3-7380-46e1-95ce-5db8943dff7a"
    ),
    Topping(
        "34",
        "Basil",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fbasil.png?alt=media&token=4e57b896-a529-4ae4-aa5b-2e4616f317e9"
    ),
    Topping(
        "35",
        "Pineapple",
        "$1.00",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fpineapple.png?alt=media&token=9d491809-94a2-4e4c-9ada-ff9f11acaf18"
    ),
    Topping(
        "36",
        "Onion",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fonion.png?alt=media&token=478ce024-02a0-494d-b5cb-6eb2dd908cac"
    ),
    Topping(
        "37",
        "Chili Peppers",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fchilli.png?alt=media&token=1ababda4-a97b-43b8-bd06-4bc0305c9f79"
    ),
    Topping(
        "38",
        "Spinach",
        "$0.50",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Ftoppings%2Fspinach.png?alt=media&token=44937b97-f236-4ed9-961a-0e7982e52800"
    )
)

val sampleMenu = mutableListOf(
    // Pizzas
    MenuItem(
        "1",
        "Margherita",
        "Tomato sauce, mozzarella, fresh basil, olive oil",
        "$8.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmargherita.png?alt=media&token=ef4b15d1-de0f-46d1-a036-644e193f5bbc",
        "Pizza"
    ),
    MenuItem(
        "2",
        "Pepperoni",
        "Tomato sauce, mozzarella, pepperoni",
        "$9.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fpepperoni.png?alt=media&token=eafb8f6d-3cf7-491e-85e8-0258eaf21c1c",
        "Pizza"
    ),
    MenuItem(
        "3",
        "Hawaiian",
        "Tomato sauce, mozzarella, ham, pineapple",
        "$10.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fhawaiian.png?alt=media&token=6f1ab78f-aa88-4cce-9dc7-f80c01b5120c",
        "Pizza"
    ),
    MenuItem(
        "4",
        "BBQ Chicken",
        "BBQ sauce, mozzarella, grilled chicken, onion, corn",
        "$11.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fbbq_chicken.png?alt=media&token=ec59a3b6-ceec-45da-8a4b-87f37c957226",
        "Pizza"
    ),
    MenuItem(
        "5",
        "Four Cheese",
        "Mozzarella, gorgonzola, parmesan, ricotta",
        "$11.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ffour_cheese.png?alt=media&token=446bff1e-41a0-4218-a82b-5ffcc1903974",
        "Pizza"
    ),
    MenuItem(
        "6",
        "Veggie Delight",
        "Tomato sauce, mozzarella, mushrooms, olives, bell pepper, onion, corn",
        "$9.79",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fveggie_delight.png?alt=media&token=5b7c2d80-9524-4f9c-afed-b91fa7bddde4",
        "Pizza"
    ),
    MenuItem(
        "7",
        "Meat Lovers",
        "Tomato sauce, mozzarella, pepperoni, ham, bacon, sausage",
        "$12.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fmeat_lovers.png?alt=media&token=b8f567a0-d2e9-4eb3-9b82-550086039654",
        "Pizza"
    ),
    MenuItem(
        "8",
        "Spicy Inferno",
        "Tomato sauce, mozzarella, spicy salami, jalapeños, red chili pepper, garlic",
        "$11.29",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fspicy_inferno.png?alt=media&token=b355067e-0867-4091-a15e-03f16655a604",
        "Pizza"
    ),
    MenuItem(
        "9",
        "Seafood Special",
        "Tomato sauce, mozzarella, shrimp, mussels, squid, parsley",
        "$13.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Fseafood_special.png?alt=media&token=10a690d0-558c-460d-ae17-9d7e5cf43670",
        "Pizza"
    ),
    MenuItem(
        "10",
        "Truffle Mushroom",
        "Cream sauce, mozzarella, mushrooms, truffle oil, parmesan",
        "$12.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fpizza%2Ftruffle_mushroom.png?alt=media&token=559f4708-49d6-43d8-b59c-447d5669c015",
        "Pizza"
    ),

    // Drinks
    MenuItem(
        "11",
        "Mineral Water",
        "Still or sparkling",
        "$1.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fmineral_water.png?alt=media&token=3e5621a4-5de4-45ec-a988-e6136c11a9d5",
        "Drinks"
    ),
    MenuItem(
        "12",
        "7-Up",
        "Lemon-lime soda",
        "$1.89",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fseven_up.png?alt=media&token=74d4bbf6-677a-4633-aede-1e85a2ac84c7",
        "Drinks"
    ),
    MenuItem(
        "13",
        "Pepsi",
        "Classic cola",
        "$1.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fpepsi.png?alt=media&token=7d2ac61c-ca29-4d55-bbae-4cce56849b14",
        "Drinks"
    ),
    MenuItem(
        "14",
        "Orange Juice",
        "Freshly squeezed",
        "$2.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Forange_juice.png?alt=media&token=3b530bda-9f4d-4078-acc8-570f3352cc06",
        "Drinks"
    ),
    MenuItem(
        "15",
        "Apple Juice",
        "Sweet and refreshing",
        "$2.29",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Fapple_juice.png?alt=media&token=11d81306-8e6a-40ba-b78d-48d338bd3399",
        "Drinks"
    ),
    MenuItem(
        "16",
        "Iced Tea (Lemon)",
        "Chilled with a hint of lemon",
        "$2.19",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fdrink%2Ficed_tea.png?alt=media&token=e77c2a96-c63b-468c-8cfd-66f0d1cd21dd",
        "Drinks"
    ),

    // Sauces
    MenuItem(
        "17",
        "Garlic Sauce",
        "Creamy garlic dip",
        "$0.59",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fgarlic_sauce.png?alt=media&token=543594b1-177b-4f81-a0b6-59a42ee68c24",
        "Sauces"
    ),
    MenuItem(
        "18",
        "BBQ Sauce",
        "Smoky barbecue dip",
        "$0.59",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fbbq_sauce.png?alt=media&token=7dc571ab-59d1-456c-9aa9-f7c5fd293d80",
        "Sauces"
    ),
    MenuItem(
        "19",
        "Cheese Sauce",
        "Rich cheddar cheese dip",
        "$0.89",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fcheese_sauce.png?alt=media&token=a048a10b-3c62-4064-ada6-013d2377c00b",
        "Sauces"
    ),
    MenuItem(
        "20",
        "Spicy Chili Sauce",
        "Hot and tangy dip",
        "$0.59",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fsauce%2Fspicy_chili_sauce.png?alt=media&token=66ac6dfc-5273-4f06-bda2-c9a8e3633383",
        "Sauces"
    ),

    // Ice Cream
    MenuItem(
        "21",
        "Vanilla Ice Cream",
        "Classic vanilla bean",
        "$2.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fvanilla.png?alt=media&token=f62c9fd7-0086-49ae-bd6b-a5cab4ed2a0d",
        "Ice Cream"
    ),
    MenuItem(
        "22",
        "Chocolate Ice Cream",
        "Rich dark chocolate",
        "$2.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fchocolate.png?alt=media&token=bd5d4dd2-a9ac-4229-9db4-c843f9eed021",
        "Ice Cream"
    ),
    MenuItem(
        "23",
        "Strawberry Ice Cream",
        "Made with real strawberries",
        "$2.49",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fstrawberry.png?alt=media&token=7a2cf52c-eee3-457d-86f1-72b3c7cfc33f",
        "Ice Cream"
    ),
    MenuItem(
        "24",
        "Cookies Ice Cream",
        "Vanilla with cookie chunks",
        "$2.79",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fcookies.png?alt=media&token=a42b67b4-9c8e-4c06-acee-61b582ea1fb2",
        "Ice Cream"
    ),
    MenuItem(
        "25",
        "Pistachio Ice Cream",
        "Nutty and creamy",
        "$2.99",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fpistachio.png?alt=media&token=a25de1f8-30f7-4f65-b66e-a5395362cb9b",
        "Ice Cream"
    ),
    MenuItem(
        "26",
        "Mango Sorbet",
        "Dairy-free mango sorbet",
        "$2.69",
        "https://firebasestorage.googleapis.com/v0/b/lazybites-237b4.firebasestorage.app/o/app_assets%2Fimages%2Ffood_items%2Fice_cream%2Fmango_sorbet.png?alt=media&token=0c26ec24-528a-43e8-8b89-a8c77ab1efda",
        "Ice Cream"
    )
)
