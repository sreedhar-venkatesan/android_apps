package vcc.asxh.multiplesoundfiles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class AudioTimeLineThree (context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var audioFileDuration: Int = 0
    private var currentPlaybackPosition: Int = 0
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var markerRadius = 10f

    init {
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 5f
        markerPaint.color = Color.RED
    }

    fun setAudioFileDuration(duration: Int) {
        audioFileDuration = duration
        invalidate() // Redraw the view
    }

    fun updateCurrentPosition(position: Int) {
        currentPlaybackPosition = position
        invalidate() // Redraw the view
    }

    fun updateCurrentPosition2(position: Int) {
        currentPlaybackPosition = position
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (audioFileDuration == 0) return // Don't draw if duration is not set

        val lineLength = width.toFloat() // Use view width as line length
        canvas.drawLine(0f, height / 2f, lineLength, height / 2f, linePaint)

        val markerPosition = (currentPlaybackPosition / audioFileDuration.toFloat()) * lineLength
        canvas.drawCircle(markerPosition, height / 2f, markerRadius, markerPaint)
    }
}