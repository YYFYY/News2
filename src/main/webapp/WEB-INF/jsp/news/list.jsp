<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <c:forEach items="${thirdMenus }" var="thirdMenu">
				<a href="#" class="easyui-linkbutton" iconCls="${thirdMenu.icon }" onclick="${thirdMenu.url}" plain="true">${thirdMenu.name }</a> 
				
				</c:forEach>
        </div>
        <div class="wu-toolbar-search">
            <label>新闻标题:</label><input id="search-name" class="wu-text" style="width:100px">
             <label>作者:</label><input id="search-author" class="wu-text" style="width:100px">
             <label>新闻分类:</label> <select
					id="search-category" class="easyui-combobox" panelHeight="auto"
					style="width: 120px">
					<option value="-1">全部</option>
					<c:forEach items="${newsCategoryList }" var="category">
						<option value="${category.id }">${category.name }</option>
					</c:forEach>
				</select> 
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>

<%@include file="../common/footer.jsp"%>

<!-- End of easyui-dialog -->
<script type="text/javascript">
	
	
	
	/**
	* 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var item = $('#data-datagrid').datagrid('getSelected');
				if(item == null || item.length == 0){
					$.messager.alert('信息提示','请选择要删除的数据！','info');
					return;
				}
				$.ajax({
					url:'delete',
					dataType:'json',
					type:'post',
					data:{id:item.id},
					success:function(data){
						if(data.type == 'success'){
							$.messager.alert('信息提示','删除成功！','info');
							$('#data-datagrid').datagrid('reload');
						}else{
							$.messager.alert('信息提示',data.msg,'warning');
						}
					}
				});
			}	
		});
	}
	
	/**
	* Name 打开添加窗口
	*/
	function openAdd(){

		window.location.href='addlist';
	
	}
	
	/**
	* 打开修改窗口
	*/
	function openEdit(){
		var item = $('#data-datagrid').datagrid('getSelected');
		if(item == null || item.length == 0){
			$.messager.alert('信息提示','请选择要修改的数据！','info');
			return;
		}
		window.location.href='editlist?id='+item.id;
	}	
	
	
	//搜索按钮监听
	$("#search-btn").click(function(){
		var option = {name:$("#search-name").val(),
				categoryId:$("#search-category").combobox('getValue'),
				author:$("#search-author").val()};
		$('#data-datagrid').datagrid('reload',option);
	});
	
	/** 
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'list2',
		rownumbers:true,
		singleSelect:true,
		pageSize:20,           
		pagination:true,
		multiSort:true,
		fitColumns:true,
		idField:'id',
	    treeField:'name',
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'title',title:'标题',width:300,formatter:function(value,row,index){
				return '<a href="../../news/detail?id='+row.id+'" target="_blank">' + value + '</a>';
			}},
			{ field:'categoryId',title:'所属分类',sortable:true,width:100,formatter:function(value,rows,index){
				return rows.newsCategory.name;
			}},
			{ field:'author',title:'作者',sortable:true,width:50},
			{ field:'tags',title:'标签',sortable:true,width:100},
			{ field:'viewNumber',title:'浏览量',sortable:true,width:30},
			{ field:'commentNumber',title:'评论数',sortable:true,width:30},
		]],
	});
</script>