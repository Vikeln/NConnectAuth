package co.ke.auth.services.impl;

import co.ke.auth.models.AppResponseModel;
import co.ke.auth.models.ValidationMessages;
import co.ke.auth.services.ResponseInterface;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class ResponseInterfaceImpl implements ResponseInterface<AppResponseModel, Optional> {

    @Override
    public AppResponseModel newEntityResponse(Optional entity) {
        AppResponseModel responseModel = new AppResponseModel(false);
        if (entity.isPresent()) {
            responseModel = new AppResponseModel(true);
            responseModel.setData(entity);
            String tName = entity.get().getClass()
                    .getSimpleName();

            tName = (tName.endsWith("s") ? tName.substring(0, tName.length() - 1) : tName).concat(" created");

            responseModel.setMessage(tName);
            responseModel.setHttpStatus(HttpStatus.CREATED);
        } else {
            responseModel.setMessage("error creating new entity");
        }
        return responseModel;
    }

    @Override
    public AppResponseModel entityResponse(Object param, Optional entity, String message, Class<?> Class) {
        if (param == null) {
            AppResponseModel resmodel = new AppResponseModel(false);

            resmodel.setMessage(message);
            Logger.getLogger(Class.getName()).log(Logger.Level.WARN, "null parameter passed");
            resmodel.setHttpStatus(HttpStatus.BAD_REQUEST);
            return resmodel;
        }
        AppResponseModel responseModel = new AppResponseModel(entity.isPresent() ? true : false);
        responseModel.setData(entity);
        responseModel.setMessage(message);
        responseModel.setHttpStatus(HttpStatus.OK);
        return responseModel;
    }

    @Override
    public AppResponseModel validationResponse(ValidationMessages entity) {

        AppResponseModel responseModel = new AppResponseModel(false);
        responseModel.setData(entity);
        responseModel.setMessage("Validation errors");
        responseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
        return responseModel;
    }

    @Override
    public AppResponseModel entityResponse(Object[] params, Optional entity, String message, Class<?> Class) {
        if (params == null || Arrays.asList(params).contains(null)) {
            AppResponseModel resmodel = new AppResponseModel(false);

            resmodel.setMessage("null parameter passed");
            Logger.getLogger(Class.getName()).log(Logger.Level.WARN, "null parameter passed");
            resmodel.setHttpStatus(HttpStatus.BAD_REQUEST);
            return resmodel;
        }
        AppResponseModel responseModel = new AppResponseModel(true);
        responseModel.setData(entity);
        responseModel.setMessage(message);
        responseModel.setHttpStatus(HttpStatus.OK);
        return responseModel;
    }

    @Override
    public AppResponseModel collectionResponse(Collection collection) {
        AppResponseModel responseModel = new AppResponseModel(true);
        responseModel.setHttpStatus(HttpStatus.OK);

        if (collection.isEmpty()) {
            responseModel.setMessage("no records found");
            responseModel.setData(Optional.empty());
            return responseModel;
        }
        responseModel.setData(collection);
        responseModel.setMessage(collection.size() + " records");
        return responseModel;
    }

    @Override
    public AppResponseModel enumResponseModel(Class e, boolean status) {
        AppResponseModel model = new AppResponseModel();
        model.setHttpStatus(HttpStatus.BAD_REQUEST);
        model.setStatus(status);
        if (e.isEnum()) {

            Object[] objList = e.getEnumConstants();

            if (status) {
                model.setData(objList);
            } else {
                model.setMessage(String.format("invalid parameter passed. Expected parameters%s", Arrays.asList(objList)));
            }
            return model;
        }
        throw new IllegalArgumentException(String.format("class[%s] not an enum", e.getName()));
    }
}
