package com.example.android.tictac.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.tictac.db.entity.Jogador;

import java.util.List;

@Dao
public interface JogadorDao {

    @Insert
    void insert(Jogador jogador);

    @Update
    void update(Jogador jogador);

    @Delete
    void delete(Jogador jogador);

    @Query("SELECT * FROM jogador")
    LiveData<List<Jogador>> findJogadorById();

    @Query("DELETE FROM jogador")
    void deleteAll();
}
