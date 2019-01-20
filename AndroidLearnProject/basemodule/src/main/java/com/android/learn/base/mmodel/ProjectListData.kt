package com.android.learn.base.mmodel

import java.io.Serializable


/**
 * @author quchao
 * @date 2018/2/24
 */

class ProjectListData {

    /**
     * "curPage": 1,
     * "datas": [],
     * "offset": 0,
     * "over": true,
     * "pageCount": 1,
     * "size": 15,
     * "total": 8
     */
    var data: ListData? = null

    inner class ListData {
        var curPage: Int = 0
        var datas: List<ProjectData>
        var offset: Int = 0
        var over: Boolean = false
        var pageCount: Int = 0
        var size: Int = 0
        var total: Int = 0
        //        public int getCurPage() {
        //            return curPage;
        //        }
        //
        //        public void setCurPage(int curPage) {
        //            this.curPage = curPage;
        //        }
        //
        //        public int getOffset() {
        //            return offset;
        //        }
        //
        //        public void setOffset(int offset) {
        //            this.offset = offset;
        //        }
        //
        //        public boolean isOver() {
        //            return over;
        //        }
        //
        //        public void setOver(boolean over) {
        //            this.over = over;
        //        }
        //
        //        public int getPageCount() {
        //            return pageCount;
        //        }
        //
        //        public void setPageCount(int pageCount) {
        //            this.pageCount = pageCount;
        //        }
        //
        //        public int getSize() {
        //            return size;
        //        }
        //
        //        public void setSize(int size) {
        //            this.size = size;
        //        }
        //
        //        public int getTotal() {
        //            return total;
        //        }
        //
        //        public void setTotal(int total) {
        //            this.total = total;
        //        }
    }

    inner class ProjectData : Serializable {

        //            {
        //    "apkLink": "",
        //        "author": "LiangLuDev",
        //        "chapterId": 294,
        //        "chapterName": "完整项目",
        //        "collect": false,
        //        "courseId": 13,
        //        "desc": "注册登录、用户信息、用户密码、用户图像修改、书籍分类、本地书籍扫描、书架、书籍搜索（作者名或书籍名）、书籍阅读（仅txt格式，暂不支持PDF等其他格式）、阅读字体、背景颜色、翻页效果等设置、意见反馈（反馈信息发送到我的邮箱）、应用版本更新",
        //        "envelopePic": "http://www.wanandroid.com/blogimgs/fab6fb8b-c3aa-495f-b6a9-c007d78751c0.gif",
        //        "fresh": false,
        //        "id": 2836,
        //        "link": "http://www.wanandroid.com/blog/show/2116",
        //        "niceDate": "2018-04-22",
        //        "origin": "",
        //        "projectLink": "https://github.com/LiangLuDev/WeYueReader",
        //        "publishTime": 1524376619000,
        //        "superChapterId": 0,
        //        "superChapterName": "",
        //        "tags": [ ],
        //        "title": "微Yue电子书阅读 WeYueReader",
        //        "type": 0,
        //        "visible": 1,
        //        "zan": 0
        //}
        var apkLink: String? = null
        //        public String getApkLink() {
        //            return apkLink;
        //        }
        //
        //        public void setApkLink(String apkLink) {
        //            this.apkLink = apkLink;
        //        }
        //
        var author: String
        var chapterId: Int = 0
        var chapterName: String? = null
        var collect: Boolean = false
        var courseId: Int = 0
        //
        //        public void setChapterId(int chapterId) {
        //            this.chapterId = chapterId;
        //        }
        //
        //        public String getChapterName() {
        //            return chapterName;
        //        }
        //
        //        public void setChapterName(String chapterName) {
        //            this.chapterName = chapterName;
        //        }
        //
        //        public boolean isCollect() {
        //            return collect;
        //        }
        //
        //        public void setCollect(boolean collect) {
        //            this.collect = collect;
        //        }
        //
        //        public int getCourseId() {
        //            return courseId;
        //        }
        //
        //        public void setCourseId(int courseId) {
        //            this.courseId = courseId;
        //        }
        //
        var desc: String
        var envelopePic: String
        var id: Int = 0
        //
        //        public int getId() {
        //            return id;
        //        }
        //
        //        public void setId(int id) {
        //            this.id = id;
        //        }

        var link: String
        var niceDate: String
        var origin: String? = null
        var projectLink: String? = null
        var superChapterId: Int = 0
        var superChapterName: String? = null
        var publishTime: Long = 0
        //
        //        public String getOrigin() {
        //            return origin;
        //        }
        //
        //        public void setOrigin(String origin) {
        //            this.origin = origin;
        //        }
        //
        //        public String getProjectLink() {
        //            return projectLink;
        //        }
        //
        //        public void setProjectLink(String projectLink) {
        //            this.projectLink = projectLink;
        //        }
        //
        //        public int getSuperChapterId() {
        //            return superChapterId;
        //        }
        //
        //        public void setSuperChapterId(int superChapterId) {
        //            this.superChapterId = superChapterId;
        //        }
        //
        //        public String getSuperChapterName() {
        //            return superChapterName;
        //        }
        //
        //        public void setSuperChapterName(String superChapterName) {
        //            this.superChapterName = superChapterName;
        //        }
        //
        //        public long getPublishTime() {
        //            return publishTime;
        //        }
        //
        //        public void setPublishTime(long publishTime) {
        //            this.publishTime = publishTime;
        //        }
        //
        var title: String
        var visible: Int = 0
        var zan: Int = 0
        //
        //        public int getVisible() {
        //            return visible;
        //        }
        //
        //        public void setVisible(int visible) {
        //            this.visible = visible;
        //        }
        //
        //        public int getZan() {
        //            return zan;
        //        }
        //
        //        public void setZan(int zan) {
        //            this.zan = zan;
        //        }

    }
}