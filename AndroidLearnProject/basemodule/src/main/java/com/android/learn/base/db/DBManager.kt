package com.android.learn.base.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import com.android.learn.base.greendao.gen.DaoMaster
import com.android.learn.base.greendao.gen.DaoSession
import com.android.learn.base.greendao.gen.SearchRecordDao

import org.greenrobot.greendao.query.QueryBuilder

class DBManager(private val context: Context) {
    private var openHelper: DaoMaster.DevOpenHelper? = null
    /**
     * 获取可写数据库
     */
    private val writableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = DaoMaster.DevOpenHelper(context, dbName, null)
            }
            return openHelper!!.getWritableDatabase()
        }
    /**
     * 获取可读数据库
     */
    private val readableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = DaoMaster.DevOpenHelper(context, dbName, null)
            }
            return openHelper!!.getReadableDatabase()
        }

    init {
        openHelper = DaoMaster.DevOpenHelper(context, dbName, null)
    }

    /**
     * 插入一条记录
     *
     * @param searchRecord
     */
    fun insertUser(searchRecord: SearchRecord) {
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        userDao.insert(searchRecord)
    }

    /**
     * 插入用户集合
     *
     * @param users
     */
    fun insertUserList(users: List<SearchRecord>?) {
        if (users == null || users.isEmpty()) {
            return
        }
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        userDao.insertInTx(users)
    }

    /**
     * 删除一条记录
     *
     * @param searchRecord
     */
    fun deleteUser(searchRecord: SearchRecord) {
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        userDao.delete(searchRecord)
    }

    fun deleteAll() {
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        userDao.deleteAll()
    }

    /**
     * 更新一条记录
     *
     * @param searchRecord
     */
    fun updateUser(searchRecord: SearchRecord) {
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        userDao.update(searchRecord)
    }

    /**
     * 查询用户列表
     */
    fun queryUserList(): List<SearchRecord> {
        val daoMaster = DaoMaster(readableDatabase)
        val daoSession = daoMaster.newSession()
        val userDao = daoSession.getSearchRecordDao()
        val qb = userDao.queryBuilder()
        return qb.list()
    }

    companion object {
        private val dbName = "search_record"
        private var mInstance: DBManager? = null

        /**
         * 获取单例引用
         *
         * @param context
         * @return
         */
        fun getInstance(context: Context): DBManager {
            if (mInstance == null) {
                synchronized(DBManager::class.java) {
                    if (mInstance == null) {
                        mInstance = DBManager(context)
                    }
                }
            }
            return mInstance
        }
    }


}