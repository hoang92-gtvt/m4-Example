package com.codegym.controller;

import com.codegym.model.City;
import com.codegym.model.Country;
import com.codegym.service.city.ICityService;
import com.codegym.service.countries.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/city")
public class CityController {
    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

    @ModelAttribute("countries")
    public Iterable<Country> countries() {
        return countryService.findAll();
    }

    @GetMapping("/list")
    public ModelAndView showListCity() {
        Iterable<City> cities = cityService.findAll();
        ModelAndView modelAndView = new ModelAndView("/cities/list");
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateCity() {
        ModelAndView modelAndView = new ModelAndView("/cities/create");
        modelAndView.addObject("city", new City());
        return modelAndView;
    }


    @PostMapping("/create")
    public ModelAndView saveCity( @ModelAttribute("city") City city) {

        try{
           cityService.save(city);
           Iterable<City> cities = cityService.findAll();
           ModelAndView modelAndView = new ModelAndView("/cities/list");
           modelAndView.addObject("cities", cities);
           modelAndView.addObject("message", "City is added");
           return modelAndView;

       }catch(Exception e) {

           ModelAndView modelAndView = new ModelAndView("/cities/create");
           modelAndView.addObject("city", city);
           modelAndView.addObject("message", "The action is fail");
           return modelAndView;
       }
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<City> cityOptional = cityService.findById(id);
        if (cityOptional.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/cities/edit");
            modelAndView.addObject("city", cityOptional.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateCity(@ModelAttribute("city") City city) {
        try{
            cityService.save(city);
            Iterable<City> cities = cityService.findAll();
            ModelAndView modelAndView = new ModelAndView("/cities/list");
            modelAndView.addObject("cities", cities);
            modelAndView.addObject("message", "City is update");
            return modelAndView;

        }catch(Exception e) {

            ModelAndView modelAndView = new ModelAndView("/cities/create");
            modelAndView.addObject("city", city);
            modelAndView.addObject("message", "The action is fail");
            return modelAndView;
        }
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detailForm(@PathVariable Long id) {
        Optional<City> city = cityService.findById(id);
        if (city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/cities/detail");
            modelAndView.addObject("city", city.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }


    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        Optional<City> city = cityService.findById(id);
        if (city.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/cities/delete");
            modelAndView.addObject("city", city.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCity(@ModelAttribute("city") City city) {
        cityService.delete(city.getId());
        return "redirect:/city/list";
    }



}
