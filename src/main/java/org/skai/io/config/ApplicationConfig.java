package org.skai.io.config;

import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;
import org.skai.io.util.SportsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    BasketballService basketballService() {
        return new BasketballService();
    }

    @Bean
    HandballService handballService() {
        return new HandballService();
    }

    @Bean
    SportsUtil sportsInit() {
        return new SportsUtil();
    }


}
