@file:Suppress("UNUSED_ANONYMOUS_PARAMETER")

package com.zerdasoftware.tableview

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView

import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.filter.Filter
import com.evrencoskun.tableview.pagination.Pagination
import androidx.fragment.app.Fragment
import com.zerdasoftware.tableview.tableview.TableViewAdapter
import com.zerdasoftware.tableview.tableview.TableViewListener
import com.zerdasoftware.tableview.tableview.TableViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var moodFilter: Spinner
    private lateinit var genderFilter: Spinner
    private lateinit var previousButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var tablePaginationDetails: TextView
    private lateinit var mTableView: TableView
    private var mTableFilter: Filter? = null
    private var mPagination: Pagination? = null
    private var mPaginationEnabled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchField = view.findViewById<EditText>(R.id.query_string)
        searchField.addTextChangedListener(mSearchTextWatcher)
        moodFilter = view.findViewById(R.id.mood_spinner)
        moodFilter.onItemSelectedListener = mItemSelectionListener

        genderFilter = view.findViewById(R.id.gender_spinner)
        genderFilter.onItemSelectedListener = mItemSelectionListener

        val itemsPerPage = view.findViewById<Spinner>(R.id.items_per_page_spinner)

        val tableTestContainer = view.findViewById<View>(R.id.table_test_container)

        previousButton = view.findViewById(R.id.previous_button)
        nextButton = view.findViewById(R.id.next_button)
        val pageNumberField = view.findViewById<EditText>(R.id.page_number_text)
        tablePaginationDetails = view.findViewById(R.id.table_details)

        if (mPaginationEnabled) {
            tableTestContainer.visibility = View.VISIBLE
            itemsPerPage.onItemSelectedListener = onItemsPerPageSelectedListener

            previousButton.setOnClickListener(mClickListener)
            nextButton.setOnClickListener(mClickListener)
            pageNumberField.addTextChangedListener(onPageTextChanged)
        } else {
            tableTestContainer.visibility = View.GONE
        }

        mTableView = view.findViewById(R.id.tableview)

        initializeTableView()

        if (mPaginationEnabled) {
            mTableFilter = Filter(mTableView)
            mPagination = Pagination(mTableView)
            mPagination!!.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener)
        }
    }

    private fun initializeTableView() {
        val tableViewModel = TableViewModel()
        val tableViewAdapter = TableViewAdapter(tableViewModel)
        mTableView.setAdapter(tableViewAdapter)
        mTableView.tableViewListener = TableViewListener(mTableView)
        tableViewAdapter.setAllItems(
            tableViewModel.getColumnHeaderList(),
            tableViewModel.getRowHeaderList(),
            tableViewModel.getCellList()
        )
    }

    fun filterTable(filter: String) {
        if (mTableFilter != null) { mTableFilter!!.set(filter) }
    }

    fun filterTableForMood(filter: String) {
        if (mTableFilter != null) { mTableFilter!!.set(TableViewModel.MOOD_COLUMN_INDEX, filter) }
    }

    fun filterTableForGender(filter: String) {
        if (mTableFilter != null) { mTableFilter!!.set(TableViewModel.GENDER_COLUMN_INDEX, filter) }
    }

    private fun nextTablePage() {
        if (mPagination != null) { mPagination!!.nextPage() }
    }

    private fun previousTablePage() {
        if (mPagination != null) { mPagination!!.previousPage() }
    }

    fun goToTablePage(page: Int) {
        if (mPagination != null) { mPagination!!.goToPage(page) }
    }

    fun setTableItemsPerPage(itemsPerPage: Int) {
        if (mPagination != null) { mPagination!!.itemsPerPage = itemsPerPage }
    }

    private val onTableViewPageTurnedListener = Pagination.OnTableViewPageTurnedListener { numItems, itemsStart, itemsEnd ->
            val currentPage = mPagination?.currentPage ?: 0
            val pageCount = mPagination?.pageCount ?: 0
            previousButton.visibility = View.VISIBLE
            nextButton.visibility = View.VISIBLE

            if (currentPage == 1 && pageCount == 1) {
                previousButton.visibility = View.INVISIBLE
                nextButton.visibility = View.INVISIBLE
            }

            if (currentPage == 1) { previousButton.visibility = View.INVISIBLE }

            if (currentPage == pageCount) { nextButton.visibility = View.INVISIBLE }

            tablePaginationDetails.text = getString(R.string.table_pagination_details, currentPage.toString(), itemsStart.toString(), itemsEnd.toString())
        }

    private val mItemSelectionListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position > 0) {
                val filter = position.toString()
                if (parent == moodFilter) {
                    filterTableForMood(filter)
                } else if (parent == genderFilter) {
                    filterTableForGender(filter)
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private val mSearchTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filterTable(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val onItemsPerPageSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val itemsPerPage: Int = if ("All" == parent?.getItemAtPosition(position).toString()) 0 else parent?.getItemAtPosition(position).toString().toInt()
            setTableItemsPerPage(itemsPerPage)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private val mClickListener = View.OnClickListener { v ->
        when (v) {
            previousButton -> previousTablePage()
            nextButton -> nextTablePage()
        }
    }

    private val onPageTextChanged = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val page: Int = if (TextUtils.isEmpty(s)) 1 else s.toString().toInt()
            goToTablePage(page)
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}