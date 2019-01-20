package com.android.speechdemo.xf

import android.content.Context
import android.preference.EditTextPreference
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast

import java.util.regex.Pattern

/**
 * 输入框输入范围控制
 */
class SettingTextWatcher(private val mContext: Context, private val mEditTextPreference: EditTextPreference, internal var minValue: Int//最小值
                         , internal var maxValue: Int//最大值
) : TextWatcher {
    private var editStart: Int = 0
    private var editCount: Int = 0

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        //		Log.e("demo", "onTextChanged start:"+start+" count:"+count+" before:"+before);
        editStart = start
        editCount = count
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        //		Log.e("demo", "beforeTextChanged start:"+start+" count:"+count+" after:"+after);
    }

    override fun afterTextChanged(s: Editable) {
        if (TextUtils.isEmpty(s)) {
            return
        }
        val content = s.toString()
        //		Log.e("demo", "content:"+content);
        if (isNumeric(content)) {
            val num = Integer.parseInt(content)
            if (num > maxValue || num < minValue) {
                s.delete(editStart, editStart + editCount)
                mEditTextPreference.editText.text = s
                Toast.makeText(mContext, "超出有效值范围", Toast.LENGTH_SHORT).show()
            }
        } else {
            s.delete(editStart, editStart + editCount)
            mEditTextPreference.editText.text = s
            Toast.makeText(mContext, "只能输入数字哦", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        /**
         * 正则表达式-判断是否为数字
         */
        fun isNumeric(str: String): Boolean {
            val pattern = Pattern.compile("[0-9]*")
            return pattern.matcher(str).matches()
        }
    }

}
