
package com.calerts.computer_alertsbe.utils.configAPI;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        String dotenvPath = System.getenv("DOTENV_PATH");

        Dotenv dotenv = Dotenv.configure()
                .directory(dotenvPath != null ? dotenvPath : ".")
                .load();
        return new Cloudinary(dotenv.get("CLOUDINARY_URL"));

    }
}

