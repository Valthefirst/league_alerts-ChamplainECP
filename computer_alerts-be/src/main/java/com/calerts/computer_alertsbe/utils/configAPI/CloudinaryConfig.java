
package com.calerts.computer_alertsbe.utils.configAPI;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", System.getenv("ddihej6gw"),
                "api_key", System.getenv("774976436626872"),
                "api_secret", System.getenv("5PuaO04mxzwACVBnxmwrYxOBT0A")
        ));
    }
}

