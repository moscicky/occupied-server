package pl.moscicki.remoteserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RemoteServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(RemoteServerApplication.class, args);
  }

}

