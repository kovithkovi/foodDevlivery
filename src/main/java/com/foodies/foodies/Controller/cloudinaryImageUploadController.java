package com.foodies.foodies.Controller;

import com.foodies.foodies.Services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


@RestController
@RequestMapping("/upload")
public class cloudinaryImageUploadController {
    @Autowired
    private FoodService foodService;

    @PostMapping()
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        String data = foodService.uploadImage(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


}
