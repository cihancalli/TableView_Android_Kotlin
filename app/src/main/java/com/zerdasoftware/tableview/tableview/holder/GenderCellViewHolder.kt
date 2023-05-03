@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.zerdasoftware.tableview.tableview.holder

import android.view.View
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.TableViewModel
import java.util.Objects

class GenderCellViewHolder(itemView: View) : MoodCellViewHolder(itemView) {

        fun setData(data: Objects) {
        val gender = data as Int
        val genderDrawable = if (gender == TableViewModel.BOY) R.drawable.ic_male else R.drawable.ic_female

        cell_image!!.setImageResource(genderDrawable)
    }

}
