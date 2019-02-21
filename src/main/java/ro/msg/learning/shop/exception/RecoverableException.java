package ro.msg.learning.shop.exception;

import ro.msg.learning.shop.util.ResourceBundleUtil;

public class RecoverableException extends RuntimeException {

    public RecoverableException(Enum pEnum) {
        super(ResourceBundleUtil.getMessageFromEnum(pEnum));
    }
}
