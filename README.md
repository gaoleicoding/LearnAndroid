 
简介

学Android 主要是采用 MVP + RxJava2 + Retrofit + Multimodule 等架构设计。利用开源的api获取有关android知识的数据，非常感谢张鸿洋老师提供的开放api！向开源者致敬！项目中把目前流行的项目框架都集成到了，代码结构清晰并且有详细注释，如有建议或疑问可留言反馈 ，喜欢的话留下你的Star让我知道^_^。

开放API地址

https://github.com/hongyangAndroid/wanandroid

主要功能

首页：轮播图、文章列表

项目：项目列表

知识体系：开发环境、基础知识、用户交互等知识点

公众号：几个有名的公众号推荐的Android文章

我的：我的收藏、待办、设置

搜索：热门搜索、历史搜索，支持语音搜索

文章详情：跳转到对应的网页

登录、注册、退出登录

收藏：我的收藏、添加收藏、取消收藏

设置：反馈与建议、夜间模式、语言设置、字体大小
 
项目地址：

java版本：https://github.com/gaoleicoding/AndroidLearnProject

kotlin版本：https://github.com/gaoleicoding/AndroidLearnProject/tree/kotlin
 

主要开源框架

    //okhttp retrofit
    api rootProject.ext.dependencies["okhttp3"]
    api rootProject.ext.dependencies["retrofit"]
    api rootProject.ext.dependencies["retrofit-converter-gson"]
 
    
    //butterknife
    api rootProject.ext.dependencies["butterknife"]
    annotationProcessor rootProject.ext.dependencies["butterknife-compiler"]
 
    // glide
    api rootProject.ext.dependencies["glide"]
    annotationProcessor rootProject.ext.dependencies["glide-compiler"]
 
    // banner
    api rootProject.ext.dependencies["banner"]
 
    // rxjava
    api rootProject.ext.dependencies["rxjava"]
    api rootProject.ext.dependencies["rxandroid"]
    api rootProject.ext.dependencies["retrofit2:adapter-rxjava2"]
 
    // dagger2
    api rootProject.ext.dependencies["dagger"]
    annotationProcessor rootProject.ext.dependencies["dagger-compiler"]
 
    // eventbus
    api rootProject.ext.dependencies["event-bus"]
    annotationProcessor rootProject.ext.dependencies["eventbus-annotation-processor"]
 
 
    //okhttp保存cookie
    api rootProject.ext.dependencies["PersistentCookieJar"]
 
    //下拉刷新框架
    api rootProject.ext.dependencies["SmartRefreshLayout"]
    api rootProject.ext.dependencies["SmartRefreshHeader"]
 
    //RecyclerView万能适配器
    compile rootProject.ext.dependencies["BaseRecyclerViewAdapterHelper"]
 
    //友盟统计
    api rootProject.ext.dependencies["umeng-common"]
    api rootProject.ext.dependencies["umeng-analytics"]
 
    //崩溃自定义跳转界面
    api rootProject.ext.dependencies["customactivityoncrash"]
     //    知识点模块的旋转效果
    api rootProject.ext.dependencies["infinitecycleviewpager"]
     //    首页欢迎的SVGA动效
    api rootProject.ext.dependencies["SVGAPlayer-Android"]
     //    搜索页的flow标签
    api rootProject.ext.dependencies["flowlayout"]
    //    搜索历史使用的数据库框架
    api rootProject.ext.dependencies["greendao"]
    //    activity滑动消失框架
    api rootProject.ext.dependencies["bga-swipebacklayout"]
    // StatusUtil 沉浸式状态栏
    api('com.jaeger.statusbarutil:library:1.4.0') {
        exclude group: 'com.android.support', module: 'recyclerview-v7'
    }
    //   换肤框架，不必重启应用
    api project(':xskinloader-lib')
 

app扫码下载体验

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/KAbY.png)

 
app效果图

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/1.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/2.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/3.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/4.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/5.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/6.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/7.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/8.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/9.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/10.jpg)

![image](https://github.com/gaoleiandroid1201/AndroidLearnProject/raw/master/material/screenshots/11.jpg)











