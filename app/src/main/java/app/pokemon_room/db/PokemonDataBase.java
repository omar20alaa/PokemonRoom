package app.pokemon_room.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.pokemon_room.model.Pokemon;

@Database(entities = Pokemon.class , version = 2 , exportSchema = false)
public abstract class PokemonDataBase extends RoomDatabase {

    public abstract PokemonDao pokemonDao();

}
