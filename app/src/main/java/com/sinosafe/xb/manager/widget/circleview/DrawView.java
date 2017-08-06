package com.sinosafe.xb.manager.widget.circleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：   com.cnmobi.circleview
 * 内容摘要： //绘制扇形、圆形图。
 * 修改备注：
 * 创建时间： 2017/7/11 0011
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class DrawView extends View {
    private RectF rectF;
    private List<Integer> colors = new ArrayList<>();
    private List<SectorItem> mList = new ArrayList<>();
    // 半径
    private int radius = 0;
    // view刷新
    private int a = 0;
    private int refeshAngle = 0;
    private int number = 0;
    private boolean running = true;

    public DrawView(Context context) {
        super(context);
        init(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {

        colors.add(Color.parseColor("#C9DEFE"));
        colors.add(Color.parseColor("#C9DEFE"));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿
        paint.setStyle(Paint.Style.STROKE);// Style.FILL: 实心, STROKE:空心, FILL_OR_STROKE:同时实心与空心
//		paint.setStrokeCap(Cap.ROUND);// 画笔样式：圆形 Cap.ROUND, 方形 Cap.SQUARE
//		paint.setStrokeJoin(Join.ROUND);// 平滑效果
        paint.setStrokeWidth(15);

        a += 10;
        refeshAngle += 10;
        int temAngle = 0;

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(14);

        for (int i = 0; i < number; i++) {
            paint.setColor(colors.get(i));
            temAngle += mList.get(i).getEndAngle() - mList.get(i).getStartAngle();
            if (temAngle >= a) {
                drawMyView(canvas, paint, rectF, mList.get(i).getStartAngle(), refeshAngle);
                if (refeshAngle == mList.get(i).getEndAngle() - mList.get(i).getStartAngle()) {
                    refeshAngle = 0;
                    running = false;
                    number = (number < mList.size()) ? number + 1 : mList.size();
                }
            } else {
                drawMyView(canvas, paint, rectF, mList.get(i).getStartAngle(), Math.abs(mList.get(i).getEndAngle() - mList.get(i).getStartAngle()));
                running = false;
            }
        }
    }

    private void drawMyView(Canvas canvas, Paint paint, RectF rectF, int startAngle, int endAngle) {
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawArc(rectF, startAngle, endAngle, true, paint);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = getMeasureWH(widthMeasureSpec);
        int measureHeigh = getMeasureWH(heightMeasureSpec);

        if (measureWidth > measureHeigh) {
            radius = measureHeigh / 2;
        } else {
            radius = measureWidth / 2;
        }

        if (getMeasuredWidth() != 0 && getMeasuredHeight() != 0) {
            rectF = new RectF(0, 0, radius * 2, radius * 2);
        }

        if (measureWidth > measureHeigh) {
            setMeasuredDimension(measureHeigh, measureHeigh);
        } else {
            setMeasuredDimension(measureWidth, measureWidth);
        }
    }

    private int getMeasureWH(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        return specSize;
    }

    public void setData(List<SectorItem> list) {
        mList.clear();
        mList.addAll(list);
        this.number = 1;
        running = true;
        new Thread(new DrawThread()).start();
    }


    // 停止
    public void stop() {
        a = 0;
        refeshAngle = 0;
        number = 0;
        postInvalidate();
    }

    class DrawThread implements Runnable {
        @Override
        public void run() {
            /**
             * 使用while循环不断的刷新view的半径
             * 当半径小于100每次增加10 invalidate()重绘view会报错
             * android.view.ViewRootImpl$CalledFromWrongThreadException 是非主线程更新UI
             * Android给提供postInvalidate();快捷方法来重绘view
             *  现在明白了invalidate和postInvalidate的小区别了吧
             */
            while (running) {
                postInvalidate();
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

