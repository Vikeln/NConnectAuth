package co.ke.auth.services;

import co.ke.auth.models.AppResponseModel;

import java.util.List;

public interface PermissionService {

    public AppResponseModel createRealmPermissions(List<String> permissions);

}
