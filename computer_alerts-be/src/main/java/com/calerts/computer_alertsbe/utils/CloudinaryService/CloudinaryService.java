package com.calerts.computer_alertsbe.utils.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    // Constructor injection to use the Cloudinary bean configured in CloudinaryConfig
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Upload an image to Cloudinary
     * @param file the image file to upload
     * @return a Mono wrapping the URL of the uploaded image
     */
    public Mono<String> uploadImage(MultipartFile file) {
        return Mono.create(sink -> {
            try {
                // Upload the file to Cloudinary
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

                // Extract the URL of the uploaded image
                String imageUrl = (String) uploadResult.get("url");

                // Return the image URL to the caller
                sink.success(imageUrl);
            } catch (IOException e) {
                // Handle the exception if the upload fails
                sink.error(new RuntimeException("Error uploading image to Cloudinary", e));
            }
        });
    }
}
