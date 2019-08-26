$("#logOut").on("click",function(){
	$.ajax({
        type: 'POST',
        url: '../Index2Servlet',
        dataType: 'json',
        data: {
        	type:'loginOut'
        },
        success: function (json) {
        	window.location.href='login.html';
        }
	})
})

function showUserName(){
	$.ajax({
        type: 'POST',
        url: '../Index2Servlet',
        dataType: 'json',
        data: {
        	type:'showName'
        },
        success: function (json) {
        	var obj=json.obj;
        	$("#uname").text(obj);
        }
	})
}