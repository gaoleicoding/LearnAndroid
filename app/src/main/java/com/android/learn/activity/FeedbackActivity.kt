package com.android.learn.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.android.learn.R
import com.android.learn.base.activity.BaseActivity
import com.android.learn.base.email.MailSender
import com.android.learn.base.utils.Utils


class FeedbackActivity : BaseActivity() {

    @BindView(R.id.iv_back)
    lateinit var iv_back: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.send_btn)
    lateinit var send_btn: TextView
    @BindView(R.id.add_attachment)
    lateinit var add_attachment: TextView
    @BindView(R.id.file_dir)
    lateinit var file_dir: TextView

    @BindView(R.id.et_email_title)
    lateinit var et_email_title: EditText
    @BindView(R.id.et_email_content)
    lateinit var et_email_content: EditText

    private val FILECHOOSER_RESULTCODE = 1
    private val sendEmail = "gaoleiemail@163.com"//发送方邮件
    private val sendEmaiPassword = "gl1201"//发送方邮箱密码(或授权码)
    private val receiveEmail = "gaoleiandroid@163.com"//接收方邮件
    private var file_path: String? = null

    override val layoutId: Int
        get() = R.layout.activity_help_feedback

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData(bundle: Bundle?) {
        title!!.text = getString(R.string.help_feedback)
        iv_back!!.visibility = View.VISIBLE
    }


    @OnClick(R.id.send_btn, R.id.add_attachment)
    fun click(view: View) {
        when (view.id) {
            R.id.send_btn -> {
                val senderRunnable = SenderRunnable(sendEmail, sendEmaiPassword)
                val sendTitle = et_email_title!!.text.toString()
                val sendContent = et_email_content!!.text.toString()
                if ("" == sendTitle.trim { it <= ' ' }) {
                    Utils.showToast(getString(R.string.please_input_contact), true)
                    return
                }
                if ("" == sendContent.trim { it <= ' ' }) {
                    Utils.showToast(getString(R.string.please_input_content), true)
                    return
                }
                senderRunnable.setMail(sendTitle, sendContent,
                        receiveEmail, file_path)
                Thread(senderRunnable).start()
            }
            R.id.add_attachment -> showFileChooser()
        }//                sendEmail();
    }


    private fun showFileChooser() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        startActivityForResult(Intent.createChooser(i, "File Chooser"),
                FILECHOOSER_RESULTCODE)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val uri = data!!.data

            file_path = getPathByUri4kitkat(this, uri)
            Log.d("gaolei", "file_path---------------" + file_path!!)
            file_dir!!.text = file_path

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    internal inner class SenderRunnable(private val user: String, private val password: String) : Runnable {
        private var subject: String? = null
        private var body: String? = null
        private var receiver: String? = null
        private val sender: MailSender
        private var attachment: String? = null

        init {
            sender = MailSender(user, password)
            var mailhost = user.substring(user.lastIndexOf("@") + 1,
                    user.lastIndexOf("."))
            if (mailhost != "gmail") {
                mailhost = "smtp.$mailhost.com"
                Log.i("hello", mailhost)
                sender.mailhost = mailhost
            }
        }

        fun setMail(subject: String, body: String, receiver: String,
                    attachment: String?) {
            this.subject = subject
            this.body = body
            this.receiver = receiver
            this.attachment = attachment
        }

        override fun run() {
            // TODO Auto-generated method stub
            try {
                sender.sendMail(subject, body, user, receiver!!, attachment)
                Utils.showToast(getString(R.string.feedback_send_success), false)
                runOnUiThread { finish() }
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                if (e.message != null)
                    Utils.showToast(getString(R.string.feedback_send_fail), false)
                e.printStackTrace()
            } finally {
                finish()
            }
        }
    }

    private fun sendEmail() {
        val email = Intent(Intent.ACTION_SENDTO)
        /*不带附件发送邮件*/
        //        email.setType("plain/text");
        email.data = Uri.parse(receiveEmail)

        /*设置邮件默认地址，多个收件人，String数组*/
        //    email.putExtra(android.content.Intent.EXTRA_EMAIL, (String[])mMailReceivers.toArray(new String[mMailReceivers.size()]));
        /*多个抄送人，String数组*/
        //        email.putExtra(android.content.Intent.EXTRA_CC, (String[])mMailCopyTos.toArray(new String[mMailCopyTos.size()]));
        /*邮件标题*/
        email.putExtra(Intent.EXTRA_SUBJECT, et_email_title!!.text.toString())
        /*邮件正文*/
        email.putExtra(Intent.EXTRA_TEXT, et_email_content!!.text.toString())
        //调用系统的邮件系统
        startActivity(Intent.createChooser(email, "请选择邮件发送软件"))

    }

    companion object {


        fun startActivity(context: Context) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context.startActivity(intent)
        }

        // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使了。targetSdkVersion 22；如果targetSdkVersion>=23怎需要动态获取WRITE_EXTERNAL_STORAGE权限；如果targetSdkVersion>=24 则可能需要用到FileProvider
        @SuppressLint("NewApi")
        fun getPathByUri4kitkat(context: Context, uri: Uri?): String? {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri!!)) {// ExternalStorageProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id))
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {// MediaProvider
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {// MediaStore
                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {// File
                return uri.path
            }
            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }
    }

}
