package com.jobDemo.workDemo.FileEncrypt;

import com.jobDemo.workDemo.Authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileSchemaRepository extends JpaRepository<FileSchema,Long> {
    List<FileSchema> findByUser(User user);

}
