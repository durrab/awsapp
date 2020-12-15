package com.h2app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h2app.model.Weather;
import com.h2app.repository.LocationRepository;
import com.h2app.repository.WeatherRepository;



@Service
public class WeatherService {
	
	@Autowired
	WeatherRepository weatherRepository;
	
	@Autowired
	LocationRepository locationRepository;
	
	public void save(final Weather weather) {
		this.weatherRepository.save(weather);
	}
	
	public List<Weather> getAll(){
		final List<Weather> weatherList = new ArrayList<>();
		weatherRepository.findAll().forEach(weather -> weatherList.add(weather));
		return weatherList;
	}

}
