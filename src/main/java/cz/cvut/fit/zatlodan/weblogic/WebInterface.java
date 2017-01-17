package cz.cvut.fit.zatlodan.weblogic;

import cz.cvut.fit.zatlodan.datamanip.models.Customer;
import cz.cvut.fit.zatlodan.datamanip.models.Sale;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 21.12.16.
 */
public class WebInterface {

    private static String BASE_REST_URI = "http://localhost:8080/SpringTermWork/rest";

    public static List<Customer> getAllCustomers() {
        final String uri = BASE_REST_URI + "/customers";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Customer[]> responseEntity = rt.getForEntity(uri, Customer[].class);
        Customer[] customers = responseEntity.getBody();
        ArrayList<Customer> list = new ArrayList<>();
        for (Customer c : customers) {
            list.add(c);
        }
        return list;
    }

    public static List<Sale> getAllCustomerSales(Long id) {
        final String uri = BASE_REST_URI + "/customers/" + Long.toString(id);
        RestTemplate rt = new RestTemplate();
        System.out.println(id);
        Customer c = rt.getForObject(uri, Customer.class);
        return c.getSales();
    }

    public static void deleteCustomer(Long id) {
        final String uri = BASE_REST_URI + "/customers/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(id));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri, params);
    }

    public static void createCustomer(String name, String emailAddress, String address, String contact) {
        final String uri = BASE_REST_URI + "/customers";
        RestTemplate restTemplate = new RestTemplate();

        if (name.isEmpty() || address.isEmpty()) {
            throw new IllegalArgumentException("Create Customer: Name and address in Customer cannot be empty.");
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
        params.add("emailAddress", emailAddress);
        params.add("address", address);
        params.add("contact", contact);

        String c = restTemplate.postForObject(uri, params, String.class);
    }

    public static void updateCustomer(Long id, String name, String emailAddress, String address, String contact) {
        final String uri = BASE_REST_URI + "/customers/" + Long.toString(id);
        RestTemplate restTemplate = new RestTemplate();

        if (emailAddress == null) {
            emailAddress = "";
        }
        if (contact == null) {
            contact = "";
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
        params.add("emailAddress", emailAddress);
        params.add("address", address);
        params.add("contact", contact);

        restTemplate.put(uri, params);
    }

    public static void createSale(String info, String date, String status, String customerId) {
        final String uri = BASE_REST_URI + "/sales";
        RestTemplate restTemplate = new RestTemplate();

        if (date.isEmpty() || status.isEmpty() || customerId.isEmpty()) {
            throw new IllegalArgumentException("Create Sale: Invalid create argument (date|status|custId).");
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("info", info);
        params.add("date", date);
        params.add("status", status);
        params.add("custId", customerId);
        String c = restTemplate.postForObject(uri, params, String.class);
    }

    public static void updateSale(Long id, String info, String date, short status, Long customerId) {
        final String uri = BASE_REST_URI + "/sales/" + Long.toString(id);
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("info", info);
        params.add("date", date);
        params.add("status", Short.toString(status));
        params.add("custId", Long.toString(customerId));

        restTemplate.put(uri, params);
    }

    public static void deleteSale(Long id) {
        final String uri = BASE_REST_URI + "/sales/" + Long.toString(id);

        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(id));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(uri, params);
    }

    public static List<Sale> getAllSales() {
        final String uri = BASE_REST_URI + "/sales";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Sale[]> responseEntity = rt.getForEntity(uri, Sale[].class);
        Sale[] sales = responseEntity.getBody();
        ArrayList<Sale> list = new ArrayList<>();
        for (Sale c : sales) {
            list.add(c);
        }
        return list;
    }

    public static String getBaseRestUri() {
        return BASE_REST_URI;
    }

    public static void setBaseRestUri(String baseRestUri) {
        BASE_REST_URI = baseRestUri;
    }
}
