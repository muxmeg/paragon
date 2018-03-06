package com.application.controllers.socket;

import com.application.dto.NavigationCommandsDto;
import com.application.dto.NavigationDataDto;
import com.application.model.Ship;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class MeteorStormController {

    public static final String TOPIC_METEOR_STORM_DATA = "/topic/meteorStormData";

    private final SimpMessagingTemplate template;

    public MeteorStormController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void updateStormPrediction(int nextStormLevel) {
        template.convertAndSend(TOPIC_METEOR_STORM_DATA, nextStormLevel);
    }
}
