package com.example.dnd5emonsterviewer.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dnd5emonsterviewer.data.model.Monster
import com.example.dnd5emonsterviewer.data.model.MonsterUrls
import com.example.dnd5emonsterviewer.data.remote.MonsterAPI

class Repository(private val api: MonsterAPI) {

    private val _monsterUrls = MutableLiveData<List<MonsterUrls>>()
    val monsterUrls: LiveData<List<MonsterUrls>>
        get() = _monsterUrls

    private val _monsters = MutableLiveData<MutableList<Monster>>()
    val monsters: LiveData<MutableList<Monster>>
        get() = _monsters

    var allMonsters = mutableListOf<Monster>()

    private val _doneLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val doneLoading: MutableLiveData<Boolean>
        get() = _doneLoading

    init {
        _doneLoading.value = false
    }

    suspend fun loadMonsterUrls() {
        _monsterUrls.value = api.retrofitService.getMonsterReferences().results
        getMonsters()
    }

    suspend fun getMonsters() {
        val monsterList = mutableListOf<Monster>()
        if (monsterUrls.value != null) {
            /**
            for (url in monsterUrls.value!!) {
            monsterList.add(api.retrofitService.getMonster(url.index))
            }
             */
            var totalCount = 0
            while (totalCount < monsterUrls.value!!.size) {
                var indivCount = 0
                while (indivCount < 25 && totalCount < monsterUrls.value!!.size) {
                    monsterList.add(
                        api.retrofitService.getMonster(monsterUrls.value!![totalCount].index)
                    )
                    indivCount++
                    totalCount++
                }
                allMonsters = monsterList
                _monsters.value = monsterList
            }
            _doneLoading.value = true
        }
        /**
        allMonsters = monsterList
        _monsters.value = monsterList
        */
    }

    fun filterList(empty: Boolean, filter: String) {
        var filteredList = mutableListOf<Monster>()
        if (empty) {
            _monsters.value = allMonsters
        }
        else {
            for (monster in allMonsters) {
                if (monster.name.lowercase().contains(filter)) filteredList.add(monster)
            }
            _monsters.value = filteredList
        }
    }

    fun sortBy(sortBy: String) {
        var sortedList = mutableListOf<Monster>()
        when (sortBy) {
            "A-Z" -> _monsters.value?.sortedBy { it.name }?.let { sortedList.addAll(it) }
            "Z-A" -> _monsters.value?.sortedByDescending { it.name }?.let { sortedList.addAll(it) }
            "CR ascending" -> _monsters.value?.sortedBy { it.challenge_rating }?.let { sortedList.addAll(it) }
            "CR descending" ->_monsters.value?.sortedByDescending { it.challenge_rating }?.let { sortedList.addAll(it) }
            else -> _monsters.value?.sortedBy { it.name }?.let { sortedList.addAll(it) }
        }
        _monsters.value = sortedList
    }



}