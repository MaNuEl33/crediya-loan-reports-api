package co.com.crediya.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReactiveWebError {
    GENERIC_ERROR("RW_001", "An unexpected error has occurred. Please try again in a moment."),
    INVALID_TOKEN("RW_002", "Invalid token."),
    INSUFFICIENT_PERMISSIONS("RW_003", "You do not have sufficient permissions.");

    private final String code;
    private final String message;
}
