package com.hyl.mis.hylviewdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ServiceWorkerClient;

import com.hyl.mis.hylviewdemo.R;

/**
 * Created by mis on 2016/11/1.
 * 思路：
 * 定义宽高 ，里面设置
 */

public class GuaGuaLeView extends View {
    private Context context;
    private Paint mTPaint, mPaint;
    private String text = "恭喜你中了100万大奖";
    private Rect mbound = new Rect();
    private Path mPath;
    private Bitmap mHongBao;
    private Bitmap mBitmap;
    private boolean mComplete = false;
    private Canvas mCanvas;

    //全部调用第三个方法
    public GuaGuaLeView(Context context) {
        this(context, null);
    }

    public GuaGuaLeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaLeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化画笔
        initPaint();
        //初始化path
        initPath();
        //初始化图片资源
        setUpBitmap();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新定义宽高
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {//子最大可以达到的指定大小
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mHongBao.getWidth(), MeasureSpec.AT_MOST);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {//子最大可以达到的指定大小
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHongBao.getHeight(), MeasureSpec.AT_MOST);

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(text, getWidth() / 2 - mbound.width() / 2, getHeight() / 2 + mbound.height() / 2, mTPaint);
        if (mComplete)
            return;
        drawPath();
        canvas.drawBitmap(mHongBao,0,0,null);
        super.onDraw(canvas);
    }
    /**
     * 设置Xfermode 模式  PorterDuff.Mode.DST_OUT 取下层绘制非交集部分
     */
    private void drawPath() {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x= (int) event.getX();
        int y= (int) event.getY();
        int downx=0;
        int downy=0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downx=x;
                downy=y;
                mPath.moveTo(downx,downy);
                break;
            case MotionEvent.ACTION_MOVE:
                int distanceX=x-downx;
                int distanceY=y-downy;
                //当触摸达到距离5的时候才刮
                if(Math.abs(distanceX)>5||Math.abs(distanceY)>5){
                    mPath.lineTo(x,y);
                    invalidate();
                }
                downx=x;
                downy=y;
                break;
            case MotionEvent.ACTION_UP:
                cheakArea();
                break;

        }
        return true;
    }

    private void cheakArea() {
        new Thread(new Runnable() {
            int w=mHongBao.getWidth();
            int h=mHongBao.getHeight();
            //红包的像素总合
            int HBATotolrea=w*h;
            //存储图片像素点的值
            int[] mHBATotolrea=new int[HBATotolrea];
            //当前面积
            int mCurArea;
            private String TAG;
            @Override
            public void run() {
                //得到每一个像素的值
                mHongBao.getPixels(mHBATotolrea,0,w,0,0,w,h);
                for(int i=0;i<w;i++){
                    for(int j=0;j<h;j++){
                        int index=w*i+j;
                        if(mHBATotolrea[index]==0){
                            mCurArea += 1;
                        }


                    }
                    int a =mCurArea*100/HBATotolrea;
                    //当刮到大于60 的时候显示结果
                    if(a>60){
                        mComplete=true;
                    }
                }

            }
        }
        ).start();
    }

    private void setUpBitmap() {
        //先绘制dst,再设置xfermode,最后绘制src
        //第一个参数是包含你要加载的位图资源文件的对象（一般写成 getResources（）就ok了）；第二个时你需要加载的位图资源的Id。
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.p2);
        mHongBao = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mHongBao);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mCanvas.drawBitmap(mBitmap, 0, 0, null);

    }

    private void initPath() {
        mPath = new Path();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE); //画笔类型 STROKE空心 FILL 实
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置笔触
        mPaint.setDither(true);//防抖动
        mPaint.setStrokeWidth(15);//设置宽度
        mPaint.setStrokeJoin(Paint.Join.ROUND);//画笔笔刷类型 如影响画笔但始末端
//        mPaint.setColor(0xaa0000ff);

        mTPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTPaint.setStyle(Paint.Style.FILL);
        mTPaint.setStrokeCap(Paint.Cap.ROUND);
        mTPaint.setDither(true);
        mTPaint.setColor(0xaaff0000);
        mTPaint.setTextSize(30);
        measureText();

    }

    private void measureText() {

        mTPaint.getTextBounds(text, 0, text.length(), mbound);//获取文字所在矩形的长度
    }


}
