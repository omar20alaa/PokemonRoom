package app.pokemon_room.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.pokemon_room.R;
import app.pokemon_room.adapter.PokemonAdapter;
import app.pokemon_room.model.Pokemon;
import app.pokemon_room.view_model.PokemonViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private RecyclerView recyclerView;
    private Button to_home_button;
    private PokemonAdapter pokemonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initViews();
        initViewModel();
        initRv();
        setupSwipe();
        eventClick();
    }

    private void eventClick() {
        to_home_button.setOnClickListener(view ->
                startActivity(new Intent(FavoriteActivity.this , MainActivity.class)));
    }

    private void initRv() {
        pokemonAdapter = new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getFavPokemon();
        viewModel.getFavList().observe(this, pokemons -> pokemonAdapter.setList(pokemons));
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
                viewModel.deletePokemon(swipedPokemon.getName());
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(FavoriteActivity.this, "Pokemon Removed From DataBase", Toast.LENGTH_SHORT).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.fav_recyclerView);
        to_home_button = findViewById(R.id.to_home_button);
    }
}