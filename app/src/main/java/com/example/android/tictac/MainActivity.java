package com.example.android.tictac;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tictac.db.entity.Jogador;
import com.example.android.tictac.viewmodel.JogadorViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private JogadorViewModel mViewModel;
    private List<Jogador> jogadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jogadores = new ArrayList<>();
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        mViewModel = ViewModelProviders.of(this).get(JogadorViewModel.class);
        mViewModel.getJogador().observe(this, new Observer<List<Jogador>>() {
            @Override
            public void onChanged(@Nullable List<Jogador> jogador) {
                jogadores = jogador;
                if(jogador.isEmpty()){
                    textViewPlayer1.setText("Player 1: " + jogador.get(0).getPontuacao());
                    textViewPlayer2.setText("Player 2: " + jogador.get(1).getPontuacao());
                }else {
                    textViewPlayer1.setText(jogador.get(0).getNome() + ": " + jogador.get(0).getPontuacao());
                    textViewPlayer2.setText(jogador.get(1).getNome() + ": " + jogador.get(1).getPontuacao());
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, jogadores.get(0).getNome() + " wins!", Toast.LENGTH_LONG).show();
        Jogador jogador = new Jogador(jogadores.get(0).getNome(), player1Points);
        jogador.setId(1);
        mViewModel.update(jogador);
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, jogadores.get(1).getNome() + " wins!", Toast.LENGTH_LONG).show();
        Jogador jogador = new Jogador(jogadores.get(1).getNome(), player2Points);
        jogador.setId(2);
        mViewModel.update(jogador);
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Deu Empate", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(jogadores.get(0).getNome() + ": " + jogadores.get(0).getPontuacao());
        textViewPlayer2.setText(jogadores.get(1).getNome() + ": " + jogadores.get(1).getPontuacao());
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
//        player1Points = 0;
//        player2Points = 0;
        jogadores.get(0).setPontuacao(0);
        jogadores.get(1).setPontuacao(0);
        mViewModel.update(jogadores.get(0));
        mViewModel.update(jogadores.get(1));
        updatePointsText();
        resetBoard();

    }

}
