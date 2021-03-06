/**
 * @date 2014-10-09
 * @author lee
 * @see 页面加载、所有相关元素的事件绑定及通用方法的定义
 */
$(function(){
	
	$(".title-time-box span:last").html(getNowFormatDate(new Date()));
	//时间显示
	/*window.setInterval(function(){
		var date = new Date();
		var second = date.getSeconds();
		
		date.setSeconds(second, 1000);
		
		$(".title-time-box span:last").html(date.toLocaleString());
	}, 1000);*/
	
	//加载菜单
	$.post("data/menu.json",{},function(data){
		var _accordion = $("#index-accordion");
		
		$.each(data,function(i, o){
			
			var _ul = $('<ul class="menu-box"></ul>');
			
			$.each(o.children,function(i,child){
				var _a = $('<a></a>');
				
				_a.data("menuUrl",child.menuUrl);
				_a.data("openMode",child.openMode);
				
				if("dialog" == child.openMode){
					_a.data("showType",child.showType);
					_a.data("width",child.width);
					_a.data("height",child.height);
					_a.data("param",child.param);
					_a.data("maximizable",child.maximizable);
				}
				
				var _span = $('<span>' + child.menuName + '</span>');
				
				_a.append(_span);
				
				var _li = $("<li><div></div></li>");
				
				_li.find("div").append(_a);
				
				_ul.append(_li);
			});
			
			_accordion.accordion("add",{
				title:o.menuName,
				iconCls:o.menuPic,
				content:_ul
			});
		});
		
		_accordion.accordion("unselect",$("#index-accordion .accordion-body:last").parent().index());
	},"json");
	
	//系统样式
	$("#styleBox").combobox({
		url:"data/style.json",
		method:"post",
		groupField:"group",
		textField:"text",
		valueField:"value",
		onSelect:function(record){
			var href = record.value;
			
			$.each($("link"),function(index,obj){
				if(obj.href.toLowerCase()
						.indexOf("easyui.css") > -1){
					obj.href = href;
					return false;
				}
			});
		}
	});
	
	//左侧菜单点击事件
	$("#index-accordion").on({
		click:function(){
			var title = $.trim($(this).text());
			var href = $(this).data("menuUrl");
			var mode = $(this).data("openMode");
			
			var menuIndex = $(this).closest("div.panel").index();
			var subMenuIndex = $(this).closest("li").index();
			
			if("tab" == mode){
				openMenu({
					title:title,
					href:href,
					target:{
						mode:mode,
						tabId:"#index-tab",
						menu:menuIndex,
						subMenu:subMenuIndex
					}
				});
			}else if("dialog" == mode){
				var showType = $(this).data("showType");
				var width = $(this).data("width");
				var height = $(this).data("height");
				var param = $(this).data("param");
				var maximizable = $(this).data("maximizable");
				
				if(0 == width || null == width || "" == width || undefined == width){
					width = 600;
				}
				
				if(0 == height || null == height || "" == height || undefined == height){
					height = 400;
				}
				
				openMenu({
					title:title,
					href:href,
					maximizable:maximizable,
					param:param,
					target:{
						type:showType,
						mode:mode,
						width:width,
						height:height
					}
				});
			}else if("iframe" == mode){
				openMenu({
					title:title,
					href:href,
					target:{
						mode:mode,
						tabId:"#index-tab",
						menu:menuIndex,
						subMenu:subMenuIndex
					}
				});
			}else if("window" == mode){
				openMenu({
					href:href,
					target:{
						mode:mode
					}
				});
			}
		},
		mouseover:function(){
			$(this).parent().addClass("hover");
		},
		mouseout:function(){
			$(this).parent().removeClass("hover");
		}
	},"div ul li div a");
	
	//tab页事件绑定
	$("#index-tab").tabs({
		onSelect: function(title,index){
			var selectedTab = $(this).tabs("getSelected");
			
			var menuIndex = selectedTab.panel("options").menu;
			var subMenuIndex = selectedTab.panel("options").subMenu;
			
			var selectedMenu = $("#index-accordion").accordion("getSelected");
			
			if($("ul.menu-box li div.selected")){
				$("ul.menu-box li div.selected").removeClass("selected");
			}
			
			if(selectedMenu){
				var selectedMenuIndex = $("#index-accordion").accordion("getPanelIndex", selectedMenu);
				
				if(menuIndex != selectedMenuIndex){
					$("#index-accordion").accordion("unselect", selectedMenuIndex);
					$("#index-accordion").accordion("select", menuIndex);
				}
			}else{
				$("#index-accordion").accordion("select", menuIndex);
			}
			
			$(".menu-box:visible").children("li:eq(" + subMenuIndex + ")").children("div").addClass("selected");	
		}
	});
	
	$("#open-div").click(function(){
		var isOpen = $(this).attr("o");
		if(isOpen=="false"){
			$(".layout-panel-north").hide();
			$(".layout-panel-west").css("top","0px");
			$(".layout-panel-center").css("top","0px");
			$(this).css("top","1px");
			$(this).css("right","80px");
			$(this).find("#index-open-all").linkbutton({iconCls:"icon-shrink",text:"缩小"});
			$(this).attr("o",true);
		}else{
			$(".layout-panel-north").show();
			$(".layout-panel-west").css("top","112px");
			$(".layout-panel-center").css("top","112px");
			$(this).css("top","84px");
			$(this).css("right","219px");
			$(this).find("#index-open-all").linkbutton({iconCls:"icon-open",text:"全屏"});
			$(this).attr("o",false);
		}
		$(window).resize();
	})
	
	
	//弹出框初始化
	$("#common-dialog").dialog({
		cache:false, 
		modal:true, 
		closed:true,
		method:"post",
		resizable:true, 
		buttons:"#common-dialog-buttons",
		onClose:function(){
			$("#common-dialog-buttons").children(":hidden").show();
			$(this).panel().find(".panel-body").children().remove();
			
			if(null != intervalId){
				window.clearInterval(intervalId);
				intervalId = null;
			}
			
			if(null != timeoutId){
				window.clearTimeout(timeoutId);
				timeoutId = null;
			}
		}
	});
	
	//弹出框按钮事件
	$("#common-dialog-save").click(function(){
		
		var ifValid = $("#common-dialog form")
						.form("enableValidation")
						.form("validate");
		
//		JP
		//easyui的异步请求头部没有x-requested-with
		if(ifValid){
			if(Callback.submitBySelf){
				$("#common-dialog form").form("submit",{
					url: $(this).attr("action"),
					onSubmit: function() {
						if(null != Callback.onSubmit){
							var flag = Callback.onSubmit();
							
							Callback.clear(Callback.onSubmit);
							return flag;
						}
					},
					success:function(data){
						Callback.clear(Callback.onSubmit);
						data = eval("(" + data + ")");
						if(data.success){
							$("#common-dialog").dialog("close");
							Messager.show({
								title:"&nbsp;",
								content:data.message
							});
							
							if(null != Callback.success){
								Callback.success();
								Callback.clear(Callback.success);
							}
							
							var curTab = $("#index-tab").tabs("getSelected");
							
							//curTab.find(".datagrid-view").children("table[id$='grid']").datagrid("load");
							curTab.find(".datagrid-header").find("a[id$='search']").click();
						}else{
							Messager.alert({
								type:"error",
								title:"&nbsp;",
								content:data.message
							});
						}
					}
				});
			} else {
				$.ajax({
	                //cache: true,
	                type: "POST",
	                url:$("#common-dialog form").attr("action"),
	                data:$("#common-dialog form").serialize(),//
	                async: true,
	                beforeSend: function(){
	                	if(null != Callback.onSubmit){
							var flag = Callback.onSubmit();
							
							Callback.clear(Callback.onSubmit);
							return flag;
						}
	                },
	                success: function(data) {
	                	if(Object.prototype.toString.call(data) === "[object String]"){
							data = eval("(" + data + ")");
						}
						
						if(data.success){
						 	$("#common-dialog").dialog("close");
							Messager.show({
								title:"&nbsp;",
								content:data.message
							});
							
							if(null != Callback.success){
								Callback.success();
								Callback.clear(Callback.success);
							}
							
							var curTab = $("#index-tab").tabs("getSelected");
							
							//curTab.find(".datagrid-view").children("table[id$='grid']").datagrid("load");
							curTab.find(".datagrid-header").find("a[id*='search']").click();
						}else{
							Messager.alert({
								type:"error",
								title:"&nbsp;",
								content:data.message + ",操作失败"
							});
						}
	                }
	            });
			}
			
			/*
		if(ifValid){
			$("#common-dialog form").form("submit",{
				url: $(this).attr("action"),
				onSubmit: function() {
					if(null != Callback.onSubmit){
						var flag = Callback.onSubmit();
						
						Callback.clear(Callback.onSubmit);
						return flag;
					}
				},
				success:function(data){
					$("#common-dialog").dialog("close");
					data = eval("(" + data + ")");
					if(data.success){
						Messager.show({
							title:"&nbsp;",
							content:data.message
						});
						
						if(null != Callback.success){
							Callback.success();
							Callback.clear(Callback.success);
						}
						
						var curTab = $("#index-tab").tabs("getSelected");
						
						//curTab.find(".datagrid-view").children("table[id$='grid']").datagrid("load");
						curTab.find(".datagrid-header").find("a[id$='search']").click();
					}else{
						Messager.alert({
							type:"error",
							title:"&nbsp;",
							content:data.message
						});
					}
				}
			});*/
		}
	});
	
	$("#common-dialog-reset").click(function(){
		$("#common-dialog form").form("clear");
	});
	
	$("#common-dialog-close").click(function(){
		$("#common-dialog").dialog("close");
	});
	
	//tab页标题事件委派
	$("#index-tab").on("contextmenu","li:gt(0)",function(event){
		event.preventDefault();
		
		var thisTabTitle = $(this).text();
		$("#index-tab").tabs("select", thisTabTitle);
		
		$("#index-menu").menu("show",{
			left:event.pageX,
			top:event.pageY
		});
	});
	
	//tab页标题菜单相关按钮事件
	$("#index-menu-closeSelf").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabTitle = curTab.panel("options").title;
		
		$("#index-tab").tabs("close",curTabTitle);
	});
	
	$("#index-menu-closeOthers").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li"),function(index, obj){
			
			var tabTitle = $(obj).text();
			
			if(index > 0 && tabTitle != curTabTitle){
				$("#index-tab").tabs("close", tabTitle);
			}
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeLeft").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li:lt(" + curTabIndex + ")"), function(index, obj){
			if(index > 0){
				var tabTitle = $(obj).text();
				
				$("#index-tab").tabs("close", tabTitle);
			}			
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeRight").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li:gt(" + curTabIndex + ")"), function(index, obj){
			var tabTitle = $(obj).text();
			
			$("#index-tab").tabs("close", tabTitle);
		});
		
		$("#index-tab").tabs("select",curTabTitle);
	});
	
	$("#index-menu-closeAll").click(function(){
		var curTab = $("#index-tab").tabs("getSelected");
		
		var curTabIndex = $("#index-tab").tabs("getTabIndex", curTab);
		var curTabTitle = curTab.panel("options").title;
		
		$.each($("#index-tab li"), function(index, obj){
			if(index > 0){
				var tabTitle = $(obj).text();
				
				$("#index-tab").tabs("close", tabTitle);
			}
		});
	});
	
	$("#index-update").click(function() {
		Callback.onSubmit= function(){
			var oldPassword = $("#oldPassword").val();
			var newPassword = $("#newPassword").val();
			var confirmPassword = $("#confirmPassword").val();
			$("#oldPassword-msg").hide();
			$("#newPassword-msg").hide();
			$("#confirmPassword-msg").hide();
			if(oldPassword == null ||oldPassword == ""){
				$("#oldPassword-msg").html("请输入原始密码").show();
				return false;
			}
			if(newPassword == null || newPassword == ""){
				$("#newPassword-msg").html("请输入新密码").show();
				return false;
			}
			if(newPassword.length<6){
				$("#newPassword-msg").html("密码长度最少为6位").show();
				return false;
			}
			if(confirmPassword == null || confirmPassword == ""){
				$("#confirmPassword-msg").html("请在次输入密码").show();
				return false;
			}
			if(newPassword != confirmPassword ){
				$("#confirmPassword-msg").html("两次输入的密码不一致").show();
				return false;
			}
			$("#newPassword").val(hex_md5(newPassword));
			$("#confirmPassword").val(hex_md5(confirmPassword));
		}
		openDialog({
			title: "修改密码",
			width: 420,
			height: 280,
			maximizable: false,
			href: "base/ChangePassword.jsp"
		});
	});
	
	//注销
	$("#index-logout").click(function(){
		$.messager.confirm('确认','是否退出当前登陆用户！',function(r){    
			if (r){
				self.location.href = "login/logout.do";
			}else{
				return ;
			}
		});  
		
	});
});

