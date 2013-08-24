<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"> 

  function addcallback(){
   //应该做字段的校验
   document.getElementById("addForm").submit();
  }
  
  function findcallback(){
   document.getElementById("findForm").submit();
  }
  
</script>
</head>
<body topmargin="0px;" leftmargin="0px" onload="init()">
 <table width="100%" border="0">
  <tr>
   <td>
    <input type="button" value="删除" onclick="del()"><input type="button" value="新建" onclick="add()"><input type="button" value="查询" onclick="find()"><input type="button" value="刷新" onclick="location.href='<%=basePath%>manager/findmanagers.do'"/>
   </td>
  </tr>
 </table>
 <table id="tablelist" width="100%" style="border-style: solid;" cellpadding="0" cellspacing="0" frame="box" rules="all" bordercolor="#66B3FF">
  <tr style="background-color: #ffffff;color: #66B3FF;border-color: #66B3FF;">
   <th height="20px"><input type="checkbox" name="checkedAll" id="checkedAll"/></th>
   <th>登录名</th>
   <th>角色</th>
   <th>创建时间</th>
  </tr>
  <c:forEach items="${pageResult.result}" var="u" varStatus="s">
  <tr align="center">
   <td height="20px"><input type="checkbox" name="ids" value="${u.managerid }"/></td>
   <td><a href="<%=basePath%>manager/managerinfo_${u.managerid}.do">${u.loginname }</a></td>
   <td>${u.rolestr }</td>
   <td>${u.intimestr }</td>
  </tr>
  </c:forEach>
 </table>
 <table width="100%">
  <tr>
   <td height="20px" align="right">
    <form method="POST" name="pageForm" id="pageForm" action="">
    <pageset:PageBar pageUrl="findmanagers.do" pageAttrKey="pageResult"></pageset:PageBar>
     <c:if test="${manager.loginname != null && manager.loginname != ''}">
      <input type="hidden" name="loginname" value="${manager.loginname }" />
     </c:if>
    </form>
   </td>
  </tr>
 </table>
 <form id="delForm" action="<%=basePath%>manager/delmanagers.do" method="post">
  <input type="hidden" name="ids4del" id="ids4del" val="">
 </form>
 <div id="finddialog" title="查询">
  <table width="100%" height="100px">
  <form id="findForm" action="<%=basePath%>manager/findmanagers.do" method="post">
  <tr>
   <td>登录名：</td><td><input type="text" name="loginname" maxlength="10" style="height: 43px;"/></td>
  </tr>
  </form>
  </table>
 </div>
 <div id="adddialog" title="新建">
  <table>
  <form id="addForm" action="<%=basePath%>manager/addmanager.do" method="post">
   <tr><td>用户名：</td><td><input type="text" maxlength="10" name="loginname"/></td></tr>
   <tr><td>入表时间：</td><td><input type="text" maxlength="20" name="intime"/></td></tr>
   <tr>
    <td>角色：</td>
    <td>
     <select name="role">
      <option value="0">总管理员</option>
      <option value="1">管理员</option>
     </select>
    </td>
   </tr>
  </form>
  </table>
 </div>
 <%@ include file="/include/confirm.jsp" %>
</body>
</html>