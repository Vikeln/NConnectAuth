package co.ke.mymobi.controllers;


import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.RoleDto;
import co.ke.mymobi.services.RoleService;
import co.ke.mymobi.utils.Perm;
import co.ke.mymobi.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author TMwaura on 28/10/2019
 * @Project admin-dashboard
 */

@RestController
@RequestMapping(value = "/roles")
public class RolesController {
    @Autowired
    RoleService service;

    @GetMapping
//    @PreAuthorize(Perm.CAN_VIEW_ROLES)
    public ResponseEntity<AppResponseModel> home(HttpServletRequest request) {
        return ResponseEntity.ok(service.findAllRoles(Utilities.getRequestAppKey(request)));
    }
    @GetMapping("view/{roleId}")
//    @PreAuthorize(Perm.CAN_VIEW_ROLES)
    public ResponseEntity<AppResponseModel> viewOne(HttpServletRequest request, @PathVariable("roleId") Integer roleId) {
        return ResponseEntity.ok(service.findAllRoles(Utilities.getRequestAppKey(request), roleId));
    }

    @GetMapping("{roleId}")
//    @PreAuthorize(Perm.CAN_VIEW_ROLES)
    public ResponseEntity<AppResponseModel> getOne(HttpServletRequest request, @PathVariable("roleId") Integer roleId) {
        return ResponseEntity.ok(service.viewOneRoles(Utilities.getRequestAppKey(request), roleId));
    }


    @PostMapping(value = "/addrole")
    @PreAuthorize(Perm.CAN_CREATE_ROLES)
    public ResponseEntity<AppResponseModel> addRole(@RequestBody RoleDto role, HttpServletRequest request) {
        AppResponseModel appResponseModel = new AppResponseModel();
        if (role.getPermissions().isEmpty()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Cannot Add Role With Null Permissions");
            return ResponseEntity.badRequest().body(appResponseModel);
        }
        return ResponseEntity.ok(createUpdateRole(role, request));
    }

    @PutMapping(value = "/editrole")
    @PreAuthorize(Perm.CAN_EDIT_ROLES)
    public ResponseEntity<AppResponseModel> editRole(@RequestBody RoleDto role, HttpServletRequest request) {
        if (role.getId() == null) {
            AppResponseModel appResponseModel = new AppResponseModel();
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Cannot update Role With Null id");
            return ResponseEntity.ok(appResponseModel);
        }
        return ResponseEntity.ok(createUpdateRole(role, request));
    }

    public AppResponseModel createUpdateRole(RoleDto role, HttpServletRequest request) {
        return service.createRole(role, Utilities.getRequestAppKey(request));
    }
}
