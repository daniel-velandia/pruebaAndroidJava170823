package com.example.movies.ui.viewModel;

import android.os.Handler;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.model.Movie;
import com.example.movies.model.MovieProvider;

import java.util.List;

public class MainViewModel {

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final Handler handler = new Handler();

    public MainViewModel() {
        this.loading.postValue(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                movies.postValue(MovieProvider.getMovies());
            }
        }, 2000);
    }

    public void loadMovies() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                movies.postValue(MovieProvider.getMovies());
            }
        }, 2000);
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies;
    }

    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading.postValue(loading);
    }
}
