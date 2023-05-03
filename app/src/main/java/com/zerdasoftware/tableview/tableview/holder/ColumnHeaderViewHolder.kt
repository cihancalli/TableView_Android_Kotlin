@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package com.zerdasoftware.tableview.tableview.holder

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.evrencoskun.tableview.ITableView
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder
import com.evrencoskun.tableview.sort.SortState
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.model.ColumnHeader


class ColumnHeaderViewHolder(itemView: View, private val tableView: ITableView?) :
    AbstractSorterViewHolder(itemView) {

    private val LOG_TAG: String = ColumnHeaderViewHolder::class.java.simpleName

    private val column_header_container: LinearLayout = itemView.findViewById(R.id.column_header_container)
    private val column_header_textview: TextView = itemView.findViewById(R.id.column_header_textView)
    private val column_header_sortButton: ImageButton = itemView.findViewById(R.id.column_header_sortButton)

    private val mSortButtonClickListener = View.OnClickListener { view ->
        when (sortState) {
            SortState.ASCENDING -> tableView?.sortColumn(adapterPosition, SortState.DESCENDING)
            SortState.DESCENDING -> tableView?.sortColumn(adapterPosition, SortState.ASCENDING)
            else -> {
                tableView?.sortColumn(adapterPosition, SortState.DESCENDING)
            }
        }
    }

    init {
        column_header_sortButton.setOnClickListener(mSortButtonClickListener)
    }

    fun setColumnHeader(columnHeader: ColumnHeader?) {
        column_header_textview.text = columnHeader?.getData()?.toString()
        column_header_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        column_header_textview.requestLayout()
    }

    override fun onSortingStatusChanged(sortState: SortState) {
        Log.e(
            LOG_TAG,
            " + onSortingStatusChanged: x:  $adapterPosition, " +
                    "old state: $sortState, current state: $sortState, " +
                    "visibility: ${column_header_sortButton.visibility}"
        )

        super.onSortingStatusChanged(sortState)

        column_header_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT

        controlSortState(sortState)

        Log.e(
            LOG_TAG,
            " - onSortingStatusChanged: x:  $adapterPosition, " +
                    "old state: $sortState, current state: $sortState, " +
                    "visibility: ${column_header_sortButton.visibility}"
        )

        column_header_textview.requestLayout()
        column_header_sortButton.requestLayout()
        column_header_container.requestLayout()
        itemView.requestLayout()
    }

    private fun controlSortState(sortState: SortState) {
        when (sortState) {
            SortState.ASCENDING -> {
                column_header_sortButton.visibility = View.VISIBLE
                column_header_sortButton.setImageResource(R.drawable.ic_down)
            }
            SortState.DESCENDING -> {
                column_header_sortButton.visibility = View.VISIBLE
                column_header_sortButton.setImageResource(R.drawable.ic_up)
            }
            else -> {
                column_header_sortButton.visibility = View.INVISIBLE
            }
        }
    }
}
