package com.zerdasoftware.tableview.tableview

import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.model.Cell
import com.zerdasoftware.tableview.tableview.model.ColumnHeader
import com.zerdasoftware.tableview.tableview.model.RowHeader
import java.util.*

class TableViewModel {

    companion object {
        const val MOOD_COLUMN_INDEX = 3
        const val GENDER_COLUMN_INDEX = 4

        const val SAD = 1
        const val HAPPY = 2
        const val BOY = 1
        const val GIRL = 2

        private const val COLUMN_SIZE = 10
        private const val ROW_SIZE = 10
    }

    // Drawables
    @DrawableRes
    private val mBoyDrawable: Int = R.drawable.ic_male
    @DrawableRes
    private val mGirlDrawable: Int = R.drawable.ic_female
    @DrawableRes
    private val mHappyDrawable: Int = R.drawable.ic_happy
    @DrawableRes
    private val mSadDrawable: Int = R.drawable.ic_sad

    private fun getSimpleRowHeaderList(): List<RowHeader> {
        val list = mutableListOf<RowHeader>()
        for (i in 0 until ROW_SIZE) {
            val header = RowHeader(i.toString(), "row $i")
            list.add(header)
        }
        return list
    }

    private fun getRandomColumnHeaderList(): List<ColumnHeader> {
        val list = mutableListOf<ColumnHeader>()

        for (i in 0 until COLUMN_SIZE) {
            var title = "column $i"
            val nRandom = Random().nextInt()
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column $i"
            }

            val header = ColumnHeader(i.toString(), title)
            list.add(header)
        }

        return list
    }

    private fun getCellListForSortingTest(): List<List<Cell>> {
        val list = mutableListOf<List<Cell>>()
        for (i in 0 until ROW_SIZE) {
            val cellList = mutableListOf<Cell>()
            for (j in 0 until COLUMN_SIZE) {
                var text: Any = "cell $j $i"

                val random = Random().nextInt()
                if (j == 0) {
                    text = i
                } else if (j == 1) {
                    text = random
                } else if (j == MOOD_COLUMN_INDEX) {
                    text = if (random % 2 == 0) HAPPY else SAD
                } else if (j == GENDER_COLUMN_INDEX) {
                    text = if (random % 2 == 0) BOY else GIRL
                }

                val id = "$j-$i"

                val cell = if (j == 3 || j == 4) {
                    Cell(id, text)
                } else {
                    Cell(id, text)
                }
                cellList.add(cell)
            }
            list.add(cellList)
        }

        return list
    }

    @DrawableRes
    fun getDrawable(value: Int, isGender: Boolean): Int {
        return if (isGender) {
            if (value == BOY) mBoyDrawable else mGirlDrawable
        } else {
            if (value == SAD) mSadDrawable else mHappyDrawable
        }
    }

    fun getCellList(): List<List<Cell>> { return getCellListForSortingTest() }

    fun getRowHeaderList(): List<RowHeader> { return getSimpleRowHeaderList() }

    fun getColumnHeaderList(): List<ColumnHeader> { return getRandomColumnHeaderList() }


}