package com.example.snek;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class SnekApplication extends GameApplication {
    //objects on map
    private SnekHead snekHead;
    private ArrayList<SnekSegment> segments;

    //snekHead movement
    private Direction currentDirection = Direction.EAST, previousDirection = Direction.EAST;
    private Direction nextDirection;
    private boolean movedThisUpdate = false;
    private double headX, headY;
    private double previousX, previousY;

    //game vars
    private boolean[][] gameMap;
    private TimerAction snekTimer;
    private boolean isGameOver;
    private boolean gameWon;
    private Apple apple;
    private double ups = 2.5;

    //constants
    private final int SNEK_OFFSET = Constants.SnekGameConstants.SNEK_OFFSET;
    private final int SNEK_STEP = Constants.SnekGameConstants.SNEK_STEP;

    @Override
    public void initSettings(GameSettings settings) {
        settings.setWidth(Constants.Window.WINDOW_WIDTH);
        settings.setHeight(Constants.Window.WINDOW_HEIGHT);
    }

    @Override
    public void initGame() {
        resetVariables();
        snekTimer = FXGL.getGameTimer().runAtInterval(() -> {
            if (moveCheck()){
                updateSnek();
            } else {
                snekTimer.expire();// stops the timer
                gameOver();
            }
            if (gameWon()) {
                gameOver();
            }
            if (isHeadIntersectingApple()) {
                moveApple();
                addSegment();
            }
        }, Duration.seconds(1.0 / ups));
    }

    @Override
    public void initInput() {
        FXGL.onKeyDown(KeyCode.W, () -> setCurrentDirection(Direction.NORTH));
        FXGL.onKeyDown(KeyCode.A, () -> setCurrentDirection(Direction.WEST));
        FXGL.onKeyDown(KeyCode.S, () -> setCurrentDirection(Direction.SOUTH));
        FXGL.onKeyDown(KeyCode.D, () -> setCurrentDirection(Direction.EAST));
        FXGL.onKeyDown(KeyCode.UP, () -> setCurrentDirection(Direction.NORTH));
        FXGL.onKeyDown(KeyCode.LEFT, () -> setCurrentDirection(Direction.WEST));
        FXGL.onKeyDown(KeyCode.DOWN, () -> setCurrentDirection(Direction.SOUTH));
        FXGL.onKeyDown(KeyCode.RIGHT, () -> setCurrentDirection(Direction.EAST));
        FXGL.onKeyDown(KeyCode.R, () -> {if (isGameOver) restartGame();});

        FXGL.onKey(KeyCode.K, this::addSegment);
        FXGL.onKeyDown(KeyCode.L, this::moveApple);
        FXGL.onKeyDown(KeyCode.Q, () -> {if (ups - .05 > 0.05) {
                                            ups -= .05;
                                            restartTimer();
                                        }});
        FXGL.onKey(KeyCode.E, () -> {ups += .05;
                                        restartTimer();});
    }

    @Override
    public void onUpdate(double tpf) {

    }

    public void gameOver(){
        if (isGameOver) {
            Text gameOverText = new Text("GAME OVER!");
            gameOverText.setScaleX(15);   gameOverText.setScaleY(15);
            FXGL.addUINode(gameOverText, (int) (Constants.Window.WINDOW_WIDTH / 2), (int) (Constants.Window.WINDOW_HEIGHT / 3) + 20);
        } else if (gameWon) {
            Text gameWonText = new Text("YOU WIN!");
            gameWonText.setScaleX(15);   gameWonText.setScaleY(15);
            FXGL.addUINode(gameWonText, (int) (Constants.Window.WINDOW_WIDTH / 2), (int) (Constants.Window.WINDOW_HEIGHT / 3) + 20);
        }
        Text scoreText = new Text("You got a score of " + segments.size() + "!");
        scoreText.setScaleX(7.5);    scoreText.setScaleY(7.5);
        FXGL.addUINode(scoreText, (int) (Constants.Window.WINDOW_WIDTH/2) - 20, (int) (Constants.Window.WINDOW_HEIGHT/2) + 10);

        Text restartGameText = new Text("Press R to restart game");
        restartGameText.setScaleX(5);    restartGameText.setScaleY(5);
        FXGL.addUINode(restartGameText, (int) (Constants.Window.WINDOW_WIDTH/2) - 20, (int) (Constants.Window.WINDOW_HEIGHT/2) + 50);
    }

    public void restartGame(){
        FXGL.getGameController().startNewGame();
    }

    //returns true if it is alive after moving, false if not
    public boolean moveCheck(){
        previousX = headX;   previousY = headY;
        switch (currentDirection){
            case NORTH -> changeY(-SNEK_STEP);
            case SOUTH -> changeY(SNEK_STEP);
            case EAST -> changeX(SNEK_STEP);
            case WEST -> changeX(-SNEK_STEP);
        }
        if (previousDirection != currentDirection){
            snekHead.changeGameFace(currentDirection);
        }
        previousDirection = currentDirection;
        if (nextDirection != null) {
            currentDirection = nextDirection;
            nextDirection = null;
        }
        movedThisUpdate = false;
        return !isDead();
    }

    public void setCurrentDirection(Direction dir){
        if (!isOpposite(currentDirection, dir) && !movedThisUpdate) {
            previousDirection = currentDirection;
            currentDirection = dir;
            movedThisUpdate = true;
        } else {
            nextDirection = dir;
        }
    }


    public void changeX(double changeInX){
        headX += changeInX;
    }


    public void changeY(double changeInY){
        headY += changeInY;
    }

    // returns true if currDir and desired are opposite
    public boolean isOpposite(Direction currDir, Direction desiredDir){
        return (currDir == Direction.NORTH && desiredDir == Direction.SOUTH) || (currDir == Direction.WEST && desiredDir == Direction.EAST) ||
                (currDir == Direction.SOUTH && desiredDir == Direction.NORTH) || (currDir == Direction.EAST && desiredDir == Direction.WEST) ;
    }

    public boolean isDead(){
        if (isXOutOfBounds() || isYOutOfBounds() || isHeadIntersectingBody()) {
            isGameOver = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean isXOutOfBounds(){
        return headX < 0 || headX >= Constants.Window.WINDOW_WIDTH - (Constants.SnekGameConstants.SNEK_OFFSET * 2);
    }

    private boolean isYOutOfBounds(){
        return headY < 0 || headY >= Constants.Window.WINDOW_HEIGHT - (Constants.SnekGameConstants.SNEK_OFFSET * 2);
    }

    private boolean isHeadIntersectingBody(){
        return gameMap[getIndex(headY)][getIndex(headX)];
    }

    private boolean isHeadIntersectingApple(){
        return getIndex(headX) == getIndex(apple.getX()) && getIndex(headY) == getIndex(apple.getY());
    }

    public boolean gameWon(){
        if (segments.size() >= 119) {
            gameWon = true;
            return true;
        } else {
            return false;
        }
    }

    public void updateSnek(){
        snekHead.updateVisual(headX, headY);
        for (int i = 0; i < segments.size(); i++) {
            if (i == 0) {
                segments.get(i).updateVisual(previousX, previousY);
                segments.get(i).changeSegmentDirection(previousDirection);
            } else {
                SnekSegment prevSegment = segments.get(i - 1);
                segments.get(i).updateVisual(prevSegment.getPrevX(), prevSegment.getPrevY());
                segments.get(i).changeSegmentDirection(prevSegment.getPrevConnection());
            }
        }
        updateMap();
    }

    public void addSegment(){
        if (segments.isEmpty()) {
            segments.add(new SnekSegment(previousDirection, previousX, previousY));
        } else {
            SnekSegment lastSegment = segments.getLast();
            segments.add(new SnekSegment(lastSegment.getPrevConnection(), lastSegment.getPrevX(), lastSegment.getPrevY()));
        }
    }

    public void resetVariables(){
        currentDirection = previousDirection = Direction.EAST;
        segments = new ArrayList<>();
        headX = 2 * SNEK_OFFSET; headY = 2 * SNEK_OFFSET;
        snekHead = new SnekHead(currentDirection);
        snekHead.updateVisual(headX,headY);
        nextDirection = null;
        isGameOver = false;
        movedThisUpdate = false;
        gameMap = new boolean[10][12];
        apple = new Apple(getScreenCoordinates(6), getScreenCoordinates(5));
    }

    public int getIndex(double point){
        return (int)((point - (2 * SNEK_OFFSET)) / SNEK_STEP);
    }

    public double getScreenCoordinates(int index) {
        return (double)(index * SNEK_STEP) + (2 * SNEK_OFFSET);
    }

    public void updateMap(){
        gameMap[getIndex(headY)][getIndex(headX)] = true;
        if (!segments.isEmpty()) {
            SnekSegment lastSegment = segments.getLast();
            if (lastSegment.getPrevX() != 0 && lastSegment.getPrevY() != 0)
                gameMap[getIndex(lastSegment.getPrevY())][getIndex(lastSegment.getPrevX())] = false;
        } else {
            gameMap[getIndex(previousY)][getIndex(previousX)] = false;
        }
    }

    public void moveApple(){
        while (true) {
            int random = (int)(Math.random() * 120);
            int dividend = random / 12;
            if (!gameMap[dividend][random % 12]) {
                apple.updateVisual(getScreenCoordinates(random % 12), getScreenCoordinates(dividend));
                break;
            }
        }
    }

    public void restartTimer(){
        if (! (isGameOver && gameWon)) {
            snekTimer.expire();
            snekTimer = FXGL.getGameTimer().runAtInterval(() -> {
                if (moveCheck()) {
                    updateSnek();
                } else {
                    snekTimer.expire();// stops the timer
                    gameOver();
                }
                if (gameWon()) {
                    snekTimer.expire();// stops the timer
                    gameOver();
                }
                if (isHeadIntersectingApple()) {
                    moveApple();
                    addSegment();
                }
            }, Duration.seconds(1.0 / ups));
        }
    }
}
