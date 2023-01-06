package app.pokemon_room.network;

import app.pokemon_room.model.PokemonResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {


    @GET("pokemon")
    Observable<PokemonResponse> getPokemons();

}
