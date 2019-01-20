package com.wind.me.xskinloader.entity

class SkinAttr(
        /***
         * 对应View的属性
         */
        var attrName: String,
        /***
         * 属性值对应的reference id值，类似R.color.XX
         */
        var attrValueRefId: Int,
        /***
         * 属性值refrence id对应的名称，如R.color.XX，则此值为"XX"
         */
        var attrValueRefName: String,
        /***
         * 属性值refrence id对应的类型，如R.color.XX，则此值为color
         */
        var attrValueTypeName: String) {

    override fun toString(): String {
        return ("SkinAttr \n[\nattrName=" + attrName + ", \n"
                + "attrValueRefId=" + attrValueRefId + ", \n"
                + "attrValueRefName=" + attrValueRefName + ", \n"
                + "attrValueTypeName=" + attrValueTypeName
                + "\n]")
    }
}
