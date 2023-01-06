package app.pokemon_room.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import app.pokemon_room.R;
import app.pokemon_room.adapter.PokemonAdapter;
import app.pokemon_room.model.Pokemon;
import app.pokemon_room.view_model.PokemonViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private RecyclerView recyclerView;
    private Button to_fav_button;
    private PokemonAdapter pokemonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initRv();
        initViewModel();
        setupSwipe();
        eventClick();
    }

    private void initViews() {
        to_fav_button = findViewById(R.id.to_fav_button);
        recyclerView = findViewById(R.id.pokemon_recyclerView);
    }

    private void eventClick() {
        to_fav_button.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this , FavoriteActivity.class)));
    }

    private void setupSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPokemonPosition = viewHolder.getAdapterPosition();
                Pokemon swipedPokemon = pokemonAdapter.getPokemonAt(swipedPokemonPosition);
                viewModel.insertPokemon(swipedPokemon);
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Pokemon Saved To DataBase", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemons();
        viewModel.getPokemon_list().observe(this, pokemons -> pokemonAdapter.setList(pokemons));
    }

    private void initRv() {
        pokemonAdapter = new PokemonAdapter(this );
        recyclerView.setAdapter(pokemonAdapter);
    }
}