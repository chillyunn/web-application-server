# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* BufferdReader
* byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath()); 사용시 경로가 최상위 폴더에서 시작됨.<br>
왜 현재 폴더에서 시작되지 않을까?
* slf4j를 사용한 로깅
* HTTP 요청메시지와 응답메시지
* 요청메시지는 요청라인 - 요청헤더 - 공백 - 요청본문으로 구성됨
* 응답메시지는 상태라인 - 응답헤더 - 공백 - 응답본문으로 구성됨
* HTTP 요청시 서버는 웹페이지를 구성하는 모든 자원(HTML,CSS,JS 등)을 한번에 응답으로 보내지 않는다.
* /index.html 요청시 서버는 HTML만 전달하고 클라이언트가 HTML 내용을 분석해 CSS,JS 등이 포함되어있으면 서버에 해당 자원을 다시 요청한다.

### 요구사항 2 - get 방식으로 회원가입
* indexOf("?")와 subString(index+1)을 이용한 쿼리스트링 분리
* indexOf의 seperator가 없을 때 -1이 return 됨.
* queryString을 받을 때, @이 이상하게 입력되는 현상 발생

### 요구사항 3 - post 방식으로 회원가입
* POST로 데이터를 전달할 경우 전달하는 데이터는 HTTP 본문에 담긴다.
* HTTP 본문에 전달되는 데이터는 GET 방식으로 데이터를 전달할 때의 이름=값과 같다.
* HTTP 본문의 길이는 HTTP 헤더의 Content-Length의 값이다.
* HTML은 기본적으로 GET 과 POST 메소드만 지원한다.
* PUT, DELETE 메소드는 AJAX에서 지원하는 기능이다.
### 요구사항 4 - redirect 방식으로 이동
* 회원가입 후 /index.html로 리다이렉트를 하면 화면은 index.html이 나오지만 주소는 /user/create로 남아있음.
* 이순간 새로고침을 누르게 되면 이전과 똑같은 회원가입 요청이 다시 발생한다. -> 같은 데이터가 중복으로 전송되는 이슈가 발생한다.
* 브라우저가 이전 요청정보를 유지하고 있기 때문이다.
* 클라이언트의 /user/create 요청에 HTTP Status 200과 /index.html으로 응답하게되면 클라이언트는 변경된 주소로 다시 요청하지 않는다.
* 하지만 HTTP Status 302와 /index.html으로 응답하게 되면 클라이언트는 변경된 주소로 다시 요청을 하게 된다. 즉 요청과 응답이 두번 발생하게 된다.
* 302 상태 코드를 활용한 페이지 이동방식은 많은 라이브러리와 프레임워크에서 리다이렉트 이동 방식으로 알려져 있다.
* 4XX 상태 코드: 클라이언트에 오류가 있음.
* 5XX 상태 코드: 서버가 유효한 요청을 명백하게 수행하지 못했음.

### 요구사항 5 - cookie
* HTTP는 요청을 보내고 응답을 받으면 클라이언트와 서버 간의 연결을 끊는다.
* 때문에 각 요청 사이에 상태를 공유할 수 없고, 때문에 HTTP를 무상태 프로토콜이라고 한다.
* HTTP는 클라이언트의 행위를 기억하기 위해 쿠키를 지원한다.
* 서버에서 로그인 요청을 받으면 로그인 성공/실패 여부에 따라 응답 헤더에 Set-Cookie로 결과 값을 저장할 수 있다.
* 클라이언트는 응답 헤더에 Set-Cookie가 존재할 경우 값을 읽어 서버에 보내는 요청 헤더의 Cookie 헤더 값으로 다시 전송한다.
* 클라이언트도 Set-Cookie를 통해 결과 값을 저장할 수 있다.
* HTTP는 헤더를 통해 공유할 데이터를 매번 다시 전송하는 방식으로 데이터를 공유한다.

### 요구사항 6 - 사용자 목록 출력
* 로그인을 했을 때, 응답헤더에서 Set-Cookie:logined=true를 작성하면 클라이언트는 cookie:logined=true를 갖게된다.
* 사용자 목록 요청을 하게되었을때 서버는 클라이언트의 cookie 값을 확인하여 로그인 여부를 확인하게된다.
* 사용자 목록을 출력하는 과정에서 StringBuilder를 사용하였는데, String은 변경이 불가능하기때문에, 문자열을 연결할 경우에 새 문자열을 생성하게되고, 이전 문자열은 GC로 돌아간다.
* 만약 많은 수의 문자열 연결이 발생할 경우, 이는 메모리 사용량을 증가시킨다.
* StringBuilder는 변경 가능한 문자열을 만들어 주기 때문에, String의 대안이 될 수 있다.
* StringBuffer 또한 비슷한 기능을 가지고 있는데, StringBuffer는 동기화를 지원하지만, StringBuilder는 동기화를 지원하지 않는다.
* 때문에 멀티스레드 환경에서는 StringBuffer를 사용하는 것이 좋다.
* 하지만 멀티스레드 환경이 아닐 때는 일반적으로 StringBuilder가 StringBuffer보다 속도 면에서 성능이 좋다.
* 알지 못한것:Collection<T>
### 요구사항 7 - stylesheet 적용
* 처음 프로그램을 실행했을 때부터 css가 적용되지 않는 이유가 궁금했었는데, 200 응답을 보내는 코드에서 Content-Type이 text/html로 고정되어있었기 때문에, css 요청에 대한 응답을 하더라도 브라우저가 css를 읽을 수 없었다.
* Response200CssHeader() 메소드를 통해서 Content-Type을 html/css로 변경하고 재실행 하였는데, css가 적용되지 않았다.
* 원본 깃허브에 있는 코드와 비교했을 때, Response200CssHeader() 메소드에 Content-Type을 설정하는 곳에서, 책에는 charset을 설정하는 부분이 없었는데, 원본 깃허브에는 적용되어 있다는 사실을 인지하고 수정하여 재실행했더니 css가 적용되었다.

### heroku 서버에 배포 후

### maven
* https://slipp.net/wiki/pages/viewpage.action?pageId=10420233
### 그래들 학습
* http://kwonnam.pre.kr/wiki/gradle
### HTTP 학습
* https://www3.ntu.edu.sg/home/ehchua/programming/webprogramming/HTTP_Basics.html
* 프로가 되기 위한 웹 기술 입문(고모리 유스케,위키북스/2012) ~3장
* HTTP & Network : 그림으로 배우는 책으로 학습(우에노 센,영진닷컴/2015)
* HTTP 완벽 가이드(데이빗 고울리,인사이트/2014)
### 네트워크
* 성공과 실패를 결정하는 1%의 네트워크 원리(Tsutomu Tone/성안당/2015)