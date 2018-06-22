package co.newsbullet.android.particle;

import android.graphics.Color;

import java.util.Random;

public class ColorController {
    public enum ColorMode {
        MONOCHROME, COLOUR
    }

    public static int[] getMonoChromeColorSet() {
        int[] monoColors = {Color.parseColor("#787876"),
                Color.parseColor("#878786"),
                Color.parseColor("#898987"),
                Color.parseColor("#676765"),
                Color.parseColor("#777777"),
                Color.parseColor("#ababab"),
                Color.parseColor("#9a9a9a")};
        return monoColors;
    }

    public static int getRandomColor(Random random, int[] colors) {
        int index = random.nextInt(colors.length);
        return colors[index];
    }
}
