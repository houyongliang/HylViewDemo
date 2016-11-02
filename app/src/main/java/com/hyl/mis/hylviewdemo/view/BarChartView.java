package com.hyl.mis.hylviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.hyl.mis.hylviewdemo.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.lang.reflect.Array.getInt;

/**
 * Created by mis on 2016/11/2.
 */

public class BarChartView extends View {

    private static  final  int  OFFSET =5 ;
    private int mCount;
    private Paint mPaint;
    private int mRectHeight;
    private int mRectWidth;
    private LinearGradient mLinearGradient;
    private int mWidth;

    //重写构造方法
    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {//初始化视图
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);//设置画笔
        mPaint.setStyle(Paint.Style.FILL);//设置填充样式
        //初始化属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BarChartView);
        if (ta != null) {
            mCount = ta.getInt(R.styleable.BarChartView_count, 6);
            ta.recycle();//清空
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mRectHeight = getMeasuredHeight();
        mRectWidth = (int) (mWidth *0.8/mCount);
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.GREEN, Color.RED, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);//设置 着色器

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<mCount;i++){
            double mRandom = Math.random();
            float mcurrentHeight= (float) (mRectHeight*mRandom);
            float width= (float) (mWidth*0.4/2+OFFSET);
            canvas.drawRect(width+i*mRectWidth,mcurrentHeight,width+(i+1)*mRectWidth,mRectHeight,mPaint);
        }
        postInvalidateDelayed(300);
    }
}
