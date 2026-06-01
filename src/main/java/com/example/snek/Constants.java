package com.example.snek;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public final class Constants {
    public static class Window{
        public static final int WINDOW_WIDTH = 1200 + (SnekGameConstants.SNEK_OFFSET * 2);
        public static final int WINDOW_HEIGHT = 1000 + (SnekGameConstants.SNEK_OFFSET * 2);
    }
    public static class SnekBodyConstants {
        public static final int SNEK_SIZE = Window.WINDOW_HEIGHT / 10; // 100 currently
        public static final int SNEK_EYE_SIZE = SNEK_SIZE / 5 - SnekGameConstants.SNEK_OFFSET / 2; // 17.5
        public static final int SNEK_MOUTH_SIZE = SNEK_EYE_SIZE * 3 - SnekGameConstants.SNEK_OFFSET / 2; // 50
        public static final Paint MAIN_COLOR = Color.web("#32a852"); //dark green
        public static final Paint CONNECTION_COLOR = Color.web("#a2a852"); // light green
    }
    public static class SnekGameConstants {
        public static final double UPS = 2.5; //ups = updates per second
        public static final int SNEK_OFFSET = 5;
        public static final int SNEK_STEP = 100;
    }
    public static class AppleConstants {
        public static final int PIXEL_SIZE = SnekBodyConstants.SNEK_SIZE / 16; // 6 or 6.25
        public static final Paint APPLE_RED = Color.web("#FF0800"); // red
        public static final Paint APPLE_STEM = Color.web("#000000"); // black
    }
}
