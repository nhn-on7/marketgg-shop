package com.nhnacademy.marketgg.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.marketgg.server.controller.file.FileController;
import com.nhnacademy.marketgg.server.dto.response.file.ImageResponse;
import com.nhnacademy.marketgg.server.entity.Asset;
import com.nhnacademy.marketgg.server.service.file.FileService;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FileService fileService;

    private ImageResponse imageResponse;

    @BeforeEach
    void setUp() {
        imageResponse = new ImageResponse("이미지 응답", 1L, "이미지 주소", 1, Asset.create());

    }

    @Test
    void testRetrieveImage() throws Exception {
        given(fileService.retrieveImage(anyLong())).willReturn(imageResponse);

        mockMvc.perform(get("/storage/1"))
               .andExpect(status().isOk());

        then(fileService).should(times(1)).retrieveImage(anyLong());
    }

    @Test
    void testUploadAndRetrieveImage() throws Exception {
        URL url = getClass().getClassLoader().getResource("img/lee.png");
        String filePath = Objects.requireNonNull(url).getPath();
        MockMultipartFile imageFile =
                new MockMultipartFile("image", "test.png", "image/png", new FileInputStream(filePath));

        given(fileService.uploadImage(any(MultipartFile.class))).willReturn(imageResponse);

        mockMvc.perform(multipart("/storage")
                                .file(imageFile)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
               .andExpect(status().isOk());

        then(fileService).should(times(1)).uploadImage(any(MultipartFile.class));
    }

}
