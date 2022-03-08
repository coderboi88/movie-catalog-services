package com.aditya.MovieCatalogService.resources;

import com.aditya.MovieCatalogService.model.CatalogItem;
import com.aditya.MovieCatalogService.model.Movie;
import com.aditya.MovieCatalogService.model.UserRating;
import com.aditya.MovieCatalogService.service.MovieInfo;
import com.aditya.MovieCatalogService.service.RatingDataInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RatingDataInfo ratingDataInfo;
    @Autowired
    private MovieInfo movieInfo;

    @GetMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        //get all rated movie ids
        UserRating userRating = ratingDataInfo.getUserRatingInfo(userId);
        return userRating.getUserRatings().stream().map(rating ->movieInfo.getMovieInfo(rating))
                .collect(Collectors.toList());
   }

    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Arrays.asList(new CatalogItem("NO Movie","",0));
    }

    @PostMapping("/movie")
    public String postCatalog(@RequestBody Movie movie){
        String ans = restTemplate.postForObject("http://movie-info-service/movies",movie,String.class);
        return ans;
    }

}
/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/
