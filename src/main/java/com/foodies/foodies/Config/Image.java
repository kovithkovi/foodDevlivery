package com.foodies.foodies.Config;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
@Configuration
public class Image {
    @Bean
    public com.cloudinary.Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dbh79kfux");
        config.put("api_key", "595585441885535");
        config.put("api_secret", "iaCANXa2S7bYybfBYLTSA5dNq0k");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
