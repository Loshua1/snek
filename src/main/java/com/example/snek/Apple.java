package com.example.snek;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Apple {
    private double x, y;

    private final Group apple = new Group();
    private Entity appleEntity = new Entity();

    private final int PIXEL_SIZE = Constants.AppleConstants.PIXEL_SIZE;
    private final Paint APPLE_RED = Constants.AppleConstants.APPLE_RED;
    private final Paint STEM_COLOR = Constants.AppleConstants.APPLE_STEM;
    public Apple(double x, double y) {
        this.x = x;   this.y = y;

        Rectangle appleRect1 = new Rectangle(14 * PIXEL_SIZE, 4 * PIXEL_SIZE);
        appleRect1.setX(     PIXEL_SIZE);   appleRect1.setY( 7 * PIXEL_SIZE);
        appleRect1.setFill(APPLE_RED);
        Rectangle appleRect2 = new Rectangle(12 * PIXEL_SIZE, 8 * PIXEL_SIZE);
        appleRect2.setX( 2 * PIXEL_SIZE);   appleRect2.setY( 5 * PIXEL_SIZE);
        appleRect2.setFill(APPLE_RED);
        Rectangle appleRect3 = new Rectangle(10 * PIXEL_SIZE, PIXEL_SIZE);
        appleRect3.setX( 3 * PIXEL_SIZE);   appleRect3.setY(13 * PIXEL_SIZE);
        appleRect3.setFill(APPLE_RED);
        Rectangle appleRect4 = new Rectangle( 8 * PIXEL_SIZE,11 * PIXEL_SIZE);
        appleRect4.setX( 4 * PIXEL_SIZE);   appleRect4.setY( 4 * PIXEL_SIZE);
        appleRect4.setFill(APPLE_RED);
        Rectangle appleRect5 = new Rectangle( 4 * PIXEL_SIZE,13 * PIXEL_SIZE);
        appleRect5.setX( 6 * PIXEL_SIZE);   appleRect5.setY( 3 * PIXEL_SIZE);
        appleRect5.setFill(APPLE_RED);

        Rectangle stemRect1 = new Rectangle(PIXEL_SIZE, 3 * PIXEL_SIZE);
        stemRect1.setX( 7 * PIXEL_SIZE);    stemRect1.setY( 2 * PIXEL_SIZE);
        stemRect1.setFill(STEM_COLOR);
        Rectangle stemRect2 = new Rectangle(2 * PIXEL_SIZE,PIXEL_SIZE);
        stemRect2.setX( 8 * PIXEL_SIZE);    stemRect2.setY(     PIXEL_SIZE);
        stemRect2.setFill(STEM_COLOR);

        apple.getChildren().addAll(appleRect1,appleRect2,appleRect3,appleRect4,appleRect5,stemRect1,stemRect2);
        init();
    }

    public void init(){
        appleEntity = FXGL.entityBuilder().view(apple).buildAndAttach();
        appleEntity.setPosition(x, y);
    }

    public void updateVisual(double newX, double newY){
        x = newX;   y = newY;
        appleEntity.setPosition(x, y);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}
