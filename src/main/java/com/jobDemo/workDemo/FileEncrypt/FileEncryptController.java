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
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(path = "/api")
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
            @RequestParam("username") String username,
            @RequestParam("encryptionKey") String encryptionKey,
            @RequestParam("decryptionKey") String decryptionKey) throws Exception {
        User user = authenticationService.getUserByUsername(username);
        if (user != null) {
            try {
                byte[] fileBytes = file.getBytes();
                byte[] encryptedFile = fileEncryptService.encryptWithKey(user.getEncryptionKey(), fileBytes);

                FileSchema fileSchema = new FileSchema();
                fileSchema.setUser(user);
                fileSchema.setFileName(file.getOriginalFilename());
                fileSchema.setEncryptedContent(encryptedFile);
                fileSchema.setUploaderUsername(username);
                fileSchema.setEncryptionKey(encryptionKey);
                fileSchema.setDecryptionKey(decryptionKey);
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

    @GetMapping("/user/{username}")
    public ResponseEntity<List<FileSchema>> getUserFiles(@PathVariable String username) {
        User user = authenticationService.getUserByUsername(username);
        if (user != null) {
            List<FileSchema> userFiles = fileSchemaRepository.findByUser(user);
            return ResponseEntity.ok(userFiles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
