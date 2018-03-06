package com.application.controllers.socket;

import com.application.services.gamelogic.MeteorStormService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MeteorStormMappingController {

    public static final String TOPIC_METEOR_STORM_DATA_REQUEST = "/meteorStormData/request";

    private final MeteorStormService meteorStormService;

    public MeteorStormMappingController(MeteorStormService meteorStormService) {
        this.meteorStormService = meteorStormService;
    }

    @MessageMapping(TOPIC_METEOR_STORM_DATA_REQUEST)
    public void navigationCommandRequest(String request) {
        meteorStormService.updateMeteorStormData();
    }

}
