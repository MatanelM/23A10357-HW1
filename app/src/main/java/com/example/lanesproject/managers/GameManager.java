package com.example.lanesproject.managers;

import com.example.lanesproject.entities.Player;
import com.example.lanesproject.util.ScoreComparator;

import java.util.ArrayList;
import java.util.Collections;

public class GameManager {

    private static int HIGHSCORE_SIZE = 5;
    private static int DELAY = 500;
    private static int SPAWN_CYCLE = 3;
    private static int MAX = 1;

    private int currentCycle = 0;
    private int location = 0;
    private int lives = 3;
    private boolean isLevelEasy = false;

    private ArrayList<Player> highScore= new ArrayList<Player>();;

    private boolean isPaused = false;
    private int score = 0;
    private int[][] asteroidsMat = {
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0,0},
    };

    public GameManager(boolean isLevelEasy) {
        this.isLevelEasy = isLevelEasy;
        if ( !isLevelEasy ){
            MAX = 2;
            SPAWN_CYCLE = 1;
        }
        for (int i = 0; i < 5; i++) {
            highScore.add(new Player("Hello", 0, 0.0, 0.0));
        }
    }

    public GameManager(){
        // TODO - init from SP
    }

    public boolean isHit() {
        boolean hit = false;
        for(int i = 0, j = -2; i < this.asteroidsMat[0].length; i++, j++){
            hit = hit || (this.location == j && this.asteroidsMat[this.asteroidsMat.length-1][i] == 1);
        }
        return hit;
    }

    public ArrayList<Player> gethighScore() {
        return highScore;
    }

    public void sethighScore(ArrayList<Player> highScore) {
        this.highScore = highScore;
    }

    public int getLives() { return this.lives; }

    public void hitAsteroid() { if ( this.lives != 0) this.lives -= 1; }

    public boolean isGameLost() { return this.lives == 0; }

    public void changeLocation(int toward) {
        if ( location == -2 && toward == -1 ) return;
        if ( location == 2 && toward == 1 ) return;
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
        if ( this.isGameLost()) return;
        this.score ++;
        for (int i = this.asteroidsMat.length - 1; i > 0; i--) {
            for (int j = 0; j < asteroidsMat[0].length; j++) {
                this.asteroidsMat[i][j] = this.asteroidsMat[i-1][j];
            }
        }
        for(int i = 0 ; i < asteroidsMat[0].length ; i ++ ){
            this.asteroidsMat[0][i] = 0;
        }

        if ( this.currentCycle == this.SPAWN_CYCLE ) {
            this.currentCycle = 0;
        }else { this.currentCycle ++; }
    }
    public boolean hasSpawn() { return this.currentCycle == 0; }
    public void spawnAsteroid(){
        for (int i = 0; i < MAX; i++) {
            int laneNum = (int)(Math.random() * asteroidsMat[0].length);
            if (this.asteroidsMat[0][laneNum] == 1){
                i--;
            }else{
                this.asteroidsMat[0][laneNum] = 1;
            }
        }

    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public int[][] getMat(){
        return this.asteroidsMat;
    }
    public void setMat(int[][] mat) { this.asteroidsMat = mat; }

    public void printMat(){
        for (int i = 0; i < asteroidsMat.length; i++) {
            for (int j = 0; j < asteroidsMat[0].length; j++) {
                System.out.print(this.asteroidsMat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    public String matToString(){
        String matStr = "[";
        for (int i = 0; i < asteroidsMat.length; i++) {
            matStr += "[";
            for (int j = 0; j < asteroidsMat[0].length; j++) {

                matStr += this.asteroidsMat[i][j];
                if ( j != asteroidsMat[0].length - 1)
                    matStr += ",";
            }
            matStr += "]";
            if ( i != asteroidsMat.length - 1)
                matStr += ",";

        }
        matStr += "]";
        return matStr;
    }

    public boolean isInHighScore(Player player){
        if (this.highScore.size() <= HIGHSCORE_SIZE){
            return true;
        }

        if (player.getScore() > this.highScore.get(this.highScore.size()-1).getScore()){
            return true;
        }
        return false;
    }

    public void addToHighScore(Player player){
        this.highScore.set(HIGHSCORE_SIZE-1, player);
        Collections.sort(this.highScore, new ScoreComparator());
    }

    @Override
    public String toString() {
        String highScoreStr = "[";
        for (int i = 0; i < this.highScore.size(); i++) {
            highScoreStr += this.highScore.get(i).toString();
            if ( i != this.highScore.size() - 1) {
                highScoreStr+= ",";
            }
        }

        highScoreStr += "]";
        return "GameManager{" +
                "isLevelEasy=" + this.isLevelEasy +
                ", asteroidsMat=" + this.matToString() +
                ", lives=" + this.lives +
                ", currentCycle=" +this.currentCycle +
                ", location=" +this.location +
                ", highScore=" + highScoreStr +
                "}";
    }

    public int getScore() {
        return this.score;
    }
    public void addToScore(int amount){
        this.score += amount;
    }
    public void addToLife(){
        this.lives ++;
    }
}
