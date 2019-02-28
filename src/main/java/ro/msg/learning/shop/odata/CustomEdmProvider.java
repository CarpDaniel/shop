package ro.msg.learning.shop.odata;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.api.exception.ODataException;

import java.util.ArrayList;
import java.util.List;

public class CustomEdmProvider extends EdmProvider {

    static final String ENTITY_SET_NAME_ORDERS = "Orders";
    static final String ENTITY_SET_NAME_ORDER_DETAILS = "OrderDetails";
    static final String ENTITY_NAME_ORDER = "Order";
    static final String ENTITY_NAME_ORDER_DETAIL = "OrderDetail";

    private static final String NAMESPACE = "org.apache.olingo.odata2.ODataOrderDetails";

    private static final FullQualifiedName ENTITY_TYPE_1_1 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_ORDER_DETAIL);
    private static final FullQualifiedName ENTITY_TYPE_1_2 = new FullQualifiedName(NAMESPACE, ENTITY_NAME_ORDER);

    private static final FullQualifiedName COMPLEX_TYPE_1_1 = new FullQualifiedName(NAMESPACE, "Address");
    private static final FullQualifiedName COMPLEX_TYPE_1_2 = new FullQualifiedName(NAMESPACE, "Customer");

    private static final FullQualifiedName ASSOCIATION_ORDER_DETAIL_ORDER = new FullQualifiedName(NAMESPACE, "OrderDetail_Order_Order_OrderDetails");

    private static final String ROLE_1_1 = "OrderDetail_Order";
    private static final String ROLE_1_2 = "Order_OrderDetails";

    private static final String ENTITY_CONTAINER = "ODataOrderDetailsEntityContainer";

    private static final String ASSOCIATION_SET = "OrderDetails_Orders";

    @Override
    public List<Schema> getSchemas() throws ODataException {
        List<Schema> schemas = new ArrayList<Schema>();

        Schema schema = new Schema();
        schema.setNamespace(NAMESPACE);

        List<EntityType> entityTypes = new ArrayList<EntityType>();
        entityTypes.add(getEntityType(ENTITY_TYPE_1_1));
        entityTypes.add(getEntityType(ENTITY_TYPE_1_2));
        schema.setEntityTypes(entityTypes);

        List<ComplexType> complexTypes = new ArrayList<ComplexType>();
        complexTypes.add(getComplexType(COMPLEX_TYPE_1_1));
        complexTypes.add(getComplexType(COMPLEX_TYPE_1_2));
        schema.setComplexTypes(complexTypes);

        List<Association> associations = new ArrayList<Association>();
        associations.add(getAssociation(ASSOCIATION_ORDER_DETAIL_ORDER));
        schema.setAssociations(associations);

        List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
        EntityContainer entityContainer = new EntityContainer();
        entityContainer.setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);

        List<EntitySet> entitySets = new ArrayList<EntitySet>();
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_ORDER_DETAILS));
        entitySets.add(getEntitySet(ENTITY_CONTAINER, ENTITY_SET_NAME_ORDERS));
        entityContainer.setEntitySets(entitySets);

        List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
        associationSets.add(getAssociationSet(ENTITY_CONTAINER, ASSOCIATION_ORDER_DETAIL_ORDER, ENTITY_SET_NAME_ORDERS, ROLE_1_2));
        entityContainer.setAssociationSets(associationSets);

        entityContainers.add(entityContainer);
        schema.setEntityContainers(entityContainers);

        schemas.add(schema);

        return schemas;
    }

    @Override
    public EntityType getEntityType(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {

            if (ENTITY_TYPE_1_1.getName().equals(edmFQName.getName())) {

                //Properties
                List<Property> properties = new ArrayList<Property>();
                properties.add(new SimpleProperty().setName("Product").setType(EdmSimpleTypeKind.String).setFacets(new Facets().setNullable(false)));
                properties.add(new SimpleProperty().setName("Quantity").setType(EdmSimpleTypeKind.String));

                //Navigation Properties
                List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
                navigationProperties.add(new NavigationProperty().setName("Order")
                        .setRelationship(ASSOCIATION_ORDER_DETAIL_ORDER).setFromRole(ROLE_1_1).setToRole(ROLE_1_2));

                return new EntityType().setName(ENTITY_TYPE_1_1.getName())
                        .setProperties(properties)
                        .setKey(new Key().setKeys(new ArrayList<>()))
                        .setNavigationProperties(navigationProperties);

            } else if (ENTITY_TYPE_1_2.getName().equals(edmFQName.getName())) {

                //Properties
                List<Property> properties = new ArrayList<Property>();
                properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int64).setFacets(new Facets().setNullable(false)));
                properties.add(new SimpleProperty().setName("ShippedFrom").setType(EdmSimpleTypeKind.String).setFacets(new Facets()));
                properties.add(new ComplexProperty().setName("Address").setType(new FullQualifiedName(NAMESPACE, "Address")));
                properties.add(new ComplexProperty().setName("Customer").setType(new FullQualifiedName(NAMESPACE, "Customer")));
                properties.add(new SimpleProperty().setName("OrderDate").setType(EdmSimpleTypeKind.DateTime).setFacets(new Facets()));

                //Navigation Properties
                List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
                navigationProperties.add(new NavigationProperty().setName("OrderDetails")
                        .setRelationship(ASSOCIATION_ORDER_DETAIL_ORDER).setFromRole(ROLE_1_2).setToRole(ROLE_1_1));

                //Key
                List<PropertyRef> keyProperties = new ArrayList<>();
                keyProperties.add(new PropertyRef().setName("Id"));
                Key key = new Key().setKeys(keyProperties);

                return new EntityType().setName(ENTITY_TYPE_1_2.getName())
                        .setProperties(properties)
                        .setHasStream(true)
                        .setKey(key)
                        .setNavigationProperties(navigationProperties);
            }
        }
        return null;
    }

    @Override
    public ComplexType getComplexType(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (COMPLEX_TYPE_1_1.getName().equals(edmFQName.getName())) {
                List<Property> properties = new ArrayList<Property>();
                properties.add(new SimpleProperty().setName("Country").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("City").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("County").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("Street").setType(EdmSimpleTypeKind.String));
                return new ComplexType().setName(COMPLEX_TYPE_1_1.getName()).setProperties(properties);
            }
            if (COMPLEX_TYPE_1_2.getName().equals(edmFQName.getName())) {
                List<Property> properties = new ArrayList<Property>();
                properties.add(new SimpleProperty().setName("Id").setType(EdmSimpleTypeKind.Int64).setFacets(new Facets().setNullable(false)));
                properties.add(new SimpleProperty().setName("FirstName").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("LastName").setType(EdmSimpleTypeKind.String));
                properties.add(new SimpleProperty().setName("UserName").setType(EdmSimpleTypeKind.String));
                return new ComplexType().setName(COMPLEX_TYPE_1_2.getName()).setProperties(properties);
            }
        }
        return null;
    }

    @Override
    public Association getAssociation(FullQualifiedName edmFQName) throws ODataException {
        if (NAMESPACE.equals(edmFQName.getNamespace())) {
            if (ASSOCIATION_ORDER_DETAIL_ORDER.getName().equals(edmFQName.getName())) {
                return new Association().setName(ASSOCIATION_ORDER_DETAIL_ORDER.getName())
                        .setEnd1(new AssociationEnd().setType(ENTITY_TYPE_1_1).setRole(ROLE_1_1).setMultiplicity(EdmMultiplicity.MANY))
                        .setEnd2(new AssociationEnd().setType(ENTITY_TYPE_1_2).setRole(ROLE_1_2).setMultiplicity(EdmMultiplicity.ONE));
            }
        }
        return null;
    }

    @Override
    public EntityContainerInfo getEntityContainerInfo(String name) throws ODataException {
        if (name == null || ENTITY_CONTAINER.equals(name)) {
            return new EntityContainerInfo().setName(ENTITY_CONTAINER).setDefaultEntityContainer(true);
        }
        return null;
    }

    @Override
    public EntitySet getEntitySet(String entityContainer, String name) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ENTITY_SET_NAME_ORDER_DETAILS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_1);
            } else if (ENTITY_SET_NAME_ORDERS.equals(name)) {
                return new EntitySet().setName(name).setEntityType(ENTITY_TYPE_1_2);
            }
        }
        return null;
    }

    @Override
    public AssociationSet getAssociationSet(String entityContainer, FullQualifiedName association, String sourceEntitySetName, String sourceEntitySetRole) throws ODataException {
        if (ENTITY_CONTAINER.equals(entityContainer)) {
            if (ASSOCIATION_ORDER_DETAIL_ORDER.equals(association)) {
                return new AssociationSet().setName(ASSOCIATION_SET)
                        .setAssociation(ASSOCIATION_ORDER_DETAIL_ORDER)
                        .setEnd1(new AssociationSetEnd().setRole(ROLE_1_2).setEntitySet(ENTITY_SET_NAME_ORDERS))
                        .setEnd2(new AssociationSetEnd().setRole(ROLE_1_1).setEntitySet(ENTITY_SET_NAME_ORDER_DETAILS));
            }
        }
        return null;
    }
}
