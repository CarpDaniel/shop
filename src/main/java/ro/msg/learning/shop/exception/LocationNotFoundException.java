package ro.msg.learning.shop.exception;

import ro.msg.learning.shop.messages.ExceptionTextTemplate;

public class LocationNotFoundException extends RecoverableException {

    public LocationNotFoundException() {
        super(ExceptionTextTemplate.ETT_LOCATION_NOT_FOUND);
    }
}
