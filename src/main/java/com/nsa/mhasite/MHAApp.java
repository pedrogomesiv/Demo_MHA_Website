package com.nsa.mhasite;

import com.nsa.mhasite.file.FileStorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})

public class MHAApp {

    static final Logger LOG = LoggerFactory.getLogger(MHAApp.class);

    public static void main(String args[]) {
        LOG.debug("Starting app");
        SpringApplication.run(MHAApp.class, args);
    }


}


//@SpringBootApplication
//@EnableConfigurationProperties({
//        FileStorageProperties.class
//})
//public class FileDemoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(FileDemoApplication.class, args);
//    }
//}