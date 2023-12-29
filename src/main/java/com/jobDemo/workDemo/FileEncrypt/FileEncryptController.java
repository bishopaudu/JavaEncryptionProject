package com.jobDemo.workDemo.FileEncrypt;

import com.jobDemo.workDemo.Authentication.AuthenticationService;
import com.jobDemo.workDemo.Authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "api/v1/file")
public class FileEncryptController {

    @Autowired
    private FileEncryptService fileEncryptService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private FileSchemaRepository fileSchemaRepository;
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<String>("file Upload ", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(
            @RequestParam("file")MultipartFile file,
            @RequestParam("date") String uploadDate,
            @RequestParam("username") String username) throws Exception {
        User user = authenticationService.getUserByUsername(username);
        if (user != null) {
            try {
                byte[] fileBytes = file.getBytes();
                byte[] encryptedFile = fileEncryptService.encryptWithKey(user.getEncryptionKey(), fileBytes);

                FileSchema fileSchema = new FileSchema();
                fileSchema.setUser(user);
                fileSchema.setFileName(file.getOriginalFilename());
                fileSchema.setEncryptedContent(encryptedFile);
                fileSchema.setUploadDate(LocalDateTime.parse(uploadDate)); // Parse the date string
                fileSchema.setUploaderUsername(username);

                fileSchemaRepository.save(fileSchema);

                return new ResponseEntity<String>("File uploaded successfully", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<String>("could not upload file" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } else {
            return new ResponseEntity<String>("username not found in database",HttpStatus.BAD_REQUEST);
        }


    }
}
