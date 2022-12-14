package com.ukadovlad21.goalcounterv2


data class Winner(val name: String, var score: String)

class DataHolder {
    companion object {
        private var dataHolder: DataHolder? = null

        fun instance(): DataHolder {
            if (dataHolder == null) {
                dataHolder = DataHolder()
            }
            return dataHolder as DataHolder
        }
    }

    val winnerList = ArrayList<Winner>()
    fun addWinner(name: String, score: String) {
        val winner = Winner(name, score)

        winnerList.add(winner)
    }





    fun halfsInGame(game: Int): Int {
        return when (game) {
            R.drawable.ic_basketball, R.drawable.ic_water_polo -> 4
            R.drawable.ic_football, R.drawable.ic_handball -> 2
            R.drawable.ic_hockey -> 3
            R.drawable.ic_polo -> 6
            else -> 0
        }
    }

    fun durationsInGame(game: Int): Long {
        return when (game) {
            R.drawable.ic_basketball -> 6//00
            R.drawable.ic_football -> 2700
            R.drawable.ic_hockey -> 1200
            R.drawable.ic_water_polo -> 480
            R.drawable.ic_handball -> 1800
            R.drawable.ic_polo -> 420
            else -> 0
        }
    }
}