//记录运行函数ID
var intervalId = null;
var timeoutId = null;

//定义公用回调函数
var Callback = {
		submitBySelf: false,
		onSubmit: null,
		success: null,
		clear: function(obj){
			obj = null;
		}
}

function isNull(value){
	return "" == value || null == value || undefined == value;
}

//定义菜单打开方式
function openMenu(menuConfig){
	
	var title = null;
	var href = null;
	
	var mode = menuConfig.target.mode;
	
	if("tab" == mode){
		title = menuConfig.title;
		href = menuConfig.href;
		
		var tabId = menuConfig.target.tabId;
		
		var menuIndex = menuConfig.target.menu;
		var subMenuIndex = menuConfig.target.subMenu;
		
		if($(tabId).tabs("exists",title)){
			$(tabId).tabs("select",title)
		}else{
			$(tabId).tabs("add",{
				title:title,
				href:href,
				menu:menuIndex,
				subMenu:subMenuIndex,
				tools:[{
					iconCls:"icon-mini-refresh",
					handler:function(){
						window.setTimeout(function(){
							$(tabId).tabs("getSelected").panel("refresh");							
						}, 0);
					}
				}],
				closable:true
			});
		}
	}else if("dialog" == mode){
		title = menuConfig.title;
		href = menuConfig.href;
		
		var type = menuConfig.target.type;
		var width = menuConfig.target.width;
		var height = menuConfig.target.height;
		var maximizable = menuConfig.maximizable;
		
		if(!maximizable&&maximizable!=undefined){
			maximizable = false;
		}
		openDialog({
			type:type,
			param:menuConfig.param,
			maximizable:maximizable,
			title:title,
			href:href,
			width:width,
			height:height
		});
	}else if("iframe" == mode){
		title = menuConfig.title;
		href = menuConfig.href;
		
		var tabId = menuConfig.target.tabId;
		
		var menuIndex = menuConfig.target.menu;
		var subMenuIndex = menuConfig.target.subMenu;
		
		if($(tabId).tabs("exists",title)){
			$(tabId).tabs("select",title)
		}else{
			var content = "<iframe src='" + href + "' width='100%' scrolling='auto' height='100%' frameborder='0' marginheight='0' marginwidth='0'/>";
			
			$(tabId).tabs("add",{
				title:title,
				content:content,
				menu:menuIndex,
				subMenu:subMenuIndex,
				tools:[{
					iconCls:"icon-mini-refresh",
					handler:function(){
						window.setTimeout(function(){
							$(tabId).tabs("getSelected").children("iframe").attr("src",href);							
						}, 0);
					}
				}],
				closable:true
			});
		}
	}else if("window" == mode){
		href = menuConfig.href;
		
		window.open(href);
	}
}

