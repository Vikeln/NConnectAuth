package co.ke.auth.controllers;


import co.ke.auth.models.AppResponseModel;
import co.ke.auth.models.UserDto;
import co.ke.auth.repositories.UserTypeDao;
import co.ke.auth.services.UserService;
import co.ke.auth.utils.Utilities;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;


@RequestMapping(value = "/users")
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserTypeDao userTypeDao;

    @GetMapping
    @RolesAllowed("CAN_VIEW_USERS")
    public ResponseEntity<AppResponseModel> list(HttpServletRequest request) {
        return ResponseEntity.ok(userService.findAll(Utilities.getRequestAppKey(request)));
    }

    @GetMapping("admins")
    @RolesAllowed("CAN_VIEW_USERS")
    public ResponseEntity<AppResponseModel> listAdmins(HttpServletRequest request) {
        return ResponseEntity.ok(userService.findAll(Utilities.getRequestAppKey(request)));
    }


    @GetMapping("view/{userId}")
    @RolesAllowed("CAN_VIEW_USER_DETAILS")
    public ResponseEntity<AppResponseModel> viewOne(HttpServletRequest request, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.viewOne(Utilities.getRequestAppKey(request), userId));
    }

    @GetMapping("/{userId}")
    @RolesAllowed("CAN_VIEW_USER_DETAILS")
    public ResponseEntity<AppResponseModel> getOne(HttpServletRequest request, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.findOne(Utilities.getRequestAppKey(request), userId));
    }


    @PostMapping(path = "/create")
    @RolesAllowed("CAN_CREATE_USERS")
    public ResponseEntity<AppResponseModel> createUser(@RequestBody UserDto userDTO, HttpServletRequest request) {
        return ResponseEntity.ok(userService.createUpdateUsers(Utilities.getRequestAppKey(request), userDTO,2));
    }

    @PostMapping(path = "/admin/create")
    @RolesAllowed("CAN_CREATE_USERS")
    public ResponseEntity<AppResponseModel> createAdminUser(@RequestBody UserDto userDTO, HttpServletRequest request) {
        return ResponseEntity.ok(userService.createUpdateUsers(Utilities.getRequestAppKey(request), userDTO,1));
    }


    @PostMapping(path = "/update")
    @RolesAllowed("CAN_EDIT_USERS")
    public ResponseEntity<AppResponseModel> updateUser(@RequestBody UserDto userDTO, HttpServletRequest request) {
        if (userDTO.getId() == null) {
            AppResponseModel appResponseModel = new AppResponseModel();
            appResponseModel.setMessage("Cannot update a null id");
            appResponseModel.setStatus(false);
            return ResponseEntity.ok(appResponseModel);
        }
        return ResponseEntity.ok(userService.createUpdateUsers(Utilities.getRequestAppKey(request), userDTO, null));
    }


    @GetMapping(path = "/enable")
    @RolesAllowed("CAN_ENABLE_USER_ACCOUNTS")
    public ResponseEntity<AppResponseModel> enableUser(@RequestParam(required = true) String userName) {
        return ResponseEntity.ok(userService.enableUser(userName));
    }

    @GetMapping("/user-types")
    @ApiOperation(value = "get user types")
    public ResponseEntity getTypes() {
        return ResponseEntity.ok().body(userTypeDao.findAll());
    }

    @GetMapping(path = "/deactivateUser/{userId}")
    public ResponseEntity<AppResponseModel> deactivateUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.changeUserAccountStatus(userId));
    }
}