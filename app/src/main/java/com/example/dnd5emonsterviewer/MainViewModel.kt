package com.example.dnd5emonsterviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dnd5emonsterviewer.data.Repository
import com.example.dnd5emonsterviewer.data.remote.MonsterAPI
import kotlinx.coroutines.launch

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private val repository = Repository(MonsterAPI)

    val monsterUrls = repository.monsterUrls
    val monsters = repository.monsters

    init {
        loadMonsterUrls()
    }

    private fun loadMonsterUrls() {
        viewModelScope.launch {
            repository.loadMonsterUrls()
        }
    }

    fun filter(filter: String) {
        var filterIsEmpty = true
        if (filter.isEmpty()) filterIsEmpty = true
        for (char in filter) {
            if (char != ' ') filterIsEmpty = false
        }

        repository.filterList(filterIsEmpty, filter)
    }

    fun sortBy(sortBy: String) {
        repository.sortBy(sortBy)
    }

}