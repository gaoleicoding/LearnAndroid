package com.android.learn.base.mmodel

/**
 * @author quchao
 * @date 2018/2/26
 */

class RegisterLoginData : BaseData() {

    var data: Data? = null

    inner class Data {
        var username: String? = null
        var password: String? = null
        var email: String? = null
        var icon: String? = null
        var token: String? = null
        var id: Int = 0
        var type: Int = 0
        //    private List<Integer> chapterTops;
        var collectIds: List<Int>? = null

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
