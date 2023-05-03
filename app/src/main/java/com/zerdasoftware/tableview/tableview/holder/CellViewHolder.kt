package com.zerdasoftware.tableview.tableview.holder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.model.Cell

class CellViewHolder( itemView: View) : AbstractViewHolder(itemView) {
    private val cell_textview: TextView = itemView.findViewById(R.id.cell_data)
    private val cell_container: LinearLayout = itemView.findViewById(R.id.cell_container)

    fun setCell(cell: Cell?) {
        cell_textview.text = cell?.getData().toString()
        cell_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        cell_textview.requestLayout()
    }
}
