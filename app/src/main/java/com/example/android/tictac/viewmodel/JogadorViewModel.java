package com.example.android.tictac.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.tictac.db.entity.Jogador;
import com.example.android.tictac.repository.Repository;

import java.util.List;


public class JogadorViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Jogador>> jogador;

    public JogadorViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.jogador = this.repository.getJogador();
    }

    public void insert(Jogador jogador) {
        this.repository.insert(jogador);
    }

    public void update(Jogador jogador) {
        this.repository.update(jogador);
    }

    public void delete(Jogador jogador) {
        this.repository.delete(jogador);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }

    public LiveData<List<Jogador>> getJogador() {
        return this.jogador;
    }
}
