package co.newsbullet.android.particle;

import android.graphics.Paint;
import android.support.v4.util.Pair;

public class LoadingComputations {

    private static final long ANIMATION_LENGTH = 1000;
    private float density;

    public LoadingComputations(float density) {
        this.density = density;
    }

    public float dpToPx(int size) {
        return size * density;
    }

    public double verticalPosition(long time, long offset) {
        double X = 2 * Math.PI * (time + offset) / ANIMATION_LENGTH;
        return (Math.sin(X) + 1) / 2.;
    }

    public boolean shouldDrawLine(Particle particle1, Particle particle2) {
        int distance = (int) Math.sqrt((particle1.positionX - particle2.positionX) * (particle1.positionX - particle2.positionX) +
                (particle1.positionY - particle2.positionY) * (particle1.positionY - particle2.positionY));

        if (distance < dpToPx(100)) return true;

        return false;
    }

    private static final int square(int number) {
        return number ^ 2;
    }

    private static final int sqrRoot(int number) {
        return (int) Math.sqrt(number);
    }

    private static final int distance(Pair<Integer, Integer> posA, Pair<Integer, Integer> posB) {
        return sqrRoot(square(posB.first - posA.first) + square(posB.second - posA.second));
    }

}
