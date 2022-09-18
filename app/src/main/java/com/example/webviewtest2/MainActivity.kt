package com.example.webviewtest2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // コードで記述するケース
        // コードで記述するケース
        val webView = WritableWebView(this)
        webView.getSettings().setJavaScriptEnabled(true);
        setContentView(webView)
        webView.loadUrl("https://www.google.co.jp/maps/@34.0691774,136.2025026,8.54z?hl=ja")
    }
}


class WritableWebView : WebView {
    private var _writedPath: Path? = null
    private var _paint: Paint? = null
    private var _writable: Boolean = false

    constructor(context: Context?) : super(context!!) {
        _writedPath = Path()
        _paint = Paint(Paint.DITHER_FLAG)
        _paint!!.isAntiAlias = true
        _paint!!.isDither = true
        _paint!!.color = -0x10000
        _paint!!.style = Paint.Style.STROKE
        _paint!!.strokeJoin = Paint.Join.ROUND
        _paint!!.strokeCap = Paint.Cap.ROUND
        _paint!!.strokeWidth = 12f
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        _writedPath = Path()
        _paint = Paint(Paint.DITHER_FLAG)
        _paint!!.isAntiAlias = true
        _paint!!.isDither = true
        _paint!!.color = -0x10000
        _paint!!.style = Paint.Style.STROKE
        _paint!!.strokeJoin = Paint.Join.ROUND
        _paint!!.strokeCap = Paint.Cap.ROUND
        _paint!!.strokeWidth = 12f
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        _writedPath = Path()
        _paint = Paint(Paint.DITHER_FLAG)
        _paint!!.isAntiAlias = true
        _paint!!.isDither = true
        _paint!!.color = -0x10000
        _paint!!.style = Paint.Style.STROKE
        _paint!!.strokeJoin = Paint.Join.ROUND
        _paint!!.strokeCap = Paint.Cap.ROUND
        _paint!!.strokeWidth = 12f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(_writedPath!!, _paint!!)
    }

    private var mX = 0f
    private var mY = 0f
    private fun touch_start(x: Float, y: Float) {
        _writedPath!!.reset()
        _writedPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touch_move(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            _writedPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touch_up() {
        _writedPath!!.lineTo(mX, mY)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (_writable === true) {
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touch_start(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    touch_move(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    touch_up()
                    invalidate()
                }
            }
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4f
    }
}