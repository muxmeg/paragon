package com.application.services.gamelogic;

import com.application.tasks.JumpShipTask;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JumpShipTaskService {
    private final Map<String, JumpShipTask> preparedTasks = new HashMap<>();

    public void addTask(JumpShipTask task) {
        preparedTasks.put(task.getClass().getName(), task);
    }

    public void cleanTasks() {
        preparedTasks.clear();
    }

    public Collection<JumpShipTask> getPreparedTasks() {
        return preparedTasks.values();
    }
}
