<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Spring3框架</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<link type="text/css" href="<%=basePath%>css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="<%=basePath%>js/jquery.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ui.min.js" type="text/javascript"></script>
<script type="text/javascript">
 $(function () {
  // Accordion
  $("#accordion").accordion({ header: "h3" });
 });
 
 function hidleft(){
	 var ltd = document.getElementById("lefttd");
	 if(ltd.style.display=="none"){
		 ltd.style.display="";
	 }else{
		 ltd.style.display="none";
	 }
 }
</script>
<style type="text/css">
 body{font-family:"Segoe UI", Helvetica, Verdana;font-size: 12px;background: #0072C5;height: 700px;margin: 0px;}
 #wrapper{width:200px;}
 h1{font-weight:normal;}
 .header { margin-top: 2em;font-weight:normal; }
 #dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
 #dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
 ul#icons {margin: 0; padding: 0;}
 ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
 ul#icons span.ui-icon {float: left; margin: 0 4px;}
 #verticalSliders{height:140px;padding-top:20px;}
 #verticalSliders > div{float:left;margin:20px;}
</style>
</head>
  
<body>
 <table width="100%" height="480px" border="0" cellpadding="0" cellspacing="0" bgcolor="#3399ff">
  <tr height="80px" bgcolor="#ffffff">
   <td colspan="3" bgcolor="#0072C5">
   欢迎
   </td>
  </tr>
  <tr bgcolor="#FFFFFF">
   <td height="100%" width="300px" valign="top" id="lefttd" style="border-right:1px solid #0072C5;">
    <div id="accordion">
     <div>
      <h3><a href="#">系统管理</a></h3>
      <div>
       <a target="main" href="<%=basePath%>manager/findmanagers.do">管理员管理</a><br/>
       <a target="main" href="../logout.html">退出</a><br/>
      </div>
	 </div>
	 <div>
	  <h3><a href="#">上传管理</a></h3>
	  <div>
	   <a target="main" href="<%=basePath%>upload/uploadpage.do">上传文件</a>
	  </div>
	 </div>
	 <div>
	  <h3><a href="#">功能例子</a></h3>
	  <div><a target="main" href="../target2.jsp">选项卡例子</a></div>
	 </div>
	</div>
   </td>
   <a href="#" onclick="hidleft()"><td height="100%" valign="middle" width="4px"><img border="0" src="<%=basePath%>img/lr.gif"/></td></a>
   <td height="100%" valign="top"><iframe id="main" width="100%" height="100%" frameborder="0" name="main" src="../target.jsp"/></td>
  </tr>
 </table>
</body>
</html>
