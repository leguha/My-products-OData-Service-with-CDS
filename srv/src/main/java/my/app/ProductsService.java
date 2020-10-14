package my.app;

import java.util.ArrayList;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.service.prov.api.*;
import com.sap.cloud.sdk.service.prov.api.annotations.*;
import com.sap.cloud.sdk.service.prov.api.exits.*;
import com.sap.cloud.sdk.service.prov.api.request.*;
import com.sap.cloud.sdk.service.prov.api.response.*;
import org.slf4j.*;

public class ProductsService {

    private static final Logger LOG = CloudLoggerFactory.getLogger(ProductsService.class.getName());

    @BeforeRead(entity = "Products", serviceName = "CatalogService")
    public BeforeReadResponse beforeReadProducts(ReadRequest req, ExtensionHelper h) {
        LOG.error("##### Products - beforeReadProducts ########");
        return BeforeReadResponse.setSuccess().response();
    }

    @AfterRead(entity = "Products", serviceName = "CatalogService")
    public ReadResponse afterReadProducts(ReadRequest req, ReadResponseAccessor res, ExtensionHelper h) {
        EntityData ed = res.getEntityData();
        EntityData ex = EntityData.getBuilder(ed).addElement("amount", 1000).buildEntityData("Products");
        return ReadResponse.setSuccess().setData(ex).response();
    }

    @AfterQuery(entity = "Products", serviceName = "CatalogService")
    public QueryResponse afterQueryProducts(QueryRequest req, QueryResponseAccessor res, ExtensionHelper h) {
        List<EntityData> dataList = res.getEntityDataList(); // original list
        List<EntityData> modifiedList = new ArrayList<EntityData>(dataList.size()); // modified list
        for (EntityData ed : dataList) {
            EntityData ex = EntityData.getBuilder(ed).addElement("amount", 1000).buildEntityData("Products");
            modifiedList.add(ex);
        }
        return QueryResponse.setSuccess().setData(modifiedList).response();
    }
}