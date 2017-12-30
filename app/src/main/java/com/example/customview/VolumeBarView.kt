package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class VolumeBarView : View {

    private var linePaint: Paint? = null
    private var defaultLineWidth: Int? = null
    private var defaultLineHeight: Int? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        linePaint = Paint()
        defaultLineWidth = resources.getDimensionPixelSize(R.dimen.volume_bar_default_width)
        defaultLineHeight = resources.getDimensionPixelSize(R.dimen.volume_bar_default_height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> defaultLineWidth
            View.MeasureSpec.UNSPECIFIED -> defaultLineWidth
            else -> defaultLineWidth
        }

        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> defaultLineHeight
            View.MeasureSpec.UNSPECIFIED -> defaultLineHeight
            else -> defaultLineHeight
        }

        setMeasuredDimension(width!!, height!!)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0.0F, 0.0F, width.toFloat(), 0.0f, linePaint)
    }

}

