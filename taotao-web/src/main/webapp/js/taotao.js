var TT = TAOTAO = {
	checkLogin : function(){
		var _token = $.cookie("TT_COOKIE");
		if(_token==""){
			return;
		}
		$.ajax({
			url : "http://www.taotao.com/service/user/" + _token,
			dataType : "json",
			type : "GET",
			success : function(data){
					var html =data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
			},
			error:function (data) {
				alert("出错了~~~"+data)
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});