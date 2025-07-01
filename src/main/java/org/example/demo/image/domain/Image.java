package org.example.demo.image.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.common.time.port.DateHolder;
import org.example.demo.common.util.port.UuidHolder;

@Getter
public class Image {

    private final Long id;
    private final String filename;
    private final String fileUrl;
    private final Long uploader;
    private final String createAt;
    private final String modifiedAt;

    @Builder
    public Image(Long id, String filename, String fileUrl, Long uploader, String createAt, String modifiedAt) {
        this.id = id;
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.uploader = uploader;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static Image create(long userId, String filename) {
        return Image.builder()
                .filename(filename)
                .uploader(userId)
                .build();
    }

    public Image setFileUrlAfterUpload(String fileUrl) {
        return Image.builder()
                .id(id)
                .filename(filename)
                .fileUrl(fileUrl)
                .uploader(uploader)
                .createAt(createAt)
                .build();
    }

    public Image readyForUpload(DateHolder dateHolder, UuidHolder uuidHolder) {
        return Image.builder()
                .filename(uuidHolder.getUuid() + "_" + filename)
                .uploader(uploader)
                .createAt(dateHolder.now())
                .build();
    }
}
