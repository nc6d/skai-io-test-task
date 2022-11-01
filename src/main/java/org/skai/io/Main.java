package org.skai.io;

import org.skai.io.parser.StatsParser;
import org.skai.io.service.PlayerStats;
import org.skai.io.service.MvpService;
import org.skai.io.config.ApplicationConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Main {

    public static final ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

    public static void main(String[] args) throws Exception {
        File files = new File("csv");
        List<PlayerStats> playerStatsList = new ArrayList<>();

        for (File file : Objects.requireNonNull(files.listFiles())) {
            playerStatsList.addAll(new StatsParser().parseMatchStatsAndSetScore(new FileInputStream(file)));
        }

        System.out.println("MVP: " + new MvpService().getMvp(playerStatsList));
    }

}