package com.sprint.mission.discodeit.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // common error
    INTERNAL_SERVER_ERROR(500, "C001", "Internal server error"),

    // error related to user
    USERID_NOT_FOUND(404, "U001", "User id not found"),
    USERNAME_OR_PASSWORD_INCORRECT(404, "U002", "Username or password incorrect"),
    USERNAME_DUPLICATED(400, "U003", "Duplicated Username"),
    EMAIL_DUPLICATED(400, "U004", "Duplicated Email"),

    // channel
    CHANNELID_NOT_FOUND(404, "C001", "Channel id not found"),
    NO_MESSAGE_IN_CHANNEL(404, "C002", "channel have no messages"),

    // message
    MESSAGEID_NOT_FOUND(404, "M001", "Message id not found"),

    // UserStatus(User Profile)
    USERSTATUSID_NOT_FOUND(404, "US001", "UserStatus id not found"),
    USERSTATUS_DUPLICATED(400, "US002", "Duplicated UserStatus"),

    // ReadStatus
    READSTATUSID_NOT_FOUND(404, "RS001", "ReadStatus id not found"),
    READSTATUS_DUPLICATED(400, "RS002", "Duplicated ReadStatus"),

    // BinaryContent(profile image, message attachment, ...)
    BINARYCONTENTID_NOT_FOUND(404, "BC001", "BinaryContent id not found"),
    ;

    private final int status;
    private final String code;
    private final String message;
}
