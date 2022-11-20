package com.android.base.activity

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat

import com.android.base.utils.PermissionUtil

import com.android.base.utils.PermissionUtil.PERMISSION_CODE


/**
 * Created by gaolei on 2018/4/26.
 */

open class BasePermisssionActivity : FragmentActivity() {
    private var mRequestPermissionCallBack: PermissionUtil.RequestPermissionCallBack? = null


    /**
     * 发起权限请求
     *
     * @param context
     * @param permissions
     * @param callback
     */

    fun requestPermission(context: Context,
                          callback: PermissionUtil.RequestPermissionCallBack, permissions: Array<String>) {
        this.mRequestPermissionCallBack = callback

        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        var isAllGranted = true
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false
                ActivityCompat.requestPermissions(context as Activity, permissions, PERMISSION_CODE)
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack!!.granted()
        }
    }

    /**
     * 权限请求结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasAllGranted = true

        when (requestCode) {
            PERMISSION_CODE -> {
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        hasAllGranted = false
                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            PermissionUtil.requestForeverDenyDialog(this@BasePermisssionActivity, *permissions)

                        } else {
                            PermissionUtil.requestDenyDialog(this@BasePermisssionActivity, *permissions)

                            //用户拒绝权限请求，但未选中“不再提示”选项
                        }
                        mRequestPermissionCallBack!!.denied()
                    }
                }
                if (grantResults.size > 0 && hasAllGranted) {
                    mRequestPermissionCallBack!!.granted()
                }
            }
        }
    }


}
