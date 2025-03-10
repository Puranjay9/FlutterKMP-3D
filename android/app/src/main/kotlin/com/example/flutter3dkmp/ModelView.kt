package com.example.flutter3dkmp

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.platform.PlatformView

class ModelView internal constructor(context: Context , id: Int , messenger: BinaryMessenger) : PlatformView , MethodCallHandler {

    private var view : View = LayoutInflater.from(context).inflate(R.layout.activity_model_native_view, null)
    private var channel : MethodChannel = MethodChannel(messenger, "model_view_$id")

    override fun getView(): View? {
        return view
    }

    init {
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method){
            "update" -> update(call , result)
            else -> result.notImplemented()
        }
    }

    private fun update(call: MethodCall , result: MethodChannel.Result){
        val isUpdating = call.argument<Boolean>("isUpdating")
        result.success(null)
    }

    override fun dispose() {

    }

    private val touchListener : View.OnTouchListener = object : View.OnTouchListener{

        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            return true
        }
    }
}