package com.nhnacademy.marketgg.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.server.elastic.dto.request.SearchRequest;
import com.nhnacademy.marketgg.server.elastic.dto.response.SearchBoardResponse;
import com.nhnacademy.marketgg.server.exception.NotFoundException;
import com.nhnacademy.marketgg.server.service.PostService;
import com.nhnacademy.marketgg.server.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 게시판 검색을 지원하는 Controller 입니다.
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/shop/v1/search/boards")
@RequiredArgsConstructor
public class SearchBoardController {

    private final PostService postService;
    private final SearchService searchService;

    /**
     * 카테고리 내에서 검색어를 통한 검색 후, 검색 결과 목록을 담은 후 검색 목록 조회페이지로 이동합니다.
     *
     * @param categoryCode - 지정한 카테고리의 식별번호입니다.
     * @param keyword      - 검색을 진행할 검색어입니다.
     * @param pageable     - 검색 목록의 페이지 정보입니다.
     * @return 지정한 카테고리 내의 검색어를 통한 검색 결과 목록을 반환합니다.
     * @throws JsonProcessingException JSON 콘텐츠를 처리(파싱, 생성)할 때 발생하는 모든 문제시 예외를 던집니다.
     * @throws ParseException          파싱 오류 발생 시 예외를 던집니다.
     * @since 1.0.0
     */
    @PostMapping("/categories/{categoryCode}")
    public ModelAndView searchForCategory(@PathVariable final String categoryCode,
                                          @RequestParam final String keyword,
                                          final Pageable pageable)
            throws ParseException, JsonProcessingException {

        ModelAndView mav = new ModelAndView("board/" + checkType(categoryCode) + "/index");
        SearchRequest request = new SearchRequest(keyword, PageRequest.of(pageable.getPageNumber(),
                                                                          pageable.getPageSize()));

        List<SearchBoardResponse> response;

        response = searchService.searchBoardForCategory(categoryCode, request, "categoryCode");

        mav.addObject("responses", response);
        mav.addObject("keyword", keyword);
        mav.addObject("page", pageable.getPageNumber());
        mav.addObject("searchType", "default");
        mav.addObject("isEnd", this.checkPageEnd(response));
        mav.addObject("reasons", postService.retrieveOtoReason());
        mav.addObject("isAdmin", "no");

        return mav;
    }

    static String checkType(String categoryCode) {
        switch (categoryCode) {
            case "701": {
                return "notices";
            }
            case "702": {
                return "oto-inquiries";
            }
            case "703": {
                return "faqs";
            }
            default: {
                throw new NotFoundException("해당 카테고리 타입을 찾을 수 없습니다.");
            }
        }
    }

    private <T> Integer checkPageEnd(final List<T> list) {
        if (list.size() < 11) {
            return 1;
        }
        return 0;
    }

}
