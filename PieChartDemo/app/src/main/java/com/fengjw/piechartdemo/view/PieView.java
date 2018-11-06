package com.fengjw.piechartdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class PieView extends View {

    private Paint mPaint = new Paint();
    private float startAngle = 0;
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private int mWidth, mHeight;
    private ArrayList<PieData> mData;

    public PieView(Context context) {
        this(context, null);//这里指向第二个构造函数
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mData){
            return;
        }

        float currentStartAngle = startAngle;
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);

        for (int i = 0; i < mData.size(); i ++){
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentStartAngle, pieData.getAngle(), true, mPaint);
            currentStartAngle += pieData.getAngle();
        }

    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> data) {
        mData = data;
        initData(mData);
        invalidate();
    }

    private void initData(ArrayList<PieData> data){
        if (null == data || data.size() == 0){
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < data.size(); i ++){
            PieData pieData = data.get(i);
            sumValue += pieData.getValue();

            int j = i % mColors.length;
            pieData.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < data.size(); i ++){
            PieData pieData = data.get(i);

            float precentage = pieData.getValue() / sumValue;
            float angle = precentage * 360;

            pieData.setPercentage(precentage);
            pieData.setAngle(angle);
            sumAngle += angle;
        }
    }
}
