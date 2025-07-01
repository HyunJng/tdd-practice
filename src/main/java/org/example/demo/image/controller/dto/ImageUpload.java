package org.example.demo.image.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.example.demo.image.domain.Image;

public class ImageUpload {

    @Data
    public static class Response {
        Long id;
        String filename;
        String fileUrl;

        @Builder
        public Response(String fileUrl, Long id, String filename) {
            this.fileUrl = fileUrl;
            this.id = id;
            this.filename = filename;
        }

        public static Response from(Image image) {
            return Response.builder()
                    .id(image.getId())
                    .filename(image.getFilename())
                    .fileUrl(image.getFileUrl())
                    .build();
        }
    }
}
