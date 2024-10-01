package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                                .info(new Info()
                                        .title("Voll.med API")
                                        .description("A VollMed API é uma aplicação desenvolvida em Java com Spring Framework, destinada à gestão de uma clínica médica. Com recursos de autenticação JWT, a API permite o cadastro de médicos, pacientes e consultas, fornecendo endpoints públicos para login e cadastro de usuários.\n" +
                                                "\n" +
                                                "Foi implementado um workflow de Integração Contínua (CI) utilizando GitHub Actions para testes e builds em pull requests, e um processo de Entrega Contínua (CD) que realiza o deploy automático da aplicação em produção. A aplicação é containerizada com Docker e implantada na Azure, garantindo atualizações rápidas e escalabilidade.\n")
                                        .contact(new Contact()
                                                .name("Vinicius Leonel")
                                                .email("viniciuslps.cms@gmail.com"))
                                        .license(new License()
                                                .name("Apache 2.0")
                                                ));
    }
}
