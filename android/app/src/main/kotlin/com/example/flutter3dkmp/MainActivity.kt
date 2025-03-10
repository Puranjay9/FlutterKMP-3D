package com.example.flutter3dkmp

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val channelName = "android"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        flutterEngine.platformViewsController.registry.registerViewFactory(
            "model_view",
            ModelViewFactory(flutterEngine.dartExecutor.binaryMessenger)
        )

        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channelName)

        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "getname" -> result.success("Android ${android.os.Build.VERSION.RELEASE} fuck yeah")
                else -> result.notImplemented()
            }
        }
    }
}

