package com.zerdasoftware.tableview.tableview.popup

import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.sort.SortState
import com.zerdasoftware.tableview.R
import com.zerdasoftware.tableview.tableview.holder.ColumnHeaderViewHolder

class ColumnHeaderLongPressPopup(
    viewHolder: ColumnHeaderViewHolder,
    tableView: TableView
) : PopupMenu(viewHolder.itemView.context, viewHolder.itemView),
    PopupMenu.OnMenuItemClickListener {

    private val ASCENDING = 1
    private val DESCENDING = 2
    private val HIDE_ROW = 3
    private val SHOW_ROW = 4
    private val SCROLL_ROW = 5

    private val mTableView: TableView = tableView
    private val mXPosition: Int = viewHolder.adapterPosition

    init {
        createMenuItem()
        changeMenuItemVisibility()

        setOnMenuItemClickListener(this)
    }

    private fun createMenuItem() {
        val context = mTableView.context
        menu.add(Menu.NONE, ASCENDING, 0, context.getString(R.string.sort_ascending))
        menu.add(Menu.NONE, DESCENDING, 1, context.getString(R.string.sort_descending))
        menu.add(Menu.NONE, HIDE_ROW, 2, context.getString(R.string.hiding_row_sample))
        menu.add(Menu.NONE, SHOW_ROW, 3, context.getString(R.string.showing_row_sample))
        menu.add(Menu.NONE, SCROLL_ROW, 4, context.getString(R.string.scroll_to_row_position))
        menu.add(Menu.NONE, SCROLL_ROW, 0, "change width")
    }

    private fun changeMenuItemVisibility() {
        when (mTableView.getSortingStatus(mXPosition)) {
            SortState.UNSORTED -> {}
            SortState.DESCENDING -> { menu.getItem(1).isVisible = false }
            SortState.ASCENDING -> { menu.getItem(0).isVisible = false }
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            ASCENDING -> { mTableView.sortColumn(mXPosition, SortState.ASCENDING) }
            DESCENDING -> { mTableView.sortColumn(mXPosition, SortState.DESCENDING) }
            HIDE_ROW -> { mTableView.hideRow(5) }
            SHOW_ROW -> { mTableView.showRow(5) }
            SCROLL_ROW -> { mTableView.scrollToRowPosition(5) }
        }
        return true
    }
}