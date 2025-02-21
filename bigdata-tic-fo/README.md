## Setting 가이드


#####
이클립스내 Preferences > Java > Compiler > JDK확인해보기
프로젝트내 Properties > Gradle > {{Override workspace settings}} 체크하고 javahome잡아주기
######

---

**소프트웨어 구성**
0. Spring Tool Suite4 설치(필수)
1. jdk17
2. Spring Boot 2.3.5 RELEASE
3. apache-tomcat-8.0.33 (Spring Boot Embed)
4. Spring JDBC 5.1.8 (Spring Boot Embed)
5. Spring AOP 5.1.8 (Spring Boot Embed)
6. Spring Session 2.1.7 RELEASE (Spring Boot Embed)
7. Mybatis 2.1.3 (Dependency)
8. Lombok 1.18.8 (Dependency)
9. Swagger 2.9.2 (Dependency) : @도메인/swagger-ui.html
10. Gradle 5.4.1

---

**셋팅**
1. 해당 프로젝트를 import 및 svn에서 내려 받습니다.
```
2. 프로젝트명 오른쪽 클릭 -> gradle -> Refresh Gradle Project
```
3. lombok 설치 - 파일은 eclipse폴더 안에 위치
   (참조 url : https://countryxide.tistory.com/16)
    커멘드실행 : java -jar lombok.jar
```
4. Boot Dashboard 활성(Window -> Show View -> Boot Dashboard 선택)
```
5. Boot Dashboard 설정
   설정할 서버 마우스 오른쪽 클릭 -> Open Config 선택 -> 탭 Arguments 선택 -> Vm Argument 영역에 아래내용 입력(yaml 추가시 관련 부분 추가) -> 서버 구동

-Dspring.profiles.active=dev
-Dspring.config.location=classpath:config/dev/config-dev.yaml,classpath:config/dev/login-exclude-dev.yaml,classpath:config/common/message.yaml
```
6. /resources/config 폴더 설정 프로젝트에 맞게 변경
```

---

**가이드 코드로 신규 플젝 생성시 변경 사항**
- bootstrap.yaml 파일의 application.name 과 server.port 변경
```
aplication.name=bigdatatic >>> {프로젝트명}
server.port=8080 >>> {해당서비스에 정의된 포트}
```
- settings.gradle 의 프로젝트명 respository 명과 통일

해당 변경사항은 STS 환경에서 프로젝트 import 시 gradle 프로젝트 인식이 잘 되지 않는 부분 해결 방안입니다.
```
rootProject.name = 'bigdatatic' >>> {프로젝트명}

---
1.인증정보조회(cert)

	certLst.jsp	인증목록
	certDtl.jsp	인증상세

2.대시보드(dbr)

	dbrPage.jsp	대시보드

3.메뉴(layout)

	headerLayout.jsp	상위메뉴
	bottomLayout.jsp	하위

4.메인(main)

	main.jsp	메인

5.회원관리(mbr)

	mbrReg.jsp	회원등록
	mbrLst.jsp	회원목록
	mbrDtl.jsp	회원상세
	login.jsp	로그인

6.통계(stats)

	statsLstTab02.jsp	인증구분
	statsLstTab01.jsp	인증상태
	statsLst.jsp		통계목록
---

1. 순서
svn (https://192.168.190.103/svn/BIGDATA/bigdatatic)

---
*.svn
- bigdata1/bigdata!
- bigdata2/bigdata!
- bigdata/bigdata!


