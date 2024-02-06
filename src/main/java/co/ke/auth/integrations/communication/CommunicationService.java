package co.ke.auth.integrations.communication;

import co.ke.auth.clients.HttpClientBuilder;
import co.ke.auth.exceptions.DataUnMarshalingException;
import co.ke.auth.exceptions.NoDataFoundException;
import co.ke.auth.integrations.services.ClientServiceImpl;
import co.ke.auth.models.BulkMail;
import co.ke.auth.models.Mail;
import co.ke.auth.models.MessageModel;
import co.ke.auth.models.NewSmsModel;
import co.ke.auth.utils.Status;
import co.ke.auth.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommunicationService extends ClientServiceImpl<Status> {

    private HttpClientBuilder billerService = new HttpClientBuilder<Status>();

    @Value("${communication-url}")
    private String resourceUrl;

    public ResponseEntity sendSMS(NewSmsModel smsModel, Integer client) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

        map.add("contact", smsModel.getContact());
        map.add("entity", smsModel.getEntityId());
        map.add("entityType", smsModel.getEntityType());
        map.add("createdBy", smsModel.getCreatedBy());
        map.add("client", client);

        log.info("sms => {}", smsModel.getText());

        UriComponents uriComponents = billerService.getUriComponent(resourceUrl, map, "comm", "v1", "sms", "blank");

        try {
            return billerService.postEntity(uriComponents, smsModel, headers, Status.class, HttpMethod.POST);


        } catch (Exception e) {
            log.info("Error occurred during CommunicationService send sms =>  " + Utilities.toJson(e.getMessage()));
            return null;
        }

    }

    public Status sendBulkSMS(List<MessageModel> smsModel, Integer client, String createdBy) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

        map.add("createdBy", createdBy);
        map.add("client", client);
        map.add("messages", smsModel);

        UriComponents uriComponents = billerService.getUriComponent(resourceUrl, map, "comm", "v1", "sms","bulk");

        try {
            Optional<Status> optionalUserResponse = getEntity(billerService.postEntity(uriComponents, smsModel, headers, Status.class, HttpMethod.POST), Status.class);

            return optionalUserResponse.orElse(null);

        } catch (NoDataFoundException | DataUnMarshalingException e) {
            log.info("Error occurred during CommunicationService send sms =>  " + Utilities.toJson(e.getMessage()));
            return null;
        }

    }

    public ResponseEntity sendEmailData(Mail smsModel, Integer entityId, String entityType, String createdBy, Integer client) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        LinkedMultiValueMap<String, Object> formValues = new LinkedMultiValueMap<String, Object>();

        formValues.add("contact", smsModel.getTo());
        formValues.add("entity", entityId);
        formValues.add("client", client);
        formValues.add("entityType", entityType);
        formValues.add("createdBy", createdBy);

        UriComponents uriComponents = billerService.getUriComponent(resourceUrl, formValues, "comm", "v1", "email","blank");

        try {
            return billerService.postEntity(uriComponents, smsModel, headers, Status.class, HttpMethod.POST);

        } catch (Exception e) {
            log.info("Error occurred during CommunicationService send email =>  " + Utilities.toJson(e.getMessage()));
            return null;
        }

    }

    public Status sendBulkEmailData(List<BulkMail> smsModel, String createdBy, Integer client) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        LinkedMultiValueMap<String, Object> formValues = new LinkedMultiValueMap<String, Object>();

        formValues.add("mails", smsModel);
        formValues.add("client", client);
        formValues.add("createdBy", createdBy);

        UriComponents uriComponents = billerService.getUriComponent(resourceUrl, formValues, "comm", "v1", "email", "bulk");

        try {
            Optional<Status> optionalUserResponse = getEntity(billerService.postEntity(uriComponents, smsModel, headers, Status.class, HttpMethod.POST), Status.class);

            return optionalUserResponse.orElse(null);

        } catch (Exception e) {
            log.info("Error occurred during CommunicationService send emails =>  " + Utilities.toJson(e.getMessage()));
            return null;
        }

    }

}
