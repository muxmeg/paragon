package com.application.services.gamelogic;

import com.application.tasks.ScheduledTask;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduledTaskService {
    private final Map<String, ScheduledTask> scheduledTasks = new HashMap<>();

    public void addTask(ScheduledTask task) {
        if (task != null) {
            scheduledTasks.put(task.getClass().getName(), task);
        }
    }

    public void cleanTasks() {
        scheduledTasks.clear();
    }

    public Collection<ScheduledTask> getScheduledTasks() {
        return scheduledTasks.values();
    }
}
