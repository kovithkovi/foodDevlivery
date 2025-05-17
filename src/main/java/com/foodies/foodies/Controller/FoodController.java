package com.foodies.foodies.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodies.foodies.IO.FoodRequest;
import com.foodies.foodies.IO.FoodResponse;
import com.foodies.foodies.Services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString, @RequestPart("image") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest foodResquest = null;
        try{
            foodResquest = objectMapper.readValue(foodString, FoodRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON food format", e);
        }
        FoodResponse foodResponse = foodService.addFood(foodResquest, file);
        return foodResponse;
    }
}
