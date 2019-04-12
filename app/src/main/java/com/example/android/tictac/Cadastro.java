package com.example.android.tictac;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.tictac.db.entity.Jogador;
import com.example.android.tictac.viewmodel.JogadorViewModel;

public class Cadastro extends AppCompatActivity {

    private EditText e1, e2;
    private Jogador j1, j2;
    private JogadorViewModel jogadorViewModel;
    private static String ERRO = "Insira os nomes dos jogadores!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().trim().isEmpty() || e2.getText().toString().trim().isEmpty()) {
                    return;
                }
                salvar();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

        jogadorViewModel = ViewModelProviders.of(this).get(JogadorViewModel.class);
    }

    private void salvar(){
        j1 = new Jogador(e1.getText().toString(), 0);
        j2 = new Jogador(e2.getText().toString(), 0);
        jogadorViewModel.insert(j1);
        jogadorViewModel.insert(j2);
    }
}
