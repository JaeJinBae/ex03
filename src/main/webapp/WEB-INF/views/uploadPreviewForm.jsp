<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style type="text/css">
	#dropBox{
		width:300px;
		height:300px;
		border:1px solid #ccc;
		overflow: auto;
	}
	#dropBox img{
		max-width: 100%;
		max-height: 100%;
	}
</style>
</head>
<body>
	<form id="f1" action="uploadPreview" method="post" enctype="multipart/form-data">
		작성자 이름: <input type="text" name="writer" placeholder="작성자 이름"><br>
		파일 선택: <input id="file" type="file" multiple="multiple" name="file"><br>
		<input type="submit" value="전송">
	</form>
	<div id="dropBox">
	</div>
	
	<script type="text/javascript">
		$("#file").change(function(){
			$("#dropBox").empty();
			var file=document.getElementById("file");
			
			$(file.files).each(function(i,obj){
				var reader=new FileReader();
				reader.onload=function(e){
					var imgObj=$("<img>").attr("src",e.target.result);
					$("#dropBox").append(imgObj);
				}
				reader.readAsDataURL(file.files[i]);
			});
			
		});
	</script>
</body>
</html>








