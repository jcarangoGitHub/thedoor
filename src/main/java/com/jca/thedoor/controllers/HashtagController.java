package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.Hashtag;
import com.jca.thedoor.payload.DeleteObjectsRequest;
import com.jca.thedoor.service.impl.HashtagMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hashtag")
public class HashtagController {
    private final HashtagMongoService hashtagMongoService;

    public HashtagController(HashtagMongoService hashTagMongoService) {
        hashtagMongoService = hashTagMongoService;
    }

    @PostMapping("/hashtag")
    public ResponseEntity<Hashtag> saveHashtag(@Valid @RequestBody Hashtag hashtag) {
        return hashtagMongoService.saveHashtag(hashtag);
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Hashtag>> findByGroup(@Valid @RequestBody Hashtag hashtag) {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userlogged = (User) authentication.getPrincipal();
        System.out.println(userlogged);*/
        return hashtagMongoService.findAllHashtagsByGroup(hashtag.getUser(), hashtag.getGroup());
    }

    @DeleteMapping("hashtag")
    public ResponseEntity<Integer> deleteByNamesAndUserAndGroup(@Valid @RequestBody DeleteObjectsRequest request) {
        hashtagMongoService.deleteAllByNameAndUserAndGroup(request.getObjects(), request.getUser(), request.getGroup());
        return ResponseEntity.ok(request.getObjects().length);
    }
}
