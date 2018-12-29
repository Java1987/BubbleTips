package com.sv.bubbletipslib

import android.content.Context
import android.graphics.Rect
import android.view.View

object ViewUtils {

    /**
     * @param view
     * @param visible
     */
    fun setVisibility(view: View?, visible: Int) {
        if (view != null && view.visibility != visible) {
            view.visibility = visible
        }
    }

    /**
     *
     * @param view      Target View
     * @param container container
     * @return
     */
    fun getViewTopOf(view: View, container: View): Int {
        var view = view
        var top = view.top
        while (view.parent != null && view.parent !== container) {
            view = view.parent as View
            top += view.top
        }
        return top
    }

    /**
     * @param view      Target View
     * @param container container
     * @return
     */
    fun getViewBottomOf(view: View, container: View): Int {
        return getViewTopOf(view, container) + (view.bottom - view.top)
    }

    /**
     * @param view      Target View
     * @param container container
     * @return
     */
    fun getViewLeftOf(view: View, container: View): Int {
        var view = view
        var left = view.left
        while (view.parent != null && view.parent !== container) {
            view = view.parent as View
            left += view.left
        }
        return left
    }

    /**
     * @param view      Target View
     * @param container container
     * @return
     */
    fun getViewRightOf(view: View, container: View): Int {
        return getViewLeftOf(view, container) + (view.right - view.left)
    }

    /**
     * 获取view在container中的相对Rect
     *
     * @param view      Target View
     * @param container container
     * @return
     */
    fun getViewBoundsOf(view: View, container: View): Rect {
        val rect = Rect()
        rect.top = getViewTopOf(view, container)
        rect.bottom = getViewBottomOf(view, container)
        rect.left = getViewLeftOf(view, container)
        rect.right = getViewRightOf(view, container)
        return rect
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        if (context == null) {
            return 0
        }
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp(像素)
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

}
