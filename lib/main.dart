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
  static const plugin = const MethodChannel('fitness.flutter.io/ad');

  @override
  void initState() {
    super.initState();

    adFinish(this.context);
  }

  static adFinish(BuildContext context) async {
    try {
      await plugin.invokeMethod('adFinish');
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
    return Container(
      child: AndroidView(viewType: 'plugins.lgh.top/splash_ad'), //开屏
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
      appBar: AppBar(),
      body: Center(
        child: Column(
          children: <Widget>[
            FlatButton(
              color: Colors.blue,
              child: Text(
                '播放激励视频',
                style: TextStyle(fontSize: 18, color: Colors.white),
              ),
              onPressed: () {
                _toRewardVideoPage(context);
              },
            ),
            FlatButton(
                color: Colors.blue,
                child: Text(
                  '打开插屏广告',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
                onPressed: () {
                  _toExpressAdPagePage(context);
                }),
            FlatButton(
                color: Colors.blue,
                child: Text(
                  '打开原生Banner',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
                onPressed: () {
                  _toListAdPage(context);
                }),
          ],
        ),
      ),
    );
  }

  void _toRewardVideoPage(BuildContext context) {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => RewardVideoPage()));
  }

  void _toExpressAdPagePage(BuildContext context) {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => ExpressAdPage()));
  }

  void _toListAdPage(BuildContext context) {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => NativeAdPage()));
  }
}

class RewardVideoPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: AndroidView(viewType: 'plugins.lgh.top/reward_video'),
    );
  }
}

class ExpressAdPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      child: Center(
        child: AndroidView(viewType: 'plugins.lgh.top/express_ad'),
      ),
    );
  }
}

class NativeAdPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('固定广告'),),
      body: Center(
        child: AndroidView(viewType: 'plugins.lgh.top/native_ad'),
      ),
    );
  }
}
