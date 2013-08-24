<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body onload="init()">
<form method="post" action="<%=basePath%>upload/uploadfile.do" enctype="multipart/form-data">
 <input type="file" name="file" />
 <input type="file" name="file2" />
 <input type="submit"  value="上传"/>
</form>
<%@ include file="/include/confirm.jsp" %>
</body>