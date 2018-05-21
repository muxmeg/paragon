package com.application.services;

import com.application.dto.ShipDataDto;
import com.application.dto.ShipTaskDto;
import com.application.dto.UserMessage;
import com.application.tasks.ScheduledTask;
import com.application.tasks.ShipTaskType;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log4j2
public class EventLoggingService {
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//    Writer fstream = null;
//    BufferedWriter out = null;
//try {
//        fstream = new OutputStreamWriter(new FileOutputStream(mergedFile), StandardCharsets.UTF_8);
    public synchronized void logChat(UserMessage message) {
        try {
            OutputStreamWriter fileWriter = new OutputStreamWriter(
                    new FileOutputStream(System.getProperty("user.home") + File.separator + "chat.txt",
                    true), StandardCharsets.UTF_8);
            val builder = new StringBuilder();
            fileWriter.write(builder.append("\n")
                    .append(dateFormat.format(new Date()))
                    .append(" -- ")
                    .append(message.getSender())
                    .append(" => ")
                    .append(message.getTarget())
                    .append(" -- ")
                    .append(message.getBody())
                    .toString());
            fileWriter.close();
        } catch (IOException e) {
            log.error("Can't write to chat file", e);
        }
    }

    public synchronized void logTask(ShipTaskDto dto, ShipTaskType taskType) {
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + File.separator + "actions.txt", true);
            val builder = new StringBuilder();
            fileWriter.write(builder.append("\n")
                    .append(dateFormat.format(new Date()))
                    .append(" -- ")
                    .append(taskType.equals(ShipTaskType.IMMEDIATE) ? "Immediate" : "Scheduled")
                    .append(" -- ")
                    .append(dto.toString())
                    .toString());
            fileWriter.close();
        } catch (IOException e) {
            log.error("Can't write to tasks file", e);
        }
    }

    public synchronized void logNavigationTask(ScheduledTask task) {
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + File.separator + "navigationCommands.txt", true);
            val builder = new StringBuilder();
            fileWriter.write(builder.append("\n")
                    .append(dateFormat.format(new Date()))
                    .append(" -- Scheduled -- ")
                    .append(task != null ? task.toString() : "Incorrect command")
                    .toString());
            fileWriter.close();
        } catch (IOException e) {
            log.error("Can't write to tasks file", e);
        }
    }

    public synchronized void logShipChange(ShipDataDto ship) {
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + File.separator + "shipData.txt", true);
            val builder = new StringBuilder();
            fileWriter.write(builder.append("\n")
                    .append(dateFormat.format(new Date()))
                    .append(" -- ")
                    .append(ship.toString())
                    .toString());
            fileWriter.close();
        } catch (IOException e) {
            log.error("Can't write to shipData file", e);
        }
    }

    public synchronized void logShipJump(ShipDataDto shipDataDto) {
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.home") + File.separator + "shipJumpData.txt", true);
            val builder = new StringBuilder();
            fileWriter.write(builder.append("\n")
                    .append(dateFormat.format(new Date()))
                    .append(" -- ")
                    .append(shipDataDto.toString())
                    .toString());
            fileWriter.close();
        } catch (IOException e) {
            log.error("Can't write to shipData file", e);
        }
    }

}
