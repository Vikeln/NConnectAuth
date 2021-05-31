package co.ke.mymobi.services;

import co.ke.mymobi.models.AppResponseModel;

import java.util.List;

public interface PermissionService {

    public AppResponseModel createRealmPermissions(List<String> permissions);

}
