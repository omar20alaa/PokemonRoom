package app.pokemon_room.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import app.pokemon_room.db.PokemonDao;
import app.pokemon_room.db.PokemonDataBase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {


    @Provides
    @Singleton
    public static PokemonDataBase provideDatabase(Application application) {
        return Room.databaseBuilder(application, PokemonDataBase.class , "favorite_database_file")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDataBase pokemonDataBase) {
        return pokemonDataBase.pokemonDao();
    }


}
