package com.application.services.gamelogic;

import com.application.tasks.ScheduledTask;
import com.application.tasks.scheduled.ScheduledTaskType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ScheduledTaskService {
    private final Map<ScheduledTaskType, ScheduledTask> scheduledTasks = new HashMap<>();

    public void addTask(ScheduledTask task) {
        scheduledTasks.put(task.getScheduledTaskType(), task);
    }

    public boolean contains(Set<ScheduledTask> tasks) {
        return scheduledTasks.values().containsAll(tasks);
    }

    public void cleanTasks() {
        scheduledTasks.clear();
    }

    public Collection<ScheduledTask> getScheduledTasks() {
        return scheduledTasks.values();
    }
}
