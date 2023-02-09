package com.example.dnd5emonsterviewer.data.model

data class Monster(
    val index: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val armor_class: List<ArmorClass>,
    val hit_points: Int,
    val hit_dice: String,
    val hit_points_roll: String,
    val speed: Speed,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val challenge_rating: Double,
    val image: String = ""
)

data class ArmorClass(
    val type: String,
    val value: Int
)

data class Speed(
    val walk: String = "",
    val fly: String = "",
    val swim: String = ""
)


