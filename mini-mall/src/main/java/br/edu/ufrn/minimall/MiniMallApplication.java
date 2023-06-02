package br.edu.ufrn.minimall;

import br.edu.ufrn.minimall.model.Usuario;
import br.edu.ufrn.minimall.repository.MiniaturaRepository;
import br.edu.ufrn.minimall.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MiniMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniMallApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(MiniaturaRepository miniaturaRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            List<Usuario> users = Stream.of(
                    new Usuario(1L, "Eric", "Eric", encoder.encode("0123"), "ericcouto@gmail.com", true),
                    new Usuario(2L, "Taniro", "Taniro", encoder.encode("4567"), "tanirochacon@gmail.com", false),
                    new Usuario(3L, "Fatima", "Fatima", encoder.encode("8910"), "fatimadolivaira@gmail.com", false)
            ).collect(Collectors.toList());
            usuarioRepository.saveAll(users);
        };
    }


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
    }


}
