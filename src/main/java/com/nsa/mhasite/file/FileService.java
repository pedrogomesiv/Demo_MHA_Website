package com.nsa.mhasite.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Class references from: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

//REFERENCING STARTS HERE:
@Service
public class FileService {

    static final Logger LOG = LoggerFactory.getLogger(FileService.class);
    private final Path fileStorageLocation;
    private FileStorageProperties fileSP;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileSP = fileStorageProperties;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            //throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            LOG.debug("Upload Dir: " + this.fileSP.getUploadDir());
            if(resource.exists()) {
                return resource;
            } else {
                //throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            //throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
        return null;
    }
}
//REFERENCING ENDS HERE