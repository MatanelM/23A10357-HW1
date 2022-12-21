package com.example.lanesproject.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lanesproject.R;
import com.example.lanesproject.callbacks.CallBack_userProtocol;
import com.example.lanesproject.entities.Player;
import com.example.lanesproject.managers.GsonManager;

public class UserFragment extends Fragment {

    private CallBack_userProtocol callback;
    private Button[] players;
    private TextView[] scores;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        findViews(view);
        initViews();

        updateScreen();
        return view;
    }

    private void updateScreen() {
        String[] highScoreStr = new String[]{
                getActivity().getIntent().getExtras().getString("Player1"),
                getActivity().getIntent().getExtras().getString("Player2"),
                getActivity().getIntent().getExtras().getString("Player3"),
                getActivity().getIntent().getExtras().getString("Player4"),
                getActivity().getIntent().getExtras().getString("Player5"),
        };
        Player[] players = new Player[]{
                GsonManager.getInstance().fromJson(highScoreStr[0], Player.class),
                GsonManager.getInstance().fromJson(highScoreStr[1], Player.class),
                GsonManager.getInstance().fromJson(highScoreStr[2], Player.class),
                GsonManager.getInstance().fromJson(highScoreStr[3], Player.class),
                GsonManager.getInstance().fromJson(highScoreStr[4], Player.class),
        };

        for(int i =0 ; i < 5 ; i ++ ){
            String name = players[i].getName();
            int score = players[i].getScore();
            if ( score == 0 ) {
                continue;
            }
            this.players[i].setText(name);
            scores[i].setText(Integer.toString(score));
        }
    }

    private void findViews(@NonNull View view) {
        players = new Button[] {
                (Button) view.findViewById(R.id.Name1),
                (Button) view.findViewById(R.id.Name2),
                (Button) view.findViewById(R.id.Name3),
                (Button) view.findViewById(R.id.Name4),
                (Button) view.findViewById(R.id.Name5),
        };
        scores = new TextView[]{
                (TextView) view.findViewById(R.id.Score1),
                (TextView) view.findViewById(R.id.Score2),
                (TextView) view.findViewById(R.id.Score3),
                (TextView) view.findViewById(R.id.Score4),
                (TextView) view.findViewById(R.id.Score5),
        };
    }

    private void initViews(){
        for(int i = 0 ; i < 5;  i ++ ){
            players[i].setOnClickListener(v -> {
                user1Clicked();
            });
        }
    }

    private void user1Clicked() {
        if (callback != null)
            callback.user("Tom");
    }

    public void setCallback(CallBack_userProtocol callback) {
        this.callback = callback;
    }


}