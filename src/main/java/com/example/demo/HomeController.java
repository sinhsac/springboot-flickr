package com.example.demo;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    Flickr flickr;

    @GetMapping
    public String home(@RequestParam(value = "userId", defaultValue = "100906270@N03") String userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "100") Integer size
                       ) {
        try {
            PhotoList<Photo> photoList = flickr.getPeopleInterface().getPublicPhotos(userId, size, page);
            System.out.println("debug");
        } catch (FlickrException e) {
            e.printStackTrace();
        }
        return "home";
    }

}
