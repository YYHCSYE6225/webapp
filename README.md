# webapp

### useful plugins

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>



### Authentication
dependencies

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>2.5.5</version>
        </dependency>

use basic authentication

### AWS service
dependencies

        <dependency>
            <groupId>com.amazonaws</groupId>
            <artifactId>aws-java-sdk-s3</artifactId>
            <version>1.11.821</version>
        </dependency>

### RDS init
using init.sql to initialize the database tables. using .properties file to config