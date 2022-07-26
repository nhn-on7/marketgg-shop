package com.nhnacademy.marketgg.server.dto.response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@Getter
public class DefaultPageResult<DTO, EN> {

    private List<DTO> dtoList;

    public DefaultPageResult(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }

}
