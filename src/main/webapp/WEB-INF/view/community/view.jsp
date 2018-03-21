<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<div  id = "wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp"/>
		<h1>${community.title}</h1>
		<p>${community.displayFilename }</p>
		<p>${community.body}</p>
		<h3>
			<c:choose>
				<c:when test="${not empty community.memberVO }">
								${community.memberVO.nickname} (${community.memberVO.email})
								IP주소:${community.requestIp }
							</c:when>
							<c:otherwise>
								탈퇴한 회원 IP주소:${community.requestIp }
							</c:otherwise>
						</c:choose>
			</h3>
		<p>조회수 : ${community.viewCount }| 추천수 :
			${community.recommendCount} | 작성일 : ${community.writeDate}</p>
		<c:if test="${not empty community.displayFilename }">
			<p>
				<a href="<c:url value="/get/${community.id}"/>">
					${community.displayFilename } </a>
			</p>
		</c:if>


		<p>
			<a href="/">뒤로가기</a> | <a
				href="<c:url value="/recommend/${community.id}" />">추천하기</a>   
				<c:if test="${community.memberVO.id==sessionScope.__USER__.id}" >
					<a href="<c:url value="/modify/${community.id}"/>">		|	수정하기</a>
					<a href="<c:url value="/remove/${community.id}" />"> | 삭제하기</a> 
				</c:if>
		</p>
	</div>
</body>
</html>