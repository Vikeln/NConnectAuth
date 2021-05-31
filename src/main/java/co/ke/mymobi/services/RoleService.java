package co.ke.mymobi.services;


import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.RoleDto;

public interface RoleService {
    public AppResponseModel findAllRoles(String tenantAppKey);

    AppResponseModel createRole(RoleDto role, String tenantAppKey);

    AppResponseModel findAllPrivileges( String tenantAppKey);

    AppResponseModel findAllRoles(String requestAppKey, Integer userId);

    AppResponseModel viewOneRoles(String requestAppKey, Integer userId);
}
