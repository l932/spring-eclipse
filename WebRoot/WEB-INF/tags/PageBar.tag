<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageUrl" required="true" rtexprvalue="true" description="分页页面对应的URl" %>
<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true" description="Page对象在Request域中的键名称" %>
<c:set var="pageUrl" value="${pageUrl}" />
<%
   String separator = pageUrl.indexOf("?") > -1?"&":"?";
   jspContext.setAttribute("pageResult", request.getAttribute(pageAttrKey));
   jspContext.setAttribute("pageUrl", pageUrl);
   jspContext.setAttribute("separator", separator);
%>
<script type="text/javascript">
 function pageFind(pageNo){
  var url = "${pageUrl}"+"${separator}pageNo="+pageNo;
  document.getElementById("pageForm").action=url;
  document.getElementById("pageForm").submit();
 }
</script>
<div style="font:12px;background-color:#ffffff">
	共${pageResult.totalPageCount}页，第${pageResult.currentPageNo}页
	<c:if test="${pageResult.currentPageNo <=1}">
	   首页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo >1 }">
	   <a href="javascript:pageFind(1)">首页</a>&nbsp;&nbsp;	   
	</c:if>
	<c:if test="${pageResult.hasPreviousPage}">
	  <a href="javascript:pageFind(${pageResult.currentPageNo -1 })">上一页</a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${!pageResult.hasPreviousPage}">
	  上一页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.hasNextPage}">
	  <a href="javascript:pageFind(${pageResult.currentPageNo +1 })">下一页</a>&nbsp;&nbsp;
	</c:if>
	<c:if test="${!pageResult.hasNextPage}">
	  下一页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo >= pageResult.totalPageCount}">
	   末页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.currentPageNo < pageResult.totalPageCount}">
	   <a href="javascript:pageFind(${pageResult.totalPageCount })">末页</a>&nbsp;&nbsp;	   
	</c:if>	
</div>