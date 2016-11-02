package com.hyl.mis.hylviewdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hyl.mis.hylviewdemo.R;

import static android.R.attr.height;
import static android.R.attr.textSize;
import static android.R.attr.width;
import static com.hyl.mis.hylviewdemo.R.styleable.CircleView;

/**
 * Created by mis on 2016/11/1.
 */

public class CircleView extends View {
    String tag="CircleView";
    private  AttributeSet attrs;
    private Context context;
    private int circleColor;
    private int arcColor;
    private int textColor;
    private float textSize;
    private String text;
    private int startAngle;
    private int sweepAngle;
    private int mCircleXY;
    private float mRadius;
    private Paint mCirclePaint;
    private RectF mRectF;
    private Paint mArcPaint;
    private Paint mTextPaint;
    private TypedArray ta;
    private int width;
    private int height;

    public CircleView(Context context) {
        super(context);
        this.context=context;

    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        initTypedArray();

    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        this.attrs=attrs;
        initTypedArray();

    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();

        Log.e(tag,width+"\n"+"height");
        int length=Math.min(width, height);
        Log.e(tag,"length"+length);
        mCircleXY = length / 2;
        mRadius = length * 0.5f / 2;
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(circleColor);

        mRectF = new RectF(length*0.1f,length*0.1f,length*0.9f,length*0.9f);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(arcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(width *0.1f);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSth(canvas);
    }

    private void drawSth(Canvas canvas) {
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);
        canvas.drawArc(mRectF,startAngle,sweepAngle,false,mArcPaint);
        canvas.drawText(text,0,text.length(),mCircleXY,mCircleXY+textSize/4,mTextPaint);
    }


    private void initTypedArray() {
        ta= context.obtainStyledAttributes(attrs,R.styleable.CircleView);
        if(ta!=null){
            circleColor = ta.getColor(R.styleable.CircleView_circleColor, 0);
            arcColor = ta.getColor(R.styleable.CircleView_arcColor, 0);
            textColor = ta.getColor(R.styleable.CircleView_textColor, 0);
            textSize = ta.getDimension(R.styleable.CircleView_textSize, 20);
            text = ta.getString(R.styleable.CircleView_text);
            startAngle = ta.getColor(R.styleable.CircleView_startAngle, 0);
            sweepAngle = ta.getColor(R.styleable.CircleView_sweepAngle, 90);
            ta.recycle();
        }
    }


}
