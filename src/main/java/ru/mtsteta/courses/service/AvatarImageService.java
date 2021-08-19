package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtsteta.courses.dao.AvatarImageRepository;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.AvatarImage;
import ru.mtsteta.courses.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarImageService {
    private final AvatarImageRepository avatarImageRepository;
    private final UserRepository userRepository;

    @Value("${file.storage.path}")
    private String path;

    @Transactional
    public void save(String username, String contentType, InputStream inputStream) {
        Optional<AvatarImage> imageOptional = avatarImageRepository.findAvatarImageByUser_Username(username);

        String filename;
        AvatarImage avatarImage;
        if (imageOptional.isEmpty()) {
            filename = UUID.randomUUID().toString();
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(IllegalArgumentException::new);
            avatarImage = new AvatarImage(null, contentType, filename, user);
        } else {
            avatarImage = imageOptional.get();
            filename = avatarImage.getFilename();
            avatarImage.setContentType(contentType);
        }

        avatarImageRepository.save(avatarImage);

        try (OutputStream outputStream = Files.newOutputStream(Path.of(path, filename), CREATE, WRITE, TRUNCATE_EXISTING)) {
            inputStream.transferTo(outputStream);
        } catch (Exception ex) {
            log.error("Can't write to file {}", filename, ex);
            throw new IllegalStateException(ex);
        }
    }

    public Optional<String> getContentType(String username) {
        return avatarImageRepository.findAvatarImageByUser_Username(username)
                .map(AvatarImage::getContentType);
    }

    public Optional<byte[]> getAvatarImageByUsername(String username) {
        return avatarImageRepository.findAvatarImageByUser_Username(username)
                .map(AvatarImage::getFilename)
                .map(filename -> {
                    try {
                        return Files.readAllBytes(Path.of(path, filename));
                    } catch (IOException ex) {
                        log.error("Can't read file {}", filename, ex);
                        throw new IllegalStateException(ex);
                    }
                });
    }
}
