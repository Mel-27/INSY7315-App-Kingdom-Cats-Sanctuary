package com.ayushi.will

/**
 * Represents a single cat profile.
 *
 * In production [imageUrl] points at a blob inside the "cat-images" container on
 * Azure Storage. [localImageRes] is only used as an offline fallback / demo placeholder
 * and is ignored once [imageUrl] is available.
 */
data class Cat(
    val id: String = "",
    val name: String = "",
    val breed: String = "",
    val ageLabel: String = "",
    val bio: String = "",
    val imageUrl: String = "",
    val localImageRes: Int? = null,
    val isAdopted: Boolean = false
)
