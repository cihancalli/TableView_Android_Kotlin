package com.zerdasoftware.tableview.tableview.model

import com.evrencoskun.tableview.filter.IFilterableModel
import com.evrencoskun.tableview.sort.ISortableModel
import java.util.Objects

open class Cell(
    private val mId: String,
    private val mData: Any?) : ISortableModel,
    IFilterableModel {
    private val mFilterKeyword: String = mData?.toString() ?: ""

    override fun getId(): String { return mId }

    override fun getContent(): Any? { return mData }

    fun getData(): Any? { return mData }

    override fun getFilterableKeyword(): String { return mFilterKeyword }
}

