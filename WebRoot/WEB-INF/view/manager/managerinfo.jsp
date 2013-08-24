<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
  function check(){
   //做校验
   return true;
  }
</script>
</head>
<body topmargin="0px;" leftmargin="0px">
 <form action="updmanager.do" method="post">
 <table width="100%" border="1">
  <tr>
   <td>登录名：<input type="text" maxlength="10" name="loginname" value="${manager.loginname }"/></td>
   <td>角色：
    <select name="role">
    <c:choose>
     <c:when test="${manager.role==0 }">
      <option value="0" selected="selected">总管理员</option>
      <option value="1">管理员</option>
     </c:when>
     <c:otherwise>
      <option value="0">总管理员</option>
      <option value="1" selected="selected">管理员</option>
     </c:otherwise>
    </c:choose>
    </select>
    <input type="hidden" name="managerid" value="${manager.managerid }"/>
   </td>
  </tr>
  <tr>
   <td colspan="2" align="right">
    <input type="submit" value="提交" onclick="return check()"/>
    <input type="button" value="返回" onclick="location.href='<%=basePath%>manager/findmanagers.do'"/>
   </td>
  </tr>
 </table>
 </form>
</body>
</html>