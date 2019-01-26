package com.android.learn.base.utils

import android.app.Activity

import java.util.LinkedList

class ExitAppUtils private constructor() {

    private val mActivityList = LinkedList<Activity>()

    fun addActivity(activity: Activity) {
        mActivityList.add(activity)
    }

    fun delActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    fun exit() {
        for (activity in mActivityList) {
            activity.finish()
        }

        System.exit(0)
    }

    companion object {
        val instance = ExitAppUtils()
    }


}