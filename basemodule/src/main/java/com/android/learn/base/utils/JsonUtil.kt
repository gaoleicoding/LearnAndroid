package com.android.learn.base.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList
import java.util.HashMap

/**
 * JSON字符换和对象间转换的工具类
 */
object JsonUtil {

    private val GSON = Gson()

    /**
     * 将json字符串转换成相应的对象
     *
     * @param jsonString json字符串
     * @param classOfT   对象类型的class
     * @param <T>        对象的类型
     * @return 转换后的对象
     * @throws JsonParseException if json is not a valid representation for an object of type classOfT
    </T> */
    @Throws(RuntimeException::class)
    fun <T> decode(jsonString: String, classOfT: Class<T>): T {
        return GSON.fromJson(jsonString, classOfT)
    }

    /**
     * 将json字符串转换成相应的对象
     *
     * @param jsonString json字符串
     * @param typeOfT    对象类型的type
     * @param <T>        对象的类型
     * @return 转换后的对象
     * @throws JsonParseException if json is not a valid representation for an object of type typeOfT
    </T> */
    @Throws(RuntimeException::class)
    fun <T> decode(jsonString: String, typeOfT: Type): T {
        return GSON.fromJson(jsonString, typeOfT)
    }

    /**
     * 将已经从json转换后的对象（通常是一个Map）转换成对应Bean的对象实例
     *
     * @param object  一个转json换中的过程对象，通常是一个Map，若传值为null，则返回null
     * @param typeOfT 要转换的对象类型的type
     * @param <T>     要转换的对象类型
     * @return 转换后的对象
     * @throws JsonParseException if object is not a valid representation for an object of type typeOfT
    </T> */
    @Throws(RuntimeException::class)
    fun <T> decode(`object`: Any, typeOfT: Type): T? {
        val jsonString = GSON.toJson(`object`)
        return GSON.fromJson<T>(jsonString, typeOfT)
    }

    /**
     * 将一个JavaBean转换成json字符串
     *
     * @param object 待转换的对象
     * @return json字符串
     * @throws JsonParseException if there was a problem while parsing object.
     */
    @Throws(RuntimeException::class)
    fun encode(`object`: Any): String {
        return GSON.toJson(`object`)
    }


    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    fun parseJson(json: String): JsonObject {
        val parser = JsonParser()
        return parser.parse(json).asJsonObject
    }

    /**
     * 根据javaBean转json字符串再将字符串转Map对象
     *
     * @param object
     * @return
     */
    fun javaBeanToMap(`object`: Any): Map<String, Any> {
        val strJson = encode(`object`)
        return toMap(parseJson(strJson))
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    fun toMap(json: JsonObject): Map<String, Any> {
        val map = HashMap<String, Any>()
        val entrySet = json.entrySet()
        val iter = entrySet.iterator()
        while (iter.hasNext()) {
            val entry = iter.next()
            val key = entry.key
            val value = entry.value
            if (value is JsonArray)
                map[key as String] = toList(value)
            else if (value is JsonObject)
                map[key as String] = toMap(value)
            else
                map[key as String] = value
        }
        return map
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    fun toList(json: JsonArray): List<Any> {
        val list = ArrayList<Any>()
        for (i in 0 until json.size()) {
            val value = json.get(i)
            if (value is JsonArray) {
                list.add(toList(value))
            } else if (value is JsonObject) {
                list.add(toMap(value))
            } else {
                list.add(value)
            }
        }
        return list
    }

    /**
     * Json解析公用方法 数组
     *
     * @param json  解析参数
     * @param token 解析的类型 new TypeToken<T>(){}
     * ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList></ArrayList><T>>(){});
     * @return
    </T></T></T> */
    fun <T> fromJson(json: JsonElement, token: TypeToken<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson<T>(json, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    /**
     * Json解析公用方法 数组
     *
     * @param json 解析参数
     * @param type 解析的类型 new TypeToken<T>(){}.getType()
     * ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList></ArrayList><T>>(){});
     * @return
    </T></T></T> */
    fun <T> fromJson(json: JsonElement, type: Type): T? {
        try {
            val gson = Gson()
            return gson.fromJson<T>(json, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    /**
     * Json解析公用方法 单个类
     *
     * @param json 解析参数
     * @param t    解析的类。 class
     * @return
     */
    fun <T> fromJson(json: JsonElement, t: Class<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson(json, t)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    /**
     * Json解析公用方法 数组
     *
     * @param json  解析参数
     * @param token 解析的类型 new TypeToken<T>(){}
     * ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList></ArrayList><T>>(){});
     * @return
    </T></T></T> */
    fun <T> fromJson(json: String, token: TypeToken<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson<T>(json, token.type)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    /**
     * Json解析公用方法 单个类
     *
     * @param json 解析参数
     * @param t    解析的类。 class
     * @return
     */
    fun <T> fromJson(json: String, t: Class<T>): T? {
        try {
            val gson = Gson()
            return gson.fromJson(json, t)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return null
    }

    /**
     * 把数据转换成Json结构
     *
     * @param mType Object的数据，支持所有类型
     * @return
     */
    fun toJson(mType: Any): String {
        val mGson = Gson()
        return mGson.toJson(mType)
    }
}

