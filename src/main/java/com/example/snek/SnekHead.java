package com.example.snek;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnekHead {
    private final Group head = new Group();
    private Entity headEntity = new Entity();

    public SnekHead(Direction startingDir){
        changeGameFace(startingDir);
        init();
    }

    public void init(){
        headEntity = FXGL.entityBuilder().view(head).buildAndAttach();
    }

    public Entity getHeadEntity(){
        return headEntity;
    }
    
    public void changeGameFace(Direction currentDirection){
        int EYE_SIZE = Constants.SnekBodyConstants.SNEK_EYE_SIZE;
        int BODY_SIZE = Constants.SnekBodyConstants.SNEK_SIZE;
        int MOUTH_SIZE = Constants.SnekBodyConstants.SNEK_MOUTH_SIZE;
        int SNEK_OFFSET = Constants.SnekGameConstants.SNEK_OFFSET;
        head.getChildren().clear();

        Rectangle headBox = new Rectangle(BODY_SIZE - (2 * SNEK_OFFSET), BODY_SIZE - (2 * SNEK_OFFSET));
        Rectangle leftEye = new Rectangle(EYE_SIZE,EYE_SIZE);
        Rectangle rightEye = new Rectangle(EYE_SIZE,EYE_SIZE);
        Rectangle mouth = null;

        switch (currentDirection){
            case NORTH -> {
                leftEye.setX(EYE_SIZE);    leftEye.setY(MOUTH_SIZE);
                rightEye.setX(MOUTH_SIZE);    rightEye.setY(MOUTH_SIZE);
                mouth = new Rectangle(MOUTH_SIZE,EYE_SIZE);
                mouth.setX(EYE_SIZE);    mouth.setY(EYE_SIZE);
            }
            case EAST -> {
                leftEye.setX(EYE_SIZE);    leftEye.setY(EYE_SIZE);
                rightEye.setX(EYE_SIZE);    rightEye.setY(MOUTH_SIZE);
                mouth = new Rectangle(EYE_SIZE,MOUTH_SIZE);
                mouth.setX(MOUTH_SIZE);    mouth.setY(EYE_SIZE);
            }
            case SOUTH -> {
                leftEye.setX(EYE_SIZE);    leftEye.setY(EYE_SIZE);
                rightEye.setX(MOUTH_SIZE);    rightEye.setY(EYE_SIZE);
                mouth = new Rectangle(MOUTH_SIZE,EYE_SIZE);
                mouth.setX(EYE_SIZE);    mouth.setY(MOUTH_SIZE);
            }
            case WEST -> {
                leftEye.setX(MOUTH_SIZE);    leftEye.setY(EYE_SIZE);
                rightEye.setX(MOUTH_SIZE);    rightEye.setY(MOUTH_SIZE);
                mouth = new Rectangle(EYE_SIZE,MOUTH_SIZE);
                mouth.setX(EYE_SIZE);    mouth.setY(EYE_SIZE);
            }
        }
        headBox.setFill(Color.web("#32a852"));
        head.getChildren().addAll(headBox, leftEye, rightEye, mouth);

//        headEntity = FXGL.entityBuilder().view(head).buildAndAttach();
    }

    public void updateVisual(double headX, double headY){
        headEntity.setPosition(headX,headY);
    }
}
