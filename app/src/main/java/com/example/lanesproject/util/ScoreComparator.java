package com.example.lanesproject.util;

import com.example.lanesproject.entities.Player;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player a, Player b) {

        if (a.getScore() > b.getScore())
            return -1;
        if (a.getScore() < b.getScore())
            return 1;
        return 0;
    }

}
