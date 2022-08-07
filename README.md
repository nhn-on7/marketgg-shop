# Market GG Shop Service

해당 프로젝트는 Market GG 에 필요한 API 를 제공함으로써 Client 의 요청을 받아 그에 상응하는 서비스를 수행합니다.

# Getting Started

```
./mvnw spring-boot:run
```

# Project Architecture

![marketgg-architecture-v1-0-2](https://user-images.githubusercontent.com/38161720/183286626-494edf2f-2a6c-4207-b70f-57d85b838704.png)


# Features

## 사용자

- 데이브, 덕춘 작성

## GG 패스

### Copark

- GG 패스 구독 갱신일 확인
- GG 패스 구독 신청
- GG 패스 구독 해지

## 카테고리

### Copark, Moomin

- 카테고리 분류표 목록 조회
- 카테고리 분류번호, 카테고리 번호, 이름, 나열순서를 입력으로 카테고리 등록
- 카테고리 번호를 입력으로 카테고리 단건 조회
- 카테고리 분류번호를 입력으로 카테고리 목록 조회
- 전체 카테고리 목록 조회
- 카테고리 이름, 나열순서 수정
- 카테고리 단건 삭제

## 라벨

### Copark

- 상품 등록 시 추가할 수 있는 라벨 등록
- 등록되어있는 라벨 삭제
- 전체 라벨 목록 조회

## 엘라스틱 서치

### Copark

- LogStash 를 이용한 엘라스틱 서치 서버와 Shop 서버에서 사용하는 RDBMS 동기화
- LogStash 를 이용한 엘라스틱 서치 서버 에러 로깅 처리
- Kibana 를 이용한 엘라스틱 서치 서버 Index 관리
- X-Pack 을 이용한 엘라스틱 서치 서버 보안 활성화
- 상품/(고객센터)게시글 동의어 연관어/동의어 검색
- 상품/(고객센터)게시글 영/한 오타 변환 검색
- 상품/(고객센터)게시글 검색 시 자동완성 지원
- 상품 카테고리 내 가격별 검색 지원
- 게시글 카테고리 내 옵션별 검색 지원

### Techinical Issue

- 엘라스틱 서치는 자체적인 Transaction 이 없다 어떻게 처리?
    - LogStash 를 이용하여 주기마다 데이터의 동기화 처리를 진행
- 엘라스틱 서치 서버의 자체적인 오류로 서비스가 마비?
    - 예비 인덱스를 두는 형식으로 해결예정

## 상품

### Comsun

- Object Storage를 이용한 파일 관리
    - local/storage를 구분하여 파일 업로드
    - 조회시 storage 주소를 통해 이미지 다운르도
- 페이지네이션을 이용한 상품 조회
- 상품 등록, 수정 구현
    - 로컬 업로드 고도화
- 상품 삭제기능 구현

### Techinical Issue

서버 스케일 아웃에 대비하여 파일 관리를 어떻게 할 것인가.

- 기존 로컬에 저장하던 기능 → cloud로 이전

그렇다면 기존의 코드는?

- 객체 지향적인 설계를 통해 기존 코드의 변화 없는 기능 확장 고려

### Panda

- 상품 등록 구현
    - Apache Commons FileUpload를 사용한 로컬 업로드 기능 구현
    

## 상품 문의

### Panda

- 상품문의 등록, 조회, 삭제
- 상품문의 관리자 답글 등록

## 찜

### Moomin

- 찜 등록, 해제
- 찜한 상품 목록 조회

## 쿠폰

### Moomin

- 쿠폰 등록, 단건 조회, 목록 조회, 수정, 삭제

### Panda

- 사용자에게 지급되는 GivenCoupon 등록, 삭제, 조회
- 사용자가 사용하면 기록에 남는 UsedCoupon 생성, 삭제
### **Techinical Issue**
- Spring Event → 회원가입 시 쿠폰 자동 지급
- Spring Event → 베스트 후기 선정 시 쿠폰 자동 지급
- Scheduler ****→ 매일 생일인 회원에게 쿠폰 자동 지급
- Batch + Scheduler ****→ 매월 1일 회원 등급 업데이트 + 등급에 따른 쿠폰 지급

## 포인트

### Copark

- 결제 시 등급별 회원 적립금 적립
- 포인트 적립/사용 내역 등록
- 마켓 GG 회원 자신의 포인트 적립/사용 내역 목록 조회
- 관리자의 마켓 GG 회원 전체 포인트 적립/사용 내역 목록 조회

### Techinical Issue

- 포인트 적립/사용 내역 등록 시 Transaction의 기준?
    - 적립/사용이 적용되는 서비스 내부에 동일 Transaction 으로 관리 필요

## 장바구니

## 주문

- 기능 구현 중

## 배송

- 기능 구현 중

## 후기

### Comsun

- Object Storage를 이용한 파일 관리 (상품과 동일)
    - local/storage를 구분하여 파일 업로드
    - 조회시 storage 주소를 통해 이미지 다운르도

### Techinical Issue

- 서비스 레이어가 너무 많은 의존성을 갖고 있다.
    - 고민중

## 고객센터

### [공지사항]

### Copark

- 공지사항 등록
- 공지사항 단건 조회
- 공지사항 목록 조회
- 공지사항 수정
- 공지사항 삭제

### [FAQ]

### Copark

- FAQ 등록
- FAQ 목록 조회
- FAQ 수정
- FAQ 삭제

### [1:1 문의]

### Moomin

- 1:1 문의 등록
- 1:1 문의 단건 조회
- 1:1 문의 목록 조회
- 1:1 문의 삭제
- 1:1 문의 답변

### Techinical Issue

- 고객센터의 3개의 게시판 타입에 대해 동일한 작업 수행
    - 하나의 CsPostService 에서 같은 행위에 대한 동일 메소드로 처리
    - 컨트롤러의 @RoleCheck 로 해결되지 않는 부분 Service Layer 에서 처리

## Tech Stack

### Build Tools

![ApacheMaven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=ApacheMaven&logoColor=white)

### Datebases

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=flat&logo=Elasticsearch&logoColor=white)

