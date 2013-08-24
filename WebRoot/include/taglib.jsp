<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="/standart/c" prefix="c" %>
<%@ taglib prefix="pageset" tagdir="/WEB-INF/tags" %>
<%
	String path = request.getContextPath();
	String ipport = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link type="text/css" href="<%=basePath%>css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="<%=basePath%>js/jquery.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () {
 // Accordion
 $("#accordion").accordion({ header: "h3" });
 // Tabs
 $('#tabs').tabs();
 //Button
 $('#button').button();
 $('input[type=button]').button();
 $('input[type=submit]').button();
 
 // Dialog
 $('#dialog').dialog({
  autoOpen: false,
  width: 600,modal:true,
  buttons: {
   "确认": function () {
    $(this).dialog("close");
   },
   "关闭": function () {
    $(this).dialog("close");
   }
  }
 });
 
 $('#adddialog').dialog({
  autoOpen: false,
  width: 600,modal:true,
  buttons: {
   "提交": function () {
    $(this).dialog("close");
    addcallback();
   },
   "取消": function () {
    $(this).dialog("close");
   }
  }
 });
 
 $('#finddialog').dialog({
  autoOpen: false,
  width: 600,modal:true,
  buttons: {
   "查询": function () {
    $(this).dialog("close");
    findcallback();
   },
   "取消": function () {
    $(this).dialog("close");
   }
  }
 });
 
 $('#msgdialog').dialog({
  autoOpen: false,
  width: 600,modal:true,
  buttons: {
   "确认": function () {
    $(this).dialog("close");
   },
   "关闭": function () {
    $(this).dialog("close");
   }
  }
 });
 
 /*确认框，对dialog进行了重写
  1、添加了confirm样式，去掉右上角的X按钮，因为找不到监听这个按钮事件的方法
  2、使用户按Esc按钮失效，也是因为找不到监听的方法
  3、添加了确认、取消按钮的回调函数，以方便下一步的操作
  4、添加前两者，主要是用户体验中，Esc、右上角关闭图标感觉等同于取消按钮，但是我无法去监听这两个的事件，导致无法去调用取消按钮的回调函数
  */
 $('#confirm').dialog({
  autoOpen: false,
  width: 600,modal:true,closeOnEscape:false,
  dialogClass: "confirm",
  buttons: {
   "确认": function () {
    $(this).dialog("close");
    callback(true);
   },
   "取消": function () {
    $(this).dialog("close");
    callback(false);
   }
  }
 });
 
 // Dialog Link
 $('#dialog_link').click(function () {
  $('#dialog').dialog('open');
   return false;
 });
  
 // Autocomplete
 $("#autocomplete").autocomplete({
  source: ["c++", "java", "php", "coldfusion", "javascript", "asp", "ruby", "python", "c", "scala", "groovy", "haskell", "perl"]
 });
 
 // Datepicker
 $('#datepicker').datepicker({
  inline: true
 });
 
 // Slider
 $('#slider').slider({
  range: true,
  values: [17, 67]
 });
 
 var values = [50, 80, 20, 40, 70];
 $("#verticalSliders > div").each(function (i, item) {
  $(item).slider({ orientation: "vertical", range: "min", min: 0, max: 100, value: values[i] });
 });
 
 //hover states on the static widgets
 $('#dialog_link, ul#icons li').hover(
  function () { $(this).addClass('ui-state-hover'); },
   function () { $(this).removeClass('ui-state-hover'); }
 );
});





$("document").ready(
 function(){
  $("#tablelist tr:even").addClass("tdstyle2");
  $("#tablelist tr:odd").addClass("tdstyle1");
  //监听全选框的点击事件
  $("#checkedAll").click(
   function(){
    //获取全选框是否被选中
    var isCheckedAll = $(this).attr("checked")=="checked";
     //遍历name为ids的复选框
     $("input[type=checkbox][name=ids]").each(
      function(){
       //将复选框的选中状态设置的与全选框一致
       $(this).attr("checked",isCheckedAll);
      }
     )
   }
  );
 });
 
 //设置全局的要删除的主键数组
 var vals = [];
 function del(){
  //遍历所有name为ids的选中的复选框
  $("input[name='ids']:checked").each(
   //将复选框的值（数据主键）压入到数组
   function(){
    vals.push($(this).val());
   });
   //如果数组长度为0，弹出要求至少选择一条记录的提示框
   if(vals.length==0){
    $("#dialog").dialog("open");
    return false;
   }else{
    //如果有数据，则弹出确认删除与否的确认框，根据选择（确认、取消）回调callback函数
    $("#confirm").dialog("open");
   }
  }
  
  //确认框的回调函数
  function callback(flag){
   //如果是确认，将数组压入到的delForm中的隐藏的name为ids4del的val中，提交执行删除form，否则清空数组
   if(flag){
    $("#ids4del").val(vals.join());
    var form = document.getElementById("delForm");
    form.submit();
   }else{
    vals = [];
   }
  }
  
  function add(){
   $("#adddialog").dialog("open");
  }
  
  function find(){
   $("#finddialog").dialog("open");
  }
  
  function init(){
   var remsg = "${remsg}";
   if(remsg!=""){
    $("#msgdialog").dialog("open");
   }
  }
</script>
<style type="text/css">
 body{font-family:"Segoe UI", Helvetica, Verdana;font-size: 12px;}
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
 .confirm .ui-dialog-titlebar-close{ display: none;}
 tr.tdstyle1 td{
  background: #ffffff;
  border-color:#ffffff;color:#4A4A4A;
 }
 tr.tdstyle2 td{
  background: #DAD2CF;
  border-color:#DAD2CF;color:#ffffff;
 }
 tr.tdstyle2 a{
  background: #DAD2CF;
  border-color:#DAD2CF;color:#ffffff;
 }
 .thstyle{
  background-color: #0072C5;color: #ffffff;border-color: #0072C5;
 }
 
</style>
