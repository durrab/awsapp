package com.h2app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.h2app.model.Book;
import com.h2app.model.Weather;
import com.h2app.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController(value = "/weather")
public class WeatherApiRestController {
	
	@Autowired
	WeatherService service;
	
	/*@PostMapping(value= "/weather/save")
    public Long save(final @RequestBody Weather weather) {
        service.save(weather);
        return weather.getId();
    }*/

   
    
}
