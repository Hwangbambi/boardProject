# 게시판 프로젝트
<h2>1. API 설계</h2>
<img width="502" alt="스크린샷_20221201_022916" src="https://user-images.githubusercontent.com/100077017/204973459-33b6e630-be8c-4204-8cfd-70da436832c7.png">
<br>
<h2>2. Usecase</h2>
<img width="459" alt="스크린샷_20221201_024620" src="https://user-images.githubusercontent.com/100077017/204975428-cbf876df-e75c-43af-b512-c14aa1c3277e.png">
<br>
<h2>3. 구현내용</h2>
1) 전체 게시글 목록 조회 API<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 제목, 작성자명, 작성 내용, 작성 날짜 조회<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 작성 날짜 기 내림차순으로 정렬<br><br>
2) 게시글 작성 API<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 제목, 작성자명, 비밀번호, 작성 내용 저장 후 저장된 게시글을 Client 로 반환<br><br>
3) 선택한 게시글 조회 API<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용 조회<br><br>
4) 선택한 게시글 수정 API<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 수정 요청시 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환<br><br>
5) 선택한 게시글 삭제 API<br>
&nbsp;&nbsp;&nbsp;&nbsp;- 삭제 요청시 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환<br>

