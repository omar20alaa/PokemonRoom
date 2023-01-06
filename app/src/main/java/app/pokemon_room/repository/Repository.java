package app.pokemon_room.repository;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.pokemon_room.db.PokemonDao;
import app.pokemon_room.model.Pokemon;
import app.pokemon_room.model.PokemonResponse;
import app.pokemon_room.network.PokemonApiService;
import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;

    @Inject
    public Repository(PokemonApiService pokemonApiService , PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons() {
        return pokemonApiService.getPokemons();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        pokemonDao.deletePokemon(pokemonName);
    }

    public LiveData<List<Pokemon>> getFavPokemon() {
       return pokemonDao.getPokemons();
    }

}
