import 'package:flutter/material.dart';

class Demo1 extends StatefulWidget {
  @override
  _Demo1State createState() => new _Demo1State();
}

class _Demo1State extends State<Demo1> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Colors.red,
        title: new Text("demo1"),
      ),
      body: new Center(
        child: new Text("haha"),
      ),
    );
  }
}
