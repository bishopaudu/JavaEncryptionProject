package com.jobDemo.workDemo.FileEncrypt;

import com.jobDemo.workDemo.Authentication.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_files")
@Getter
@Setter
public class FileSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Column(name = "encrypted_content", columnDefinition = "BLOB")
    private byte[] encryptedContent;
    @Column(name = "uploader_username")
    private String uploaderUsername;

}
