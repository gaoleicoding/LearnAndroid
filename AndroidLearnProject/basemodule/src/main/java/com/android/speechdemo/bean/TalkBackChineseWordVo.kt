package com.android.speechdemo.bean

import java.io.Serializable

/**
 * Created by LiuXing on 2016/10/27.
 */
class TalkBackChineseWordVo : Serializable {
    /**
     * word，单字
     */
    var w: String? = null
    /**
     * score，分数
     */
    var sc: Int = 0
}
