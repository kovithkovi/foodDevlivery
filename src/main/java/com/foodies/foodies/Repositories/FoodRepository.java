package com.foodies.foodies.Repositories;


import com.foodies.foodies.Entites.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<FoodEntity, String> {
}
