$(function(){
	var total = window.totalPnumber||260;/*总人数*/
	var totalArr = [];/*总人数数组*/
	var Awards = [];/*获奖号码数组*/
	var newarr = [];/*新数组，用来替换和操作*/
	var timer = null;/*创建定时器*/
	var showNum;/*抽中的号码*/
	var mathNum;/*随机的索引*/
	/*号码填入数组*/
	for (var i = 1; i<=total ; i++) {
		if(i<10){
			totalArr[i-1] = '00'+i;
		}else if(i>9 && i<100){
			totalArr[i-1] = '0'+i;
		}else {
			totalArr[i-1] = i;
		}
	};

	/*把总人数数组付给新数组*/
	newarr = totalArr;
	/*开始随机跳号码*/
	function start(){
		clearInterval(timer);
		timer = setInterval(function(){
			/*打乱数组*/
			newarr = newarr.sort(function(){return Math.random()>.5 ? -1 : 1; });
//			mathNum = Math.floor(Math.random()*newarr.length)
//			showNum = newarr[mathNum];
			/*屏幕号码跳动*/
			$(".num").html(newarr[0]);
		},30);
	};
	
	/*停止随机号码*/
	function stop(){
		clearInterval(timer);
	};
	
	
	$(document).keydown(function(e){
	    if(!e) var e = window.event; 
	    /*按键盘的z键开始*/
	    if(e.keyCode==90){
	    	start();
	    	$(".num").attr("class","num");
	    };
	    /*按键盘的t键开始*/
	    if(e.keyCode==84){
	    	stop();
	    	$(".num").attr("class","num animation");
	    	
	    };
	    /*抽中的号码要不要 y键*/
	    if(e.keyCode==89){
	    	/*把抽中的号码放进获奖号码数组*/
	    	Awards.push(newarr[0]);
	    	/*删除获奖号码，并且生成新数组*/
	    	newarr.splice(0, 1);
	    	if(Awards.length!="20"){
	    		$(".three span").html("000");
	    	};
	    	
	    	console.log(Awards+":"+Awards.length);
	    };
	    /*按键盘的r键,显示出结果*/
	   	if(e.keyCode==82){
	   		var result = "";
   			for(var i = 0; i<Awards.length;i++){
   				result +="<li>"+Awards[i]+"</li>";
   			};
   			$(".three span").fadeOut();
   			$(".three ul").html(result).attr("class","animation1").css("display","block");
	    };
	    /*下一个抽奖 n 键*/
	    if(e.keyCode==78){
	    	if(Awards.length=="10"){
	    		$(".three ul").fadeOut(function(){
	    			$(".three span").fadeIn();
	    			$(".three").css("background-image","url(images/erdengjiang.jpg)")
	    		});
	    	}else if(Awards.length=="16"){
	    		$(".three ul").fadeOut(function(){
	    			$(".three span").fadeIn();
	    			$(".three").css("background-image","url(images/yidengjiang.jpg)")
	    		});
	    	}else if(Awards.length=="19"){
	    		$(".three ul").fadeOut(function(){
	    			$(".three span").fadeIn();
	    			$(".three").css("background-image","url(images/tedengjiang.jpg)")
	    		});
	    	};
	    	
	   		$(".three ul").attr("class","");
	    	
	    };
	    
	    
	});
	
	
	
	
});
