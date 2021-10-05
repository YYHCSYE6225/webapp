package edu.neu.coe.csye6225.webapp.entity;

import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

/**
 * @author Yuhan
 */
@Data
public class User {
    String id;
    String firstName;
    String lastName;
    String password;
    String username;
    @ReadOnlyProperty
    LocalDateTime accountCreated;
    LocalDateTime accountUpdated;
}
