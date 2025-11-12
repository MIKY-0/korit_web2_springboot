# git?
-코드변경에 따른 버전 관리 툴

# git 명령어
1. git init : 명령어가 실행되는 경로에서 코드 추적을 시작하겠다.
2. git add : 변경사항에 대해 임시저장. -> 다른말로 스테이징 영역에 저장.
3. git commit : 이때까지 add한 부분에 대해 하나의 버전으로 저장.
4. git remote add origin [깃허브 저장소 url] 
5. git push -u origin main : origin(원격저장소)와 main(로컬)을 동기화해서 전송하겠다.(최초 1회만 적용. 그 이후에는 git push만.)