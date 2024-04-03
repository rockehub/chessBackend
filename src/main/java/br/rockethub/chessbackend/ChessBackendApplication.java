package br.rockethub.chessbackend;

import br.rockethub.chessbackend.authentication.repositories.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
