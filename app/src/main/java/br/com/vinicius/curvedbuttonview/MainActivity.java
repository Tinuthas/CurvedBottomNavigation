package br.com.vinicius.curvedbuttonview;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class MainActivity extends AppCompatActivity {

    CurvedButtomNavigationView buttomNavigationView;
    VectorMasterView fab, fab1, fab2;
    RelativeLayout lin_id;
    PathModel outline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View
        buttomNavigationView = (CurvedButtomNavigationView) findViewById(R.id.bottom_nav);
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        lin_id = findViewById(R.id.lin_id);

        buttomNavigationView.inflateMenu(R.menu.main_menu);
        buttomNavigationView.setSelectedItemId(R.id.action_shipping);

        // Set event for bottom nav
        buttomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_shopping:
                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                        draw(6);
                        lin_id.setX(buttomNavigationView.firstCurveControlPoint1.x);
                        fab.setVisibility(View.VISIBLE);
                        fab1.setVisibility(View.GONE);
                        fab2.setVisibility(View.GONE);
                        dwanAnimation(fab);
                        break;

                    case R.id.action_shipping:
                        Toast.makeText(MainActivity.this, "Shipping", Toast.LENGTH_SHORT).show();
                        draw(2);
                        lin_id.setX(buttomNavigationView.firstCurveControlPoint1.x);
                        fab.setVisibility(View.GONE);
                        fab1.setVisibility(View.VISIBLE);
                        fab2.setVisibility(View.GONE);
                        dwanAnimation(fab1);
                        break;
                    case R.id.action_customer:
                        Toast.makeText(MainActivity.this, "Custumer", Toast.LENGTH_SHORT).show();
                        draw();
                        lin_id.setX(buttomNavigationView.firstCurveControlPoint1.x);
                        fab.setVisibility(View.GONE);
                        fab1.setVisibility(View.GONE);
                        fab2.setVisibility(View.VISIBLE);
                        dwanAnimation(fab2);
                        break;
                }
                return true;
            }
        });

    }

    private void initFab() {
        draw(2);
        lin_id.setX(buttomNavigationView.firstCurveControlPoint1.x);
        fab.setVisibility(View.GONE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
        dwanAnimation(fab1);
    }

    private void draw() {
      buttomNavigationView.firstCurveStartPoint.set((buttomNavigationView.navigationBarWidth * 10/12)
      - (buttomNavigationView.CURVE_CIRCLE_RADIUS*2)
      - (buttomNavigationView.CURVE_CIRCLE_RADIUS/3), 0);

        buttomNavigationView.firstCurveEndPoint.set(buttomNavigationView.navigationBarWidth * 10/12,
                buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4));

        buttomNavigationView.secondCurveStartPoint = buttomNavigationView.firstCurveEndPoint;
        buttomNavigationView.secondCurveEndPoint.set((buttomNavigationView.navigationBarWidth * 10/12)
                + (buttomNavigationView.CURVE_CIRCLE_RADIUS*2) + (buttomNavigationView.CURVE_CIRCLE_RADIUS/3), 0);

        buttomNavigationView.firstCurveControlPoint1.set(buttomNavigationView.firstCurveStartPoint.x
                        + buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4),
                buttomNavigationView.firstCurveStartPoint.y);

        buttomNavigationView.firstCurveControlPoint2.set(buttomNavigationView.firstCurveEndPoint.x
                        -(buttomNavigationView.CURVE_CIRCLE_RADIUS*2) + buttomNavigationView.CURVE_CIRCLE_RADIUS,
                buttomNavigationView.firstCurveEndPoint.y);

        buttomNavigationView.secondCurveControlPoint1.set(buttomNavigationView.secondCurveStartPoint.x
                        + (buttomNavigationView.CURVE_CIRCLE_RADIUS * 2) - buttomNavigationView.CURVE_CIRCLE_RADIUS,
                buttomNavigationView.secondCurveStartPoint.y);

        buttomNavigationView.secondCurveControlPoint2.set(buttomNavigationView.secondCurveEndPoint.x
                        -(buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4)),
                buttomNavigationView.secondCurveEndPoint.y);

    }

    private void dwanAnimation(final VectorMasterView fab) {
        outline = fab.getPathModelByName("outline");
        outline.setStrokeColor(Color.WHITE);
        outline.setTrimPathEnd(0.0f);
        // Init valueAnimator
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                fab.update();
            }
        });
        valueAnimator.start();
    }

    private void draw(int i) {
        buttomNavigationView.firstCurveStartPoint.set((buttomNavigationView.navigationBarWidth/i)
        -(buttomNavigationView.CURVE_CIRCLE_RADIUS*2) - (buttomNavigationView.CURVE_CIRCLE_RADIUS/3),
                0);

        buttomNavigationView.firstCurveEndPoint.set(buttomNavigationView.navigationBarWidth/i,
                buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4));

        buttomNavigationView.secondCurveStartPoint = buttomNavigationView.firstCurveEndPoint;
        buttomNavigationView.secondCurveEndPoint.set((buttomNavigationView.navigationBarWidth/i)
         + (buttomNavigationView.CURVE_CIRCLE_RADIUS*2) + (buttomNavigationView.CURVE_CIRCLE_RADIUS/3), 0);

        buttomNavigationView.firstCurveControlPoint1.set(buttomNavigationView.firstCurveStartPoint.x
        + buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4),
                buttomNavigationView.firstCurveStartPoint.y);

        buttomNavigationView.firstCurveControlPoint2.set(buttomNavigationView.firstCurveEndPoint.x
            -(buttomNavigationView.CURVE_CIRCLE_RADIUS*2) + buttomNavigationView.CURVE_CIRCLE_RADIUS,
                buttomNavigationView.firstCurveEndPoint.y);

        buttomNavigationView.secondCurveControlPoint1.set(buttomNavigationView.secondCurveStartPoint.x
                        + (buttomNavigationView.CURVE_CIRCLE_RADIUS * 2) - buttomNavigationView.CURVE_CIRCLE_RADIUS,
                buttomNavigationView.secondCurveStartPoint.y);

        buttomNavigationView.secondCurveControlPoint2.set(buttomNavigationView.secondCurveEndPoint.x
                        -(buttomNavigationView.CURVE_CIRCLE_RADIUS + (buttomNavigationView.CURVE_CIRCLE_RADIUS/4)),
                buttomNavigationView.secondCurveEndPoint.y);
    }
}
