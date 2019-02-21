package ro.msg.learning.shop.messages;

import java.io.Serializable;

public enum ExceptionTextTemplate implements Serializable {
    ETT_ORDER_NOT_CREATED,
    ETT_LOCATION_NOT_FOUND,
    ETT_IO_EXCEPTION,
    ETT_NOT_WRITABLE_EXCEPTION,
    ETT_NOT_READABLE_EXCEPTION;
}
