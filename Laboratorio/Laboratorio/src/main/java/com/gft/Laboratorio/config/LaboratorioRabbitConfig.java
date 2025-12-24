package com.gft.Laboratorio.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LaboratorioRabbitConfig {

    @Bean
    public TopicExchange exameExchange() {
        return new TopicExchange("exame.exchange");
    }

    @Bean
    public Queue exameSolicitadoQueue() {
        return new Queue("exame.solicitado.queue", true);
    }

    @Bean
    public Binding exameSolicitadoBinding(
            Queue exameSolicitadoQueue,
            TopicExchange exameExchange
    ) {
        return BindingBuilder
                .bind(exameSolicitadoQueue)
                .to(exameExchange)
                .with("exame.complexo.solicitado");
    }
}