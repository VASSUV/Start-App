package ru.vassuv.startapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedVectorDrawable
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.graphics.drawable.VectorDrawableCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import ru.vassuv.startapp.R
import android.view.animation.RotateAnimation
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


class SpinnerView(context: Context,
                  attrs: AttributeSet? = null,
                  defStyleAttr: Int = 0,
                  defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0, 0)

    private var spinnerDrawable: AnimatedVectorDrawableCompat? = null

    private var px = 0f
    private var py = 0f

    private var pxOld = 0f
    private var pyOld = 0f

    private var ox = 0f
    private var oy = 0f

    private var diametr = 0
    private var angle: Double = 0.0
    private var side = 1
    private val acceleration = 0.05
    private val time = 30

    private lateinit var boundsSpinner: Rect

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 100
        val desiredHeight = 100

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        //Measure Width
        width = when {
            widthMode == EXACTLY -> widthSize
            widthMode == AT_MOST -> Math.min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        //Measure Height
        height = when {
            heightMode == EXACTLY -> heightSize
            heightMode == AT_MOST -> Math.min(desiredHeight, heightSize)
            else -> desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)

        diametr = if (width < height) width else height
        boundsSpinner = Rect((width - diametr) / 2,
                (height - diametr) / 2,
                diametr + (width - diametr) / 2,
                diametr + (height - diametr) / 2)
        spinnerDrawable = AnimatedVectorDrawableCompat.create(context, R.drawable.spinning)
        spinnerDrawable?.bounds = boundsSpinner
        ox = (width / 2).toFloat()
        oy = (height / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawText("$px $py", 200f, 200f, Paint().apply { color = Color.WHITE })
        canvas?.rotate(-angle.toFloat(), ox, oy)
        spinnerDrawable?.draw(canvas)
        super.onDraw(canvas)
    }

    private var coroutineJob: Job? = null

    override fun onTouchEvent(event: MotionEvent?) = when (event?.action) {
        MotionEvent.ACTION_DOWN -> {
            coroutineJob?.cancel()
            pxOld = px
            pyOld = py
            px = event.x
            py = event.y
            angle = Math.atan2((px - width / 2).toDouble(), (py - height / 2).toDouble()) * 360 / Math.PI
            invalidate()
            true
        }
        MotionEvent.ACTION_UP -> {
            coroutineJob = launch(UI) {
                var speed: Int = Math.sqrt(Math.pow((px - pxOld).toDouble(), 2.0) + Math.pow((py - pyOld).toDouble(), 2.0)).toInt()
                spinnerDrawable?.start()
                while (speed > 0) {
                    angle += side * (speed * time + acceleration * time * time / 2)/100
                    speed -= (acceleration * time).toInt()
                    delay(time)
                    invalidate()
                }
                spinnerDrawable?.stop()
            }
            true
        }
        MotionEvent.ACTION_MOVE -> {
            pxOld = px
            pyOld = py
            px = event.x
            py = event.y
            var tempAngle = Math.atan2((px - width / 2).toDouble(), (py - height / 2).toDouble()) * 360 / Math.PI

            if (Math.abs(tempAngle - angle) > Math.abs(tempAngle + 360 - angle))
                tempAngle += 360
            else if (Math.abs(tempAngle - angle) > Math.abs(tempAngle - 360 - angle))
                tempAngle -= 360
            side = if(tempAngle > angle) 1 else -1
            angle = tempAngle
            invalidate()
            true
        }
        else -> false
    }
}