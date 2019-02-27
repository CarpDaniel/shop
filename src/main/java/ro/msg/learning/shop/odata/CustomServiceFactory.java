package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomServiceFactory extends ODataServiceFactory {

    private final CustomDataStore customDataStore;
    @Override
    public ODataService createService(ODataContext oDataContext) throws ODataException {

        EdmProvider edmProvider = new CustomEdmProvider();
        ODataSingleProcessor singleProcessor = new CustomODataSingleProcessor(customDataStore);

        return createODataSingleProcessorService(edmProvider, singleProcessor);
    }
}
