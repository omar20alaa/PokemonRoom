package app.pokemon_room.view_model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.pokemon_room.model.Pokemon;
import app.pokemon_room.repository.Repository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PokemonViewModel extends ViewModel {

    private Repository repository;

    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

   MutableLiveData<ArrayList<Pokemon>> pokemon_list = new MutableLiveData<>();
    private LiveData<List<Pokemon>> favList = null;

    public MutableLiveData<ArrayList<Pokemon>> getPokemon_list() {
        return pokemon_list;
    }

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    @SuppressLint("CheckResult")
    public void getPokemons() {
        repository.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(pokemonResponse -> {
                    ArrayList<Pokemon> list = pokemonResponse.getResults();
                    for (Pokemon pokemon : list) {
                        String url = pokemon.getUrl();
                        String[] pokemonIndex = url.split("/");
                        pokemon.setUrl("https://pokeres.bastionbot.org/images/pokemon/" + pokemonIndex[pokemonIndex.length - 1] + ".png");
                    }
                    return list;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemon_list.setValue(result),
                        error -> Log.e("viwModel", error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletePokemon(String pokemonName) {
        repository.deletePokemon(pokemonName);
    }

    public void getFavPokemon() {
        favList = repository.getFavPokemon();
    }

}
