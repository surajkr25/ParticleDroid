package co.newsbullet.android.particle;

import android.support.v4.util.Pair;

public class Particle {

    public int positionX;
    public int positionY;
    public Pair<Integer, Integer> position;
    public int radius;
    public int color;
    private int speedX = 2;
    private int speedY = 2;

    public Particle() {
    }

    public Particle(int positionX, int positionY, int radius, int color, int speedX, int speedY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.color = color;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void updatePostion(int canWidth, int canHeight) {
        if (this.positionX <= 0 + this.radius) {
            this.speedX *= -1;
        }
        if (this.positionX >= canWidth - this.radius) {
            this.speedX *= -1;
        }
        if (this.positionY <= 0 + this.radius) {
            this.speedY *= -1;
        }
        if (this.positionY >= canHeight - this.radius) {
            this.speedY *= -1;
        }

        positionX += speedX;
        positionY += speedY;
    }
}
