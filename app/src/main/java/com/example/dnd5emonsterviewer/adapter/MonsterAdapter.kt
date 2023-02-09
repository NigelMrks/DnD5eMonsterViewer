package com.example.dnd5emonsterviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.dnd5emonsterviewer.R
import com.example.dnd5emonsterviewer.data.model.Monster

class MonsterAdapter() : RecyclerView.Adapter<MonsterAdapter.ItemViewHolder>() {

    private var dataset = mutableListOf<Monster>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.monster_name)
        val type: TextView = view.findViewById(R.id.monster_type)
        val cr: TextView = view.findViewById(R.id.monster_challenge_rating)
        val ac: TextView = view.findViewById(R.id.monster_armor_class)
        val hp: TextView = view.findViewById(R.id.monster_hit_points)
        val speed: TextView = view.findViewById(R.id.monster_speed)
        val strength: TextView = view.findViewById(R.id.monster_str)
        val dexterity: TextView = view.findViewById(R.id.monster_dex)
        val constitution: TextView = view.findViewById(R.id.monster_con)
        val intelligence: TextView = view.findViewById(R.id.monster_int)
        val wisdom: TextView = view.findViewById(R.id.monster_wis)
        val charisma: TextView = view.findViewById(R.id.monster_cha)
        val image: ImageView = view.findViewById(R.id.monster_image)

    }

    fun submitList(list: MutableList<Monster>) {
        dataset = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.monster_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val monster: Monster = dataset[position]

        holder.name.text = monster.name
        holder.type.text = "${monster.size} ${monster.type}, ${monster.alignment}"
        holder.ac.text = "${monster.armor_class[0].value} (${monster.armor_class[0].type})"
        holder.hp.text = "${monster.hit_points} (${monster.hit_points_roll})"
        var speed = ""
        if (monster.speed.walk != "") speed += monster.speed.walk
        if (monster.speed.fly != "") speed += ", " + monster.speed.fly
        if (monster.speed.swim != "") speed += ", " + monster.speed.swim
        holder.speed.text = speed
        val strMod = getMod(monster.strength)
        var strModString = if (strMod < 0) "+$strMod" else "$strMod"
        holder.strength.text = "${monster.strength} ($strModString)"

        val dexMod = getMod(monster.dexterity)
        var dexModString = if (dexMod < 0) "+$dexMod" else "$dexMod"
        holder.dexterity.text = "${monster.dexterity} ($dexModString)"

        val conMod = getMod(monster.constitution)
        var conModString = if (conMod < 0) "+$conMod" else "$conMod"
        holder.constitution.text = "${monster.constitution} ($conModString)"

        val intMod = getMod(monster.intelligence)
        var intModString = if (intMod < 0) "+$intMod" else "$intMod"
        holder.intelligence.text = "${monster.intelligence} ($intModString)"

        val wisMod = getMod(monster.wisdom)
        var wisModString = if (wisMod < 0) "+$wisMod" else "$wisMod"
        holder.wisdom.text = "${monster.wisdom} ($wisModString)"

        val chaMod = getMod(monster.charisma)
        var chaModString = if (chaMod < 0) "+$chaMod" else "$chaMod"
        holder.charisma.text = "${monster.charisma} ($chaModString)"

        when (monster.challenge_rating) {
            0.125 -> holder.cr.text = "CR 1/8"
            0.25 -> holder.cr.text = "CR 1/4"
            0.5 -> holder.cr.text = "CR 1/2"
            else -> holder.cr.text = "CR " + monster.challenge_rating.toInt().toString()
        }

        holder.image.load("https://www.dnd5eapi.co" + monster.image) {
            transformations(CircleCropTransformation())
            error(R.drawable.baseline_broken_image_24)
        }

    }
    private fun getMod(score: Int) : Int {
        var mod = 10
        when (score) {
            in 0..1 -> mod = -5
            in 2..3 -> mod = -4
            in 4..5 -> mod = -3
            in 6..7 -> mod = -2
            in 8..9 -> mod = -1
            in 10..11 -> mod = 0
            in 12..13 -> mod = 1
            in 14..15 -> mod = 2
            in 16..17 -> mod = 3
            in 18..19 -> mod = 4
            in 20..21 -> mod = 5
            in 22..23 -> mod = 6
            in 24..25 -> mod = 7
            in 26..27 -> mod = 8
            in 28..29 -> mod = 9
            30 -> mod = 10
            else -> 0
        }
        return mod
    }
    override fun getItemCount(): Int {
        return dataset.size
    }
}
