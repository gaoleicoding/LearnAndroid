package com.android.base.thirdframe.retrofit.interceptor

import android.util.Base64
import android.util.Log

import java.io.IOException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer

class ParamsEncryptInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {


        var request = chain.request()
        try {
            val oldBody = request.body()
            val buffer = Buffer()
            oldBody!!.writeTo(buffer)
            val strOldBody = buffer.readUtf8()
            val mediaType = MediaType.parse("text/plain; charset=utf-8")
            //在发动报文前，用Base64和AES对参数进行加密转码
            val strNewBody = encryptDataWithSymmetricKey(generateSymmetric(), strOldBody)
            val body = RequestBody.create(mediaType, strNewBody!!)
            request = request.newBuilder().header("Content-Type", body.contentType()!!.toString())
                    .header("Content-Length", body.contentLength().toString()).method(request.method(), body).build()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        }

        return chain.proceed(request)
    }

    companion object {

        private val TAG = ParamsEncryptInterceptor::class.java.simpleName

        private val DEBUG = true

        //加密算法自己和服务端约定即可
        private fun encrypt(str: String): String {
            //your code
            return ""
        }

        //生成了对称密钥
        fun generateSymmetric(): SecretKeySpec? {
            // Set up secret key spec for 128-bit AES encryption and decryption
            var sks: SecretKeySpec? = null
            try {
                val sr = SecureRandom.getInstance("SHA1PRNG")
                sr.setSeed("any data used as random seed".toByteArray())
                val kg = KeyGenerator.getInstance("AES")
                kg.init(128, sr)
                val secretKey = kg.generateKey()
                //在这里可以用从服务端获取到的RSA公钥对刚才随机产生的加密密钥secretKey进行加密
                sks = SecretKeySpec(secretKey.encoded, "AES")

                println("AES KEY: $sks")
            } catch (e: Exception) {
                Log.e(TAG, "AES secret key spec error")
            }

            return sks
        }

        //用SecretKeySpec加密数据
        @Throws(IOException::class, NoSuchAlgorithmException::class, InvalidKeySpecException::class)
        private fun encryptDataWithSymmetricKey(symmKey: SecretKeySpec?, data: String): String? {

            // encryption
            val toBeCiphred = data.toByteArray(charset("UTF-8"))
            var encryptedData: String? = null

            try {
                val c = Cipher.getInstance("AES")
                c.init(Cipher.ENCRYPT_MODE, symmKey)
                val encodedBytes = c.doFinal(toBeCiphred)
                println("BYTE STRING (ASYMM): $encodedBytes")
                //将SecretKeySpec转换为Base64字符串格式
                encryptedData = Base64.encodeToString(encodedBytes, Base64.DEFAULT)

            } catch (e: Exception) {
                Log.e(TAG, "AES encryption error")
                throw RuntimeException(e)
            }

            return encryptedData
        }
    }
}