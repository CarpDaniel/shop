package ro.msg.learning.shop.exception;

import ro.msg.learning.shop.messages.ExceptionTextTemplate;

public class OrderNotCreatedException extends RecoverableException {

    public OrderNotCreatedException() {
        super(ExceptionTextTemplate.ETT_ORDER_NOT_CREATED);
    }
}
