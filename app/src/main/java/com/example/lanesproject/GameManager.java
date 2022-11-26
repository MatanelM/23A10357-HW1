package com.example.lanesproject;

public class GameManager {

    private int DELAY = 1000;
    private int SPAWN_CYCLE = 1;

    private int currentCycle = 0;
    private int location = 0;
    private int lives = 3;

    private int[][] asteroidsMat = {
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
            {0,0,0},
    };


    public GameManager() {
    }

    public boolean isHit() {
        boolean hitLeft = this.location == -1 && this.asteroidsMat[6][0] == 1;
        boolean hitRight = this.location == 1 && this.asteroidsMat[6][2] == 1;
        boolean hitCenter = this.location == 0 && this.asteroidsMat[6][1] == 1;

        return hitLeft || hitCenter || hitRight;
    }

    public int getLives() { return this.lives; }

    public void hitAsteroid() { if ( this.lives != 0) this.lives -= 1; }

    public boolean isGameLost() { return this.lives == 0; }

    public void changeLocation(int toward) {
        if ( location == -1 && toward == -1 ) return;
        if ( location == 1 && toward == 1 ) return;
        location += toward;
    }

    public int getLocation() { return this.location; }

    public int getDelay() {
        return DELAY;
    }

    public void setDelay(int delay) {
        this.DELAY = delay;
    }

    public void makeProgress(){
        for (int i = this.asteroidsMat.length - 1; i > 0; i--) {
            for (int j = 0; j < 3; j++) {
                this.asteroidsMat[i][j] = this.asteroidsMat[i-1][j];
            }
        }
        this.asteroidsMat[0][0] = 0;
        this.asteroidsMat[0][1] = 0;
        this.asteroidsMat[0][2] = 0;

        if ( this.currentCycle == this.SPAWN_CYCLE ) {
            this.currentCycle = 0;
        }else { this.currentCycle ++; }
    }
    public boolean hasSpawn() { return this.currentCycle == 0; }
    public void spawnAsteroid(){
        int laneNum = (int)(Math.random() * 3);
        this.asteroidsMat[0][laneNum] = 1;
    }

    public int[][] getMat(){
        return this.asteroidsMat;
    }

    public void printMat(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.asteroidsMat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }
}
