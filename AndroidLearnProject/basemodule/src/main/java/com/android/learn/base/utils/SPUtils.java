package com.android.learn.base.utils;
 
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
 
import android.content.Context;
import android.content.SharedPreferences;

import com.android.learn.base.application.CustomApplication;

public class SPUtils
{
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "share_data";
 

	public static void put(String key, Object object)
	{
 
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
 
		if (object instanceof String)
		{
			editor.putString(key, (String) object);
		} else if (object instanceof Integer)
		{
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean)
		{
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float)
		{
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long)
		{
			editor.putLong(key, (Long) object);
		} else
		{
			editor.putString(key, object.toString());
		}
 
		SharedPreferencesCompat.apply(editor);
	}
 

	public static Object get(String key, Object defaultObject)
	{
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
 
		if (defaultObject instanceof String)
		{
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer)
		{
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean)
		{
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float)
		{
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long)
		{
			return sp.getLong(key, (Long) defaultObject);
		}
 
		return null;
	}
 

	public static void remove( String key)
	{
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}
 
	/**
	 * 清除所有数据
	 */
	public static void clear()
	{
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}
 
	/**
	 * 查询某个key是否已经存在
	 */
	public static boolean contains(String key)
	{
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}
 
	/**
	 * 返回所有的键值对
	 */
	public static Map<String, ?> getAll()
	{
		SharedPreferences sp = CustomApplication.context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}
 
	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat
	{
		private static final Method sApplyMethod = findApplyMethod();
 
		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod()
		{
			try
			{
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e)
			{
			}
 
			return null;
		}
 
		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor)
		{
			try
			{
				if (sApplyMethod != null)
				{
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e)
			{
			} catch (IllegalAccessException e)
			{
			} catch (InvocationTargetException e)
			{
			}
			editor.commit();
		}
	}
 
}
