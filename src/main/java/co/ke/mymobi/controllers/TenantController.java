package co.ke.mymobi.controllers;

import co.ke.mymobi.entities.User;
import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.TenantDTo;
import co.ke.mymobi.repositories.UserDao;
import co.ke.mymobi.services.TenantService;
import co.ke.mymobi.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping(value = "tenants")
public class TenantController {
    ObjectMapper mapper = Utilities.mapper();
    Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private UserDao userDao;


    @Autowired
    private TenantService tenantService;

    @GetMapping
    public ResponseEntity<AppResponseModel> list() {
        return ResponseEntity.ok(tenantService.findAllTenants());
    }

    public Optional<User> findTopByUserNameIgnoreCaseOrEmail(String email, String userName) {
        return userDao.findByEmailOrUserNameIgnoreCase(email, userName);
    }


    @PostMapping(value = "/create-tenant")
    @SneakyThrows
    public ResponseEntity<AppResponseModel> addTenant(@RequestBody TenantDTo tenantDTo, HttpServletRequest request, @RequestParam boolean createUSer) {
        tenantDTo.getTenant().setId(null);
        AppResponseModel appResponseModel = new AppResponseModel();
        appResponseModel.setStatus(false);
        if (createUSer) {
            Optional<User> userNameOrEmail = findTopByUserNameIgnoreCaseOrEmail(tenantDTo.getAdminEmail(), tenantDTo.getAdminEmail());

            if (userNameOrEmail.isPresent()) {
                String existingRole = mapper.writeValueAsString(userNameOrEmail.get());
                appResponseModel.setMessage(existingRole);
                return ResponseEntity.ok(appResponseModel);
            }
        }

        return ResponseEntity.ok(createUpdateTenant(tenantDTo, createUSer));
    }

    @PutMapping(value = "/update-tenant")
//    @PreAuthorize(Perm.CAN_CREATE_ROLES)
    public ResponseEntity<AppResponseModel> updateTenant(@RequestBody TenantDTo tenantDTo, HttpServletRequest request) {
        AppResponseModel appResponseModel = new AppResponseModel();
        if (tenantDTo.getTenant().getId() == null) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Cannot update a tenant with a NULL id");
            return ResponseEntity.ok(appResponseModel);
        }
        return ResponseEntity.ok(createUpdateTenant(tenantDTo, false));
    }

    public AppResponseModel createUpdateTenant(TenantDTo tenant, boolean createUSer) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<Object> tenantOptional = tenantService.createUpdateTenant(tenant.getTenant(), tenant, createUSer);
        if (!tenantOptional.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Tenant with similar name already Exists");
            return appResponseModel;
        }
        appResponseModel.setStatus(true);
        appResponseModel.setMessage(tenant.getTenant().getId() != null ? "Tenant updated successfully!" : "Tenant created successfully!");

        appResponseModel.setData(tenantOptional.get());
        return appResponseModel;
    }

}
