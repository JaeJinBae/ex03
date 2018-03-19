<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$(".image").click(function(){
			var hiddenNum=$(this).parent().find(".hiddenNum").val();
			if(hiddenNum>=2){
				return;
			}else{
				var path=$(this).attr("src").substring(0,47);
				var fileName=$(this).attr("src").substring(49);
				$(this).attr("src",path+fileName);
				$(this).parent().find(".hiddenNum").val(Number(hiddenNum)+1);
			}
			
		});
		$(document).on("click",".del",function(){
			$(this).parent().remove();
		});
	});
</script>
</head>
<body>
	writer: ${writer}<br>
	<c:forEach var="item" items="${list}">
		<div class="wrapDiv">
			<input type="hidden" class="hiddenNum" value="1">
			file name: ${item}<br>
			<img class="image" src="displayFile?filename=${item}"><input class="del" type="button" value="X">
		</div>
		<br><br>
	</c:forEach>
	
	
</body>
</html>