package falook.hirobi.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import falook.hirobi.R;

public class FlatButton extends View{

    private String text;
    private int src;
    private int color;
    private int shadow;
    private int shadow_size;
    private int text_color;
    private int text_size;

    private float topOffset = 0;
    private Bitmap image;
    private Bitmap icon;
    private Paint paint;

    public FlatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.FlatButton, 0, 0);
        try {
            text = a.getString(R.styleable.FlatButton_text);
            color = a.getColor(R.styleable.FlatButton_bg_color, 0);
            text_color = a.getColor(R.styleable.FlatButton_text_color, 0);
            text_size = a.getInteger(R.styleable.FlatButton_text_size, 10);
            src = a.getResourceId(R.styleable.FlatButton_src, 0);
            shadow = a.getColor(R.styleable.FlatButton_shadow, 0);
            shadow_size = a.getInteger(R.styleable.FlatButton_shadow_size, 20);
            image = BitmapFactory.decodeResource(context.getResources(), src);





        } finally {
            a.recycle();
        }
    }

    public FlatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setTopOffset(float top_offset) {
        this.topOffset = top_offset;

        this.invalidate();
        this.requestLayout();
    }

    public float getTopOffset() {
        return topOffset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        paint.setColor(shadow);
        canvas.drawRoundRect(0, shadow_size, width, height, 10, 10, paint);

        paint.setColor(color);
        canvas.drawRoundRect(0, topOffset, width, height - shadow_size + topOffset, 10, 10, paint);

        if(icon == null){
            int newHeight =  (int)(height * 0.65);
            int newWidth =  (int)(image.getWidth() * newHeight * 1.0f / image.getHeight());

            icon = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
        }

        canvas.drawBitmap(icon, 0, (int) ((height - (height * 0.65)) / 2) + topOffset - shadow_size, null);


        paint.setColor(text_color);
        paint.setTextSize(text_size);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (width - bounds.width())/2;
        int y = (height + bounds.height())/2;

        canvas.drawText(text, icon.getWidth(), y - shadow_size + topOffset, paint );

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.setTopOffset(shadow_size*2/3);
                break;
            case MotionEvent.ACTION_UP:
                this.setTopOffset(0);
                break;
        }

        return true;
    }

}
