package app.pokemon_room.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import app.pokemon_room.model.Pokemon;

@Dao
public interface PokemonDao {

    @Insert
    public void insertPokemon(Pokemon pokemon);

    @Query("DELETE FROM fav_table WHERE name =:pokemonName")
    public void deletePokemon(String pokemonName);

    @Query("SELECT * FROM fav_table")
    public LiveData<List<Pokemon>> getPokemons();

}
