package org.example.demo.image.infrastructure.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.cloudfront")
public class CloudFrontProperties {
    private String domain;
}
