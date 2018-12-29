package com.sv.bubbletipslib

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView

class BubbleTipsTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var attachedToWindow = false

    private var arrowDis2Edge: Int = 0
    private var anchorViewId: Int = 0
    private var anchorView: View? = null
    private var spacing: Int = 0
    private var arrowPos: ArrowPos = ArrowPos.TC

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BubbleTipsTextView, defStyleAttr, 0)

        anchorViewId = a.getResourceId(R.styleable.BubbleTipsTextView_anchorView, 0)

        spacing = a.getDimensionPixelSize(R.styleable.BubbleTipsTextView_spacing, 0)

        val pos = a.getInt(R.styleable.BubbleTipsTextView_arrowPos, 0)
        arrowPos = findArrowPos(pos)
        setArrowPos(arrowPos)

        a.recycle()

        arrowDis2Edge = ViewUtils.dip2px(getContext(), 25f)

        ViewUtils.setVisibility(this, View.INVISIBLE)

        setOnClickListener {
            hide()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        attachedToWindow = true
        setAnchorViewId(anchorViewId)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        attachedToWindow = false
    }


    private fun findArrowPos(id: Int): ArrowPos {
        ArrowPos.values().forEach {
            if (it.id == id)
                return it
        }
        return ArrowPos.TC
    }

    private inline fun View.onPreDraw(crossinline call: () -> Unit) {
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                call()
                viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    private fun calcPos() {
        anchorView?.onPreDraw {
            val lp = layoutParams as ViewGroup.MarginLayoutParams?
            val vBounds = parent?.let {
                ViewUtils.getViewBoundsOf(anchorView as View, parent as View)
            }

            vBounds?.apply {
                when (arrowPos) {
                    ArrowPos.TL -> {
                        lp?.topMargin = bottom + spacing
                        lp?.leftMargin = centerX() - arrowDis2Edge
                    }
                    ArrowPos.TC -> {
                        lp?.topMargin = bottom + spacing
                        lp?.leftMargin = centerX() - (this@BubbleTipsTextView.width / 2)
                    }
                    ArrowPos.TR -> {
                        lp?.topMargin = bottom + spacing
                        lp?.leftMargin = centerX() - this@BubbleTipsTextView.width + arrowDis2Edge
                    }
                    ArrowPos.BL -> {
                        lp?.topMargin = top - spacing - this@BubbleTipsTextView.height
                        lp?.leftMargin = centerX() - arrowDis2Edge
                    }
                    ArrowPos.BC -> {
                        lp?.topMargin = top - spacing - this@BubbleTipsTextView.height
                        lp?.leftMargin = centerX() - (this@BubbleTipsTextView.width / 2)
                    }
                    ArrowPos.BR -> {
                        lp?.topMargin = top - spacing - this@BubbleTipsTextView.height
                        lp?.leftMargin = centerX() - this@BubbleTipsTextView.width + arrowDis2Edge
                    }
                }
            }

            lp?.apply {
                leftMargin = Math.max(0, leftMargin)
                topMargin = Math.max(0, topMargin)
                parent?.let {
                    val vp = parent as View
                    leftMargin = Math.min(vp.width - this@BubbleTipsTextView.width, leftMargin)
                    topMargin = Math.min(vp.height - this@BubbleTipsTextView.height, topMargin)
                }
                layoutParams = lp
            }
        }
    }

    fun setAnchorViewId(id: Int) {
        anchorViewId = id
        if (anchorViewId != 0 && attachedToWindow) {
            val parentView = parent as View?
            anchorView = parentView?.findViewById(anchorViewId)
            if (anchorView == null) {
                throw RuntimeException("Can't find anchor view")
            }
            invalidate()
        }
    }

    fun setAnchorView(v: View) {
        anchorView = v
        invalidate()
    }

    fun setArrowPos(pos: ArrowPos) {
        arrowPos = pos
        setBackgroundResource(arrowPos.background)
        invalidate()
    }

    fun setSpacing(s: Int) {
        spacing = s
        invalidate()
    }

    fun show() {
        hide()
        onPreDraw {
            calcPos()
            ViewUtils.setVisibility(this, View.VISIBLE)
        }
    }

    fun hide() {
        ViewUtils.setVisibility(this, View.INVISIBLE)
    }

    fun showAWhile(time: Long) {
        show()
        postDelayed({ hide() }, time)
    }

    enum class ArrowPos(val id: Int, val background: Int, val desc: String) {
        TL(0, R.drawable.bubble_top_left, "top_left"),
        TC(1, R.drawable.bubble_top_center, "top_center"),
        TR(2, R.drawable.bubble_top_right, "top_right"),
        BL(3, R.drawable.bubble_bottom_left, "bottom_left"),
        BC(4, R.drawable.bubble_bottom_center, "bottom_center"),
        BR(5, R.drawable.bubble_bottom_right, "bottom_right");
    }
}


