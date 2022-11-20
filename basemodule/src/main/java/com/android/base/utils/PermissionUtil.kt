package com.android.base.utils


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.ActivityCompat

object PermissionUtil {

    private val denyRequestContent = "%s权限 为必要权限，开通才可以正常使用相应功能"
    private val foreverDenyRequestContent = "%s权限 为必要权限,开通才可以正常使用相应功能。\n \n 请点击 \"设置\"-\"权限\"-打开所需权限。"
    val PERMISSION_CODE = 10001

    fun gotoDetailSettingIntent(context: Activity) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 9) {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", context.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.action = Intent.ACTION_VIEW
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails")
            intent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
        }
        context.startActivity(intent)

        //        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        //        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        //        intent.setData(uri);
    }

    fun requestDenyDialog(activity: Activity, vararg permissions: String) {

        val content = getContent(denyRequestContent, *permissions)

        val builder = AlertDialog.Builder(activity)

        builder.setTitle("权限申请")
                .setMessage(content)
                .setPositiveButton("申请") { dialog, id ->
                    ActivityCompat.requestPermissions(activity, permissions, PERMISSION_CODE)
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, id ->
                    if (Utils.getTopActivity(activity) == "MainActivity") {
                        activity.finish()
                    }
                    dialog.dismiss()
                }
        builder.show()
    }

    fun requestForeverDenyDialog(activity: Activity, vararg permissions: String) {

        val content = getContent(foreverDenyRequestContent, *permissions)

        val builder = AlertDialog.Builder(activity)

        builder.setTitle("权限设置")
                .setMessage(content)
                .setPositiveButton("设置") { dialog, id ->
                    gotoDetailSettingIntent(activity)
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, id -> dialog.dismiss() }
        builder.show()
    }

    fun getContent(ifForeverDeny: String, vararg permissions: String): String {
        var content = ""

        if (permissions.size == 1) {
            if (permissions[0] == Manifest.permission.CAMERA)
                content = String.format(ifForeverDeny, "相机")
            if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE)
                content = String.format(ifForeverDeny, "存储")
        }
        if (permissions.size > 1) {
            var stringBuilder = StringBuilder()
            val length = permissions.size
            for (i in 0 until length) {
                if (permissions[i] == Manifest.permission.CAMERA)
                    stringBuilder = stringBuilder.append("相机")
                if (permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    stringBuilder = stringBuilder.append("存储")
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION)
                    stringBuilder = stringBuilder.append("位置")
                if (permissions[i] == Manifest.permission.WRITE_CONTACTS)
                    stringBuilder = stringBuilder.append("联系人")
                if (i < length - 1)
                    stringBuilder = stringBuilder.append("、")
            }
            content = String.format(ifForeverDeny, stringBuilder.toString())
        }
        return content

    }

    /**
     * 权限请求结果回调接口
     */
    interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        fun granted()

        /**
         * 取消授权
         */
        fun denied()
    }
}
