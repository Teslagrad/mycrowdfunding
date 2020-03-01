<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<%@ include file="/WEB-INF/jsp/common/css.jsp"%>
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
			<jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 菜单树
						</h3>
					</div>
					<div class="panel-body">

						<ul id="treeDemo" class="ztree"></ul>



					</div>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="/WEB-INF/jsp/common/js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
			initTree();
		});
		
		function initTree(){
			var setting = {
					data: {
						simpleData: {
							enable: true,
							pIdKey:"pid"
						}
					},
					view:{
        				addDiyDom: function(treeId, treeNode){//设置节点后面显示一个按钮
        					$("#"+treeNode.tId+"_ico").removeClass();//.addClass();
        					$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>")

						}
					}
			};

			var url="${PATH}/menu/loadTree";
			var json={};
			$.get(url,json,function(result){// ListK<Tmenu> ->jSon ->简单个是json数据
				var zNodes =result;
				zNodes.push({"id":0,"name":"系统菜单","icon":"glyphicon glyphicon-th-list"});
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				treeObj.expandAll(true);
			});
			
		}
		
		
		
	</script>
</body>
</html>
