package com.android.learn.base.email

import android.content.Context
import android.os.Looper
import android.widget.Toast

object ToastUtils {
    internal var toast: Toast? = null
    fun show(context: Context, text: String) {
        try {
            if (toast != null) {
                toast!!.setText(text)
            } else {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            }
            toast!!.show()
        } catch (e: Exception) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            Looper.loop()
        }

    }
}
