package com.ayushi.will.data

import com.ayushi.will.Cat
import com.ayushi.will.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Founder(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val imageUrl: String = ""
)

data class Review(
    val id: String = "",
    val authorName: String = "",
    val rating: Int = 5,
    val comment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

object CatRepository {

    private val _cats = MutableStateFlow<List<Cat>>(emptyList())
    val cats: StateFlow<List<Cat>> = _cats

    private val _founders = MutableStateFlow<List<Founder>>(emptyList())
    val founders: StateFlow<List<Founder>> = _founders

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    init {
        loadDemoData()
    }

    fun observeCats(onResult: (List<Cat>) -> Unit) {
        onResult(_cats.value)
    }

    fun observeFounders(onResult: (List<Founder>) -> Unit) {
        onResult(_founders.value)
    }

    fun observeReviews(onResult: (List<Review>) -> Unit) {
        onResult(_reviews.value)
    }

    private fun loadDemoData() {
        _cats.value = listOf(
            Cat(
                id = "cat_ginger",
                name = "Ginger",
                breed = "Domestic Shorthair",
                ageLabel = "2 years old",
                bio = "Curious and affectionate, Ginger loves sunny railings.",
                localImageRes = R.drawable.cat_ginger
            ),
            Cat(
                id = "cat_midnight",
                name = "Midnight",
                breed = "Maine Coon",
                ageLabel = "3 years old",
                bio = "Calm and watchful, Midnight is looking for a quiet home.",
                localImageRes = R.drawable.cat_midnight
            ),
            Cat(
                id = "cat_oliver",
                name = "Oliver",
                breed = "Tabby",
                ageLabel = "2 years old",
                bio = "Cuddly and loves being indoors. Oliver is waiting for his forever home.",
                localImageRes = R.drawable.cat_oliver
            )
        )

        _founders.value = listOf(
            Founder(id = "f1", name = "Greg Bower", role = "Co-FOUNDER"),
            Founder(id = "f2", name = "Jenny Bower", role = "Co-FOUNDER")
        )

        _reviews.value = listOf(
            Review(
                id = "r1",
                authorName = "Wouter Geldershuys",
                rating = 5,
                comment = "An amazing place to visit, if you looking to adopt, it's a must"
            ),
            Review(
                id = "r2",
                authorName = "Pamiann Mac Gregor",
                rating = 5,
                comment = "Animal loving people who give of their time and from their hearts. Bless you"
            ),
            Review(
                id = "r3",
                authorName = "Shirley Lyle Gould",
                rating = 5,
                comment = "Homing a furbaby who doesn't expect anything more than love, food and a safe place..."
            ),
            Review(
                id = "r4",
                authorName = "Shanna Stokes",
                rating = 5,
                comment = "What a wonderful initiative & what lovely people that run it!"
            ),
            Review(
                id = "r5",
                authorName = "Jay Jamieson",
                rating = 5,
                comment = "Most amazing place in the world, the cats are all so happy and well looked after."
            )
        )
    }

    fun getCatById(id: String): Cat? {
        return _cats.value.find { it.id == id }
    }
}