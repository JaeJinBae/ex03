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
		width:400px;
		height:300px;
		border:1px solid black;
		overflow: auto;
	}
	#dropBox img{
		max-width:100%;
		max-height:100%;
	}
	#dropBox div.item{
		width:100px;
		height:130px;
		margin:5px;
		float:left;
	}
</style> 
</head>
<body>
	<form id="f1" action="uploadDrag" method="post" enctype="multipart/form-data">
		텍스트: <input type="text" name="test">
		<input type="submit" value="전송">
	</form>
	<br>
	<div id="dropBox">
	</div>
	
	<script>
		$(function(){
			var formData=new FormData();
			
			$("#dropBox").on("dragenter dragover",function(event){
				event.preventDefault();
			});
			$("#dropBox").on("drop",function(event){
				event.preventDefault();
				
				var files=event.originalEvent.dataTransfer.files;
				var file=files[0];
				console.log(file);
				
				var reader= new FileReader();
				reader.addEventListener("load",function(){
					var divObj=$("<div>").addClass("item");
					var imgObj=$("<img>").attr("src",reader.result);
					divObj.html(imgObj);
					
					$("#dropBox").append(divObj);
				},false);
				
				if(file){//load이벤트 발생 후 읽어야 하므로 무조건 load이벤트 후에 위치해야한다.
					reader.readAsDataURL(file);
				}
				if(formData==null){
					formData=new FormData();
				}
				
				//<input type="file" name="files" value="file">이랑 똑같음
				formData.append("files",file);
			});
			
			$("#f1").submit(function(event){
				event.preventDefault();
				formData.append("test",$("input[name='test']").val());
				
				$.ajax({
					url:"uploadDrag",
					data:formData,
					type:"post",
					//데이터를 일반적인 query string으로 변환할 것인지를 결정
					//formData로 보낼때는 안의 file data를 query String으로 변경되면 안되므로  false처리함
					processData:false,
					//true: application/x-www-form-urlencoded
					//false: multipart/form-data
					contentType:false,
					dataType:"text",
					success:function(result){
						
					}
				});
			});
		});
	</script>
</body>
</html>




