package co.newsbullet.android.particle.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import co.newsbullet.android.particle.ColorController;
import co.newsbullet.android.particle.LoadingComputations;
import co.newsbullet.android.particle.Particle;
import co.newsbullet.android.particle.ParticleConfig;
import co.newsbullet.android.particle.ParticleController;
import co.newsbullet.android.particle.R;

public class ParticleView extends View {

    private ParticleController mParticleController;
    private LoadingComputations loadingComputations;
    private ParticleConfig config;
    List<Particle> particles;

    public ParticleView(Context context) {
        super(context);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadingComputations = new LoadingComputations(getResources().getDisplayMetrics().density);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ParticleView, 0, 0);

        config = new ParticleConfig();
        config.maxSpeed = a.getInt(R.styleable.ParticleView_max_speed, 2);
        config.minSpeed = a.getInt(R.styleable.ParticleView_min_speed, 2);
        config.minRadius = (int) a.getDimensionPixelSize(R.styleable.ParticleView_min_radius, 3);
        config.maxRadius = (int) a.getDimensionPixelSize(R.styleable.ParticleView_max_radius, 5);
        config.lineDrawDistance = (int) a.getDimensionPixelSize(R.styleable.ParticleView_line_draw_distance, (int) loadingComputations.dpToPx(200));
        config.dotsCount = a.getInt(R.styleable.ParticleView_dots_count, 20);

        int colorSet = a.getResourceId(R.styleable.ParticleView_color_set, 0);

        if (colorSet == 0) {
            config.colorMode = ColorController.ColorMode.MONOCHROME;
            config.colors = ColorController.getMonoChromeColorSet();
        } else {
            config.colorMode = ColorController.ColorMode.COLOUR;

            TypedArray ta = context.getResources().obtainTypedArray(colorSet);
            int[] colors = new int[ta.length()];
            for (int i = 0; i < ta.length(); i++) {
                colors[i] = ta.getColor(i, 0);
            }
            ta.recycle();
            config.colors = colors;
        }


    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //draw circles
        for (Particle particle : particles) {
            Paint paint = new Paint();
            paint.setColor(particle.color);
            canvas.drawCircle(particle.positionX, particle.positionY, particle.radius, paint);
        }

        //draw lines
        Paint paint = new Paint();
        paint.setStrokeWidth(2);
        for (Particle particle : particles) {
            for (Particle particle1 : particles) {
                if (loadingComputations.shouldDrawLine(particle1, particle)) {
                    paint.setColor(particle.color);
                    canvas.drawLine(particle.positionX, particle.positionY, particle1.positionX, particle1.positionY, paint);
                }
            }
        }

        //animate
        for (Particle particle : particles) {
            particle.updatePostion(mParticleController.getxPosLimit(), mParticleController.getyPosLimit());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {
        mParticleController = new ParticleController(config);
        mParticleController.setxPosLimit(getMeasuredWidth());
        mParticleController.setyPosLimit(getMeasuredHeight());

        particles = mParticleController.getParticles(config.dotsCount);
    }
}
