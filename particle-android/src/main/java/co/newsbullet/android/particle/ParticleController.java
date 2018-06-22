package co.newsbullet.android.particle;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleController {
    int xPosLimit = 500;
    int yPosLimit = 500;

    ParticleConfig config;

    public ParticleController(ParticleConfig config) {
        this.config = config;
    }

    public List<Particle> getParticles(int count) {
        List<Particle> particles = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            particles.add(getParticle(random));
        }
        return particles;
    }

    private Particle getParticle(Random random) {
        return new Particle(
                random.nextInt(xPosLimit + 1),
                random.nextInt(yPosLimit + 1),
                getRandomBetween(random, config.maxRadius, config.minRadius),
                ColorController.getRandomColor(random, config.colors),
                negOrNoNeg(random, getRandomBetween(random, config.maxSpeed, config.minSpeed)),
                negOrNoNeg(random, getRandomBetween(random, config.maxSpeed, config.minSpeed))
        );
    }

    public int getRandomBetween(Random random, int max, int min) {
        return random.nextInt((max - min) + 1) + min;
    }

    public int negOrNoNeg(Random random, int speed) {
        return (random.nextBoolean() ? 1 : -1) * speed;
    }

    public void setxPosLimit(int xPosLimit) {
        this.xPosLimit = xPosLimit;
    }

    public void setyPosLimit(int yPosLimit) {
        this.yPosLimit = yPosLimit;
    }

    public int getxPosLimit() {
        return xPosLimit;
    }

    public int getyPosLimit() {
        return yPosLimit;
    }
}
