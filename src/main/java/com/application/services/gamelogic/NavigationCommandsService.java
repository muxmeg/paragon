package com.application.services.gamelogic;

import com.application.tasks.scheduled.ChangeDirection;
import com.application.tasks.scheduled.ChangeSpeed;
import com.application.tasks.ScheduledTask;
import com.application.utils.StringGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class NavigationCommandsService {
    private final StringGenerator stringGenerator;
    private final ScheduledTaskService scheduledTaskService;

    private static final String COMMAND_SYMBOLS = "abcABC123!@#$%^&*()_+=-'/.,[]{}";
    private static final int COMMAND_LENGTH = 12;
    private static Set<ScheduledTask> navigationCommands;

    private Map<String, ScheduledTask> activeNavigationCommands;
    private String activeSymbols;

    public NavigationCommandsService(StringGenerator stringGenerator, ScheduledTaskService scheduledTaskService) {
        this.stringGenerator = stringGenerator;
        this.scheduledTaskService = scheduledTaskService;

        navigationCommands = new HashSet<>();
        navigationCommands.add(new ChangeDirection(true));
        navigationCommands.add(new ChangeDirection(false));
        navigationCommands.add(new ChangeSpeed(1));
        navigationCommands.add(new ChangeSpeed(2));
        navigationCommands.add(new ChangeSpeed(-1));
    }

    public Map<String, ScheduledTask> generateNavigationCommands() {
        activeSymbols = stringGenerator.generateString(COMMAND_LENGTH, COMMAND_SYMBOLS);
        activeNavigationCommands = new HashMap<>();

        navigationCommands.forEach(this::putNavigationCommand);
        return activeNavigationCommands;
    }

    private void putNavigationCommand(ScheduledTask task) {
        String commandString;
        do {
            commandString = stringGenerator.generateStringFromUniqueSymbols(activeSymbols);
        } while (activeNavigationCommands.containsKey(commandString));
        activeNavigationCommands.put(commandString, task);
    }

    public void addNavigationCommand(String commandString) {
        scheduledTaskService.addTask(activeNavigationCommands.get(commandString));
    }

}
