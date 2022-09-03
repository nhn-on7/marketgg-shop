package com.nhnacademy.marketgg.server.elastic.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import com.nhnacademy.marketgg.server.dto.response.customerservice.PostResponse;
import com.nhnacademy.marketgg.server.dto.response.product.ProductListResponse;
import com.nhnacademy.marketgg.server.elastic.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.request.SearchRequestBodyForBool;
import com.nhnacademy.marketgg.server.util.KoreanToEnglishTranslator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * 검색을 위한 Adapter 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SearchAdapter implements SearchRepository {

    @Value("${gg.elastic.origin}")
    private String elastic;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final KoreanToEnglishTranslator translator;
    private final JSONParser parser;
    private static final String DEFAULT_ELASTIC_PRODUCT = "/products/_search";
    private static final String DEFAULT_ELASTIC_BOARD = "/boards/_search";
    private static final String PRODUCT = "product";
    private static final String BOARD = "board";
    private static final Integer PAGE_SIZE = 10;

    @Override
    public PageEntity<List<ProductListResponse>> searchProductForCategory(final SearchRequest request,
                                                                          final String priceSortType)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(priceSortType);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(
                new SearchRequestBodyForBool<>(request.getCategoryCode(), sort, request,
                                               translator.converter(request.getKeyword()),
                                               PRODUCT)), this.buildHeaders());

        String response = this.doRequest(requestEntity, PRODUCT).getBody();

        return new PageEntity<>(
                request.getPage(), request.getSize(),
                this.parsingPageData(response), this.parsingResponseBody(response));
    }

    @Override
    public PageEntity<List<ProductListResponse>> searchProductWithKeyword(final SearchRequest request,
                                                                          final String priceSortType)
            throws ParseException, JsonProcessingException {

        Map<String, String> sort = this.buildSort(priceSortType);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(
                new SearchRequestBodyForBool<>(sort, request, translator.converter(request.getKeyword()))),
                                                            this.buildHeaders());

        String response = this.doRequest(requestEntity, PRODUCT).getBody();

        return new PageEntity<>(
                request.getPage(), request.getSize(),
                this.parsingPageData(response), this.parsingResponseBody(response));
    }

    @Override
    public List<PostResponse> searchBoardWithCategoryCode(final SearchRequest request,
                                                          final String option)
            throws JsonProcessingException, ParseException {

        Map<String, String> sort = this.buildSort(null);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(
                new SearchRequestBodyForBool<>(request.getCategoryCode(), sort, request,
                                               translator.converter(request.getKeyword()),
                                               option)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, BOARD).getBody());
    }

    @Override
    public List<PostResponse> searchBoardWithOption(final String optionCode,
                                                    final SearchRequest request,
                                                    final String option)
            throws JsonProcessingException, ParseException {

        Map<String, String> sort = this.buildSort(null);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(
                new SearchRequestBodyForBool<>(request.getCategoryCode(), sort, request,
                                               translator.converter(request.getKeyword()),
                                               optionCode, option)), this.buildHeaders());

        return this.parsingResponseBody(this.doRequest(requestEntity, BOARD).getBody());
    }

    private Map<String, String> buildSort(final String type) {
        Map<String, String> sortMap = new LinkedHashMap<>();

        if (Objects.nonNull(type)) {
            sortMap.put("price", type);
        }
        sortMap.put("_score", "desc");
        sortMap.put("_id", "asc");

        return sortMap;
    }

    private ResponseEntity<String> doRequest(final HttpEntity<String> request,
                                             final String document) {
        log.error(request.toString());
        String requestUri = DEFAULT_ELASTIC_PRODUCT;
        if (document.compareTo(PRODUCT) != 0) {
            requestUri = DEFAULT_ELASTIC_BOARD;
        }

        return restTemplate.exchange(elastic + requestUri,
                                     HttpMethod.POST,
                                     request,
                                     String.class);
    }

    private <T> List<T> parsingResponseBody(final String response)
            throws ParseException, JsonProcessingException {

        List<T> list = new ArrayList<>();
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONArray hitBody = (JSONArray) hits.get("hits");
        for (Object data : hitBody) {
            JSONObject source = (JSONObject) data;
            JSONObject body = (JSONObject) source.get("_source");
            list.add(objectMapper.readValue(body.toJSONString(), new TypeReference<>() {
            }));
        }

        return list;
    }

    private Integer parsingPageData(final String response) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(response);
        JSONObject hits = (JSONObject) jsonObject.get("hits");
        JSONObject total = (JSONObject) hits.get("total");
        Long totalValue = (Long) total.get("value");

        return Math.toIntExact(totalValue / PAGE_SIZE + 1);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }

}
