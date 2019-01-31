package ro.msg.learning.shop.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CSVJacksonUtil {


    public static <T> List<T> fromCSV(Class<T> classToMap, InputStream inputStream) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        List<T> res = new ArrayList<>();
        try {
            MappingIterator<T> it = mapper.readerFor(classToMap)
                    .with(schema)
                    .readValues(inputStream);

            while (it.hasNext()) {
                res.add(it.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static <T> void toCSV(List<T> items, OutputStream outputStream, Class classType) {

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(classType);
        schema.withColumnSeparator('\t');

        try {
            mapper.writer(schema).writeValue(outputStream, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
