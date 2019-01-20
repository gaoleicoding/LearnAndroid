package com.android.learn.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView

import com.android.learn.R
import com.android.learn.base.activity.BaseMvpActivity
import com.android.learn.base.event.UpdateTodoEvent
import com.android.learn.base.mmodel.BaseData
import com.android.learn.base.mmodel.TodoData
import com.android.learn.base.mmodel.TodoData.DatasBean
import com.android.learn.base.utils.LanguageUtil
import com.android.learn.base.utils.Utils
import com.android.learn.mcontract.TodoEditContract
import com.android.learn.mpresenter.TodoEditPresenter
import com.android.learn.mpresenter.TodoPresenter

import org.greenrobot.eventbus.EventBus

import java.util.Calendar
import java.util.Date
import java.util.HashMap

import butterknife.BindView
import butterknife.OnClick


class TodoEditActivity : BaseMvpActivity<TodoEditPresenter>(), TodoEditContract.View {
    @BindView(R.id.title)
    internal var mTitle: TextView? = null
    @BindView(R.id.iv_back)
    internal var iv_back: ImageView? = null
    @BindView(R.id.todo_name)
    internal var et_title: TextInputEditText? = null
    @BindView(R.id.todo_des)
    internal var et_content: TextInputEditText? = null
    @BindView(R.id.todo_date)
    internal var mTodoDate: TextView? = null

    internal var datasBean: DatasBean

    protected override val layoutId: Int
        get() = R.layout.activity_edit_todo

    override fun initData(bundle: Bundle) {
        mTitle!!.text = getString(R.string.detail)
        iv_back!!.visibility = View.VISIBLE
        datasBean = bundle.getSerializable("todo_item") as DatasBean
        et_title!!.setText(datasBean.title)
        et_content!!.setText(datasBean.content)
        mTodoDate!!.text = datasBean.dateStr
    }

    @OnClick(R.id.save_todo, R.id.todo_date)
    fun click(view: View) {
        when (view.id) {
            R.id.save_todo -> {
                val map = HashMap<String, Any>()
                map["title"] = et_title!!.text.toString()
                map["content"] = et_content!!.text.toString()
                val date = mTodoDate!!.text.toString()
                if (!date.contains("-")) {
                    Utils.showToast(getString(R.string.select_time), true)
                    return
                }
                map["date"] = date
                map["status"] = 0
                map["type"] = 0

                mPresenter!!.updateTodo(datasBean.id, map)
            }
            R.id.todo_date -> {
                val calendar = Calendar.getInstance()

                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth -> mTodoDate!!.text = String.format("%d-%d-%d", year, month + 1, dayOfMonth) }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.datePicker.minDate = Date().time
                datePickerDialog.show()
            }
        }

    }


    override fun initPresenter(): TodoEditPresenter {
        return TodoEditPresenter()
    }

    override fun loadData() {

    }

    override fun showUpdateTodo(todoData: BaseData) {
        Utils.showToast(getString(R.string.operate_success), true)
        EventBus.getDefault().post(UpdateTodoEvent())
        finish()
    }

    companion object {

        fun startActivity(context: Context, bundle: Bundle) {
            val intent = Intent(context, TodoEditActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
