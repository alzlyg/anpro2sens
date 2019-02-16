package innobi.ru.ap2sensors;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TemperatureView extends View {
    private final static String TAG = "TemperatureView";
    private Paint paint;
    private int radius;
    private int color;
    private int temperature;


    public TemperatureView(Context context) {
        super(context);
        init();
    }

    // Вызывается при вставке элемента в макет
    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    // Вызывается при вставке элемента в макет, если был добавлен стиль
    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    // Обработка параметров в xml
    private void initAttr(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView, 0, 0);
        setRadius(typedArray.getResourceId(R.styleable.TemperatureView_cv_Radius, 100));
        typedArray.recycle();
    }

    // Подготовка элемента
    private void init() {
        Log.d(TAG, "Constructor");
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        temperature = 0;
        initTemperature();
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void setTemperature(int temperature){
        this.temperature = temperature;
        initTemperature();
    }

    private void initTemperature() {
        int opacity = 255;
        int color;
        if (temperature < 5) {
            color = Color.argb(opacity, 0, 10, 100);
        } else if (temperature < 10) {
            color = Color.argb(opacity, 0, 120, 100);
        } else if (temperature < 20) {
            color = Color.argb(opacity, 0, 150, 90);
        } else if (temperature < 30) {
            color = Color.argb(opacity, 0, 150, 0);
        } else if (temperature < 40) {
            color = Color.argb(opacity, 150, 140, 0);
        } else {
            color = Color.argb(opacity, 150, 60, 0);
        }
        paint.setColor(color);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        super.onDraw(canvas);
        int left = (canvas.getWidth()-radius)/2;

        RectF rect = new RectF(left, radius/10, left+radius, (int)(2.1*radius));
        canvas.drawRoundRect(rect, 30, 30, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize((int)(radius/3.6));
        String sTemperature = "";
        int leftText = left;
        if (temperature < -9) {
            sTemperature = String.format("%d C°", temperature);
            leftText += radius/10;
        } else if (temperature < 0) {
            sTemperature = String.format("- %d C°", -1*temperature);
            leftText += radius/7;
        } else if (temperature == 0) {
            sTemperature = " 0 C°";
            leftText += radius/5;
        } else if (temperature < 10) {
            sTemperature = String.format("+ %d C°", temperature);
            leftText += radius/7;
        } else {
            sTemperature = String.format("+%d C°", temperature);
            leftText += radius/10;
        }
        canvas.drawText(sTemperature, leftText, (int)(1.2*radius), paint);
//        Rect rect = new Rect(150, 200, 300, 400);
//        canvas.drawRect(rect, paint);
    }

    @Override
    public void invalidate() {
        Log.d(TAG, "invalidate");
        super.invalidate();
    }

    @Override
    public void requestLayout() {
        Log.d(TAG, "requestLayout");
        super.requestLayout();
    }
}
