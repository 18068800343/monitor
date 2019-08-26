var bIsViewCameraForZAxis = false;
var hwvLeft = null;
var bShowTip = true;

function HighLightSensor(modelID){
	hwv.getModel().setNodesTransparency([0], 0.8);
	hwv.getModel().setNodesHighlighted([0], false);

	hwv.getModel().setNodesHighlighted([modelID], true);
	hwv.getModel().setNodesTransparency([modelID], 1);

	hwv.getModel().getNodesBounding([modelID]).then(function (boundingBox) {
		var boxExt = boundingBox.extents();
		var boxCenter = boundingBox.center();
		var tmpValue = 6;
		var newMin = new BimViewer.Point3(boxCenter.x - boxExt.x*tmpValue,
			boxCenter.y - boxExt.y*tmpValue, boxCenter.z - boxExt.z*tmpValue);
		var newMax = new BimViewer.Point3(boxCenter.x + boxExt.x*tmpValue,
			boxCenter.y + boxExt.y*tmpValue, boxCenter.z + boxExt.z*tmpValue);
		hwv.getView().fitBounding(new BimViewer.Box(newMin, newMax), 800);
	});
}

function ActionHomeView(){
	hwv.getView().setViewOrientation(BimViewer.ViewOrientation.Iso);
}

function ShowMouseMoveTip(selectionItem) {
	var showTip = document.getElementById("showModelName");
	var selID = selectionItem.getNodeId();
	if(selID < 0) {
		return;
	}
	var curModelName = hwv.getModel().getNodeName(selID - 1);
	if(curModelName.indexOf("测点") == -1){
		showTip.style.display = "none";
		return;
	}
	//find selID for sensor list
	for(var i = 0; i < sensorDataList.length; ++i){
		if(selID - 1 == sensorDataList[i].modelID){
			showTip.innerHTML = "传感器类型:" + sensorDataList[i].sensorType + "-传感器ID:" + sensorDataList[i].sensorID + "<br /><br />" +
			sensorDataList[i].sensorDes;
			showTip.style.display = "";	
		}
	}
}

//sensorIDS和sensorStates的数目需要保证一致
//sensorIDS：传感器ModelID Array
//sensorStates: 传感器状态数组（0：正常-绿色；1：异常-灰色； 2：黄色预警；3：红色预警）
function setModelSensorStates(sensorIDS, sensorStates){
	if(sensorIDS.length != sensorStates.length){
		alert("sensorIDS和sensorStates的数目需要保证一致");
		return;
	}
	var greenIDS = [];
	var grayIDS = [];
	var yellowIDS = [];
	var redIDS = [];
	for(var i = 0; i < sensorIDS.length; ++i){
		if(sensorStates[i] == 0){
			greenIDS.push(sensorIDS[i]);
		}else if(sensorStates[i] == 1){
			grayIDS.push(sensorIDS[i]);	
		}else if(sensorStates[i] == 2){
			yellowIDS.push(sensorIDS[i]);	
		}else if(sensorStates[i] == 3){
			redIDS.push(sensorIDS[i]);	
		}
	}
	if(greenIDS.length > 0){
		hwv.getModel().setNodesFaceColor(greenIDS, new BimViewer.Color(0, 255, 0));
	}		
	if(grayIDS.length > 0){
		hwv.getModel().setNodesFaceColor(grayIDS, new BimViewer.Color(112, 112, 112));
	}
	if(yellowIDS.length > 0){
		hwv.getModel().setNodesFaceColor(yellowIDS, new BimViewer.Color(255, 255, 0));
	}
	if(redIDS.length > 0){
		hwv.getModel().setNodesFaceColor(redIDS, new BimViewer.Color(255, 0, 0));
	}
}

