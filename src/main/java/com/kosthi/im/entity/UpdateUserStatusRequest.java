package com.kosthi.im.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserStatusRequest {
    private String account;
    private Boolean isOnline;

    @JsonCreator
    public UpdateUserStatusRequest(@JsonProperty("account") String account, @JsonProperty("isOnline") Boolean isOnline) {
        this.account = account;
        this.isOnline = isOnline;
    }
}
