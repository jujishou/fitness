import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'AndroidBackTop.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: HomePage());
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const plugin = const MethodChannel('fitness.flutter.io/splash');

  @override
  void initState() {
    super.initState();

    adFinish(this.context);
  }

  static adFinish(BuildContext context) async {
    try {
      await plugin.invokeMethod('splash');
    } on PlatformException catch (e) {
      debugPrint(e.toString());
    }

    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => SecPage()));
  }

  ///设置回退到手机桌面
  static Future<bool> backDesktop() async {
    try {
      await plugin.invokeMethod('back');
    } on PlatformException catch (e) {
      debugPrint(e.toString());
    }
    return Future.value(false);
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: AndroidView(viewType: 'plugins.lgh.top/adview'),
    );

    return WillPopScope(
        onWillPop: backDesktop, //页面将要消失时，调用原生的返回桌面方法
        child: Center(
          child: AndroidView(viewType: 'plugins.lgh.top/adview'),
        ));
  }
}

class SecPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Center(
          child: Text('ssss'),
        ),
      ),
    );
  }
}
