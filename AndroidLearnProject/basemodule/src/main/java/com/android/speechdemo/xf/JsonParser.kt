package com.android.speechdemo.xf

import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * Json结果解析类
 */
object JsonParser {

    fun parseIatResult(json: String): String {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)

            val words = joResult.getJSONArray("ws")
            for (i in 0 until words.length()) {
                // 转写结果词，默认使用第一个结果
                val items = words.getJSONObject(i).getJSONArray("cw")
                val obj = items.getJSONObject(0)
                ret.append(obj.getString("w"))
                //				如果需要多候选结果，解析数组其他字段
                //				for(int j = 0; j < items.length(); j++)
                //				{
                //					JSONObject obj = items.getJSONObject(j);
                //					ret.append(obj.getString("w"));
                //				}
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ret.toString()
    }

    fun parseGrammarResult(json: String): String {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)

            val words = joResult.getJSONArray("ws")
            for (i in 0 until words.length()) {
                val items = words.getJSONObject(i).getJSONArray("cw")
                for (j in 0 until items.length()) {
                    val obj = items.getJSONObject(j)
                    if (obj.getString("w").contains("nomatch")) {
                        ret.append("没有匹配结果.")
                        return ret.toString()
                    }
                    ret.append("【结果】" + obj.getString("w"))
                    ret.append("【置信度】" + obj.getInt("sc"))
                    ret.append("\n")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ret.append("没有匹配结果.")
        }

        return ret.toString()
    }

    fun parseLocalGrammarResult(json: String): String {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)

            val words = joResult.getJSONArray("ws")
            for (i in 0 until words.length()) {
                val items = words.getJSONObject(i).getJSONArray("cw")
                for (j in 0 until items.length()) {
                    val obj = items.getJSONObject(j)
                    if (obj.getString("w").contains("nomatch")) {
                        ret.append("没有匹配结果.")
                        return ret.toString()
                    }
                    ret.append("【结果】" + obj.getString("w"))
                    ret.append("\n")
                }
            }
            ret.append("【置信度】" + joResult.optInt("sc"))

        } catch (e: Exception) {
            e.printStackTrace()
            ret.append("没有匹配结果.")
        }

        return ret.toString()
    }

    fun parseTransResult(json: String, key: String): String {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)
            val errorCode = joResult.optString("ret")
            if (errorCode != "0") {
                return joResult.optString("errmsg")
            }
            val transResult = joResult.optJSONObject("trans_result")
            ret.append(transResult.optString(key))
            /*JSONArray words = joResult.getJSONArray("results");
			for (int i = 0; i < words.length(); i++) {
				JSONObject obj = words.getJSONObject(i);
				ret.append(obj.getString(key));
			}*/
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ret.toString()
    }
}
