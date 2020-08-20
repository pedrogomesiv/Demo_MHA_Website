package com.nsa.mhasite.file;

import com.nsa.mhasite.controllers.AdminController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

//Class references from: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

//REFERENCING STARTS HERE:
@Controller
public class FileDownload {
    static final Logger LOG = LoggerFactory.getLogger(FileDownload.class);
    private FileService fileService;

    @Autowired
    public FileDownload(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/statement", method = RequestMethod.GET)
    public String serveStatementPage() {
        LOG.debug("Handling /statement");
        return "timeBankingStatement";
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        LOG.debug(fileName);

        Resource resource = fileService.loadFileAsResource(fileName);
        if (resource != null) {
            LOG.debug("Resource is not null");
            LOG.debug(resource.getFilename());
            LOG.debug(resource.toString());
        }

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOG.debug("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

//REFERENCING ENDS HERE