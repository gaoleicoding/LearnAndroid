package com.wind.me.xskinloader.util

import android.text.TextUtils

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object ReflectUtils {

    //表示Field或者Class是编译器自动生成的
    private val SYNTHETIC = 0x00001000
    //表示Field是final的
    private val FINAL = 0x00000010
    //内部类持有的外部类对象一定有这两个属性
    private val SYNTHETIC_AND_FINAL = SYNTHETIC or FINAL

    //获取类的实例的变量的值
    fun getField(receiver: Any, fieldName: String): Any? {
        return getField(null, receiver, fieldName)
    }

    //获取类的静态变量的值
    fun getField(className: String, fieldName: String): Any? {
        return getField(className, null, fieldName)
    }

    fun getField(clazz: Class<*>?, className: String, fieldName: String, receiver: Any): Any? {
        var clazz = clazz
        try {
            if (clazz == null) clazz = Class.forName(className)
            val field = clazz!!.getDeclaredField(fieldName) ?: return null
            field.isAccessible = true
            return field.get(receiver)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return null
    }

    private fun getField(className: String?, receiver: Any?, fieldName: String): Any? {
        var clazz: Class<*>? = null
        val field: Field?
        if (!TextUtils.isEmpty(className)) {
            try {
                clazz = Class.forName(className)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        } else {
            if (receiver != null) {
                clazz = receiver.javaClass
            }
        }
        if (clazz == null) return null

        try {
            field = findField(clazz, fieldName)
            if (field == null) return null
            field.isAccessible = true
            return field.get(receiver)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        return null
    }

    fun setField(receiver: Any, fieldName: String, value: Any): Any? {
        try {
            val field: Field?
            field = findField(receiver.javaClass, fieldName)
            if (field == null) {
                return null
            }
            field.isAccessible = true
            val old = field.get(receiver)
            field.set(receiver, value)
            return old
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        return null
    }

    fun setField(clazz: Class<*>, receiver: Any, fieldName: String, value: Any): Any? {
        try {
            val field: Field?
            field = findField(clazz, fieldName)
            if (field == null) {
                return null
            }
            field.isAccessible = true
            val old = field.get(receiver)
            field.set(receiver, value)
            return old
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        return null
    }

    fun callMethod(receiver: Any, methodName: String, vararg params: Any): Any? {
        return callMethod(null, receiver, methodName, *params)
    }

    fun setField(clazzName: String, receiver: Any, fieldName: String, value: Any): Any? {
        try {
            val clazz = Class.forName(clazzName)
            val field: Field?
            field = findField(clazz, fieldName)
            if (field == null) {
                return null
            }
            field.isAccessible = true
            val old = field.get(receiver)
            field.set(receiver, value)
            return old
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }


    fun callMethod(className: String, methodName: String, vararg params: Any): Any? {
        return callMethod(className, null, methodName, *params)
    }

    fun callMethod(clazz: Class<*>?, className: String, methodName: String, receiver: Any,
                   types: Array<Class<*>>, vararg params: Any): Any? {
        var clazz = clazz
        try {
            if (clazz == null) clazz = Class.forName(className)
            val method = clazz!!.getDeclaredMethod(methodName, *types)
            method.isAccessible = true
            return method.invoke(receiver, *params)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

        return null
    }

    private fun callMethod(className: String?, receiver: Any?, methodName: String, vararg params: Any): Any? {
        var clazz: Class<*>? = null
        if (!TextUtils.isEmpty(className)) {
            try {
                clazz = Class.forName(className)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        } else {
            if (receiver != null) {
                clazz = receiver.javaClass
            }
        }
        if (clazz == null) return null
        try {
            val method = findMethod(clazz, methodName, *params) ?: return null
            method.isAccessible = true
            return method.invoke(receiver, *params)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }

    private fun findMethod(clazz: Class<*>, name: String, vararg arg: Any): Method? {
        val methods = clazz.methods
        var method: Method? = null
        for (m in methods) {
            if (methodFitParam(m, name, *arg)) {
                method = m
                break
            }
        }

        if (method == null) {
            method = findDeclaredMethod(clazz, name, *arg)
        }
        return method
    }

    private fun findDeclaredMethod(clazz: Class<*>, name: String, vararg arg: Any): Method? {
        val methods = clazz.declaredMethods
        var method: Method? = null
        for (m in methods) {
            if (methodFitParam(m, name, *arg)) {
                method = m
                break
            }
        }

        return method ?: if (clazz == Any::class.java) {
            null
        } else findDeclaredMethod(clazz.superclass, name, *arg)
    }

    private fun methodFitParam(method: Method, methodName: String, vararg arg: Any): Boolean {
        if (methodName != method.name) {
            return false
        }

        val paramTypes = method.parameterTypes
        if (arg == null || arg.size == 0) {
            return if (paramTypes == null || paramTypes.size == 0) {
                true
            } else {
                false
            }
        }
        if (paramTypes!!.size != arg.size) {
            return false
        }

        for (i in arg.indices) {
            val ar = arg[i]
            val paramT = paramTypes[i]
            if (ar == null) continue

            //TODO for primitive type
            if (paramT.isPrimitive) continue

            if (!paramT.isInstance(ar)) {
                return false
            }
        }
        return true
    }

    private fun findField(clazz: Class<*>, name: String): Field? {
        try {
            return clazz.getDeclaredField(name)
        } catch (e: NoSuchFieldException) {
            if (clazz == Any::class.java) {
                e.printStackTrace()
                return null
            }
            val base = clazz.superclass
            return findField(base, name)
        }

    }

    private fun checkModifier(mod: Int): Boolean {
        return mod and SYNTHETIC_AND_FINAL == SYNTHETIC_AND_FINAL
    }

    //获取内部类实例持有的外部类对象
    fun <T> getExternalField(innerObj: Any): T? {
        return getExternalField<T>(innerObj, null)
    }

    /**
     * 内部类持有的外部类对象的形式为：
     * final Outer this$0;
     * flags: ACC_FINAL, ACC_SYNTHETIC
     * 参考：https://www.jianshu.com/p/9335c15c43cf
     * And：https://www.2cto.com/kf/201402/281879.html
     * @param innerObj 内部类对象
     * @param name 内部类持有的外部类名称，默认是"this$0"
     * @return 内部类持有的外部类对象
     */
    private fun <T> getExternalField(innerObj: Any, name: String?): T? {
        var name = name
        val clazz = innerObj.javaClass
        if (name == null || name.isEmpty()) {
            name = "this$0"
        }
        val field: Field
        try {
            field = clazz.getDeclaredField(name)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
            return null
        }

        field.isAccessible = true
        if (checkModifier(field.modifiers)) {
            try {
                return field.get(innerObj) as T
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }

        }
        return getExternalField<T>(innerObj, "$name$")
    }
}
