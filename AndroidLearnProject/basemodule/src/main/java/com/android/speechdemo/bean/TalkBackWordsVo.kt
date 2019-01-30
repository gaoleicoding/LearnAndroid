package com.android.speechdemo.bean

import java.io.Serializable

/**
 * Created by LiuXing on 2016/10/27.
 */
class TalkBackWordsVo : Serializable {
    /**
     * begin，开始
     */
    var bg: Int = 0
    /**
     * chinese word，中文分词
     */
    var cw: List<TalkBackChineseWordVo>? = null
}

