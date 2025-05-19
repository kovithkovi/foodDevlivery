package com.foodies.foodies.Controller;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodies.foodies.IO.FoodRequest;
import com.foodies.foodies.IO.FoodResponse;
import com.foodies.foodies.Services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public List<FoodResponse> getAllFood() {
        return foodService.getAllFood();
    }

    @GetMapping("/{id}")
    public FoodResponse getFood(@PathVariable String id) {
        return foodService.getFood(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable String id) {
        String status = foodService.deleteFood(id);
        return ResponseEntity.ok(status);
    }
}
