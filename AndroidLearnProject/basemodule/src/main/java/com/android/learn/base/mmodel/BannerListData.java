package com.android.learn.base.mmodel;

import java.util.List;


public class BannerListData {

//    {
//        "desc":"最新项目上线啦~", "id":13, "imagePath":
//        "http://www.wanandroid.com/blogimgs/5ae04af4-72b9-4696-81cb-1644cdcd2d29.jpg", "isVisible":
//        1, "order":0, "title":"最新项目上线啦~", "type":0, "url":"http://www.wanandroid.com/pindex"
//    }

    public int errorCode;
    public String errorMsg;
    public List<BannerData> data;
    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }
    public class BannerData {

        private int id;
        private String url;
        private String imagePath;
        private String title;
        private String desc;
        private int isVisible;
        private int order;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }


}