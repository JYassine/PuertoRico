package server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import server.stats.Server;


@SpringBootApplication
public class ServerSpring {

    public static void main(String args[]){

        for(String arg:args) {
            System.out.println(arg);
        }

        SpringApplication.run(ServerSpring.class,args);

    }

    @Bean
    public CommandLineRunner run() throws Exception{

        return args -> {
           Server.main(null);

        };

    }
}
