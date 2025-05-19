package com.foodies.foodies.Services;
import com.cloudinary.Cloudinary;
import com.foodies.foodies.Entites.FoodEntity;
import com.foodies.foodies.IO.FoodRequest;
import com.foodies.foodies.IO.FoodResponse;
import com.foodies.foodies.Repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FoodServiceImplementatioin implements FoodService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private FoodRepository foodRepo;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String publicId = UUID.randomUUID().toString();
            Map m = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("public_id", publicId)
            );

            if (m.get("secure_url") != null) {
                return m.get("secure_url").toString();
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Image upload failed");
            }
        } catch (Exception e) {
            System.out.println("Error uploading image: " + e.getMessage());
        }
        return null;
    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        FoodEntity newFoodEntity = convertToEntity(request);
        String imageUrl = uploadImage(file);
        newFoodEntity.setImageUrl(imageUrl);
        return converttoResponse(foodRepo.save(newFoodEntity));
    }

    @Override
    public List<FoodResponse> getAllFood() {
        return foodRepo.findAll().stream()
                .map(this::converttoResponse)
                .toList();
    }

    @Override
    public FoodResponse getFood(String id) {
        if (!foodRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found");
        }
        return converttoResponse(foodRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found")));
    }
    @Override
    public boolean deleteFile(String filename) {
        String originalFilenName = filename.substring(filename.lastIndexOf('/') + 1);
        originalFilenName = originalFilenName.replaceAll("(\\.jpg)+$", ".jpg");
        System.out.println(originalFilenName);
        try {
            Map m = cloudinary.uploader().destroy(originalFilenName, Map.of("invalidate", true));
            return m.get("result").equals("ok");
        } catch (Exception e) {
            System.out.println("Error deleting image: " + e.getMessage());
        }
        return false;
    }

    @Override
    public String deleteFood(String id) {
        if (!foodRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food not found");
        }
        FoodResponse food = getFood(id);
            foodRepo.deleteById(id);
            return "Food deleted successfully";
    }

    private FoodEntity convertToEntity(FoodRequest request){
        FoodEntity food = new FoodEntity();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setCategory(request.getCategory());
        food.setPrice(request.getPrice());
        return food;
    }
    private FoodResponse converttoResponse(FoodEntity food){
        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setId(food.getId());
        foodResponse.setName(food.getName());
        foodResponse.setDescription(food.getDescription());
        foodResponse.setCategory(food.getCategory());
        foodResponse.setPrice(food.getPrice());
        foodResponse.setImageUrl(food.getImageUrl());
        return foodResponse;
    }
}
