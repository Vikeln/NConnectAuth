package co.ke.auth.clients;


import co.ke.auth.utils.RequestResponseLoggingInterceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

import static co.ke.auth.utils.Utilities.ERROR_LOG_FORMAT;


@Service
public class HttpClientBuilder<T extends Object> {
    private Logger logger = LoggerFactory.getLogger(HttpClientBuilder.class);

    @Value("${client.timeout}")
    private Integer timeout = 30000;

    private ResponseModel responseModel;

    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setReadTimeout(timeout);
        return clientHttpRequestFactory;
    }

    public ResponseEntity postEntity(UriComponents components, T token, HttpHeaders userHeaders, Class<?> responseType, HttpMethod method) {
        try {
            URI urlb = components.toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            if (userHeaders != null && !userHeaders.isEmpty()) {
                headers.addAll(userHeaders);
            }
            HttpEntity<T> entity = new HttpEntity<>(token, headers);
            RestTemplate template = new RestTemplate(getClientHttpRequestFactory());
            logger.info("requestURL={} ", urlb);
            template.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
            return template.exchange(urlb, method, entity, responseType);
        } catch (HttpClientErrorException ex) {
            logger.error("HttpClientErrorException=[statusCode={} responseBody={}]", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            return errorResponseBuilder(ex.getStatusCode(), ex);
        } catch (Exception ex) {
            logger.error(ERROR_LOG_FORMAT, ex.getMessage());
            return errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public ResponseEntity getEntity(UriComponents components, HttpHeaders userHeaders, Class<?> responseType) {
        try {
            URI finalURL = components.toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            if (userHeaders != null && !userHeaders.isEmpty()) {
                headers.addAll(userHeaders);
            }
            HttpEntity<Object> entity = new HttpEntity<>(HttpEntity.EMPTY, headers);
            RestTemplate template = new RestTemplate(getClientHttpRequestFactory());
            template.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
            logger.info("requestUrl={} request={}", finalURL, entity.getBody());
            ResponseEntity<?> responseEntity = template.exchange(finalURL, HttpMethod.GET, entity, responseType);
            logger.info("responseHeader={} statusCode={}", responseEntity.getHeaders(), responseEntity.getStatusCode());
            return responseEntity;
        } catch (HttpClientErrorException ex) {
            logger.error("HttpClientErrorException=[statusCode={} responseBody={}]", ex.getRawStatusCode(), ex.getResponseBodyAsString());
            return errorResponseBuilder(ex.getStatusCode(), ex);
        } catch (Exception ex) {
            logger.error("error=[{}]", ex.getMessage());
            return errorResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private ResponseEntity errorResponseBuilder(HttpStatus httpStatus, Exception ex) {
        responseModel = new ResponseModel(false);
        responseModel.setHttpStatus(httpStatus);
        if (ex instanceof HttpClientErrorException) {
            try {
                String message = "";
                JSONObject jsonObject = new JSONObject(((HttpClientErrorException) ex).getResponseBodyAsString());
                if (jsonObject.has("error")) {
                    message = message + jsonObject.getString("error") + ". ";
                }
                if (jsonObject.has("message")) {
                    System.out.println(message);
                    message = message + jsonObject.getString("message") + ". ";
                }
                responseModel.setMessage(message);
            } catch (Exception e) {
                logger.error("error={}", e.getMessage());
                responseModel.setMessage(((HttpClientErrorException) ex).getResponseBodyAsString());
            }
        } else {
            responseModel.setMessage(ex.getMessage());
        }

        return new ResponseEntity<>(responseModel, responseModel.getHttpStatus());
    }

    public UriComponents getUriComponent(String resourceURL, MultiValueMap<String, String> requestParams, String... pathUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resourceURL).pathSegment(pathUrl);
        return requestParams == null || requestParams.isEmpty()
                ? builder.build()
                : builder.queryParams(requestParams).build();
    }


}