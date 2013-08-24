<%@ page language="java" pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/include/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <title>系统</title>
  <script type="text/javascript">
   function checkLogin(){
    var pwdReq=/^[a-zA-Z0-9_]{6,16}$/; //密码
    var unameReq=/^[a-zA-Z0-9_]{4,12}$/;//登录名
    var uname = document.getElementById("loginname");
    var pwd = document.getElementById("pwd");
    var vercode = document.getElementById("vercode");
    
    if (uname.value.replace(/\s/g,"") == "") {
     alert("请填写您的登录名");
     uname.focus();
     return false;
    }
    
    if(pwd.value.replace(/\s/g,"") == ""){
     alert("密码不可以为空");
     pwd.focus();
     return false;
    }
    
    if(vercode.value.replace(/\s/g,"") == ""){
     alert("验证码不可以为空");
     vercode.focus();
     return false;
    }
    
    document.getElementById("loginform").action = "<%=basePath%>manager/login.do";
    return true;
   }
   
   function initfocus(){
    document.getElementById("loginname").focus();
   }
 </script>
  </head>
  
  <body onload="initfocus()">
  ${remsg }
   <form action="" id= "loginform" method="post" onsubmit="return checkLogin();">
    <table>
     <tr>
      <td>用户名1：</td>
      <td><input type="text" maxlength="10" name="loginname"/></td>
     </tr>
     <tr>
      <td>密码：</td>
      <td><input type="password" maxlength="10" name="pwd"/></td>
     </tr>
     <tr>
      <td>验证码：</td>
      <td>
       <input type="text" maxlength="10" name="vercode"/>
       <img src="<%=basePath%>vercode/getvercode.do" width="55" height="20" id="kaptchaImage"  style="margin-bottom: -3px"/>
       <script type="text/javascript">
       $(function(){
        $('#kaptchaImage').click(function () {//生成验证码
        $(this).hide().attr('src', '<%=basePath%>vercode/getvercode.do?' + Math.floor(Math.random()*100) ).fadeIn(); })});
       </script></td>
     </tr>
     <tr>
      <td colspan="2"><input type="submit" value="登录"/></td>
     </tr>
    </table>
   </form>
  </body>
</html>
