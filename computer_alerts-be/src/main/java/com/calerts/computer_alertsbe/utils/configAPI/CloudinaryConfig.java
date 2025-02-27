package com.calerts.computer_alertsbe.utils.configAPI;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String CLOUDINARY_SECRET_URL;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(CLOUDINARY_SECRET_URL);

    }
}

