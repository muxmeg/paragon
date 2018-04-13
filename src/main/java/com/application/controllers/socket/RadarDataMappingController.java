package com.application.controllers.socket;

import com.application.services.gamelogic.MeteorStormService;
import com.application.services.gamelogic.RadarService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class RadarDataMappingController {

    private static final String TOPIC_METEOR_STORM_DATA_REQUEST = "/radarData/request";

    private final RadarService radarService;

    public RadarDataMappingController(RadarService radarService) {
        this.radarService = radarService;
    }

    @MessageMapping(TOPIC_METEOR_STORM_DATA_REQUEST)
    public void radarDataRequest(String request) {
        radarService.detectObjects();
    }

}
