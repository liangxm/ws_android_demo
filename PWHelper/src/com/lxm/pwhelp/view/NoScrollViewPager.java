/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package com.lxm.pwhelp.view;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    private boolean noScroll = true;
    
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public NoScrollViewPager(Context context) {
        super(context);
    }
    
    public void setNoScroll(boolean noScroll) {
        noScroll = noScroll;
    }
    
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
    
    public boolean onTouchEvent(MotionEvent arg0) {
        if(noScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }
    
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(noScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }
    
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
    
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}