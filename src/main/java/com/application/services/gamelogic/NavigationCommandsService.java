package com.application.services.gamelogic;

import com.application.controllers.socket.NavigationController;
import com.application.dto.EncryptedCommandDto;
import com.application.dto.NavigationCommandsDto;
import com.application.services.EventLoggingService;
import com.application.tasks.ShipTaskType;
import com.application.tasks.scheduled.ChangeDirection;
import com.application.tasks.scheduled.ChangeSpeed;
import com.application.tasks.ScheduledTask;
import com.application.tasks.scheduled.ScheduledTaskType;
import com.application.utils.StringGenerator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class NavigationCommandsService {
    private final StringGenerator stringGenerator;
    private final ScheduledTaskService scheduledTaskService;
    private final NavigationController navigationController;

    private static final String COMMAND_SYMBOLS = "abcdABCDX123!?@#$%^&*()+=-'/.,[]{}";
    public static final int COMMAND_LENGTH = 12;
    public static final int PARTIAL_COMMAND_LENGTH = 3;
    private final EventLoggingService eventLoggingService;
    private static final Set<ScheduledTask> navigationTasks;

    private Map<String, ScheduledTask> activeNavigationCommands;
    private String activeSymbols;

    static {
        navigationTasks = new HashSet<>();
        navigationTasks.add(new ChangeDirection(true));
        navigationTasks.add(new ChangeDirection(false));
        navigationTasks.add(new ChangeSpeed(1));
        navigationTasks.add(new ChangeSpeed(2));
        navigationTasks.add(new ChangeSpeed(-1));
    }

    public NavigationCommandsService(StringGenerator stringGenerator, ScheduledTaskService scheduledTaskService,
                                     NavigationController navigationController, EventLoggingService eventLoggingService) {
        this.stringGenerator = stringGenerator;
        this.scheduledTaskService = scheduledTaskService;
        this.navigationController = navigationController;
        this.eventLoggingService = eventLoggingService;
    }

    public Map<String, ScheduledTask> generateNavigationCommands() {
        activeSymbols = stringGenerator.generateStringFromUniqueSymbols(COMMAND_SYMBOLS, COMMAND_LENGTH);
        activeNavigationCommands = new HashMap<>();

        navigationTasks.forEach(this::putNavigationCommand);
        return activeNavigationCommands;
    }

    private void putNavigationCommand(ScheduledTask task) {
        String commandString;
        do {
            commandString = stringGenerator.generateStringFromUniqueSymbols(activeSymbols);
        } while (activeNavigationCommands.containsKey(commandString));
        activeNavigationCommands.put(commandString, task);
    }

    public Set<String> getNavigationStrings() {
        return activeNavigationCommands.keySet();
    }

    public void addNavigationCommand(String commandString) {
        Optional<ScheduledTask> scheduledTask = Optional.ofNullable(activeNavigationCommands.get(commandString));
        if (scheduledTask.isPresent()) {
            scheduledTaskService.addTask(scheduledTask.get());
            eventLoggingService.logNavigationTask(scheduledTask.get());
        } else {
            eventLoggingService.logNavigationTask(null);
        }
    }

    public Map<Integer, Character> generatePartialCommand(String commandString) {
        Map<Integer, Character> result = new HashMap<>();
        do {
            int idx = ThreadLocalRandom.current().nextInt(COMMAND_LENGTH);
            if (!result.containsKey(idx)) {
                result.put(idx, commandString.toCharArray()[idx]);
            }
        } while (result.size() < PARTIAL_COMMAND_LENGTH);
        return result;
    }

    public void updateNavigationStrings() {
        navigationController.updateNavigationStrings(getNavigationStrings());
    }

    public void updateNavigationCommands() {
        Map<String, ScheduledTask> activeNavigationCommands = generateNavigationCommands();

        updateNavigationStrings();

        List<EncryptedCommandDto> commands = activeNavigationCommands.entrySet().stream().map(
                entry -> new EncryptedCommandDto(generatePartialCommand(entry.getKey()),
                        entry.getValue())).collect(Collectors.toList());
        navigationController.updateNavigationCommands(new NavigationCommandsDto(commands));
    }

    public boolean isEvasiveManeuverActive() {
        return scheduledTaskService.contains(navigationTasks);
    }
}
