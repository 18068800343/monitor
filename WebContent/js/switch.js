$(window).load(function(){
		$('#status').bootstrapSwitch('onText','开启').bootstrapSwitch('offText','关闭').bootstrapSwitch("onColor",'success')
		.bootstrapSwitch('state',true);
		$('#status').bootstrapSwitch("onSwitchChange",function(event,state){
		    var val='';
		    var text='';
		    if(state==true){
		        val=1;
		        text='开启';
		    }else{
		        val=0;
		        text='关闭';
		    }
		})
});