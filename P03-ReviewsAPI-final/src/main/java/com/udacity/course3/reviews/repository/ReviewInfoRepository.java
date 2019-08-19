package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.mongo.ReviewInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewInfoRepository extends MongoRepository<ReviewInfo, String> {

}
