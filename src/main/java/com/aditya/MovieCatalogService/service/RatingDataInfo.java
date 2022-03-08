package com.aditya.MovieCatalogService.service;

import com.aditya.MovieCatalogService.model.Rating;
import com.aditya.MovieCatalogService.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RatingDataInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatingInfo")
    public UserRating getUserRatingInfo(String userId){
         return restTemplate.getForObject("http://rating-data-service/ratingsdata/user/"+userId,UserRating.class);
    }

    public UserRating getFallbackUserRatingInfo(String userId){
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRatings(Arrays.asList(new Rating("100",1)));
        return userRating;
    }
}
