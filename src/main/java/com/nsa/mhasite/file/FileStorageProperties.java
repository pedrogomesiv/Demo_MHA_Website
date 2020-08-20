package com.nsa.mhasite.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

//Class references from: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/


//REFERENCING STARTS HERE:
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir = "./uploads";

    public String getUploadDir() {
        return uploadDir;
    }

}
//REFERENCING ENDS HERE