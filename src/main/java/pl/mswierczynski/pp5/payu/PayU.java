package pl.mswierczynski.pp5.payu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import pl.mswierczynski.pp5.payu.exceptions.PayUException;
import pl.mswierczynski.pp5.payu.http.PayUApiClient;
import pl.mswierczynski.pp5.payu.model.CreateOrderResponse;
import pl.mswierczynski.pp5.payu.model.OrderCreateRequest;
import pl.mswierczynski.pp5.payu.model.TokenResponse;

import java.net.http.HttpResponse;
import java.util.Map;

public class PayU {
    public static final int HTTP_FORBIDDEN = 401;
    private final PayUCredentials credentials;
    private final PayUApiClient http;
    private final ObjectMapper om;
    private TokenResponse token;

    public PayU(PayUCredentials credentials, PayUApiClient payUApiClient) {
        this.credentials = credentials;
        this.http = payUApiClient;
        this.om = new ObjectMapper();
        this.om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.token = new TokenResponse();
    }

    public CreateOrderResponse handle(OrderCreateRequest orderCreateRequest) throws PayUException {
        orderCreateRequest.setMerchantPosId(credentials.getPosId());
        HttpResponse<String> response = handleOrderCreation(orderCreateRequest);

        if (response.statusCode() == HTTP_FORBIDDEN) {
            this.authorize();
            response = handleOrderCreation(orderCreateRequest);
        }

        return mapToCreateOrderResponse(response.body());
    }

    private void authorize() throws PayUException {
        var body = String.format(
            "grant_type=client_credentials&client_id=%s&client_secret=%s",
            credentials.getClientId(),
            credentials.getClientSecret()
        );

        HttpResponse<String> response = http.post(
            getUrl("/pl/standard/user/oauth/authorize"),
            body,
            Map.of(
                "Content-Type", "application/x-www-form-urlencoded"
            )
        );

        try {
            this.token = om.readValue(response.body(), TokenResponse.class);
        } catch (JsonProcessingException e) {
            throw new PayUException(e);
        }
    }

    private String valueAsString(OrderCreateRequest exampleOrderCreateRequest) throws PayUException {
        try {
            return om.writeValueAsString(exampleOrderCreateRequest);
        } catch (JsonProcessingException e) {
            throw new PayUException(e);
        }
    }

    private HttpResponse<String> handleOrderCreation(OrderCreateRequest orderCreateRequest) throws PayUException {

        HttpResponse<String> response = http.post(
            getUrl("/api/v2_1/orders"),
            valueAsString(orderCreateRequest),
            Map.of(
                "Content-Type", "application/json",
                "Authorization", String.format("Bearer %s", token.getAccessToken())
            )
        );
        return response;
    }

    private CreateOrderResponse mapToCreateOrderResponse(String body) throws PayUException {
        try {
            return om.readValue(body, CreateOrderResponse.class);
        } catch (JsonProcessingException e) {
            throw new PayUException(e);
        }
    }

    private String getUrl(String url) {
        return String.format("%s%s", credentials.getBaseUrl(), url);
    }

    public boolean isTrusted(String orderAsString, String signature) {
        var toHashed = orderAsString + credentials.getSecondKey();
        var md5Checksum = DigestUtils.md5Hex((String) toHashed).toUpperCase();

        return md5Checksum.equals(signature.toUpperCase());
    }
}
