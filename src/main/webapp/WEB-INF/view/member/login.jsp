<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- script는 싱글라인 태그로 닫으면 안돼 -->
<link rel="stylesheet" type="text/css" href="/static/css/input.css"/>
<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js" />"></script>
<script type="text/javascript">
	$().ready(function() {

		$("#loginBtn").click(function() {

			$("#loginForm").attr({
				"action" : "<c:url value="/login" />",
				"method" : "post"
			}).submit();
		})

	})
</script>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="loginForm">

			<c:if test="${sessionScope.status eq 'fail' }">
				<div id="invalidIdAndPassword">
					<div>아이디 혹은 비밀번호가 잘못 되었습니다.</div>
					<div>한번 더 확인 후 시도해 주세요.</div>
				</div>
			</c:if>

			<div>
				<input type="email" id="email" name="email" placeholder="E-MAIL을 입력하세요." />
			</div>

			<div>
				<form:errors path="email" />
				<!-- name이랑 path랑 같아야한다 -->
			</div>

			<div>
				<input type="password" id="password" name="password"
					placeholder="PASSWORD" />
			</div>

			<div>
				<form:errors path="password" />
			</div>

			<div>
				<input type="button" id="loginBtn" value="login" />
			</div>

		</form:form>
		
		<div>
			<a href="<c:url value="/regist"/>"> 아직 회원이 아니신가요? </a>
		</div>
	</div>
</body>
</html>