# **🎯** 프로젝트 소개

판매자와 사용자를 중심으로 한 마켓플레이스 시스템입니다. 

Spring Cloud 기반으로 인증, 주문, 상품 관리 서비스를 분리하여 마이크로서비스 아키텍처로 구현하였습니다. 

Gateway에서는 인증을 처리하고, 각 서비스는 권한에 따라 API 접근을 제한합니다.

---

## 🎯 시스템 구현 범위

- ✅ 회원가입/로그인부터 주문 및 상품 관리까지 전반적인 이커머스 기능 구현
- ✅ 인증은 Gateway 단에서 JWT로 처리하고, 각 서비스는 권한만 체크
- ✅ Kafka 기반 비동기 메시징을 활용한 상품 재고 처리
- ✅ 주문-상품-사용자 정보를 집계한 마이페이지 Aggregation 기능 제공

---

## **⚙️** 시스템 아키텍처

![image](https://github.com/user-attachments/assets/a31e1520-2103-4343-ad41-d255d3b44dd2)

---

## 🛠️ 기술 스택

- **⚙️ Backend Framework**: Spring Boot, Spring Cloud Gateway, Spring WebFlux
- **🔍 Service Discovery**: Eureka Server
- **🔐 Authentication**: JWT, Redis (세션 또는 토큰 캐시)
- **🗄️ Database**: MariaDB (User, Order, Item)
- **📬 Messaging**: Kafka (주문 시 상품 수량 동기화)
- **📑 API 명세서**: [Postman API Docs](https://documenter.getpostman.com/view/31441199/2sAYkHpySr)

---

## 🚨 사용 기술 검토

| 기술 | 목적 |
| --- | --- |
| Spring Boot | 서비스 단위 개발 프레임워크 |
| Spring Cloud Gateway | API Gateway 및 인증 필터 구현 |
| Spring WebFlux | 비동기 처리 기반 마이크로서비스 운영 |
| Eureka | 마이크로서비스 간 서비스 디스커버리 |
| Redis | 토큰 블랙리스트 및 캐시 저장소 |
| Kafka | 주문/재고 이벤트 처리용 메시징 시스템 |
| MariaDB | 각 서비스별 데이터 저장소 |
| Postman | API 명세서 관리 및 테스트 |

---

## **🧩** 서비스 구성

### ✅ User / Auth Server

- 회원가입, 로그인, 회원 정보 수정, 탈퇴
- JWT 토큰 생성 및 검증
- Redis를 활용한 세션 캐시 또는 블랙리스트 처리
- 사용자 권한: `GUEST`, `USER`, `SELLER`, `ADMIN`

### ✅ Item Server

- 상품 등록, 수정, 삭제
- 전체 상품 목록 / 판매자 등록 상품 / 개별 상품 조회
- Kafka를 통한 재고 동기화 처리

### ✅ Order Server

- 주문 등록, 전체/사용자/상품 기준 주문 목록 조회
- 주문 등록 시 Kafka 이벤트 발송

### ✅ 마이페이지 기능 (Aggregation)

- 최근 주문한 상품 + 해당 상품 상세 정보 Aggregation
- 주문 횟수, 배송중인 내역, 할인 받은 총액 등 통합 조회

---

## **🔐** 인증 및 권한 처리 구조

- Gateway에서 JWT 검증 후, `x-user-id`, `x-user-role` 헤더에 사용자 정보 전달
- 각 서비스는 별도 JWT 검증 없이 헤더 기반으로 권한만 확인
- 코드 중복 최소화

---

## 📌 특징 및 설계 포인트

- 인증(Gateway)과 인가(각 서비스)의 책임 분리
- 공통 유틸/Enum으로 권한 체크 로직 중복 최소화
- 서비스별 권한 기준을 자율적으로 운영 가능 → 유연한 확장성
- Spring Security 없이 헤더 기반으로 권한 체크

---
