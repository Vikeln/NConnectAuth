package co.ke.auth.controllers;

import co.ke.auth.services.RoleService;
import co.ke.auth.models.AppResponseModel;
import co.ke.auth.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("privileges")
public class PrivilegeController {

    @Autowired
    RoleService service;
    @Autowired
    PermissionService permissionService;

    @GetMapping
    public ResponseEntity<AppResponseModel> home(HttpServletRequest request) {
        return ResponseEntity.ok(service.findAllPrivileges(request.getHeader("App-Key")));
    }

    @PostMapping
    public ResponseEntity<AppResponseModel> create(HttpServletRequest request, @RequestBody List<String> permissions) {
        return ResponseEntity.ok(permissionService.createRealmPermissions(permissions));
    }
}
