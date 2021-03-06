package za.co.moronicgeek.spring.swagger.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import za.co.moronicgeek.spring.swagger.client.properties.SwaggerCloudAdminProperties;
import za.co.moronicgeek.spring.swagger.client.properties.SwaggerCloudClientProperties;
import za.co.moronicgeek.swagger.cloud.model.AdminRoutes;
import za.co.moronicgeek.swagger.cloud.model.ApplicationRegistrationMetadata;

import java.util.Collections;
import java.util.Map;

/**
 * Created by muhammedpatel on 2016/08/06.
 * <p>
 * This class will register an api with the swager cloud server
 */
public class ApplicationRegistrationService {

    private SwaggerCloudAdminProperties adminProperties;
    private SwaggerCloudClientProperties clientProperties;
    private RestTemplate template;
    private static HttpHeaders HTTP_HEADERS = createHttpHeaders();

    public ApplicationRegistrationService(SwaggerCloudClientProperties clientProperties, SwaggerCloudAdminProperties adminProperties, RestTemplate template) {
        this.adminProperties = adminProperties;
        this.clientProperties = clientProperties;
        this.template = template;
    }

    public boolean registerApplication() {
        boolean registrationSuccessful = false;
        ApplicationRegistrationMetadata self = createMetaDataApplication();
        //this will be used some day
        ResponseEntity<Map> response = template.postForEntity(adminProperties.getUrl() + AdminRoutes.REGISTER.getPath(),
                new HttpEntity<>(self, HTTP_HEADERS), Map.class);

        return registrationSuccessful;
    }

    public boolean deregisterApplication() {
        boolean deregistered = false;
        ApplicationRegistrationMetadata self = createMetaDataApplication();
        //this will be used some day
        ResponseEntity<Map> response = template.postForEntity(adminProperties.getUrl() + AdminRoutes.DEREGISTER.getPath(),
                new HttpEntity<>(self, HTTP_HEADERS), Map.class);
        return deregistered;
    }

    private ApplicationRegistrationMetadata createMetaDataApplication() {
        return ApplicationRegistrationMetadata.create(clientProperties.getName()).withSwaggerUrl(clientProperties.getSwaggerUrl()).withGroupId(clientProperties.getGroupId()).build();
    }

    private static HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return HttpHeaders.readOnlyHttpHeaders(headers);
    }


    public SwaggerCloudAdminProperties getAdminProperties() {
        return adminProperties;
    }

    public void setAdminProperties(SwaggerCloudAdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    public SwaggerCloudClientProperties getClientProperties() {
        return clientProperties;
    }

    public void setClientProperties(SwaggerCloudClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }

    public RestTemplate getTemplate() {
        return template;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public static HttpHeaders getHttpHeaders() {
        return HTTP_HEADERS;
    }

    public static void setHttpHeaders(HttpHeaders httpHeaders) {
        HTTP_HEADERS = httpHeaders;
    }
}
