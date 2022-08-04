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
* 회원가입 후 /index.html로 리다이렉트를 하면 화면은 index.html이 나오지만 주소는 /user/create로 남아있음.
* 이순간 새로고침을 누르게 되면 이전과 똑같은 회원가입 요청이 다시 발생한다. -> 같은 데이터가 중복으로 전송되는 이슈가 발생한다.
* 브라우저가 이전 요청정보를 유지하고 있기 때문이다.
* 클라이언트의 /user/create 요청에 HTTP Status 200과 /index.html으로 응답하게되면 클라이언트는 변경된 주소로 다시 요청하지 않는다.
* 하지만 HTTP Status 302와 /index.html으로 응답하게 되면 클라이언트는 변경된 주소로 다시 요청을 하게 된다. 즉 요청과 응답이 두번 발생하게 된다.
* 302 상태 코드를 활용한 페이지 이동방식은 많은 라이브러리와 프레임워크에서 리다이렉트 이동 방식으로 알려져 있다.
* 4XX 상태 코드: 클라이언트에 오류가 있음.
* 5XX 상태 코드: 서버가 유효한 요청을 명백하게 수행하지 못했음.
### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 