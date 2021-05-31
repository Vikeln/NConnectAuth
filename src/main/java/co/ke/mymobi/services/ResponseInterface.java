package co.ke.mymobi.services;

import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.ValidationMessages;

import java.util.Collection;

public interface ResponseInterface<X, T extends Object> {

    public X newEntityResponse(T t);

    public X entityResponse(Object param, T t, String message, Class<?> Class);

    AppResponseModel validationResponse(ValidationMessages entity);

    public X entityResponse(Object[] params, T t, String message, Class<?> Class);

    public X collectionResponse(Collection collection);

    AppResponseModel enumResponseModel(Class e, boolean status);
}

