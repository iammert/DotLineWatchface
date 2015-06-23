package co.mobiwise.dotlinewatchface.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import co.mobiwise.dotlinewatchface.R;

/**
 * Created by mertsimsek on 22/06/15.
 */
public class DotLineView extends View{

    private Paint mBackgroundPaint;
    private Paint mDotPaint;
    private Paint mLinePaint;

    private int mWidth;
    private int mHeight;
    private float mCenterX;
    private float mCenterY;

    public DotLineView(Context context) {
        super(context);
        init();
    }

    public DotLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DotLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

        Resources resources = getContext().getResources();

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(resources.getColor(R.color.analog_background));
        mBackgroundPaint.setAntiAlias(true);

        mDotPaint = new Paint();
        mDotPaint.setColor(resources.getColor(R.color.color_white));
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setShadowLayer(2.0f, 1.5f, 1.5f, resources.getColor(R.color.color_shadow));

        mLinePaint = new Paint();
        mLinePaint.setColor(resources.getColor(R.color.color_white));
        mLinePaint.setStrokeWidth(resources.getDimension(R.dimen.analog_hand_stroke));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mLinePaint.setShadowLayer(2.0f, 1.5f, 1.5f, resources.getColor(R.color.color_shadow));
    }

    public void changeDotLineColor(int color){
        mBackgroundPaint.setColor(color);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mCenterX, mBackgroundPaint);

        int minutes = 54;
        float minRot = minutes / 30f * (float) Math.PI;
        float hrRot = ((12 + (minutes / 60f)) / 6f) * (float) Math.PI;

        float minLength = mCenterX - 100f;
        float hrLength = mCenterY - 100f;

        float hrX = (float) Math.sin(hrRot) * hrLength;
        float hrY = (float) -Math.cos(hrRot) * hrLength;
        canvas.drawLine(mCenterX, mCenterY, mCenterX + hrX, mCenterY + hrY, mLinePaint);

        float minX = (float) Math.sin(minRot) * minLength;
        float minY = (float) -Math.cos(minRot) * minLength;
        canvas.drawCircle(mCenterX + minX, mCenterY + minY, getResources().getDimension(R.dimen.analog_hand_stroke), mDotPaint);

        canvas.save();
        canvas.restore();
    }

}
