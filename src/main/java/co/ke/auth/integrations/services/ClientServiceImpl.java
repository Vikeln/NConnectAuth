package co.ke.auth.integrations.services;

import co.ke.auth.exceptions.DataUnMarshalingException;
import co.ke.auth.exceptions.NoDataFoundException;
import co.ke.auth.models.ClientServiceResponse;
import co.ke.auth.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static co.ke.auth.utils.Utilities.ERROR_LOG_FORMAT;
public class ClientServiceImpl<T extends Object> implements ClientService<T> {
    private String noDataString="No Data Found in Response Object for : ";
    public static final Logger logger = LoggerFactory.getLogger("MFS-DevUtils");


    private ObjectMapper mapper = Utilities.mapper();

    @Override
    public Optional<Iterable<T>> findAll(ResponseEntity<ClientServiceResponse> profileDto) {
        if (profileDto.getBody() != null) {
            try {
                Iterable<T> customerProfiles = (Iterable<T>) profileDto.getBody().getData();
                return Optional.ofNullable(customerProfiles);
            } catch (Exception e) {
                myLogger(e, null);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> findOne(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) {
        try {
            Optional<T> optionalT = getEntityDto(responseEntity, responseType);
            optionalT.ifPresent(t -> {
                String valueAsString = null;
                try {
                    valueAsString = mapper.writeValueAsString(t);
                } catch (JsonProcessingException e) {
                    logger.error(e.getOriginalMessage());
                }
                logger.info("{} {}", responseType.getName(), valueAsString);
            });

            return optionalT;
        } catch (Exception e) {
            myLogger(e, responseEntity.getBody().getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> edit(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) {
        try {
            return getEntityDto(responseEntity, responseType);
        } catch (Exception e) {
            myLogger(e, null);
        }
        return Optional.empty();
    }

    private Optional<T> getEntityDto(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) throws IOException {
        T customerProfile = null;
        if (responseEntity.getBody() != null) {
            if (responseEntity.getBody().getData() == null) {
                myLogger(null, noDataString.concat(responseType.getName()));
                myLogger(null, responseEntity.getBody().getMessage());
                return Optional.empty();
            }
            customerProfile = mapper.readValue(mapper.writeValueAsString(responseEntity.getBody().getData()), responseType);
        }

        return Optional.ofNullable(customerProfile);
    }

    @Override
    public Optional<T> getEntity(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) throws NoDataFoundException, DataUnMarshalingException {
        if (responseEntity.getBody() != null) {
            if (responseEntity.getBody().getData() == null || responseEntity.getBody().getData().equals("null")) {
                String message = responseEntity.getBody().getMessage() != null ? responseEntity.getBody().getMessage() : "";
                myLogger(null, noDataString.concat(responseType.getSimpleName() + " " + message));
                throw new NoDataFoundException("Error: " + message);
            }
            try {
                logger.info("ResponseBody => {}", Utilities.toJson(responseEntity.getBody().getData()));
                return Optional.of(mapper.readValue(mapper.writeValueAsString(responseEntity.getBody()), responseType));
            } catch (IOException e) {
                throw new DataUnMarshalingException(e.getLocalizedMessage(), e);
            }
        }
        return Optional.empty();
    }


    @Override
    public List<T> getEntityList(ResponseEntity<ClientServiceResponse> responseEntity, Class<T> responseType) throws NoDataFoundException {
        try {
            if (responseEntity.getBody() != null) {
                if (responseEntity.getBody().getData() == null) {
                    String message = responseEntity.getBody().getMessage() != null ? responseEntity.getBody().getMessage() : "";
                    myLogger(null, noDataString.concat(responseType.getSimpleName() + " " + message));
                    throw new NoDataFoundException("Error: " + message);
                }
                return mapper.readValue(mapper.writeValueAsString(responseEntity.getBody().getData()), new TypeReference<List<T>>() {
                });
            }
        } catch (IOException | NoDataFoundException e) {
            throw new NoDataFoundException(e.getLocalizedMessage(), e);
        }
        return Collections.emptyList();
    }

    private void myLogger(Exception e, String message) {
        Logger logger = LoggerFactory.getLogger(getClass());
        if (e != null) {
            logger.error(ERROR_LOG_FORMAT, e.getMessage());
            logger.debug(ERROR_LOG_FORMAT, Arrays.asList(e.getStackTrace()));
        }
        if (message != null) {
            logger.error(ERROR_LOG_FORMAT, message);
        }
    }


}
