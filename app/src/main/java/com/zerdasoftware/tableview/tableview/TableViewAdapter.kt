package com.zerdasoftware.tableview.tableview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.evrencoskun.tableview.sort.SortState
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.holder.*
import com.zerdasoftware.tableview.tableview.model.Cell
import com.zerdasoftware.tableview.tableview.model.ColumnHeader
import com.zerdasoftware.tableview.tableview.model.RowHeader

class TableViewAdapter (tableViewModel: TableViewModel) : AbstractTableAdapter<ColumnHeader,RowHeader,Cell>() {
    private val mTableViewModel: TableViewModel = tableViewModel
    private val MOOD_CELL_TYPE = 1
    private val GENDER_CELL_TYPE = 2
    private val LOG_TAG = "TableViewAdapter"

    @SuppressLint("RestrictedApi")
    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        Log.e(LOG_TAG, " onCreateCellViewHolder has been called")
        val inflater = LayoutInflater.from(parent.context)
        val layout: View
        return when (viewType) {
            MOOD_CELL_TYPE -> {
                layout = inflater.inflate(R.layout.table_view_image_cell_layout, parent, false)
                MoodCellViewHolder(layout)
            }
            GENDER_CELL_TYPE -> {
                layout = inflater.inflate(R.layout.table_view_image_cell_layout, parent, false)
                GenderCellViewHolder(layout)
            }
            else -> {
                layout = inflater.inflate(R.layout.table_view_cell_layout, parent, false)
                CellViewHolder(layout)
            }
        }
    }

    override fun onBindCellViewHolder(holder: AbstractViewHolder, cellItemModel: Cell?, columnPosition: Int, rowPosition: Int) {
        when (holder.itemViewType) {
            MOOD_CELL_TYPE -> {
                val moodViewHolder = holder as MoodCellViewHolder
                moodViewHolder.cell_image.setImageResource(mTableViewModel.getDrawable(cellItemModel?.getData() as Int, false))
            }
            GENDER_CELL_TYPE -> {
                val genderViewHolder = holder as GenderCellViewHolder
                genderViewHolder.cell_image.setImageResource(mTableViewModel.getDrawable(cellItemModel?.getData() as Int, true))
            }
            else -> {
                val viewHolder = holder as CellViewHolder
                viewHolder.setCell(cellItemModel)
            }
        }
    }

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.table_view_column_header_layout, parent, false)
        return ColumnHeaderViewHolder(layout, tableView)
    }

    override fun onBindColumnHeaderViewHolder(holder: AbstractViewHolder, columnHeaderItemModel: ColumnHeader?, columnPosition: Int) {
        val columnHeaderViewHolder = holder as ColumnHeaderViewHolder
        columnHeaderViewHolder.setColumnHeader(columnHeaderItemModel)
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.table_view_row_header_layout, parent, false)
        return RowHeaderViewHolder(layout)
    }

    override fun onBindRowHeaderViewHolder(holder: AbstractViewHolder, rowHeaderItemModel: RowHeader?, rowPosition: Int) {
        val rowHeaderViewHolder = holder as RowHeaderViewHolder
        rowHeaderViewHolder.row_header_textview.text = rowHeaderItemModel?.getData().toString()
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        val corner = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_corner_layout, parent, false)

        corner.setOnClickListener {
            val sortState = this@TableViewAdapter.tableView.rowHeaderSortingStatus
            if (sortState != SortState.ASCENDING) {
                Log.d("TableViewAdapter", "Order Ascending")
                this@TableViewAdapter.tableView.sortRowHeader(SortState.ASCENDING)
            } else {
                Log.d("TableViewAdapter", "Order Descending")
                this@TableViewAdapter.tableView.sortRowHeader(SortState.DESCENDING)
            }
        }
        return corner
    }

    override fun getColumnHeaderItemViewType(position: Int): Int { return 0 }

    override fun getRowHeaderItemViewType(position: Int): Int { return 0 }

    override fun getCellItemViewType(column: Int): Int {
        return when (column) {
            TableViewModel.MOOD_COLUMN_INDEX -> MOOD_CELL_TYPE
            TableViewModel.GENDER_COLUMN_INDEX -> GENDER_CELL_TYPE
            else -> 0
        }
    }
}
