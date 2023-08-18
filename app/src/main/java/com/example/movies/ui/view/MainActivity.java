package com.example.movies.ui.view;

import static com.example.movies.ui.view.DetailActivity.MOVIE_EXTRA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.movies.adapter.MovieAdapter;
import com.example.movies.databinding.ActivityMainBinding;
import com.example.movies.events.OnClickListener;
import com.example.movies.model.Movie;
import com.example.movies.ui.viewModel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final MainViewModel mainViewModel = new MainViewModel();
    private ActivityMainBinding binding;
    private MovieAdapter adapter;
    private final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onChangeLoading();
        initUI();
    }

    public void onChangeLoading() {
        mainViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                binding.clLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.recyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initUI() {
        onChangeMovies();
        onClickRefreshListener();
    }

    private void onChangeMovies() {
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mainViewModel.setLoading(false);
                if(firstLoad) initList(movies); else refreshList(movies);
            }
        });
    }

    private void initList(List<Movie> movies) {
        configAdapter(movies);
        configRecyclerview();
        firstLoad = false;
    }

    private void refreshList(List<Movie> movies) {
        adapter.setMovies(movies);
        layoutManager.scrollToPosition(0);
    }

    private void configAdapter(List<Movie> movies) {
        adapter = new MovieAdapter(movies, new OnClickListener() {
            @Override
            public void onClickListener(Movie movie) {
                goDetailView(movie);
            }
        });
    }

    private void configRecyclerview() {
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    private void goDetailView(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        startActivity(intent);

    }

    private void onClickRefreshListener() {
        binding.iBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.setLoading(true);
                mainViewModel.loadMovies();
            }
        });
    }
}