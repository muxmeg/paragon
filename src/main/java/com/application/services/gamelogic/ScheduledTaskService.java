package com.application.services.gamelogic;

import com.application.tasks.ScheduledTask;
import com.application.tasks.scheduled.ScheduledTaskType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduledTaskService {
    private final Map<ScheduledTaskType, ScheduledTask> scheduledTasks = new HashMap<>();

    public void addTask(ScheduledTask task) {
        scheduledTasks.put(task.getScheduledTaskType(), task);
    }

    public void cleanTasks() {
        scheduledTasks.clear();
    }

    public Collection<ScheduledTask> getScheduledTasks() {
        return scheduledTasks.values();
    }
}
