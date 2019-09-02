var TT = TAOTAO = {
	checkLogin : function(){
		var _token = $.cookie("TT_COOKIE");
		if(_token==""){
			return;
		}
		$.ajax({
			url : "http://sso.taotao.com/service/user/" + _token,
			dataType : "jsonp",
			async: false,
			type : "GET",
			jsonp: "callbackparam", //服务端用于接收callback调用的function名的参数
			jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来
			success : function(data){
					alert(data)
					var html =data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
			},
			error:function (data) {
				alert("出错了~~~")
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});