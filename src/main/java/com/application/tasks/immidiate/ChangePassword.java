package com.application.tasks.immidiate;

import com.application.services.ShipTasksService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
public class ChangePassword extends ImmediateShipTask {

    private final String role;
    private final String newPassword;
    private final Boolean isSecret;

    public ChangePassword() {
        role = "";
        newPassword = "";
        isSecret = null;
    }

    public ChangePassword(String role, String newPassword, boolean isSecret, String sender) {
        super(sender);
        this.role = role;
        this.newPassword = newPassword;
        this.isSecret = isSecret;
    }

    @Override
    public void execute(ShipTasksService shipTasksService) {
        shipTasksService.changePassword(role, newPassword, isSecret);
    }
}
