import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'dart:io' show Platform;

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  static const MethodChannel _channel = MethodChannel("android");
  Future<String>? _nativeName;

  Future<String> _returnNativeName() async {
    try {
      final name = await _channel.invokeMethod<String>("getname");
      return name ?? "Unknown";
    } on PlatformException catch (e) {
      return "Error: ${e.message}";
    }
  }

  @override
  void initState() {
    super.initState();
    _nativeName = _returnNativeName();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: NativeModelView()
          // FutureBuilder<String>(
          //   future: _nativeName,
          //   builder: (context, snapshot) {
          //     if (snapshot.connectionState == ConnectionState.waiting) {
          //       return const CircularProgressIndicator();
          //     } else if (snapshot.hasError) {
          //       return Text("Error: ${snapshot.error}");
          //     } else {
          //       return Text(snapshot.data ?? "Unknown");
          //     }
          //   },
          // ),
        ),
      ),
    );
  }
}


class NativeModelView extends StatefulWidget {
  const NativeModelView({Key? key}) : super(key: key);

  @override
  State<NativeModelView> createState() => _NativeModelViewState();
}

class _NativeModelViewState extends State<NativeModelView> {
  @override
  Widget build(BuildContext context) {
    // Use AndroidView for Android platform
    if (Platform.isAndroid) {
      return AndroidView(
        viewType: 'model_view',
        onPlatformViewCreated: _onPlatformViewCreated,
      );
    }

    // Return fallback for other platforms
    return const Center(child: Text('Not supported on this platform'));
  }

  void _onPlatformViewCreated(int id) {
    // You can initialize the MethodChannel here if needed
    final channel = MethodChannel('model_view_$id');
    // Example of calling a method on the native view
    // channel.invokeMethod('update', {'isUpdating': true});
  }
}