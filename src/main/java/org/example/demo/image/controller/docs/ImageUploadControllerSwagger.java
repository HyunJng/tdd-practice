package org.example.demo.image.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.auth.domain.LoginUser;
import org.example.demo.auth.service.port.CurrentUser;
import org.example.demo.image.controller.dto.ImageUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이미지 업로드")
public interface ImageUploadControllerSwagger {


    @Operation(
            summary = "이미지 사전 업로드",
            description = "게시글에 사진을 올리기 전에 사전 업로드하여 URL을 받는다",
            security = @SecurityRequirement(name = "JWT")
    )
    ResponseEntity<ImageUpload.Response> imageUpload(@Parameter(hidden = true) @CurrentUser LoginUser loginUser,
                                                     @RequestParam MultipartFile file);

}
