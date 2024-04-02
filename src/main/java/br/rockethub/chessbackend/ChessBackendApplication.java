package br.rockethub.chessbackend;

import br.rockethub.chessbackend.authentication.controllers.UserController;
import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableRabbit
public class ChessBackendApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChessBackendApplication.class);

    UserRepository userRepository;



    public static void main(String[] args) {
        SpringApplication.run(ChessBackendApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started with command-line arguments: {}", args.getOptionNames());
        String[] sourceArgs = args.getSourceArgs();
        logger.info("Application started with arguments: {}", (Object) sourceArgs);
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());
        for (String name : args.getOptionNames()) {
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }
        logger.info("Applications started ...");
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
