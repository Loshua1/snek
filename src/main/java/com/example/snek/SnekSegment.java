package com.example.snek;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SnekSegment {
    private final int SNEK_OFFSET = Constants.SnekGameConstants.SNEK_OFFSET;
    private final int BODY_SIZE = Constants.SnekBodyConstants.SNEK_SIZE - 2 * SNEK_OFFSET;
    private final Paint MAIN_COLOR = Constants.SnekBodyConstants.MAIN_COLOR;
    private final Paint CONNECTION_COLOR = Constants.SnekBodyConstants.CONNECTION_COLOR;

    private final Group segment = new Group();
    private Entity segmentEntity = new Entity();

    private Direction connection;
    private Direction prevConnection;
    private double x, y;
    private double previousX, previousY;

    public SnekSegment(Direction dir, double x, double y){
        this.x = x;   this.y = y;

        connection = prevConnection = dir;
        Rectangle segmentBox = new Rectangle(BODY_SIZE, BODY_SIZE);
        segmentBox.setFill(MAIN_COLOR);

        Rectangle connectionBox = new Rectangle(0,0);
        switch (connection) {
            case NORTH -> {
                connectionBox = new Rectangle(BODY_SIZE - (2 * SNEK_OFFSET), 2 * SNEK_OFFSET);
                connectionBox.setX(SNEK_OFFSET); connectionBox.setY(-SNEK_OFFSET * 2);
            }
            case EAST -> {
                connectionBox = new Rectangle(2 * SNEK_OFFSET, BODY_SIZE - (2 * SNEK_OFFSET));
                connectionBox.setX(BODY_SIZE); connectionBox.setY(SNEK_OFFSET);
            }
            case SOUTH -> {
                connectionBox = new Rectangle(BODY_SIZE - (2 * SNEK_OFFSET), 2 * SNEK_OFFSET);
                connectionBox.setX(SNEK_OFFSET); connectionBox.setY(BODY_SIZE);
            }
            case WEST -> {
                connectionBox = new Rectangle(2 * SNEK_OFFSET, BODY_SIZE - (2 * SNEK_OFFSET));
                connectionBox.setX(-SNEK_OFFSET * 2); connectionBox.setY(SNEK_OFFSET);
            }
        }
        connectionBox.setFill(CONNECTION_COLOR);

        segment.getChildren().addAll(segmentBox, connectionBox);
        init();
    }

    public void init(){
        segmentEntity = FXGL.entityBuilder().view(segment).buildAndAttach();
        segmentEntity.setPosition(x, y);
    }

    public void updateVisual(double newX, double newY){
        previousX = x;   previousY = y;
        x = newX;   y = newY;
        segmentEntity.setPosition(x, y);
    }

    public void changeSegmentDirection(Direction newDir){
        prevConnection = connection;
        if (newDir != connection) {
            connection = newDir;
            segment.getChildren().clear();
            Rectangle segmentBox = new Rectangle(BODY_SIZE, BODY_SIZE);
            segmentBox.setFill(MAIN_COLOR);
            Rectangle connectionBox = new Rectangle(0,0);
            switch (connection) {
                case NORTH -> {
                    connectionBox = new Rectangle(BODY_SIZE - (2 * SNEK_OFFSET), 2 * SNEK_OFFSET);
                    connectionBox.setX(SNEK_OFFSET); connectionBox.setY(-SNEK_OFFSET * 2);
                }
                case EAST -> {
                    connectionBox = new Rectangle(2 * SNEK_OFFSET, BODY_SIZE - (2 * SNEK_OFFSET));
                    connectionBox.setX(BODY_SIZE); connectionBox.setY(SNEK_OFFSET);
                }
                case SOUTH -> {
                    connectionBox = new Rectangle(BODY_SIZE - (2 * SNEK_OFFSET), 2 * SNEK_OFFSET);
                    connectionBox.setX(SNEK_OFFSET); connectionBox.setY(BODY_SIZE);
                }
                case WEST -> {
                    connectionBox = new Rectangle(2 * SNEK_OFFSET, BODY_SIZE - (2 * SNEK_OFFSET));
                    connectionBox.setX(-SNEK_OFFSET * 2); connectionBox.setY(SNEK_OFFSET);
                }
            }
            connectionBox.setFill(CONNECTION_COLOR);

            segment.getChildren().addAll(connectionBox, segmentBox);
        }
    }

    public double getPrevX(){
        return previousX;
    }

    public double getPrevY(){
        return previousY;
    }

    public double getCurrX(){
        return x;
    }

    public double getCurrY(){
        return y;
    }

    public Direction getPrevConnection(){
        return prevConnection;
    }
}
