package com.zerdasoftware.tableview.tableview.holder

import android.view.View
import android.widget.ImageView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.TableViewModel

open class MoodCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    val cell_image: ImageView = itemView.findViewById(R.id.cell_image)

    fun setData(data: Any?) {
        val mood = data as? Int ?: return
        val moodDrawable = if (mood == TableViewModel.HAPPY) R.drawable.ic_happy else R.drawable.ic_sad
        cell_image.setImageResource(moodDrawable)
    }
}