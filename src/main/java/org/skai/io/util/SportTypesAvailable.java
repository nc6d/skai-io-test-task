package org.skai.io.util;

import lombok.Getter;
import org.skai.io.service.SportService;
import org.skai.io.service.impl.basketball.BasketballService;
import org.skai.io.service.impl.handball.HandballService;

import java.util.Map;

@Getter
public class SportTypesAvailable {
    private final Map<String, Class<? extends SportService>> sports =
            Map.of
            ("BASKETBALL", BasketballService.class,
            "HANDBALL", HandballService.class);
}
