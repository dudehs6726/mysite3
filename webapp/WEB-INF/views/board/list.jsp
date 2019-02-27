<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<form id="${pageContext.servletContext.contextPath }/search_form" action="${pageContext.servletContext.contextPath }/board/search/${page}" method="post">
					<input type="text" id="kwd" name="kwd" value="${vo.kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }"/>
					<c:forEach items="${list }" var="list" varStatus="status">
						<tr>
							<!--${count-status.index } -->
							<td>${list.rownum-status.index }</td>
							<c:choose>
								<c:when test="${list.depth > 0}">
									<td style="padding-left:${20*list.depth }px; text-align: left;"><img src="${pageContext.servletContext.contextPath }/assets/images/reply.png"/>
									<c:choose>
										<c:when test="${vo.kwd != null && vo.kwd != ''}">
											<a href="${pageContext.servletContext.contextPath }/board/view/${list.no }/${vo.page }/${vo.kwd}">${list.title }</a></td>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath }/board/view/${list.no }/${vo.page }">${list.title }</a></td>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${vo.kwd != null && vo.kwd != ''}">
											<td style="text-align: left;"><a href="${pageContext.servletContext.contextPath }/board/view/${list.no }/${vo.page }/${vo.kwd}">${list.title }</a></td>
										</c:when>
										<c:otherwise>
											<td style="text-align: left;"><a href="${pageContext.servletContext.contextPath }/board/view/${list.no }/${vo.page }">${list.title }</a></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<td>${list.userName }</td>
							<td>${list.hit }</td>
							<td>${list.writeDate}</td>
							
							<td>
								<c:if test="${list.userNo == authuser.no }">
									<c:choose>
										<c:when test="${list.fileName == null}">
											<a href="${pageContext.servletContext.contextPath }/board/delete/${list.no }" class="del">삭제</a>
										</c:when>
										<c:otherwise>
											<a href="${pageContext.servletContext.contextPath }/board/delete/${list.no }/${list.fileName}" class="del">삭제</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
							
						</tr>
					</c:forEach>
				</table>			
				 	
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:choose>
							<c:when test="${vo.page > 5 && !empty vo.kwd }">
								<li><a href="${pageContext.servletContext.contextPath }/board/search/${vo.blockStartNum - 1}/${vo.kwd }">◀</a></li>
							</c:when>
							<c:when test="${vo.page > 5 }">
								<li><a href="${pageContext.servletContext.contextPath }/board/list/${vo.blockStartNum - 1}">◀</a></li>
							</c:when>
						</c:choose>
						<c:forEach var="i" begin="${ vo.blockStartNum }" end="${vo.blockLastNum }">
							<c:choose>
								<c:when test="${i > vo.lastPageNum }">
									<li>${i }</li>
								</c:when>
								<c:when test="${i == vo.page }">
									<li class="selected">${i }</li>
								</c:when>
								<c:when test="${ !empty vo.kwd }">
									<li><a href="${pageContext.servletContext.contextPath }/board/search/${ i }/${vo.kwd }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/board/list/${ i }">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${vo.lastPageNum > vo.blockLastNum && !empty vo.kwd }">
								<li><a href="${pageContext.servletContext.contextPath }/board/search/${ vo.blockLastNum + 1 }/${vo.kwd }">▶</a></li>
							</c:when>
							<c:when test="${vo.lastPageNum > vo.blockLastNum }">
								<li><a href="${pageContext.servletContext.contextPath }/board/list/${ vo.blockLastNum + 1 }">▶</a></li>
							</c:when>
						</c:choose>
					</ul>
				</div>
				
				<c:if test="${!empty authuser }">
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/write/${vo.page }" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url ="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>