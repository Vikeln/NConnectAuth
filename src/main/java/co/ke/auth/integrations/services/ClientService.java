package co.ke.auth.integrations.services;


import co.ke.auth.exceptions.DataUnMarshalingException;
import co.ke.auth.exceptions.NoDataFoundException;
import co.ke.auth.models.ClientServiceResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService<T extends Object> {
    Optional<Iterable<T>> findAll(ResponseEntity<ClientServiceResponse> responseEntity);

    Optional<T> getEntity(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) throws NoDataFoundException, DataUnMarshalingException;

    List<T> getEntityList(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) throws NoDataFoundException;
    Optional<T> findOne(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType);
    Optional<T> edit(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType);
    
}
