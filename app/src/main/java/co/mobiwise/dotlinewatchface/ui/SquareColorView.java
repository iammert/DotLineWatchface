package co.mobiwise.dotlinewatchface.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import co.mobiwise.dotlinewatchface.R;

/**
 * Created by mertsimsek on 22/06/15.
 */
public class SquareColorView extends View{

    private Paint mBackgroundPaint;

    private static int DEFAULT_COLOR = Color.WHITE;

    private int mColor;

    private int mWidth;
    private int mHeight;

    private float mCenterX;
    private float mCenterY;

    private Bitmap mSelectedBitmap;

    private boolean isSelected;

    private float mScale = 1;

    public SquareColorView(Context context) {
        super(context);
        init();
    }

    public SquareColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SquareColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mColor = DEFAULT_COLOR;
        isSelected = false;
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_selected);
        updatePaint();
    }

    private void updatePaint(){
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mColor);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    public void setColor(int mColor){
        this.mColor = mColor;
        updatePaint();
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mCenterX = mWidth / 2f;
        mCenterY = mHeight / 2f;

        mScale = ((float) mWidth) / (float) mSelectedBitmap.getWidth();
        mSelectedBitmap = Bitmap.createScaledBitmap(mSelectedBitmap,
                (int) (mSelectedBitmap.getWidth() * mScale / 3),
                (int) (mSelectedBitmap.getHeight() * mScale / 3), true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, 60, 60, mBackgroundPaint);

        if(isSelected)
            canvas.drawBitmap(mSelectedBitmap, mCenterX - (mSelectedBitmap.getWidth()/2f), mCenterY  - (mSelectedBitmap.getHeight()/2f), null);

        canvas.save();
        canvas.restore();
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
        postInvalidate();
    }

    public boolean isSelected(){
        return isSelected;
    }
}
