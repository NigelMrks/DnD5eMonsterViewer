package com.example.dnd5emonsterviewer.data.model

data class MonsterList(
    val results: List<MonsterUrls>
)

data class MonsterUrls(
    val index: String,
    val url: String
)