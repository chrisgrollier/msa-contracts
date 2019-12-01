package net.chrisgrollier.cloud.apps.common.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;

/**
 * This method is the Field Error Dto
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class FieldError {

    /** The invalid field name . */
    private String field;

    /** The message error description. */
    private final String message;

    private FieldError(String field, String message) {
        super();
        this.field = field;
        this.message = message;
    }

    private FieldError(String message) {
        super();
        this.message = message;
    }

    public static FieldError of(String field, String message) {
        return new FieldError(field, message);
    }

    public static FieldError of(String message) {
        return new FieldError(message);
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        // @formatter:off
        return MoreObjects.toStringHelper(this)
                          .add("field", field)
                          .add("message", message)
                          .toString();
        // @formatter:on
    }

}
