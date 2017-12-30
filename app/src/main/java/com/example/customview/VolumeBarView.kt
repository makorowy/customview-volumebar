package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class VolumeBarView : View {

    private var barPaint: Paint? = null
    private var thumbPaint: Paint? = null
    private var defaultBarWidth: Int? = null
    private var defaultBarHeight: Int? = null

    private var volumeLevelWidth: Int? = null
    private var currentVolumeLevel: Int? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        barPaint = Paint()
        barPaint?.color = Color.MAGENTA

        thumbPaint = Paint()
        thumbPaint?.color = Color.GREEN

        defaultBarWidth = resources.getDimensionPixelSize(R.dimen.volume_bar_default_width)
        defaultBarHeight = resources.getDimensionPixelSize(R.dimen.volume_bar_default_height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> defaultBarWidth
            View.MeasureSpec.UNSPECIFIED -> defaultBarWidth
            else -> defaultBarWidth
        }

        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> defaultBarHeight
            View.MeasureSpec.UNSPECIFIED -> defaultBarHeight
            else -> defaultBarHeight
        }

        setMeasuredDimension(width!!, height!!)
    }

    override fun onDraw(canvas: Canvas?) {
        drawBar(canvas)
        drawThumb(canvas)
    }

    private fun drawBar(canvas: Canvas?) {
        canvas?.drawRect(0.0F, 0.0F, width.toFloat(), height.toFloat(), barPaint)
    }

    private fun drawThumb(canvas: Canvas?) {
        val thumbX = calculateThumbX()
        val thumbY = height.toFloat() / 2.0F
        val radius = height.toFloat() / 2

        canvas?.drawCircle(thumbX, thumbY, radius, thumbPaint)
    }

    private fun calculateThumbX(): Float {
        val volumeLevelWidth = this.volumeLevelWidth
        val currentVolumeLevel = this.currentVolumeLevel

        return if (volumeLevelWidth != null && currentVolumeLevel != null) {
            (width / volumeLevelWidth * currentVolumeLevel).toFloat()
        } else {
            0.0F
        }
    }

    fun calibrateVolumeLevels(volumeLevelsCount: Int, currentVolumeLevel: Int) {
        this.volumeLevelWidth = volumeLevelsCount
        this.currentVolumeLevel = currentVolumeLevel
        invalidate()
    }

    fun setVolumeLevel(volumeLevel: Int) {
        currentVolumeLevel = volumeLevel
        invalidate()
    }

}

