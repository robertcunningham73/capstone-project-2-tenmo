package com.techelevator.tenmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TenmoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenmoApplication.class, args);
    }

}

/* TODO
* Add authentication to the server
* Add exception catching to the server methods
* Add data validation for at least the post method
* Consider unit testing for the server
* Consider integration testing for the server
* Consider adding a return transfer id for the server post
* Wire up the CLI
* Consume alcoholic beverages */
