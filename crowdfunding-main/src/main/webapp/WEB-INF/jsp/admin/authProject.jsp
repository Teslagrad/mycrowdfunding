<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh_CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
	<%@ include file="/WEB-INF/jsp/common/css.jsp" %>
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
<form id="queryForm" class="form-inline" role="form" style="float:left;" action="${PATH}/admin/index" method="post">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input class="form-control has-success" value="${param.condition }" name="condition" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning" onclick="$('#queryForm').submit()"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>

<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input id="selectAll"  type="checkbox"></th>
                  <th>项目名称</th>
                  <th>发起人</th>
                  <th>目标金额（元）</th>
                  <th>众筹周期(天)</th>
                  <th>创建时间</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
              
              
              	<c:forEach items="${page.list }" var="project" varStatus="status">
	                <tr>
	                  <td>${status.count}</td>
					  <td><input type="checkbox" adminId="${admin.id }"></td>
	                  <td>${project.name }</td>
	                  <td>17875304029</td>
	                  <td>${project.money }</td>
	                   <td>${project.day }</td>
	                    <td>${project.createdate }</td>
	                  <td>
						  <button type="button" adminId="${project.id}" class="deleteBtnClass btn btn-danger btn-xs" ><i class=" glyphicon glyphicon-check"></i></button>
					  </td>
	                </tr>
                </c:forEach>
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="8" align="center">
						<ul class="pagination">
						<c:if test="${page.isFirstPage }">
								<li class="disabled"><a href="#">上一页</a></li>
						</c:if>
						<c:if test="${!page.isFirstPage }">
								<li ><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum-1}">上一页</a></li>
						</c:if>
						<c:forEach items="${page.navigatepageNums }" var="num">
							<c:if test="${num==page.pageNum }">
									<li class="active"><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num} <span class="sr-only">(current)</span></a></li>
							</c:if>		
							<c:if test="${num!=page.pageNum }">
									<li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num }</a></li>	
							</c:if>		
											
						</c:forEach>
   						<c:if test="${page.isLastPage }">
								<li class="disabled"><a href="#">下一页</a></li>
						</c:if>
						<c:if test="${!page.isLastPage }">
								<li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum+1}">下一页</a></li>
						</c:if>
								
							 </ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
			  </div>
			</div>
        </div>
      </div>
    </div>

     <%@ include file="/WEB-INF/jsp/common/js.jsp" %>
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
          
            $(".deleteBtnClass").click(function(){
            	var id=$(this).attr("adminId");
            	//询问框
				layer.confirm('是否确定通过审核？',{btn:['确定','取消']},function(index){
					
					window.location.href="${PATH}/authProject/doAuth?pageNum=${page.pageNum}&id="+id;
					layer.close(index);
				},function(index){
					layer.close(index);
				});

            });
            
            
            $("#selectAll").click(function() {
				//$("tbody input[type='checkbox']").attr("checked",this.checked);
            	$("tbody input[type='checkbox']").prop("checked",this.checked);
			});
            
            
            $("#deleteBatchBtn").click(function() {
            	var checkedBoxList = $("tbody input[type='checkbox']:checked");
            	console.log(checkedBoxList);
            	
            	if(checkedBoxList.length==0){
            		layer.msg("请选中再删除");
            		return false;
            	}
            	//var id='1,2,3,4,5';
            	var ids='';
            	var array = new Array();
            	$.each(checkedBoxList,function(i,e){
            		var adminId = $(e).attr("adminId");//取自定义属性
            		array.push(adminId);
            	});
            	ids=array.join(",");
            	console.log(ids);
            	
				layer.confirm('是否确定删除这些数据？',{btn:['确定','取消']},function(index){
					
					window.location.href="${PATH}/admin/doDeleteBatch?pageNum=${page.pageNum}&ids="+ids;
					layer.close(index);
				},function(index){
					layer.close(index);
				});
            	
            	
			});
            
            
        </script>
  </body>
</html>
    