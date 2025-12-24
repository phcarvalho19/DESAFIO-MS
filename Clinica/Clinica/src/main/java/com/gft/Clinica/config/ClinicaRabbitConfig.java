package com.gft.Clinica.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClinicaRabbitConfig {

    // Exchange comum de exames
    @Bean
    public TopicExchange exameExchange() {
        return new TopicExchange("exame.exchange");
    }

    @Bean
    public Queue exameCriadoClinicaQueue() {
        return new Queue("exame.criado.clinica.queue", true);
    }

    @Bean
    public Binding exameCriadoBinding(
            Queue exameCriadoClinicaQueue,
            TopicExchange exameExchange
    ) {
        return BindingBuilder
                .bind(exameCriadoClinicaQueue)
                .to(exameExchange)
                .with("exame.criado");
    }
}