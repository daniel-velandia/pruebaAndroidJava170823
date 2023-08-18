package com.example.movies.ui.viewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.movies.model.Movie;

public class DetailViewModel {
    private MutableLiveData<Movie> movie = new MutableLiveData<>();

    public DetailViewModel(Movie movie) {
        this.movie.postValue(movie);
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }
}
