package com.application.services.gamelogic;

import com.application.tasks.ChangeDirection;
import com.application.tasks.ChangeSpeed;
import com.application.tasks.JumpShipTask;
import com.application.utils.StringGenerator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class NavigationCommandsService {
    private final StringGenerator stringGenerator;

    private static final String COMMAND_SYMBOLS = "abcABC123!@#$%^&*()_+=-'/.,[]{}~";
    private static final int COMMAND_LENGTH = 12;
    private static Set<JumpShipTask> navigationCommands;

    private Map<String, JumpShipTask> activeNavigationCommands;
    private String activeSymbols;

    public NavigationCommandsService(StringGenerator stringGenerator) {
        this.stringGenerator = stringGenerator;

        navigationCommands = new HashSet<>();
        navigationCommands.add(new ChangeDirection(true));
        navigationCommands.add(new ChangeDirection(false));
        navigationCommands.add(new ChangeSpeed(1));
        navigationCommands.add(new ChangeSpeed(2));
        navigationCommands.add(new ChangeSpeed(-1));
    }

    public Map<String, JumpShipTask> generateNavigationCommands() {
        activeSymbols = stringGenerator.generateString(COMMAND_LENGTH, COMMAND_SYMBOLS);
        activeNavigationCommands = new HashMap<>();

        navigationCommands.forEach(this::putNavigationCommand);
        return activeNavigationCommands;
    }

    private void putNavigationCommand(JumpShipTask task) {
        String commandString;
        do {
            commandString = stringGenerator.generateStringFromUniqueSymbols(activeSymbols);
        } while (!activeNavigationCommands.containsKey(commandString));
        activeNavigationCommands.put(commandString, task);
    }

    public JumpShipTask addNavigationCommand(String commandString) {
        return activeNavigationCommands.get(commandString);
    }
}
