package com.android.learn.base.db

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id
import org.greenrobot.greendao.annotation.Generated

@Entity
class SearchRecord {
    @Id
    var id: Long? = null
    var name: String? = null

    @Generated(hash = 2029079304)
    constructor(id: Long?, name: String) {
        this.id = id
        this.name = name
    }

    @Generated(hash = 839789598)
    constructor() {
    }

}