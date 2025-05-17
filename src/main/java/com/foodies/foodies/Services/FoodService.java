package com.foodies.foodies.Services;
import com.foodies.foodies.IO.FoodRequest;
import com.foodies.foodies.IO.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
public interface FoodService {
    String uploadImage(MultipartFile file);
    FoodResponse addFood(FoodRequest request, MultipartFile file);
}