//定义dialog打开方式
function openDialog(config){
	var c_width = config.width;
	var c_height = config.height;
	var m_width = document.body.clientWidth;
	var m_height = document.body.clientHeight;
	var u_width = c_width;
	var u_height = c_height;
	if(c_width>m_width){
		u_width = m_width;
	}if(c_height >m_height){
		u_height = m_height;
	}
	config.width = u_width;
	config.height = u_height;
	$("#common-dialog-save").linkbutton({iconCls:"icon-save",text:"保存"});
	if("view" == config.type){
		$("#common-dialog-buttons").children(":not(a[id='common-dialog-close'])").hide();
	}
	if(config.param!=null&& config.param!=undefined){
		$("#common-dialog-save").linkbutton(config.param);
		if(!config.param.reset){
			$("#common-dialog-reset").hide();
		}
	}
		$("#common-dialog").dialog(config).dialog("restore").dialog("center").dialog("open");
	
}

//定义dialog对象，可调用通用dialog，临时dialog(其中事件未写全，后续如需求补齐)
var Dialog = {
		open: function(config){
			if("view" == config.type){
				$("#common-dialog-buttons").children(":not(a[id='common-dialog-close'])").hide();
			}
			
			$("#common-dialog").dialog(config).dialog("restore").dialog("center").dialog("open");
		},
		create: function(domId, config){
			var _id = "#" + domId;
			
			if($(_id) && $(_id).length > 0){
				return false;
			}
			
			var dialog = $("<div class='easyui-dialog' id='" + domId + "'></div>");
			
			$("body").append(dialog);
			
			var _width = config.width;
			var _height = config.height;
			
			if(isNull(_width)){
				_width = 500;
			}
			
			if(isNull(_height)){
				_height = 500;
			}
			
			
			
			
			dialog.dialog({
				cache: false, 
				modal: true, 
				closed: true,
				resizable:true,
				method: "post",
				title: config.title,
				width: _width,
				height: _height,
				href: config.href,
				buttons: config.buttons,
				onClose: function(){
					$(_id).dialog("destroy");
				}
			});
			
			dialog.panel().parent().children("div.dialog-button").css("text-align", "center");
			dialog.dialog("restore").dialog("center").dialog("open");
		}
}

