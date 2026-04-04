package es.upm.dit.isst.tftbibliotecafront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import es.upm.dit.isst.tftbibliotecafront.config.TftBibliotecaProperties;

@SpringBootApplication
@EnableConfigurationProperties(TftBibliotecaProperties.class)
public class TftbibliotecafrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(TftbibliotecafrontApplication.class, args);
    }
}

