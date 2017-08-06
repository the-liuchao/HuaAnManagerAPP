package com.sinosafe.xb.manager.widget;

/**
 * 类名称：   com.cnmobi.brokenlinechart
 * 内容摘要： //业绩折线图。
 * 修改备注：
 * 创建时间： 2017/6/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sinosafe.xb.manager.module.yeji.bean.YejiTendency;
import com.sinosafe.xb.manager.utils.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import luo.library.base.utils.MyLog;

public class BrokenLineChartView extends View {
    private Paint paint;

    /**
     * 左上角x
     */
    private int LEFTUPX = 120;
    /**
     * 左上角Y
     */
    private int LEFTUPY = 120;


    /**
     * 左下角Y
     */
    private int LEFTDOWNY = 550;
    /**
     * 右下角x 通过计算屏幕宽度确定
     */
    private int RIGHTDOWNX = 900;


    /**
     * 上下间隔
     */
    private int UPDOWNSPACE = 50;
    /**
     * 左右间隔
     */
    private int LEFTRIGHTSPACE = 50;

    /**
     * 左右线条数量 11
     */
    private int leftrightlines = 0;//(RIGHTDOWNX - LEFTUPX) / LEFTRIGHTSPACE;
    /**
     *  上下方向线条数量
     */
    private int updownlines = 6;

    /**
     * 是否需要绘制图
     */
    private boolean isOne;

    /**
     * X轴坐标
     */
    private ArrayList<Float> listX = new ArrayList<>();
    /**
     * Y轴坐标
     */
    private ArrayList<Float> listY = new ArrayList<>();
    /**
     * 左右线条数量--中间变量
     */
    private int count = 1;
    private int number = 1; // 最大10
    private boolean isFinish;

    /**
     * Y轴坐标值
     */
    private List<String> listYValues = new ArrayList<>();
    /**
     * X轴坐标值
     */
    private List<String> listXValues = new ArrayList<>();
    /**
     * 业绩类型--放款笔数，保费收入，逾期笔数，逾期金额
     */
    private int yeJiType;
    /**
     * 时间类型--日，周，月，年
     */
    private int timeType;
    YejiTendency yejiTendency;
    //平均值坐标
    private float avgYPoint = 0;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            //绘制每一个过渡效果
            if (number < 50) {
                number++;
                invalidate();
                handler.sendEmptyMessageDelayed(1, 80);
            }
            //绘制每一个
            else if (count < leftrightlines - 1) {
                number = 1;
                count++;
                invalidate();
                handler.sendEmptyMessageDelayed(1, 80);
            } else {
                isFinish = true;
                //invalidate();
            }
        }

        ;
    };

    public BrokenLineChartView(Context context) {
        super(context);
    }

    public BrokenLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BrokenLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getPaint().reset();
        /**
         * 外框线
         */
        // 设置颜色
        getPaint().setColor(Color.parseColor("#E0E1F3"));
        // 设置宽度
        getPaint().setStrokeWidth(3);
        // 线的坐标点 （四个为一条线）
        float[] pts = {LEFTUPX, LEFTUPY - 20, LEFTUPX, LEFTDOWNY, LEFTUPX, LEFTDOWNY, RIGHTDOWNX + 20, LEFTDOWNY};
        // 画线
        canvas.drawLines(pts, getPaint());

        /**
         * 箭头
         */
        // 通过路径画三角形
        Path path = new Path();
        getPaint().setStyle(Paint.Style.FILL);// 设置为空心
        path.moveTo(LEFTUPX - 5, LEFTUPY - 20);// 此点为多边形的起点
        path.lineTo(LEFTUPX + 5, LEFTUPY - 20);
        path.lineTo(LEFTUPX, LEFTUPY - 35);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, getPaint());

        // 第二个箭头
        path.moveTo(RIGHTDOWNX + 20, LEFTDOWNY - 5);// 此点为多边形的起点
        path.lineTo(RIGHTDOWNX + 20, LEFTDOWNY + 5);
        path.lineTo(RIGHTDOWNX + 35, LEFTDOWNY);
        canvas.drawPath(path, getPaint());

        /**
         *  中间虚线
         */
        //int updownlines = (LEFTDOWNY - LEFTUPY) / UPDOWNSPACE;
        float[] pts2 = new float[(updownlines + leftrightlines) * 4];

        // 计算位置(上下方向标值)
        for (int i = 0; i < updownlines; i++) {
            float x1 = 120;
            float y1 = LEFTDOWNY - (i + 1) * UPDOWNSPACE;
            float x2 = RIGHTDOWNX;
            float y2 = LEFTDOWNY - (i + 1) * UPDOWNSPACE;
            pts2[i * 4 + 0] = x1;
            pts2[i * 4 + 1] = y1;
            pts2[i * 4 + 2] = x2;
            pts2[i * 4 + 3] = y2;
            getPaint().setColor(Color.parseColor("#B5B7C4"));
            getPaint().setTextSize(35);
            if(listYValues.size()>0) {
                canvas.drawText(listYValues.get(i), x1 - 80, y1 + 10, getPaint());
            }

            getPaint().setColor(Color.parseColor("#E0E1F3"));
            //画线头
            canvas.drawLine(x1, y1, x1 - 10, y1, getPaint());

            //最后一个,标注单位
            if (i == updownlines - 1) {
                getPaint().setTextSize(40);
                getPaint().setColor(Color.parseColor("#545564"));
                if (yeJiType == 0 || yeJiType == 2) {
                    canvas.drawText("单位: 笔数", x1 + 20, y1-15, getPaint());
                } else {
                    canvas.drawText("单位: 万元", x1 + 20, y1-15, getPaint());
                }
            }
        }


        // 计算位置（左右方向标值）
        for (int i = 0; i < leftrightlines; i++) {
            getPaint().setTextSize(35);
            getPaint().setColor(Color.parseColor("#B5B7C4"));
            float x1 = LEFTUPX + (i + 1) * LEFTRIGHTSPACE;
            float y1 = LEFTUPY;
            float x2 = LEFTUPX + (i + 1) * LEFTRIGHTSPACE;
            float y2 = LEFTDOWNY;
            pts2[(i + updownlines) * 4 + 0] = x1;
            pts2[(i + updownlines) * 4 + 1] = y1;
            pts2[(i + updownlines) * 4 + 2] = x2;
            pts2[(i + updownlines) * 4 + 3] = y2;       //左右间距减半
            canvas.drawText(listXValues.get(i), x2 - LEFTRIGHTSPACE + 30, y2 + 50, getPaint());

            getPaint().setColor(Color.parseColor("#E0E1F3"));
            //画线头
            canvas.drawLine(x2, y2, x2, y2 + 10, getPaint());
        }
        //画多线条
        /*getPaint().setColor(Color.parseColor("#E0E0E0"));
        getPaint().setStrokeWidth(1);
        canvas.drawLines(pts2, getPaint());*/

        if(yejiTendency!=null&&yejiTendency.getAvg()>0&&yejiTendency.getMax()>0){
            //画中间线
            getPaint().setColor(Color.parseColor("#09D39C"));
            getPaint().setStrokeWidth(3);
            canvas.drawLine(LEFTUPX, avgYPoint, RIGHTDOWNX-100*2, avgYPoint, getPaint());
            //画平均值
            getPaint().setTextSize(40);
            String avgStr = "";
            if (yeJiType == 0 || yeJiType == 2) {
                avgStr = ((int)yejiTendency.getAvg())+"";
            } else {
                avgStr = MyUtils.keepTwoDecimal(yejiTendency.getAvg());
            }
            canvas.drawText("平均值("+(avgStr)+")",RIGHTDOWNX-98*2+2, avgYPoint + 10, getPaint());
            //右边线条
            //canvas.drawLine(RIGHTDOWNX - 5, (LEFTDOWNY + LEFTUPX) / 2, RIGHTDOWNX + 20, (LEFTDOWNY + LEFTUPX) / 2, getPaint());
        }

        if (isOne&&listX.size()!=0) {
            // 线的路径
            Path path2 = new Path();
            // 共几个转折点
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    path2.moveTo(listX.get(i), listY.get(i));
                } else {
                    path2.lineTo(listX.get(i), listY.get(i));
                }
            }
            // 上一个点  减去 下一个点的位置 计算中间点位置
            path2.lineTo(listX.get(count - 1) + (listX.get(count) - listX.get(count - 1)) / 50 * number,
                    listY.get(count - 1) + (listY.get(count) - listY.get(count - 1)) / 50 * number);

            getPaint().setColor(Color.parseColor("#747FF3"));
            //折现的大小
            getPaint().setStrokeWidth(5);
            getPaint().setStyle(Paint.Style.STROKE);// 设置为空心
            canvas.drawPath(path2, getPaint());

            path2.lineTo(listX.get(count - 1) + (listX.get(count) - listX.get(count - 1)) / 50 * number, LEFTDOWNY);
            path2.lineTo(listX.get(0), LEFTDOWNY);
            path2.lineTo(listX.get(0), listY.get(0));
            getPaint().setStyle(Paint.Style.FILL);// 设置为空心
            canvas.drawPath(path2, getShadeColorPaint());
            getPaint().reset();
            // 画出转折点圆圈
            for (int i = 0; i < count; i++) {
                // 画外圆
                getPaint().setColor(Color.parseColor("#747FF3"));
                getPaint().setStyle(Paint.Style.FILL);// 设置为空心
                canvas.drawCircle(listX.get(i), listY.get(i), 7, getPaint());
                // 画中心点为白色
                getPaint().setColor(Color.parseColor("#747FF3"));
                getPaint().setStyle(Paint.Style.FILL);
                canvas.drawCircle(listX.get(i), listY.get(i), 4, getPaint());
            }
            if (isFinish) {
                getPaint().setColor(Color.parseColor("#747FF3"));
                getPaint().setStyle(Paint.Style.FILL);// 设置为空心
                canvas.drawCircle(listX.get(count), listY.get(count), 7, getPaint());
                getPaint().setColor(Color.parseColor("#747FF3"));
                getPaint().setStyle(Paint.Style.FILL);
                canvas.drawCircle(listX.get(count), listY.get(count), 4, getPaint());
            }
            handler.sendEmptyMessage(1);
        }
    }

    // 获取笔
    private Paint getPaint() {
        if (paint == null)
            paint = new Paint();
        return paint;
    }

    // 修改笔的颜色
    private Paint getShadeColorPaint() {
        Shader mShader = new LinearGradient(LEFTDOWNY, LEFTUPX, RIGHTDOWNX, LEFTDOWNY,
                new int[]{Color.parseColor("#55747FF3"), Color.TRANSPARENT}, null, Shader.TileMode.CLAMP);
        // 新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
        getPaint().setShader(mShader);
        return getPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub 点击效果没有写了
        System.out.println("==========" + event.getX() + "===" + event.getY());
        //drawBrokenLine(yeJiType, timeType);
        return super.onTouchEvent(event);
    }

    /**
     * 开始绘制
     *
     * @param yeJiType 业绩类型0---3(放款笔数，保费收入，逾期笔数，逾期金额)
     * @param timeType 时间类型0---3(日，周，月，年)
     */
    public void drawBrokenLine(int yeJiType, int timeType,YejiTendency yejiTendency) {
        this.yeJiType = yeJiType;
        this.timeType = timeType;
        this.yejiTendency = yejiTendency;
        isOne = true;
        number = 1;
        count = 1;
        isFinish = false;
        listXValues.clear();
        listYValues.clear();

        //没有数据时
        if(yejiTendency==null||yejiTendency.getTendencyItem()==null||yejiTendency.getTendencyItem().size()==0){
            setXYValues(timeType);
            initLeftFightLines(0);
        }else{
            setRealXYValues(timeType);
            initLeftFightLines(1);
        }
        invalidate();
    }

    /**
     * 初始化左右间隔坐标
     */
    private void initLeftFightLines(int type) {
        listX.clear();
        listY.clear();
        if(type==1){
            for (int i = 0; i < leftrightlines; i++) {         //中间点
                float x1 = LEFTUPX + (i + 1) * LEFTRIGHTSPACE - LEFTRIGHTSPACE / 2;
                //根据数据大小，比例算出纵坐标的值
                float y1 = 1;
                float data = Float.valueOf(yejiTendency.getTendencyItem().get(i).getData());
                float max = yejiTendency.getMax()+10;
                float per = 0f;
                if(max==0)
                    per = 1;
                else
                    per = 1-data/max;
                y1 = (per * (LEFTDOWNY - LEFTUPY)) + LEFTUPY;
                listX.add(x1);
                listY.add(y1);
                MyLog.e("yyyyyy==="+y1);
            }
        }
        else{
            for (int i = 0; i < leftrightlines; i++) {         //中间点
                float x1 = LEFTUPX + (i + 1) * LEFTRIGHTSPACE - LEFTRIGHTSPACE / 2;
                float y1 = 1;
                float per = 1f;
                y1 = (per * (LEFTDOWNY - LEFTUPY)) + LEFTUPY;
                listX.add(x1);
                listY.add(y1);
            }
        }
        if(yejiTendency.getAvg()>0){

            float avg = yejiTendency.getAvg();
            float max = yejiTendency.getMax();
            float per = 0f;
            if(max==0)
                per = 1;
            else
                per = 1-avg/(max+10);
            avgYPoint = (per * (LEFTDOWNY - LEFTUPY)) + LEFTUPY;
        }
    }

    /**
     * 设置右下角x
     *
     * @param rightDownx
     */
    public void setRightDownx(int rightDownx) {
        //减80是为右边留间隔
        RIGHTDOWNX = rightDownx - 80;
    }

    //设置真实数据
    public void setRealXYValues(int timeType) {

        leftrightlines = yejiTendency.getTendencyItem().size();
        //日
        if (timeType == 0) {

            //今天起，前七天情况
            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(yejiTendency.getTendencyItem().get(i).getDate());
            }
            int temp = (int) (yejiTendency.getMax()+10)/updownlines;
            for (int i = 1; i <= updownlines; i++) {
                listYValues.add((i*temp)+"");
            }
        }
        //周
        else if (timeType == 1) {

            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(yejiTendency.getTendencyItem().get(i).getDate());
            }
            int temp = (int) (yejiTendency.getMax()+10)/updownlines;
            for (int i = 1; i <= updownlines; i++) {
                listYValues.add((i*temp)+"");
            }
        }
        //月
        else if (timeType == 2) {

            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(yejiTendency.getTendencyItem().get(i).getDate());
            }
            int temp = (int) (yejiTendency.getMax()+10)/updownlines;
            for (int i = 1; i <= updownlines; i++) {
                listYValues.add((i*temp)+"");
            }
        }
        //年
        else if (timeType == 3) {

            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(yejiTendency.getTendencyItem().get(i).getDate());
            }
            int temp = (int) (yejiTendency.getMax()+10)/updownlines;
            for (int i = 1; i <= updownlines; i++) {
                listYValues.add((i*temp)+"");
            }
        }

        //上下间隔距离
        UPDOWNSPACE = (LEFTDOWNY - LEFTUPX) / updownlines;
        //左右间隔
        LEFTRIGHTSPACE = (RIGHTDOWNX - LEFTUPX) / leftrightlines;
    }

    public void setXYValues(int yeJiType) {
        //日
        if (timeType == 0) {
            leftrightlines = 7;
            //今天起，前七天情况
            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(getBeforeDayByToday(leftrightlines - i - 1));
            }
            //最大6笔
            for (int i = 1; i <= 6; i++) {
                listYValues.add(i + ".0");
            }
        }
        //周
        else if (timeType == 1) {

            leftrightlines = 5;
            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add("第" + (i + 1) + "周");
            }
            //最大6笔
            for (int i = 0; i < 6; i++) {
                listYValues.add((35 + 5 * i) + "");
            }
        }
        //月
        else if (timeType == 2) {

            leftrightlines = 7;
            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(getBeforeMonthByToday(leftrightlines - i - 1) + "月");
            }
            //最大6笔
            for (int i = 0; i < 6; i++) {
                listYValues.add((100 + 50 * i) + "");
            }
        }
        //年
        else if (timeType == 3) {

            leftrightlines = 7;
            for (int i = 0; i < leftrightlines; i++) {
                listXValues.add(getBeforeYearByToday(leftrightlines - i - 1));
            }
            //最大6笔
            for (int i = 0; i < 6; i++) {
                listYValues.add((100 + 50 * i) + "");
            }
        }

        //上下间隔距离
        UPDOWNSPACE = (LEFTDOWNY - LEFTUPX) / 6;
        //左右间隔
        LEFTRIGHTSPACE = (RIGHTDOWNX - LEFTUPX) / leftrightlines;
    }
    /**
     * 前七天日期
     *
     * @param index
     * @return
     */
    private String getBeforeDayByToday(int index) {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -index);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 前七个月
     *
     * @param index
     * @return
     */
    private String getBeforeMonthByToday(int index) {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -index);
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 前七年
     *
     * @param index
     * @return
     */
    private String getBeforeYearByToday(int index) {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -index);
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String dateString = formatter.format(date);
        return dateString;
    }


}

