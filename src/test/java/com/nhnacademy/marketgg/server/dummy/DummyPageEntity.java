package com.nhnacademy.marketgg.server.dummy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.marketgg.server.dto.PageEntity;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DummyPageEntity<T> {

    @JsonProperty(value = "pageNumber")
    private Integer pageNumber;

    @JsonProperty(value = "pageSize")
    private Integer pageSize;

    @JsonProperty(value = "totalPages")
    private Integer totalPages;

    @JsonProperty(value = "data")
    private List<T> data;

    public PageEntity<T> getPageEntity() {
        return new PageEntity<>(pageNumber, pageSize, totalPages, data);
    }

    public static String getDummyData() {
        return "{\n" +
            "        \"pageNumber\": 0,\n" +
            "        \"pageSize\": 10,\n" +
            "        \"totalPages\": 3,\n" +
            "        \"data\": [\n" +
            "            {\n" +
            "                \"id\": 34,\n" +
            "                \"uuid\": \"14d6cfca-1642-4e01-bc13-4e003c1f9983\",\n" +
            "                \"email\": \"rlagnsals1@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"010-1234-1234\",\n" +
            "                \"createdAt\": \"2022-08-30T23:00:40\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 32,\n" +
            "                \"uuid\": \"0f8424b1-a81e-41ec-bb3c-a288a7aade86\",\n" +
            "                \"email\": \"gnsals20@naver.com\",\n" +
            "                \"name\": \"조재철호철철\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-30T20:37:38\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 31,\n" +
            "                \"uuid\": \"6c856ee7-7c40-4318-b1ba-616b062a1114\",\n" +
            "                \"email\": \"gnsals15@naver.com\",\n" +
            "                \"name\": \"박훈민\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-29T23:50:04\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 29,\n" +
            "                \"uuid\": \"fa457a8b-76b7-4f3c-9783-a8a002bd16d3\",\n" +
            "                \"email\": \"gnsals12@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01012341233\",\n" +
            "                \"createdAt\": \"2022-08-29T23:03:12\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 28,\n" +
            "                \"uuid\": \"ed9bcd6c-638f-4637-983c-6e7cb490d69d\",\n" +
            "                \"email\": \"gnsals11@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01043214321\",\n" +
            "                \"createdAt\": \"2022-08-29T22:51:21\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 27,\n" +
            "                \"uuid\": \"0bfdeddc-6632-4aa1-a832-afd44d766d25\",\n" +
            "                \"email\": \"gnsals10@gmail.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01043214321\",\n" +
            "                \"createdAt\": \"2022-08-29T22:40:23\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 26,\n" +
            "                \"uuid\": \"fbbf4496-6218-41e8-ad16-5e92ee94338c\",\n" +
            "                \"email\": \"abc@marketgg.shop\",\n" +
            "                \"name\": \"abcd\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-29T20:00:52\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 25,\n" +
            "                \"uuid\": \"937809ac-d14f-4fbb-a624-83da3de89f07\",\n" +
            "                \"email\": \"gnsals5@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-29T19:38:24\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 24,\n" +
            "                \"uuid\": \"a27f3a2b-68ee-4a30-b534-f83fa0c92545\",\n" +
            "                \"email\": \"gnsals4@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01043214321\",\n" +
            "                \"createdAt\": \"2022-08-29T19:34:34\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 23,\n" +
            "                \"uuid\": \"7c6303af-9eee-41d5-a200-48264901a616\",\n" +
            "                \"email\": \"gnsals3@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-29T19:23:21\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 22,\n" +
            "                \"uuid\": \"70630a59-c674-4831-abef-f25e9b09d97b\",\n" +
            "                \"email\": \"gnsals2@gmail.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"01043214321\",\n" +
            "                \"createdAt\": \"2022-08-29T19:16:10\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 21,\n" +
            "                \"uuid\": \"d56d4ef2-aa9a-4cf2-aeb3-91705daff891\",\n" +
            "                \"email\": \"gnsals1@gmail.com\",\n" +
            "                \"name\": \"김훈만수\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-29T18:23:27\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 20,\n" +
            "                \"uuid\": \"2e4e8391-4c73-4e25-9aa5-15682baad2c2\",\n" +
            "                \"email\": \"dbsehdduf123@gmail.com\",\n" +
            "                \"name\": \"윤동열라면\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-28T19:11:44\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 19,\n" +
            "                \"uuid\": \"3d1a83e1-0d1e-470b-917d-7247d7571148\",\n" +
            "                \"email\": \"rlagnsals@gmail.com\",\n" +
            "                \"name\": \"윤동열라면\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-28T19:01:35\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 18,\n" +
            "                \"uuid\": \"a3b49633-6fe3-4c9e-82c6-04b181d03491\",\n" +
            "                \"email\": \"gnsalsgnsals@naver.com\",\n" +
            "                \"name\": \"윤동열라면\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-28T18:36:42\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 17,\n" +
            "                \"uuid\": \"a3afa1b3-545a-4d6a-b329-c96bf3f2f78d\",\n" +
            "                \"email\": \"gnsalsgnsals@gmail.com\",\n" +
            "                \"name\": \"윤동열라면\",\n" +
            "                \"phoneNumber\": \"01012341234\",\n" +
            "                \"createdAt\": \"2022-08-28T18:35:48\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 16,\n" +
            "                \"uuid\": \"52b1f37e-4f86-46f8-a34b-839424e35273\",\n" +
            "                \"email\": \"foo@gmail.com\",\n" +
            "                \"name\": \"김김김\",\n" +
            "                \"phoneNumber\": \"12341234\",\n" +
            "                \"createdAt\": \"2022-08-27T18:37:53\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 15,\n" +
            "                \"uuid\": \"38b31d7e-2da5-4838-b82a-fa7249ad01df\",\n" +
            "                \"email\": \"bunsung91@naver.com\",\n" +
            "                \"name\": \"김훈민\",\n" +
            "                \"phoneNumber\": \"12345\",\n" +
            "                \"createdAt\": \"2022-08-27T18:17:41\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 14,\n" +
            "                \"uuid\": \"f68c7d80-6773-4cae-8087-b211dbe2b0f3\",\n" +
            "                \"email\": \"bunsung92@naver.c\",\n" +
            "                \"name\": \"min h kim\",\n" +
            "                \"phoneNumber\": \"010585003453\",\n" +
            "                \"createdAt\": \"2022-08-23T11:25:23\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 13,\n" +
            "                \"uuid\": \"e03c2a41-2b70-43ba-b869-d3bd0b1422a0\",\n" +
            "                \"email\": \"bunsung92@naver.co\",\n" +
            "                \"name\": \"min h kim\",\n" +
            "                \"phoneNumber\": \"01023443233\",\n" +
            "                \"createdAt\": \"2022-08-23T11:16:28\",\n" +
            "                \"roles\": [\n" +
            "                    \"ROLE_USER\"\n" +
            "                ]\n" +
            "            }\n" +
            "        ]\n" +
            "    }";
    }

}
