# GasStation-Recommendation-Project
주소 검색을 통한 주유소 길찾기 서비스 

----
## 프로젝트 소개
주유소 현황 데이터(공공 데이터) 및 카카오 API를 사용한 두가지 방법을 사용할 수 있는 주유소 길찾기 웹서비스
사용 공공데이터 포탈 : https://www.data.go.kr/data/15040326/fileData.do


## 개발 환경
- Java 11
- JDK 11.0.18
- SpringBoot 2.7.14
- Intelli J
- Gradle

## 테스트 환경
- Groovy
- Spock
- TestContainer
- MockWebServer

## 개발 기술
- Spring Data JPA
- Spring Retry
- Lombok
- Redis
- Docker (MariaDB, Redis)
- Handlebars
- Base62 encoding -> Shorten URL

----
## 요구사항
- 주유소 현황 데이터(공공 데이터)를 관리하고 있다 라고 가정하고, 주유소 현황 데이터는 위도 경도의 위치 정보 데이터를 가지고 있다.
- 해당 서비스로 주소 정보를 입력하여 요청하면 위치 기준에서 가까운 주유소 3곳을 추출한다.
- 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
  -> 정확한 주소를 입력 받기 위해 Kakao 우편번호 서비스 사용
- 주소는 정확한 상세 주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.
- 입력 받은 주소를 위도, 경도로 변환 하여 기존 주유소 데이터와 비교 및 가까운 약국을 찾는다.
  -> 지구는 평면이 아니기에, 구면에서 두 점 사이의 최단 거리 구하는 공식 필요. (Haversine fomula 사용하여 계산)
- 입력한 주소 정보에서 정해진 반경(10km) 내에 있는 주유소만 추천한다.
- 추출한 주유소 데이터는 길안내 URL 및 로드뷰 URL로 제공한다.
- 길안내 URL은 고객에게 제공 되기 때문에 가독성을 위해 shorten url로 제공한다.
- shorten url에 사용되는 key값은 인코딩하여 제공한다.
- shorten url의 유효 기간은 30일로 제한한다.

<주유소 공공데이터를 사용한 방식의 Flow>

<img width="764" alt="길 찾기 서비스 다이어그램" src="https://github.com/htkwon/GasStation-Recommendation-Project/assets/117131575/6c1b60bb-2316-4aa7-a59f-7d44921793da">




