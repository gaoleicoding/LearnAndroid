package com.android.learn.base.email

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.security.Security
import java.sql.Connection
import java.sql.SQLException
import java.util.Properties

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class MailSender(private val user: String, private val password: String) : Authenticator() {
    private val session: Session
    var mailhost = "smtp.qq.com"
        set(mailhost) {
            field = mailhost
            properties.setProperty("mail.host", this.mailhost)
        }//在这里配置你送邮件的主机地址，如qq邮箱、163邮箱等是不一样的
    private val port = "465"//在这里配置你送邮件的主机端口，如qq邮箱、163邮箱等是不一样的
    private val messageMultipart: Multipart
    private val properties: Properties

    init {

        properties = Properties()
        properties.setProperty("mail.transport.protocol", "smtp")
        properties.setProperty("mail.host", this.mailhost)
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.port"] = port
        properties["mail.smtp.socketFactory.port"] = port
        properties["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        properties["mail.smtp.socketFactory.fallback"] = "false"
        properties.setProperty("mail.smtp.quitwait", "false")

        session = Session.getDefaultInstance(properties, this)
        messageMultipart = MimeMultipart()
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(user, password)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(subject: String?, body: String?,
                 sender: String?, recipients: String, attachment: String?) {
        val message = MimeMessage(session)
        message.sender = InternetAddress(sender)//閭欢鍙戜欢浜�
        message.subject = subject//閭欢涓婚
        //璁剧疆閭欢鍐呭
        val bodyPart = MimeBodyPart()
        bodyPart.setText(body)
        messageMultipart.addBodyPart(bodyPart)
        //		message.setDataHandler(handler);

        //璁剧疆閭欢闄勪欢
        if (attachment != null) {
            val dataSource = FileDataSource(attachment)
            val dataHandler = DataHandler(dataSource)
            bodyPart.dataHandler = dataHandler
            bodyPart.fileName = attachment.substring(attachment.lastIndexOf("/") + 1)
        }
        message.setContent(messageMultipart)
        if (recipients.indexOf(',') > 0)
        //澶氫釜鑱旂郴浜�
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients))
        else
        //鍗曚釜鑱旂郴浜�
            message.setRecipient(Message.RecipientType.TO, InternetAddress(
                    recipients))
        Transport.send(message)
    }

    //缁ф壙DataSource璁剧疆瀛楃缂栫爜
    inner class ByteArrayDataSource : DataSource {
        private var data: ByteArray? = null
        private var type: String? = null

        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        var logWriter: PrintWriter?
            @Throws(SQLException::class)
            get() = null
            @Throws(SQLException::class)
            set(out) {

            }

        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        var loginTimeout: Int
            @Throws(SQLException::class)
            get() = 0
            @Throws(SQLException::class)
            set(seconds) {

            }

        // TODO Auto-generated method stub
        val connection: Connection?
            @Throws(SQLException::class)
            get() = null

        constructor(data: ByteArray, type: String) : super() {
            this.data = data
            this.type = type
        }

        constructor(data: ByteArray) : super() {
            this.data = data
        }

        fun setType(type: String) {
            this.type = type
        }

        override fun getContentType(): String {
                return "application/octet-stream"
        }

        @Throws(IOException::class)
        override fun getInputStream(): InputStream {
            return ByteArrayInputStream(data)
        }

        override fun getName(): String {
            return "ByteArrayDataSource"
        }

        @Throws(IOException::class)
        override fun getOutputStream(): OutputStream {
            throw IOException("Not Supported")
        }

        @Throws(SQLException::class)
        fun isWrapperFor(arg0: Class<*>): Boolean {
            // TODO Auto-generated method stub
            return false
        }

        @Throws(SQLException::class)
        fun <T> unwrap(arg0: Class<T>): T? {
            // TODO Auto-generated method stub
            return null
        }

        @Throws(SQLException::class)
        fun getConnection(theUsername: String, thePassword: String): Connection? {
            // TODO Auto-generated method stub
            return null
        }
    }

    companion object {
        init {
            Security.addProvider(JSSEProvider())
        }
    }
}
