package com.application.controllers.socket;

import com.application.dto.RadarDataDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RadarDataController {

    public static final String TOPIC_RADAR_DATA = "/topic/radarData";

    private final SimpMessagingTemplate template;

    public RadarDataController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void updateRadar(RadarDataDto data) {
        template.convertAndSend(TOPIC_RADAR_DATA, data);
    }
}
