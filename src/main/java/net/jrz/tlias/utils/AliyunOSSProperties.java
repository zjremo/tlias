package net.jrz.tlias.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aliyun.oss")
public class AliyunOSSProperties {
    // Spring中有松散绑定，比如access-key-id会自动映射为accessKeyId
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String bucketName;
    private String region;
}
