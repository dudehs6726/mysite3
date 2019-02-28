<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%pageContext.setAttribute("newline", "\n");%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div id="container">
	<c:import url ="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="guestbook">
				<form:form
					modelAttribute="guestBookVo" 
					action="${pageContext.servletContext.contextPath }/guestbook/insert" 
					method="post">
					<table>
						<tr>
							<td>이름</td>
							<td>
								<form:input path="name" />
								<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
								<form:errors path="name" />
								</p>
							</td>
							<td>비밀번호</td>
							<td>
								<form:password path="password" />
								<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
									<form:errors path="password" />
								</p>
							</td>
						</tr>
						<tr>
							<td colspan=4>
							<form:textarea path="message" id="content"/>
							<p style="margin: 0; padding: 0; font-weight:bold; color: red; text-align: left;">
								<form:errors path="message" />
							</p>
							</td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form:form>
				<ul>
				<c:set var="count" value="${fn:length(list) }"/>
				<c:forEach items="${list }" var="vo" varStatus="status">
					<li>
						<table>
							<tr>
								<td>[${count-status.index }]</td>
								<td>${vo.name }</td>
								<td>${vo.regDate }</td>
								<td><a href="${pageContext.servletContext.contextPath }/guestbook/delete/${vo.no }">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
									${fn:replace(vo.message, newline, "<br>") }	
								</td>
							</tr>
						</table>
						<br>
					</li>
				</c:forEach>	
				</ul>
			</div>
		</div>
		<c:import url ="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook"/>
		</c:import>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>