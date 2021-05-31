package co.ke.mymobi.services;

import co.ke.mymobi.entities.Tenant;
import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.TenantDTo;

import java.util.Optional;

public interface TenantService {
    AppResponseModel findAllTenants();

    Optional<Object> createUpdateTenant(Tenant tenant, TenantDTo tenantDTo, boolean createUSer);
}
