package com.aditya.MovieCatalogService.service;

import com.aditya.MovieCatalogService.model.CatalogItem;
import com.aditya.MovieCatalogService.model.Movie;
import com.aditya.MovieCatalogService.model.Rating;
import com.aditya.MovieCatalogService.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackMovieInfo")
    public CatalogItem getMovieInfo(Rating rating){

        //For each movie id ,call movie info service and get details
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
        //Put them all together
        return new CatalogItem(movie.getName(), "Desc.", rating.getRating());
    }

    public CatalogItem getFallbackMovieInfo(Rating rating){
        return new CatalogItem("Movie Name not found","",rating.getRating());
    }
}
