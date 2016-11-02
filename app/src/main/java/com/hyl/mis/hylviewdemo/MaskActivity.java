package com.hyl.mis.hylviewdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import static android.os.Build.VERSION_CODES.M;

public class MaskActivity extends Activity {
    private SurfaceHolder holder = null;
    private int screenWidth;
    private int screenHeight;
    private Bitmap bitmap;
    private int imageWidth;
    private int imageHeight;
    private int imageX;
    private int imageY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //获取屏幕宽高
        screenWidth = super.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = super.getWindowManager().getDefaultDisplay().getHeight();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.p2);
        //获取图片宽高
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        //获取在中间的边宽的距离
        imageX = (screenWidth - imageWidth) / 2;
        imageY = (screenHeight - imageHeight) / 2;
        setContentView(new MySurfaceView(this));


    }

    private class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {


        public MySurfaceView(Context context) {
            this(context, null);
        }

        public MySurfaceView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            MaskActivity.this.holder = super.getHolder();
            MaskActivity.this.holder.addCallback(this);//主活动中回调事件
            super.setFocusable(true);//获取焦点进行触摸事件
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            MaskActivity.this.setImage(1, 350, 350);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    private void setImage(float scale, int width, int heigth) {
        //获取画布,只有自己操作该画布，画布上锁
        Canvas canvas = this.holder.lockCanvas();
        //获取画笔
        Paint mPaint = new Paint();
        canvas.drawRect(0, 0, screenWidth, screenHeight, mPaint);
        //设置box
        Matrix mMatrix = new Matrix();
        mMatrix.postScale(scale, scale);//等量缩放
        //重新建Bitmap
        Bitmap target = Bitmap.createBitmap(this.bitmap, 0, 0, width, heigth, mMatrix, true);
        imageWidth = target.getWidth();
        imageHeight = target.getHeight();//重新对图片宽高赋值
        imageX = (screenWidth - imageWidth) / 2;//对位置重新赋值
        imageY = (screenHeight - imageHeight) / 2;
        canvas.translate(imageX, imageY);//指定到平移位置
        //重新画图
        canvas.drawBitmap(target, mMatrix, mPaint);
        MaskActivity.this.holder.unlockCanvasAndPost(canvas);//解锁进行提交


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        if (pointerCount == 2) {
            float pointA = event.getY(0);
            float pointB = event.getY(1);
            if (pointA < pointB) {
                float temp = pointA;
                pointA = pointB;
                pointB = temp;
            }
            if (!(event.getAction() == MotionEvent.ACTION_UP)) {//如果没有离开
                float scale = this.getScale(pointA, pointB);
                setImage(scale, 350, 500);
            }

        }
//        return super.onTouchEvent(event);
        return true;
    }


    private float getScale(float pointA, float pointB) {

        return pointA / pointB;
    }
}
