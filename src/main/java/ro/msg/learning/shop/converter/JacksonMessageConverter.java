package ro.msg.learning.shop.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.exception.RecoverableException;
import ro.msg.learning.shop.messages.ExceptionTextTemplate;
import ro.msg.learning.shop.util.CSVJacksonUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("unchecked")
@Component
public class JacksonMessageConverter<T> extends AbstractGenericHttpMessageConverter<List<T>> {

    public JacksonMessageConverter() {super(new MediaType("text", "csv"));}

    @Override
    protected void writeInternal(List<T> ts, Type type, HttpOutputMessage httpOutputMessage) {
        Class classType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        try {
            CSVJacksonUtil.toCSV(ts, httpOutputMessage.getBody(), classType);
        } catch (IOException e) {
            throw new RecoverableException(ExceptionTextTemplate.ETT_IO_EXCEPTION);
        }
    }

    @Override
    protected List<T> readInternal(Class aClass, HttpInputMessage httpInputMessage) {
        try {
            return CSVJacksonUtil.fromCSV(aClass, httpInputMessage.getBody());
        } catch (IOException e) {
            throw new RecoverableException(ExceptionTextTemplate.ETT_IO_EXCEPTION);
        }
    }

    @Override
    public List<T> read(Type type, Class aClass, HttpInputMessage httpInputMessage) {
        try {
            return CSVJacksonUtil.fromCSV(aClass, httpInputMessage.getBody());
        } catch (IOException e) {
            throw new RecoverableException(ExceptionTextTemplate.ETT_IO_EXCEPTION);
        }
    }

    @Override
    public boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
        return type instanceof Class ? this.canRead((Class) type, mediaType) : this.canRead(mediaType);
    }

    @Override
    public boolean canWrite(@Nullable Type type, Class<?> clazz, @Nullable MediaType mediaType) {

        Class listType = type instanceof Class ? type.getClass() : (Class) ((ParameterizedType) type).getRawType();
        if (!listType.isAssignableFrom(List.class)) {
            return false;
        }
        return this.canWrite(clazz, mediaType);
    }

}
