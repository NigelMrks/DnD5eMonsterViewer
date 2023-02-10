package com.example.dnd5emonsterviewer

import android.content.Context
import android.util.Log
import android.widget.Toast
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
            try {
                repository.loadMonsterUrls()
            } catch (e: Exception) {
                Log.e(TAG, "error loading monsters: $e")
            }
        }
    }

    fun filter(filter: String, context: Context) {
        val doneLoading = repository.doneLoading
        if (doneLoading.value == true) {
            var filterIsEmpty = true
            if (filter.isEmpty()) filterIsEmpty = true
            for (char in filter) {
                if (char != ' ') filterIsEmpty = false
            }

            repository.filterList(filterIsEmpty, filter)
        }
        else {
            Toast.makeText(
                context,
                "Please wait for the list to finish loading...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun sortBy(sortBy: String, context: Context) {
        val doneLoading = repository.doneLoading
        if (doneLoading.value == true) {
            repository.sortBy(sortBy)
        }
        else {
            Toast.makeText(
                context,
                "Please wait for the list to finish loading...",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}