//提示信息
var Messager = {
	"alert":function(config){
		$.messager.alert(config.title, config.content, config.type, config.fn);
	},
	"show":function(config){
		var showWidth = config.width;
		var showHeight = config.height;
		if(showWidth>200&&showHeight>200){
			$.messager.show({
            title:config.title,
            msg:config.content,
            showType:"fade",
            width:showWidth,
            height:showHeight,
            timeout:20000,
            style:{
                right:"",
                bottom:""
            }
        });	
		}else{
		$.messager.show({
            title:config.title,
            msg:config.content,
            showType:"fade",
            timeout:1000,
            style:{
                right:"",
                bottom:""
            }
        });}
	},
	"confirm":function(config){
		$.messager.confirm(config.title, config.content, config.handler);
	}
};

//tab页标题菜单显示后处理方法
function menuOnShowHandler(item){
	var selTab = $("#index-tab").tabs("getSelected");
	
	var selTabIndex = $("#index-tab").tabs("getTabIndex", selTab);
	
	var leftExist = $("#index-tab li:lt(" + selTabIndex + ")").length - 1 > 0;
	var rightExist = $("#index-tab li:gt(" + selTabIndex + ")").length > 0;
	var othersExist = leftExist || rightExist;

	leftExist
			? $("#index-menu").menu("enableItem",$("#index-menu-closeLeft"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeLeft"));
	
	rightExist
			? $("#index-menu").menu("enableItem",$("#index-menu-closeRight"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeRight"));
	
	
	othersExist 
			? $("#index-menu").menu("enableItem",$("#index-menu-closeOthers"))
			: $("#index-menu").menu("disableItem",$("#index-menu-closeOthers"));
}

//日历控件共享方法
function getDateConfig(){
	var cfg = {} || new Object();
		
	var curMaxDate = null;
	
	var target = window.event.target || window.event.srcElement;
	
	var targetId = target.id;
	
	var selectVal = $(target).closest("table").find("#repType").val();
	
	switch(selectVal){
		case "month":
			cfg.dateFmt = "yyyy-MM";
			curMaxDate = "%y-%M";
			break;
		case "year":
			cfg.dateFmt = "yyyy";
			curMaxDate = "%y";
			break;
		case "custom":
			cfg.dateFmt = "yyyy-MM-dd";
			curMaxDate = "%y-%M-%d";
			break;
		default:
			cfg.dateFmt = "yyyy-MM-dd";
			curMaxDate = "%y-%M-%d";
	}
	
	if(targetId.toLowerCase().indexOf("begintime") > -1){
		var endTimeId = $.grep($(target).closest("table")
										.find("input:text"),
								function(o, i){
									return $(o).attr("id") && $(o).attr("id").toLowerCase().indexOf("endtime") > -1;
								},
								false)[0].id;
		
		cfg.maxDate = "#F{$dp.$D(\'" + endTimeId + "\')||\'" + curMaxDate + "\'}";
	}else if(targetId.toLowerCase().indexOf("endtime") > -1){
		var beginTimeId = $.grep($(target).closest("table")
										.find("input:text"),
								function(o, i){
									return $(o).attr("id") && $(o).attr("id").toLowerCase().indexOf("begintime") > -1;
								},
								false)[0].id;
		
		cfg.minDate = "#F{$dp.$D(\'" + beginTimeId + "\')}";
		cfg.maxDate = curMaxDate;
	}
			
	return cfg;
}

//特殊字符转码处理
function transcoding(content) {
	var change = "";
	if (content != null) {
		change = content.replace(/</g, "&zj;");
		change = change.replace(/\//g, "&xg;");
		change = change.replace(/\>/g, "&yj;");
		change = change.replace(/http/g, "&hyp;");
		change = change.replace(/\"/g, "&syh;");
		change = change.replace(/LF/g, "&hh;");
		change = change.replace(/CR/g, "&hc;");
		change = change.replace(/\\/g, "&fxg;");
	}
	return change;
} 

function getNowFormatDate(day,showTime) 
{
	var Year = 0; 
	var Month = 0; 
	var Day = 0; 
	var hours = 0;
	var minutes = 0;
	var seconds = 0;
	var CurrentDate = ""; 
	Year= day.getFullYear();
	Month= day.getMonth()+1; 
	Day = day.getDate();
	hours = day.getHours();
	minutes = day.getMinutes();
	seconds = day.getSeconds();
	CurrentDate += Year + "-"; 
	if (Month >= 10 ) 
	{ 
		CurrentDate += Month + "-"; 
	} 
	else 
	{ 
		CurrentDate += "0" + Month + "-"; 
	} 
	if (Day >= 10 ) 
	{ 
		CurrentDate += Day ; 
	} 
	else 
	{ 
		CurrentDate += "0" + Day ; 
	}
	if(showTime){
		if(hours >=10){
			CurrentDate += " "+hours+":";
		}else{
			CurrentDate += " 0"+hours+":";
		}
		if(minutes >=10){
			CurrentDate +=minutes + ":";
		}else{
			CurrentDate +="0"+minutes+":";
		}if(seconds>=10){
			CurrentDate +=seconds;
		}else{
			CurrentDate +="0"+seconds;
		}
	}

	return CurrentDate; 
} 


