package br.com.vinicius.curvedbuttonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

public class CurvedButtomNavigationView extends BottomNavigationView {

    //Declare variable
    private Path path;
    private Paint paint;

    // The radius of fab button
    public final int CURVE_CIRCLE_RADIUS = 90;

    // The coordinates of the first curve
    public Point firstCurveStartPoint = new Point();
    public Point firstCurveEndPoint = new Point();
    public Point firstCurveControlPoint1 = new Point();
    public Point firstCurveControlPoint2 = new Point();

    // The coordinates of the second curve
    public Point secondCurveStartPoint = new Point();
    public Point secondCurveEndPoint = new Point();
    public Point secondCurveControlPoint1 = new Point();
    public Point secondCurveControlPoint2 = new Point();

    public int navigationBarWidth, navigationBarHeight;

    public CurvedButtomNavigationView(Context context) {
        super(context);
        initView();
    }

    public CurvedButtomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CurvedButtomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.WHITE);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Get width and height of navigation bar
        navigationBarWidth = getWidth();
        navigationBarHeight = getHeight();

        // The coordinates(x,y) of the start point before curve
        firstCurveStartPoint.set((navigationBarWidth/2)
            -(CURVE_CIRCLE_RADIUS*2)
            -(CURVE_CIRCLE_RADIUS/3), 0);

        // The coordinates (x,y) of the end point after curve
        firstCurveEndPoint.set((navigationBarWidth/2),
                CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS/4));

        // Same for second curve
        secondCurveStartPoint = firstCurveEndPoint;

        secondCurveEndPoint.set((navigationBarWidth/2) + (CURVE_CIRCLE_RADIUS * 2)
            +(CURVE_CIRCLE_RADIUS/3), 0);

        // The coordinates (x,y) of the first control point on cubic curve
        firstCurveControlPoint1.set(firstCurveStartPoint.x + (CURVE_CIRCLE_RADIUS)
                        + (CURVE_CIRCLE_RADIUS/4), firstCurveStartPoint.y);

        // The coordinates (x,y) of the second control point on cubic curve
        firstCurveControlPoint2.set(firstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2)
                + CURVE_CIRCLE_RADIUS, firstCurveEndPoint.y);

        secondCurveControlPoint1.set(secondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2)
                -CURVE_CIRCLE_RADIUS, secondCurveStartPoint.y);
        secondCurveControlPoint2.set(secondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS
                + (CURVE_CIRCLE_RADIUS/4)), secondCurveEndPoint.y);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(firstCurveStartPoint.x, firstCurveStartPoint.y);

        path.cubicTo(firstCurveControlPoint1.x, firstCurveControlPoint1.y,
                firstCurveControlPoint2.x, firstCurveControlPoint2.y,
                firstCurveEndPoint.x, firstCurveEndPoint.y);

        path.cubicTo(secondCurveControlPoint1.x, secondCurveControlPoint1.y,
                secondCurveControlPoint2.x, secondCurveControlPoint2.y,
                secondCurveEndPoint.x, secondCurveEndPoint.y);

        path.lineTo(navigationBarWidth, 0);
        path.lineTo(navigationBarWidth, navigationBarHeight);
        path.lineTo(0, navigationBarHeight);
        path.close();

        canvas.drawPath(path, paint);

    }
}
