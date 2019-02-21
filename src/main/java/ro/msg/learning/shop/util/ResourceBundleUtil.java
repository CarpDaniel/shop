package ro.msg.learning.shop.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleUtil {

    public static String getMessageFromEnum(Enum pEnum) {
        Locale locale = new Locale("en", "EN");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionTextTemplate", locale);
        return resourceBundle.getString(pEnum.name());
    }
}
