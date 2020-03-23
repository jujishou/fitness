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
  @override
  void initState() {
    super.initState();

    _splash();
  }

  Future<Null> _splash() async {
    final platform = MethodChannel('fitness.flutter.io/adFinish');
    try {
      final String result = await platform.invokeMethod('splash');
      print(result);

      Navigator.of(context)
          .push(MaterialPageRoute(builder: (context) => SecPage()));
    } on PlatformException catch (e) {}
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        onWillPop: AndroidBackTop.backDesktop, //页面将要消失时，调用原生的返回桌面方法
        child: Center(
          child: AndroidView(viewType: 'plugins.lgh.top/adview'),
        ));
  }
}

class SecPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Center(
        child: Text('ssss'),
      ),
    );
  }
}
