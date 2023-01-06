package app.pokemon_room.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import app.pokemon_room.R;
import app.pokemon_room.model.Pokemon;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {
    private List<Pokemon> mList = new ArrayList<>();
    private Context mContext;

    public PokemonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PokemonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        holder.pokemonName.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getUrl())
                .into(holder.pokemonImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Pokemon> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public Pokemon getPokemonAt(int position){
        return mList.get(position);
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonName;
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.pokemon_imageView);
            pokemonName = itemView.findViewById(R.id.pokemon_name_textView);
        }
    }
}
