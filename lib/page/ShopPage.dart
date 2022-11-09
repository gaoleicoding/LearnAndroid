import 'package:flutter/material.dart';

class ShopPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new ShopState();
  }
}

class ShopState extends State<ShopPage> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
        body: new Scaffold(
      body: new Center(
        child: new Text("商铺"),
      ),
    ));
  }
}
