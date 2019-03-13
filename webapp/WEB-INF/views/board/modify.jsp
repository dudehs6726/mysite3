<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url ="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form:form class="board-form"
					modelAttribute="boardVo"
				 	method="post" 
				 	action="${pageContext.servletContext.contextPath }/board/modify">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td>
								<form:input path="title" />
								<!-- <input type="text" name="title" value="${vo.title }">  -->
								<form:hidden path="no"/>
								<form:hidden path="userNo"/>
								<!-- <input type="hidden" name="no" value="${vo.no }">
								<input type="hidden" name="userNo" value="${userNo }">  -->
							</td>
							
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<form:textarea id="content" path="contents"/>
								<!-- <textarea id="content" name="contents">${vo.contents }</textarea>  -->
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/view/${no}/${page}">취소</a>
						<input type="submit" value="수정">
					</div>
				</form:form>				
			</div>
		</div>
		<c:import url ="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>