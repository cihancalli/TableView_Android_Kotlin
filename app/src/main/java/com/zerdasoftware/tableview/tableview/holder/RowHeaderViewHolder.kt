package com.zerdasoftware.tableview.tableview.holder

import android.view.View
import android.widget.TextView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.zerdasoftware.tableview.R


class RowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    val row_header_textview: TextView = itemView.findViewById(R.id.row_header_textview)
}