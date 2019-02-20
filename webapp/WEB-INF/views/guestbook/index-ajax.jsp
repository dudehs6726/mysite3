<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form input[type="password"]{
	padding: 5px;
	outline: none;
	width: 180px;
	border: 1px solid #888;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">

(function($){
	$.fn.hello = function(){
		console.log( $(this).attr("id") + "----> hello");
	}
})(jQuery);

var page = 0;
var isEnd = false;

var messageBox = function(title, message){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons: {
			"확인": function(){
				$(this).dialog("close");
			}
		}
	});
}

var render = function(vo, mode){
	//template library
	//ex) ejs, underscore, mustache
	
	var htmls = " <li data-no='"+ vo.no + "'> " +
					" <strong>" + vo.name + "</strong>" +
					" <p>" + vo.message.replace(/\n/g, "<br>") + " </p>" +
					" <strong></strong>" +
					" <a href='' data-no='" + vo.no + "'>삭제</a>" + 
				  " </li>";
				 
	if(mode == true){
		$("#list-guestbook").prepend(htmls);
	} else {
		$("#list-guestbook").append(htmls);
	}
	
}

var fetchList = function(){
	
	if(isEnd == true){
		return;
	}
	
	++page;
	$.ajax({
		url: "/mysite2/api/guestbook?a=ajax-list&p="+page,
		type: "get",
		dataType: "json",
		data:"",
		success: function(response){
			if(response.result == "fail"){
				console.warn(response.data);
				return;
			}
			
			if(response.data.length < 5){
				isEnd = true;
				$("#btn-next").prop("disabled", true);
			}
			//rendering
			$.each(response.data, function(index, vo){
				render(vo, false);
			});
		},
		error: function(xhr, status, e){
			console.log(status + ":" + e);
		}
		
	});
	
}

$(function(){
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		model: true,
		buttons: {
			"삭제": function(){
				console.log("ajax 삭제 작업");
				console.log($("#hidden-no").val());
				
				var $passwordDelete = $("#password-delete");
				var $hiddenNo = $("#hidden-no");
				if($passwordDelete.val() == ""){
					
					$(".validateTipsNormal").hide();
					$(".validateTipsError").show();
					return;
				}
				
				
				$.ajax({
					url: "${pageContext.servletContext.contextPath }/api/guestbook",
					type: "post",
					dataType: "json",
					data: "a=ajax-delete&no=" + $hiddenNo.val()
					     + "&password=" + $passwordDelete.val(),
					success: function(response){
						if(response.result == "fail"){
							$(".validateTipsNormal").hide();
							$(".validateTipsError").show();
							console.warn(response.data);
							return;
						}
						
						if(response.result == "succress"){
							//rendering
							$("#list-guestbook li[data-no="+response.data+"]").remove();
							dialogDelete.dialog("close");
						}
						
					},
					error: function(xhr, status, e){
						console.log(status + ":" + e);
					}
					
				});
				
			},
			"취소": function(){
				dialogDelete.dialog("close");
			}
		},
		close: function(){
			console.log("close 시 뒤처리...");
			//남아 있는 비밀번호를 지워줘야 함....
			$("#password-delete").val("");
			$(".validateTipsNormal").show();
			$(".validateTipsError").hide();
		}
	});
	
	//live event
	$(document).on("click", "#list-guestbook li a", function(event){
		event.preventDefault();
		console.log("clicked!!!" + $(this).data("no"));
		$("#hidden-no").val($(this).data("no"));
		dialogDelete.dialog("open");
		
	});
	
	//메세지 등록 폼 submit 이벤트
	$("#add-form").submit(function(event){
		//submit의 기본동작(post)
		//막아야 한다.
		event.preventDefault();
		
		//validate form data
		$inputName = $("#input-name");
		$inputPassword = $("#input-password");
		$inputContent = $("#tx-content");
		
		var name = $inputName.val();
		if(name == ""){
			messageBox("글남기기", "이름은 필수 입력 항목입니다.");
			return;
		}
		
		var password = $inputPassword.val();
		
		if(password == ""){
			messageBox("글남기기", "비밀번호는 필수 입력 항목입니다.");
			return;
		}
		
		var content = $inputContent.val();
		if(content == ""){
			messageBox("글남기기", "내용은 필수 입력 항목입니다.");
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/api/guestbook",
			type: "post",
			dataType: "json",
			data: "a=ajax-insert&name=" + name
			     + "&password=" + password
			     + "&message=" + content,
			success: function(response){
				if(response.result == "fail"){
					console.warn(response.data);
					return;
				}
				
				if(response.result == "succress"){
					//rendering
					render(response.data, true);
					$inputName.val("");
					$inputPassword.val("");
					$inputContent.val("");
				}
				
			},
			error: function(xhr, status, e){
				console.log(status + ":" + e);
			}
			
		});
	});
	
	//스크롤
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();
		
		//console.log(scrollTop + ":" + windowHeight + ":" + documentHeight);
		if(scrollTop + windowHeight + 10 > documentHeight){
			fetchList();
		}
	});
	
	
	$("#btn-next").click(function(){
		$("#btn-next").hello();
		fetchList();
	});
	
	
	//최초 리스트 가져오기
	fetchList();
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<button id="btn-next">다음</button>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTipsNormal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTipsError" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding: 30px 0;"></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>