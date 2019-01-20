package com.android.speechdemo.bean

import java.io.Serializable

/**
 * Created by LiuXing on 2016/10/27.
 */
class TalkBackVo : Serializable {
    /**
     * sentence，第几句
     */
    var sn: Int = 0
    /**
     * last sentence，是否最后一句
     */
    var isLs: Boolean = false
    /**
     * begin，开始
     */
    var bg: Int = 0
    /**
     * end，结束
     */
    var ed: Int = 0
    /**
     * words，词
     */
    var ws: List<TalkBackWordsVo>? = null
}
