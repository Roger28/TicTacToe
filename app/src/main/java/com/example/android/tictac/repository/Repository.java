package com.example.android.tictac.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.tictac.db.AppDatabase;
import com.example.android.tictac.db.dao.JogadorDao;
import com.example.android.tictac.db.entity.Jogador;

import java.util.List;


public class Repository {
    private JogadorDao jogadorDao;
    private LiveData<List<Jogador>> jogador;

    public Repository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.jogadorDao = database.jogadorDao();
        this.jogador = jogadorDao.findJogadorById();
    }

    public void insert(Jogador jogador) {
        new InsertJogadorAsyncTask(this.jogadorDao).execute(jogador);
    }

    public void update(Jogador jogador) {
        new UpdateJogadorAsyncTask(this.jogadorDao).execute(jogador);
    }

    public void delete(Jogador jogador) {
        new DeleteJogadorAsyncTask(this.jogadorDao).execute(jogador);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(this.jogadorDao).execute();
    }

    public LiveData<List<Jogador>> getJogador() {
        return this.jogador;
    }

    private static class InsertJogadorAsyncTask extends AsyncTask<Jogador, Void, Void> {

        private JogadorDao jogadorDao;

        private InsertJogadorAsyncTask(JogadorDao jogadorDao) {
            this.jogadorDao = jogadorDao;
        }

        @Override
        protected Void doInBackground(Jogador... jogador) {
            this.jogadorDao.insert(jogador[0]);
            return null;
        }
    }

    private static class UpdateJogadorAsyncTask extends AsyncTask<Jogador, Void, Void> {

        private JogadorDao jogadorDao;

        private UpdateJogadorAsyncTask(JogadorDao jogadorDao) {
            this.jogadorDao = jogadorDao;
        }

        @Override
        protected Void doInBackground(Jogador... jogador) {
            this.jogadorDao.update(jogador[0]);
            return null;
        }
    }

    private static class DeleteJogadorAsyncTask extends AsyncTask<Jogador, Void, Void> {

        private JogadorDao jogadorDao;

        private DeleteJogadorAsyncTask(JogadorDao jogadorDao) {
            this.jogadorDao = jogadorDao;
        }

        @Override
        protected Void doInBackground(Jogador... jogador) {
            this.jogadorDao.delete(jogador[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private JogadorDao jogadorDao;

        private DeleteAllAsyncTask(JogadorDao jogadorDao) {
            this.jogadorDao = jogadorDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.jogadorDao.deleteAll();
            return null;
        }
    }
}

