package blog.data;

import blog.model.Message;
import blog.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadData {

    @Bean
    CommandLineRunner initDataBase(MessageRepository messageRepository) {
        return args -> {
            log.info("Preloading " + messageRepository.save(
                    new Message('I', "BB", "Klasa - to definija obiektu")));
            log.info("Preloading " + messageRepository.save(
                    new Message('I', "AA", "Interfejs - to zbiór deklaracji abstrakcyjnych metod ")));

        };
    }
}
