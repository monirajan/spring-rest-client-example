package guru.springframework.springrestclientexample;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestClientExample {

    public static final String API_ROOT = "https://api.predic8.de:443/shop";

    @Test
    public void getCategories()
    {
        String uri = API_ROOT + "/categories/";
        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(uri, JsonNode.class);
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers()
    {
        String uri = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(uri, JsonNode.class);
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomer()
    {
        String uri = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("firstname","Monisha");
        objectMap.put("lastname","Rajan");

        JsonNode jsonNode = restTemplate.postForObject(uri, objectMap, JsonNode.class);
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateCustomer()
    {
        String uri = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("firstname","Monisha");
        objectMap.put("lastname","Rajan");

        JsonNode jsonNode = restTemplate.postForObject(uri, objectMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customer_url = jsonNode.get("customer_url").textValue();
        String id = customer_url.split("/")[3];

        objectMap.put("firstname","Moni");
        objectMap.put("lastname","Rajan");

        restTemplate.put(uri + id, objectMap);

        jsonNode = restTemplate.getForObject(uri + id, JsonNode.class);
        System.out.println(jsonNode.toString());
    }

    @Test(expected = ResourceAccessException.class)
    public void updateUsingSunHttpCustomer() {

        String uri = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("firstname", "Monisha");
        objectMap.put("lastname", "Rajan");

        JsonNode jsonNode = restTemplate.postForObject(uri, objectMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customer_url = jsonNode.get("customer_url").textValue();
        String id = customer_url.split("/")[3];

        objectMap.put("firstname", "Moni");
        objectMap.put("lastname", "Rajan");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(objectMap,httpHeaders);
        JsonNode updatedNode = restTemplate.patchForObject(uri+id,httpEntity,JsonNode.class);

        System.out.println(updatedNode.toString());
    }

    @Test
    public void updateCustomerUsingSunHttpCustomer() {

        String uri = API_ROOT + "/customers/";

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("firstname", "Monisha");
        objectMap.put("lastname", "Rajan");

        JsonNode jsonNode = restTemplate.postForObject(uri, objectMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customer_url = jsonNode.get("customer_url").textValue();
        String id = customer_url.split("/")[3];

        objectMap.put("firstname", "Moni");
        objectMap.put("lastname", "Rajan");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(objectMap,httpHeaders);
        JsonNode updatedNode = restTemplate.patchForObject(uri+id,httpEntity,JsonNode.class);

        System.out.println(updatedNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() {

        String uri = API_ROOT + "/customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("firstname", "Monisha");
        objectMap.put("lastname", "Rajan");

        JsonNode jsonNode = restTemplate.postForObject(uri, objectMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customer_url = jsonNode.get("customer_url").textValue();
        String id = customer_url.split("/")[3];

        restTemplate.delete(uri+id);

        jsonNode = restTemplate.getForObject(uri+id, JsonNode.class);
        System.out.println(jsonNode.toString());

    }
}
