package com.alta.e_commerce.controllers;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.services.CloudinaryService;
import com.alta.e_commerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
public class CloudinaryController {
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    UserService userService;

    @PostMapping("/users/photo")
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        // get userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("image is not valid!", HttpStatus.BAD_REQUEST);
        }

        // get photo's url
        Map result = cloudinaryService.upload(multipartFile);
        String urlPhoto = (String) result.get("url");
        System.out.println("url photo: " + urlPhoto);

        // setPhoto to user
        genzaiNoShiyousha.setPhoto(urlPhoto);

        // save to db
        userService.savePhoto(genzaiNoShiyousha);
        
        return new ResponseEntity<>("success upload photo", HttpStatus.OK);
    }
}