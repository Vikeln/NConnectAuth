package co.ke.auth.services;

import co.ke.auth.entities.Tenant;
import co.ke.auth.models.AppResponseModel;
import co.ke.auth.models.TenantDTo;

import java.util.Optional;

public interface TenantService {
    AppResponseModel findAllTenants();

    Optional<Object> createUpdateTenant(Tenant tenant, TenantDTo tenantDTo, boolean createUSer);
}
