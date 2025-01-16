package com.calerts.computer_alertsbe.utils.CloudinaryService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.http.codec.multipart.FilePart;
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


    public Mono<String> uploadImage(FilePart filePart) {
        return filePart.content()
                .flatMap(dataBuffer -> Mono.just(dataBuffer.asByteBuffer().array()))
                .reduce(new byte[0], (existing, next) -> {
                    byte[] combined = new byte[existing.length + next.length];
                    System.arraycopy(existing, 0, combined, 0, existing.length);
                    System.arraycopy(next, 0, combined, existing.length, next.length);
                    return combined;
                })
                .flatMap(bytes -> Mono.create(sink -> {
                    try {
                        Map<String, Object> uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
                        String imageUrl = (String) uploadResult.get("url");
                        sink.success(imageUrl);
                    } catch (IOException e) {
                        sink.error(new RuntimeException("Error uploading image to Cloudinary", e));
                    }
                }));
    }

//    public Mono<String> extractImageUrl(FilePart filePart){
//        try{
//            Map<String, Object> uploadImage = cloudinary.uploader().upload(filePart.content(), ObjectUtils.emptyMap());
//            String imageUrl = (String) uploadImage.get("url");
//            return Mono.just(imageUrl);
//        }catch (IOException e){
//            return Mono.error(new RuntimeException("Error uploading image to Cloudinary", e));
//        }
//    }
}
