import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.VoluntarRepo;
import repository.jdbc.VoluntarDbRepo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class TeledonConfig {
    @Bean
    Properties getProps() {
        Properties props = new Properties();
        try {
            System.out.println("Searching bd.config in directory: " + ((new File(".")).getAbsolutePath()));
            props.load(new FileReader("bd.config"));
        }
        catch (IOException e){
            System.err.println("Configuration file bd.config not found " + e);
        }
       return props;
    }

    @Bean
    VoluntarRepo requestsRepo(){
        return new VoluntarDbRepo(getProps());
    }

}
