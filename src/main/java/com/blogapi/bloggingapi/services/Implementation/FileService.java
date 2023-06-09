package com.blogapi.bloggingapi.services.Implementation;

import com.blogapi.bloggingapi.services.Interfaces.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService implements IFileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get file name
        String fileName = file.getOriginalFilename();

        // generate Random file name
        String randomId = UUID.randomUUID().toString();
        String randomName = randomId.concat(fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : ".png");

        // create file full path
        String filePath = path + File.separator + randomName;

        // create folder if not created
        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        // copy file to folder
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // System.out.println(folderPath);
        System.out.println(fileName);
        System.out.println(filePath);

        return randomName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullFilePath = path + File.separator + fileName;
        return new FileInputStream(fullFilePath);
    }
}
