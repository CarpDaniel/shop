package ro.msg.learning.shop.odata;

import lombok.RequiredArgsConstructor;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import ro.msg.learning.shop.entity.Order;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ro.msg.learning.shop.odata.CustomEdmProvider.ENTITY_SET_NAME_ORDERS;
import static ro.msg.learning.shop.odata.CustomEdmProvider.ENTITY_SET_NAME_ORDER_DETAILS;

@RequiredArgsConstructor
public class CustomODataSingleProcessor extends ODataSingleProcessor {

    private final CustomDataStore customDataStore;

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {

        if (uriInfo.getNavigationSegments().size() == 0) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_ORDER_DETAILS.equals(entitySet.getName())) {
                int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                List<Map<String, Object>> data = customDataStore.getOrderDetails(id);

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder propertiesBuilder = EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeFeed(contentType, entitySet, data, propertiesBuilder.build());
                }
            } else if (ENTITY_SET_NAME_ORDERS.equals(entitySet.getName())) {
                int id = getKeyValue(uriInfo.getKeyPredicates().get(0));
                Map<String, Object> data = customDataStore.getOrder(Long.valueOf(id));

                if (data != null) {
                    URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                    EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder propertiesBuilder = EntityProviderWriteProperties.serviceRoot(serviceRoot);

                    return EntityProvider.writeEntry(contentType, entitySet, data, propertiesBuilder.build());
                }
            }

            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);

        } else if (uriInfo.getNavigationSegments().size() == 1) {
            //navigation first level, simplified example for illustration purposes only
            EdmEntitySet entitySet = uriInfo.getTargetEntitySet();
            if (ENTITY_SET_NAME_ORDERS.equals(entitySet.getName())) {
                int orderKey = getKeyValue(uriInfo.getKeyPredicates().get(0));
                return EntityProvider.writeEntry(contentType, uriInfo.getTargetEntitySet(), customDataStore.getOrder(new Long(orderKey)), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }

            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }

        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {

        EdmEntitySet entitySet;

        if (uriInfo.getNavigationSegments().size() == 0) {
            entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_ORDER_DETAILS.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, customDataStore.getAllOrderDetails(), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            } else if (ENTITY_SET_NAME_ORDERS.equals(entitySet.getName())) {
                return EntityProvider.writeFeed(contentType, entitySet, customDataStore.getOrders(), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }

            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);

        } else if (uriInfo.getNavigationSegments().size() == 1) {
            //navigation first level, simplified example for illustration purposes only
            entitySet = uriInfo.getTargetEntitySet();

            if (ENTITY_SET_NAME_ORDER_DETAILS.equals(entitySet.getName())) {
                int orderDetailsKey = getKeyValue(uriInfo.getKeyPredicates().get(0));

                List<Map<String, Object>> orderDetails = new ArrayList<>();
                orderDetails.addAll(customDataStore.getOrderDetails(orderDetailsKey));

                return EntityProvider.writeFeed(contentType, entitySet, orderDetails, EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
            }

            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }

        throw new ODataNotImplementedException();
    }

    @Override
    public ODataResponse createEntity(PostUriInfo uriInfo, InputStream content,
                                      String requestContentType, String contentType) throws ODataException {
        //No support for creating and linking a new entry
        if (uriInfo.getNavigationSegments().size() > 0) {
            throw new ODataNotImplementedException();
        }


        EntityProviderReadProperties properties = EntityProviderReadProperties.init().mergeSemantic(false).build();

        ODataEntry entry = EntityProvider.readEntry(requestContentType, uriInfo.getStartEntitySet(), content, properties);
        //if something goes wrong in deserialization this is managed via the ExceptionMapper
        //no need for an application to do exception handling here an convert the exceptions in HTTP exceptions

        Map<String, Object> data = entry.getProperties();
        //now one can use the data to create the entry in the backend ...
        //retrieve the key value after creation, if the key is generated by the server

        Order order = new Order();
        order.setOrderDate((Date) data.get("OrderDate"));
        order.setShippedFrom((Integer) data.get("ShippedFrom"));

        //serialize the entry, Location header is set by OData Library
        return EntityProvider.writeEntry(contentType, uriInfo.getStartEntitySet(), entry.getProperties(), EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build());
    }

    private int getKeyValue(KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }
}