function OnSelectModelNode(uuid){
	var curModelName = hwv.getModel().getNodeName(uuid - 1);
	if(curModelName.indexOf("测点") > -1){
		hwv.getModel().setNodesHighlighted([uuid], true);
		return;
	}
	hwv.getModel().setNodesHighlighted([uuid], false);;
	return;

	hwv.getModel().setNodesHighlighted([uuid], true);

	// var curSensorIDS = [14,16,18,22,24,26];
	// var curSensorStates = [0,0,2,3,1,1];
	// setModelSensorStates(curSensorIDS, curSensorStates);
	return;
	var globalUUID = getNodeProperty(uuid-1, dataArray);
	// console.log(uuid);

	var botColor = new BimViewer.Color(250, 233, 206);
	var topColor = new BimViewer.Color(199, 219, 246);
	hwv.getView().setBackgroundColor(topColor, botColor);
	
	//uuid修正
	var nodeType = hwv.getModel().getNodeType(uuid);
	if(nodeType == BimViewer.NodeType.Body ||
		nodeType == BimViewer.NodeType.BodyInstance) {
			uuid = uuid - 1;
	}
    
	nodeType = hwv.getModel().getNodeType(uuid);
	var partIDS = new Array();
	partIDS.push(uuid);
	
	//1.设置所有uuid对应模型的透明度
	//参数1：uuid列表； 参数2：透明度数值（0-1），1.0为全透明
	hwv.getModel().setNodesTransparency(partIDS, 0.8);
	
	//2.设置所有uuid对应模型的颜色
	//参数1：uuid列表； 参数2：模型颜色数值
	hwv.getModel().setNodesFaceColor(partIDS, new BimViewer.Color(255, 0, 0));
	
	//3.重置模型的默认颜色，让模型所有构件的颜色恢复到初始状态
	//hwv.getModel().resetNodesColor();
	
	//4.隐藏/显示所有构件
	//参数1：固定为[0],参数:2：true：显示，false：隐藏
	//hwv.getModel().setNodesVisibility([0], false);
	
	//5.隐藏/显示列表中的构件
	//参数1：uuid列表,参数2：true：显示，false：隐藏
	hwv.getModel().setNodesVisibility(partIDS, true);
	
	//6.取消所有构件的高亮
	//参数1：固定为[0],参数2：true：高亮所有构件，false：取消所有构件的高亮
	hwv.getModel().setNodesHighlighted([0], false);
	
	//7.高亮列表中的构件
	//参数1：uuid列表,参数2：true：高亮构件，false：取消高亮
	hwv.getModel().setNodesHighlighted(partIDS, true);
	
	//8.视口聚焦到当前构件
	//参数1：uuid列表
	//hwv.getView().fitNodes(partIDS);
	
	//9.获取所有构件的UUID列表
	var allBodyIDS = [];
	getAllChildNodes(hwv.getModel(), 0, allBodyIDS);
	
	//10.获取该构件的所有属性
	dataArray = [];
	var proID = uuid;
	if(nodeType == BimViewer.NodeType.Body ||
		nodeType == BimViewer.NodeType.BodyInstance) {
			proID = uuid - 1;
	}
    getNodeProperty(proID, dataArray);
    
	//11.获取该构件的名称
	proID = uuid;
	if(nodeType == BimViewer.NodeType.Body ||
		nodeType == BimViewer.NodeType.BodyInstance) {
			proID = uuid - 1;
	}
}

function showObjProperties(obj) {
	var description = "";
	for(var i in obj) {
		var property = obj[i];
		description += property.name + " = " + property.value + "\n";
		if (property.category == "revit_uuid") {
			// alert(property.value);
			return property.value;
		}
	}
	//alert(description);
}

var dataArray = [];
function getNodeProperty(nodeUUID, dataArray){
	var props_promise = hwv.getModel().getNodeProperties(nodeUUID);
    if (props_promise) {
        props_promise.then(function (props) {
            if (props && Object.keys(props).length) {
                for (var key in props) {
                	var strAry = key.split("@@");
                	var keyValue = props[key];
                	dataArray.push({category:strAry[0], name: strAry[1], value: keyValue});
                }
            }
            
            showObjProperties(dataArray);
        });
    }      
}

function getAllChildNodes(model, parentID, allBodyIDS) {
	var childAry = model.getNodeChildren(parentID);
	for(var i = 0; i < childAry.length; ++i) {
		var nodeID = childAry[i];
		getAllChildNodes(model, nodeID, allBodyIDS);
		var nodeType = model.getNodeType(nodeID);
		if(nodeType == BimViewer.NodeType.Body ||
			nodeType == BimViewer.NodeType.BodyInstance) {
				allBodyIDS.push(nodeID - 1);
		}
	}
};

function afterModelReady()
{
	ActionHomeView();
	toolbar.afterLoadProject();
}

var timerPS = null;
var allBodyIDS = [];
var curShowIdx = 0;

function UpdateModelSimulation () {
}
function SimulateProgress() {
}
