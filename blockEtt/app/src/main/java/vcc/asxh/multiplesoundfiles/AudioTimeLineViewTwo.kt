package vcc.asxh.multiplesoundfiles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.text.color

class AudioTimeLineViewTwo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var durationInMillis: Long = 0
    private var progressInMillis: Long = 0

    fun setDuration(duration: Long) {
        durationInMillis = duration
        invalidate() // Request redraw
    }

    fun setProgress(progress: Long) {
        progressInMillis = progress
        invalidate() // Request redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Draw background (e.g., a gray line)
        paint.color = Color.GRAY
        paint.strokeWidth = 10f
        canvas.drawLine(0f, height / 2, width, height / 2, paint)

        // Draw progress (e.g., a blue line)
        if (durationInMillis > 0) {
            val progressRatio = progressInMillis.toFloat() / durationInMillis
            val progressWidth = width * progressRatio
            paint.color = Color.BLUE
            canvas.drawLine(0f, height / 2, progressWidth, height / 2, paint)
        }
    }
}