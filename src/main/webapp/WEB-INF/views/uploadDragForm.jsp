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
      var formData = new FormData();
      
      
      $("#dropBox").on("dragenter dragover", function(event){
         event.preventDefault(); 
      })
      
      $("#dropBox").on("drop", function(event){
         event.preventDefault();
         
         var files = event.originalEvent.dataTransfer.files;
         var file = files[0]; // 여러 개의 파일을 허용하기 위해서는 반복문을 돌리면 됨
         //console.log(file); 
         
         var reader = new FileReader();
         reader.addEventListener("load", function(){
            var divObj = $("<div>").addClass("item");
            var imgObj = $("<img>").attr("src", reader.result);
            divObj.html(imgObj);
            
            $("#dropBox").append(divObj);
         }, false);
         
         // 파일을 reader에 넣어주는 순서가 이벤트 보다 뒤에 있어야 함
         // 파일 크기가 작을 경우 리스너를 다는 명령문 보다 load가 더 빨리 끝나서 리스너가 실행되지 않을 수도 있기 때문 
         if(file){
            reader.readAsDataURL(file);
         }
         
         if(formData == null){
            formData = new FormData();
         }
         
         // <input type="file" name="files" value="file">과 동일한 명령문
         formData.append("files", file);
      })
      
      $("#f1").submit(function(event){
         event.preventDefault();
         formData.append("test", $("input[name='test']").val());
         
         $.ajax({
            url: "uploadDrag",
            data : formData,
            dataType : "json",
            type : "post",
            processData : false, // formData 형식으로 ajax를 보내기 위해서는 processData, contentType을 지정해줘야 함
            contentType : false, // multipart/form-data로 설정하는 것과 동일, false : multipart/form-data, 
                            // true : enctype="application/x-www-form-urlencoded를 의미함
            success : function(result){
               console.log(result);
               
               $("#dropBox").empty();
               if(result == null){
                  alert("전송 실패");
               }else{
                  $(result).each(function(i, obj){
                     var divObj = $("<div>").addClass("item");
                     var imgObj = $("<img>").attr("src", "displayFile?filename=" + obj);
                     divObj.append(imgObj);
                     
                     var inputObj = $("<input>").val("X").addClass("del").attr("type", "button").attr("data-del", obj);
                     divObj.append(inputObj);
                     
                     $("#dropBox").append(divObj);
                                       
                  })
               }
            }
         })
      })
      
      $(document).on("click", ".del", function(){
         var filename = $(this).attr("data-del");
         var targetDiv = $(this).parents(".item");
         
         $.ajax({
            url : "deleteFile?filename=" + filename,
            type : "get",
            dataType : "text",
            success : function(result){
               console.log(result);
               targetDiv.remove();
            }
         }) 
      })
   })
   
</script>
</body>
</html>




