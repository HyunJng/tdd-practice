package org.example.demo.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo.auth.domain.LoginUser;
import org.example.demo.auth.service.port.CurrentUser;
import org.example.demo.image.controller.docs.ImageUploadControllerSwagger;
import org.example.demo.image.controller.dto.ImageUpload;
import org.example.demo.image.domain.Image;
import org.example.demo.image.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageUploadController implements ImageUploadControllerSwagger {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageUpload.Response> imageUpload(@CurrentUser LoginUser loginUser,
                                                   @RequestParam MultipartFile file) {
        Image upload = imageService.upload(file, Image.create(loginUser.getId(), file.getOriginalFilename()));
        ImageUpload.Response from = ImageUpload.Response.from(upload);
        return ResponseEntity.ok(from);
    }

}
