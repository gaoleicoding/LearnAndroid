import 'package:flutter/material.dart';
import 'package:flutter_app/widget/Demo1.dart';

class MyPage extends StatelessWidget {
  var parentContext;

  MyPage(this.parentContext);

  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Flutter Demo',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      debugShowCheckedModeBanner: false,
      home: new PageWidget(parentContext),
//      routes: <String,WidgetBuilder>{
//        "/demo1":(BuildContext context)=>new Demo1(),
//      },
    );
  }
}

class PageWidget extends StatefulWidget {
  var parentContext;

  PageWidget(this.parentContext);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new PageState();
  }
}

class PageState extends State<PageWidget> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: new Center(
        child: new RaisedButton(
          onPressed: _pushPage,
          child: new Text("跳转"),
        ),
      ),
    );
  }

  _pushPage() {
//    Navigator.of(widget.parentContext).pushNamed("/demo1");

    Navigator.of(widget.parentContext).push(new MaterialPageRoute(
      builder: (context) {
        return new Demo1();
      },
    ));
  }
}
