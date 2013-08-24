<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 function test(num){
 if(num=="1"){
  document.getElementById("test1").src="<%=basePath%>upload/uploadpage.do";
 }else if(num=="2"){
  document.getElementById("test2").src="<%=basePath%>manager/findmanagers.do";
 }
  
 }
 
 function tabsinit(){
  document.getElementById("test1").src="<%=basePath%>upload/uploadpage.do";
 }
</script>
</head>
<body onload="tabsinit()" topmargin="0px;" leftmargin="10px">
 <div id="tabs">
  <ul>
   <li><a href="#tabs-1" onclick="test(1)">第一标签</a></li>
   <li><a href="#tabs-2" onclick="test(2)">第二标签</a></li>
   <li><a href="#tabs-3" >第三标签</a></li>
   <li><a href="#tabs-4">第四标签</a></li>
  </ul>
  <div id="tabs-1" ><iframe width="100%" height="500px" id="test1"></iframe></div>
  <div id="tabs-2" ><iframe id="test2"></iframe></div>
  <div id="tabs-3" >gfdfd</div>
  <div id="tabs-4" >111111dfgdfg</div>
 </div>

</body>
</html>