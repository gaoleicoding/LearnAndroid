package com.android.learn.base.mmodel

/**
 * @author quchao
 * @date 2018/2/26
 */

class RegisterLoginData : BaseData() {

    var data: Data? = null

    inner class Data {
        lateinit var username: String
        lateinit var password: String
        lateinit var email: String
        lateinit var icon: String
        lateinit var token: String
        var id: Int = 0
        var type: Int = 0
        //    private List<Integer> chapterTops;
        lateinit var collectIds: List<Int>

        override fun toString(): String {
            return "RegisterLoginData{" +
                    "username='" + username + '\''.toString() +
                    ", password='" + password + '\''.toString() +
                    ", email='" + email + '\''.toString() +
                    ", icon='" + icon + '\''.toString() +
                    ", token='" + token + '\''.toString() +
                    ", id=" + id +
                    ", type=" + type +
                    ", collectIds=" + collectIds +
                    '}'.toString()
        }
    }
}
