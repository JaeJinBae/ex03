<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	text: ${test}<br><br>
	file name: ${filename}<br>
	
	<img src="displayFile?filename=${filename}">
</body>
</html>