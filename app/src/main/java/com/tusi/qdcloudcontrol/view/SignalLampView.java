package com.tusi.qdcloudcontrol.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.tusi.qdcloudcontrol.R;

/**
 * Created by linfeng on 2018/10/15  18:12
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class SignalLampView extends View {

    private Paint mTextPaint;
    private Paint mBitPaint;
    private Bitmap mOnLeftAllowBit;
    private Bitmap mOnLeftForbidBit;
    private Bitmap mOnRightAllowBit;
    private Bitmap mOnRightForbidBit;
    private Bitmap mOnTopAllowBit;
    private Bitmap mOnTopForbidBit;
    private Bitmap mOnDownAllowBit;
    private Bitmap mOnDownForbidBit;
    private Bitmap mOnLeftYellowBig;
    private Bitmap mOnTopYellowBig;
    private Bitmap mOnRightYellowBig;
    private Bitmap mOnDownYellowBig;
    private Paint mBackPaint;
    private String mCountDown = "00";
    private int innerCircleDiameter;
    private int countDownWithMarginLeftAndRight;
    private Bitmap mShowingSignalBit;
    private int mDirection = DIRECTION_LEFT;
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_TOP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int LIGHT_MODE_RED = 0;
    public static final int LIGHT_MODE_GREEN = 1;
    public static final int LIGHT_MODE_YELLOW = 2;
    public int mLightMode = LIGHT_MODE_YELLOW;


    public SignalLampView(@NonNull Context context) {
        this(context, null);
    }

    public SignalLampView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignalLampView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SignalLampView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(sp2px(36));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);

        mBitPaint = new Paint();
        mBitPaint.setAntiAlias(true);
        mBitPaint.setDither(true);

        mBackPaint = new Paint();
        mBackPaint.setColor(Color.rgb(00, 00, 00));
        mBackPaint.setAntiAlias(true);
        mBackPaint.setDither(true);
        mBackPaint.setAlpha(0x77);
        initSingleLampBitmap();

        innerCircleDiameter = mOnLeftAllowBit.getWidth() + 2 * dp2px(8);
        countDownWithMarginLeftAndRight = (int) (mTextPaint.measureText(mCountDown) + 2 * dp2px(16));

        mShowingSignalBit = mOnLeftAllowBit;
        mCountDown = "00";
    }

    private void initSingleLampBitmap() {
        final Resources reso = getResources();
        mOnLeftAllowBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onleft_allow);
        mOnLeftForbidBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onleft_forbid);
        mOnLeftYellowBig = BitmapFactory.decodeResource(reso, R.drawable.ic_onleft_yellow);

        mOnTopAllowBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onup_allow);
        mOnTopForbidBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onup_forbid);
        mOnTopYellowBig = BitmapFactory.decodeResource(reso, R.drawable.ic_ontop_yellow);

        mOnRightAllowBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onright_allow);
        mOnRightForbidBit = BitmapFactory.decodeResource(reso, R.drawable.ic_onright_forbid);
        mOnRightYellowBig = BitmapFactory.decodeResource(reso, R.drawable.ic_onright_yellow);

        mOnDownAllowBit = BitmapFactory.decodeResource(reso, R.drawable.ic_ondown_allow);
        mOnDownForbidBit = BitmapFactory.decodeResource(reso, R.drawable.ic_ondown_forbid);
        mOnDownYellowBig = BitmapFactory.decodeResource(reso, R.drawable.ic_ondown_yellow);
    }

    private static final String TAG = "SignalLampView";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth();
        int height = measureHeight();
        final int realWidth = resolveSize(width, widthMeasureSpec);
        final int realHeight = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(realWidth, realHeight);
    }

    private int measureHeight() {
        return getPaddingTop() + getPaddingBottom() + innerCircleDiameter + 2 * dp2px(6);
    }

    private int measureWidth() {
        return getPaddingLeft() + getPaddingRight() + innerCircleDiameter + countDownWithMarginLeftAndRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Rect r = new Rect();
        getLocalVisibleRect(r);
        canvas.drawRoundRect(new RectF(r), getMeasuredHeight(), getMeasuredHeight(), mBackPaint);

        mBackPaint.setAlpha(0x88);
        int circleRadio = innerCircleDiameter / 2;
        canvas.drawCircle(dp2px(6) + circleRadio, getMeasuredHeight() / 2, circleRadio, mBackPaint);

        mShowingSignalBit = getShowingSignalBit(mDirection);
        Rect src = new Rect(0, 0, mShowingSignalBit.getWidth(), mShowingSignalBit.getHeight());
        int left = getPaddingLeft() + dp2px(6) + (innerCircleDiameter / 2 - mShowingSignalBit.getWidth() / 2);
        int top = getPaddingTop() + dp2px(6) + (innerCircleDiameter / 2 - mShowingSignalBit.getHeight() / 2);
        int right = left + mShowingSignalBit.getWidth();
        int bottom = top + mShowingSignalBit.getHeight();
        Rect dst = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mShowingSignalBit, src, dst, mBitPaint);

        int x = getPaddingLeft() + innerCircleDiameter;
        int x_ = (int) ((getMeasuredWidth() - x) / 2 - mTextPaint.measureText(mCountDown) / 2);
        x += x_;
        int baseLine = (int) (getMeasuredHeight() / 2 + (mTextPaint.descent() - mTextPaint.ascent()) / 2 - mTextPaint.descent());
        switch (mLightMode) {
            case LIGHT_MODE_RED:
                mTextPaint.setColor(Color.rgb(0xff, 0x3a, 0x06));
                break;
            case LIGHT_MODE_GREEN:
                mTextPaint.setColor(Color.rgb(0x1d, 0xd7, 0x0e));
                break;
            case LIGHT_MODE_YELLOW://fdb211
                mTextPaint.setColor(Color.rgb(0xfd, 0xb2, 0x11));
                break;
        }

        canvas.drawText(mCountDown, 0, mCountDown.length(), x, baseLine, mTextPaint);
    }

    //TODO  这里添加黄色的图片
    private Bitmap getShowingSignalBit(int mDirection) {
        switch (mDirection) {
            case DIRECTION_LEFT:
                return mLightMode == LIGHT_MODE_GREEN ? mOnLeftAllowBit : (mLightMode == LIGHT_MODE_RED ? mOnLeftForbidBit : mOnLeftYellowBig);
            case DIRECTION_TOP:
                return mLightMode == LIGHT_MODE_GREEN ? mOnTopAllowBit : (mLightMode == LIGHT_MODE_RED ? mOnTopForbidBit : mOnTopYellowBig);
            case DIRECTION_RIGHT:
                return mLightMode == LIGHT_MODE_GREEN ? mOnRightAllowBit : (mLightMode == LIGHT_MODE_RED ? mOnRightForbidBit : mOnRightYellowBig);
            case DIRECTION_DOWN:
                return mLightMode == LIGHT_MODE_GREEN ? mOnDownAllowBit : (mLightMode == LIGHT_MODE_RED ? mOnDownForbidBit : mOnDownYellowBig);
            default:
                return mOnLeftForbidBit;
        }
    }

    public int sp2px(final float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void updateStatus(int direction, String countDown, int lightMode) {
        this.mDirection = direction;
        this.mCountDown = countDown;
        this.mLightMode = lightMode;
        invalidate();
    }
}