### DevOps

![GitHubActions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=flat&logo=GitHubActions&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-4E98CD?style=flat&logo=SonarQube&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white)
![Logstash](https://img.shields.io/badge/Logstash-005571?style=flat&logo=Logstash&logoColor=white)
![Kibana](https://img.shields.io/badge/Kibana-005571?style=flat&logo=Kibana&logoColor=white)

### Frameworks

![SpringBoot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)

### Languages

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white&style=flat)

### Testing Tools

![Junit5](https://img.shields.io/badge/Junit5-25A162?style=flat&logo=Junit5&logoColor=white)

### 형상 관리 전략

![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)
![Sourcetree](https://img.shields.io/badge/Sourcetree-0052CC?style=flat&logo=Sourcetree&logoColor=white)

- Git Flow 를 사용하여 관리
모든 브랜치는 Pull Request에 코드 리뷰 진행 후 merge 합니다.
![image](https://user-images.githubusercontent.com/71637466/183255360-68cb4eef-cbc3-4005-9889-bf8bed192b43.png)
- Main: 배포시 사용
- Develop: 개발 단계가 끝난 부분에 대해서 Merge 내용 포함
- Feature: 기능 개발 단계
- Hot-Fix: Merge 후 발생한 버그 및 수정사항 반영 시 사용

## ERD

![marketgg_shop-v2-7-2](https://user-images.githubusercontent.com/38161720/183255251-d9a37c48-9f08-430a-851e-ed7c2af5516e.png)

## Contributors

<a href="https://github.com/nhn-on7/marketgg-shop/graphs/contributors">
<img src="https://contrib.rocks/image?repo=nhn-on7/marketgg-shop" />
</a>


## License

Market GG is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
