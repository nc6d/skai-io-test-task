package org.skai.io;

import org.skai.io.service.MvpService;
import org.skai.io.config.ApplicationConfig;
import org.skai.io.util.SportsUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class Main {

    public static final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void main(String[] args) throws Exception {
        File files = new File("csv");
        System.out.println("MVP: " + new MvpService().getMvp(SportsUtil.getMatchStatsFromCsv(files)));
    }

}