<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#image").click(function(){
			var path=$(this).attr("src").substring(0,46);
			var fileName=$(this).attr("src").substring(48);
			$(this).attr("src",path+fileName);
		});
	});
</script>
</head>
<body>
	writer: ${writer}<br>
	file name: ${filename}<br>
	
	<img id="image" src="displayFile?filename=${filename}">
</body>
</html>