BIMVIZ.UI.DefaultTreesHighlightControl = function(name, iconClass) {
	BIMVIZ.UI.DefaultControl.call(this, name, iconClass);
};

BIMVIZ.UI.DefaultTreesHighlightControl.prototype = Object.create(BIMVIZ.UI.DefaultControl.prototype);
BIMVIZ.UI.DefaultTreesHighlightControl.constructor = BIMVIZ.UI.DefaultTreesHighlightControl;

function GetChildNode(rootId, curNode) {
	var model = hwv.getModel();
	var rootChilds = model.getNodeChildren(rootId);
	for(var i = 0; i < rootChilds.length; ++i) {
		var curChildNode = {};
		curChildNode.Id = rootChilds[i].toString();
		curChildNode.Name = model.getNodeName(rootChilds[i]);
		curChildNode.Children = [];
		curChildNode.TypeName = "Project";
		if(model.getNodeType(rootChilds[i]) == BimViewer.NodeType.BodyInstance) {
			curChildNode.TypeName = "Element";
		}
		curNode.Children.push(curChildNode);

		GetChildNode(rootChilds[i], curChildNode);
	}
}

var senserIDS = [];
var sensorDataList = [];

function GetSensorChildNode(rootId, curNode) {
	var model = hwv.getModel();
	var rootChilds = model.getNodeChildren(rootId);
	for(var i = 0; i < rootChilds.length; ++i) {
		var curChildNode = {};
		curChildNode.Id = rootChilds[i].toString();
		curChildNode.Name = model.getNodeName(rootChilds[i]);
		if(curChildNode.Name.indexOf("测点") > -1){
			senserIDS.push(rootChilds[i]);
		}
		GetSensorChildNode(rootChilds[i], curChildNode);
	}
}

var curRootNode = {};

var typeID = -1;
function CreateFileTree() {
	var model = hwv.getModel();
	var rootId = 0; //model.getRootNode();
	curRootNode.Id = rootId.toString();
	curRootNode.Children = [];
	curRootNode.Name = "监测传感器";//model.getNodeName(rootId);
	curRootNode.TypeName = "Project";
	//GetChildNode(rootId, curNode);
	GetSensorChildNode(0, curRootNode);
	for(var i = 0; i < senserIDS.length; ++i){
		var nodeID = senserIDS[i];
		var props_promise = hwv.getModel().getNodeProperties(nodeID);
		if(props_promise) {
			props_promise.then(function(props) {
				var sensorData = {};
				if(props && Object.keys(props).length) {
					var bHasUUID = false;
					var bHasStr = false;
					for(var key in props) {
						var strAry = key.split("@@");
						var keyValue = props[key];
						if(strAry[0] == "revit_uuid") {
							sensorData.uuid = keyValue;
							bHasUUID = true;
						} else if(strAry[1] == "编号") {
							sensorData.sensorID = keyValue;
							bHasStr = true;
						} else if(strAry[1] == "类型") {
							sensorData.sensorType = keyValue;
							bHasStr = true;
						} else if(strAry[1] == "描述") {
							sensorData.sensorDes = keyValue;
							bHasStr = true;
						}

						if(bHasUUID && bHasStr) {
							var orgLength = sensorDataList.length;
							sensorData.modelID = senserIDS[orgLength];
							sensorDataList.push(sensorData);
							//设置传感器的特殊颜色
							hwv.getModel().setNodesFaceColor([sensorData.modelID],
								new BimViewer.Color(146, 40, 255));	
							var typeChildNode = {};
							var bFindTypeNode = false;
							for(var j = 0; j < curRootNode.Children.length; ++j){
								if(sensorData.sensorType == curRootNode.Children[j].Name){
									typeChildNode = curRootNode.Children[j];
									bFindTypeNode = true;
									break;
								}							
							}
							
							if(curRootNode.Children.length == 0 || !bFindTypeNode){
								typeChildNode.Id = typeID--;
								typeChildNode.Name = sensorData.sensorType;
								typeChildNode.Children = [];
								typeChildNode.TypeName = "Project";
								curRootNode.Children.push(typeChildNode);		
							}
							var curChildNode = {};
							curChildNode.Id = sensorData.modelID;
							curChildNode.Name = sensorData.sensorID + "-(ID:" + sensorData.modelID + ")";
							curChildNode.Children = [];
							curChildNode.TypeName = "Element";
							typeChildNode.Children.push(curChildNode);

							fileTree.length=0;
							fileTree.push(curRootNode);
						}
					}
				}
			});
		}
	}
	return curRootNode;
}

BIMVIZ.UI.DefaultTreesHighlightControl.prototype.onHide = function() {
	if(sensorDataList.length == 0){
		fileTree.push(CreateFileTree());
	}
}

var uitree = null;
var uifiletree = null;

var fileTree = [];

BIMVIZ.UI.DefaultTreesHighlightControl.prototype.onProjectLoaded = function(project) {
	fileTree.push(CreateFileTree());
}

BIMVIZ.UI.DefaultTreesHighlightControl.prototype.onShow = function(project) {

	var scope = this;
	var fileTreeNodesDic = [];

	var elementCount = 0;

	var html = '<ul id="bv_space_tab" class="nav nav-tabs nav-button-tabs">\
                    <li class="active"><a href="#spaceifctreepage" data-toggle="tab">传感器结构树</a></li>\
                </ul>\
                <div class="tab-content padding-0">\
                     <div class="tab-pane fade in active" id="spaceifctreepage">\
                        <div class="padding-20">\
                            <div id="bv_defaultHightlightTree"></div>\
                        </div>\
                    </div >\
                    <div class="tab-pane fade" id="spacefiletreepage">\
                        <div class="padding-20">\
                            <div id="bv_orispaceTree"></div>\
                        </div>\
                    </div >\
            </div>';

	this.parentDiv.html(html);
	this.parentDiv.addClass("nopadding-left nopadding-right").removeClass("padding-20");
	var treecontainer = $('#bv_defaultHightlightTree');
	var filetreecontainer = $('#bv_orispaceTree');

	function createIfcTree() {
		treecontainer.jstree({
			'core': {
				'data': function(node, cb) {
					onLoadFileTreeChildNodes(node, cb);
				},
				"animation": 0,
				"multiple": false,
				"conditionalselect": function(node, event) {
					return false;
				},
				"themes": {
					'name': 'proton',
					"dots": true,
					"icons": true,
					"stripes": false
				}
			},
			"types": {
				"default": {
					"icon": "glyphicon glyphicon-flash"
				},
				"demo": {
					"icon": "glyphicon glyphicon-ok"
				}
			},
			'plugins': ["types", "wholerow", "conditionalselect"],
			'checkbox': {
				"keep_selected_style": false,
				'tie_selection': false
			},
		});

		uitree = treecontainer.jstree(true);
		treecontainer.on('select_node.jstree', function(e, data) {
			hwv.getModel().setNodesTransparency([0], 0.3);
			hwv.getModel().setNodesHighlighted([0], false);
			//传感器父目录
			if(data.node.id == 0){
				var modelList = [];
				for(var idx = 0; idx < sensorDataList.length; ++idx){
					modelList.push(sensorDataList[idx].modelID);	
				}
				if(modelList.length > 0){
					hwv.getModel().setNodesHighlighted(modelList, true);
					hwv.getModel().setNodesTransparency(modelList, 1);
					hwv.getView().fitNodes(modelList, 800);					
				}	
				return;
			}
			if(data.node.id < 0){
				var childData = data.node.children;
				var modelList = [];
				for(var idx = 0; idx < childData.length; ++idx){
					modelList.push(childData[idx]);	
				}
				if(modelList.length > 0){
					hwv.getModel().setNodesHighlighted(modelList, true);
					hwv.getModel().setNodesTransparency(modelList, 1);
					hwv.getView().fitNodes(modelList, 800);					
				}
			}
			else{
				hwv.getModel().setNodesHighlighted([data.node.id], true);
				hwv.getModel().setNodesTransparency([data.node.id], 1);
				//hwv.getView().fitNodes([data.node.id], 800);

				hwv.getModel().getNodesBounding([data.node.id]).then(function (boundingBox) {
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
		});

		treecontainer.on('hover_node.jstree', function(e, data) {
			//hwv.getModel().setNodesHighlighted([0], false);
			//hwv.getModel().setNodesTransparency([0], 1.0);
		});
	}

	function createFileTree() {
		filetreecontainer.jstree({
			'core': {
				'data': function(node, cb) {
					onLoadFileTreeChildNodes(node, cb);
				},
				"animation": 0,
				"multiple": false,
				"check_callback": true,
				"themes": {
					'name': 'proton',
					"dots": true,
					"icons": false,
					"stripes": false
				}
			},
			"conditionalselect": function(node, event) {
				return false;
			},
			'plugins': ["types", "wholerow", "checkbox", "dnd"],
			'checkbox': {
				"keep_selected_style": false,
				'tie_selection': false
			},
		});

		uifiletree = filetreecontainer.jstree(true);
		filetreecontainer.on("check_node.jstree", function(e, data) {
			hwv.getModel().setNodesVisibility([data.node.id], true);
		});

		filetreecontainer.on("uncheck_node.jstree", function(e, data) {
			hwv.getModel().setNodesVisibility([data.node.id], false);
		});

		filetreecontainer.on('select_node.jstree', function(e, data) {
			hwv.getModel().setNodesHighlighted([data.node.id], false);
		});

		filetreecontainer.on('hover_node.jstree', function(e, data) {
			hwv.getModel().setNodesHighlighted([0], false);
			hwv.getModel().setNodesHighlighted([data.node.id], true);
		});
	}

	function collectFileTreeNodes(nodes) {
		if(nodes != null && nodes.length > 0) {
			for(var a = 0; a < nodes.length; a++) {
				var node = nodes[a];

				fileTreeNodesDic[node.Id] = node;
				collectFileTreeNodes(node.Children);
			}
		}
	}

	function create() {
		collectFileTreeNodes(fileTree);

		createIfcTree();
		if(fileTree) {
			createFileTree();
		}
	};

	function collectFileTreeElements(node, highlight) {
		if(node.TypeName == "Element") {
			if(highlight == true) {
				highlightMgr.highlightElement(node.Id);
			} else {
				highlightMgr.unHighlightElement(node.Id);
			}
		} else {
			node.Children.forEach(function(subnode, index) {
				collectFileTreeElements(subnode, highlight);
			});
		}
	}

	function collectElements(node, highlight) {
		if(node.Level == "Element") {
			if(highlight == true) {
				highlightMgr.highlightElement(node.Id);
			} else {
				highlightMgr.unHighlightElement(node.Id);
			}
		} else {
			node.Children.forEach(function(subnode, index) {
				if(subnode.Level != "Group" || subnode.Name != "IfcSpace")
					collectElements(subnode, highlight);
			});
		}
	}

	function onLoadFileTreeChildNodes(nodeinfo, callback) {
		var treenodes;
		if(nodeinfo.id == "#") {
			treenodes = fileTree;
		} else {
			var node = fileTreeNodesDic[nodeinfo.id];
			if(node != null) {
				treenodes = node.Children;
			}
		}

		if(treenodes != null) {
			var nodes = [];
			for(var i = 0; i < treenodes.length; i++) {
				var childnode = treenodes[i];
				//if (childnode.TypeName == "Element")
				//	continue;

				elementCount = 0;
				getNodeElementCount(childnode);
				var text = childnode.TypeName == "Element" ? "" : " - (" + elementCount + ")";
				var bOpened = false;
				if(childnode.Id <= 1) {
					bOpened = false;
				}
				var strType = "default";
				if(childnode.Id <= 0) {
					strType = "demo";
				}
				nodes.push({
					text: childnode.Name + text,
					id: childnode.Id,
					type: strType,
					children: childnode.Children.length > 0,
					state: {
						checked: true,
						opened: bOpened
					}
				});
			}

			callback(nodes);
		}
	};

	function getNodeElementCount(node) {
		if(node.TypeName == "Element") {
			elementCount++;
		} else {
			node.Children.forEach(function(subnode, index) {
				getNodeElementCount(subnode);
			});
		}
	}

	create();

	$('#bv_space_tab').on('click', 'a', function() {
		var text = this.text;
		if(text == '模型可见性') {
			hwv.getModel().setNodesHighlighted([0], false);
			uitree.deselect_all();
		} else {
			if(uifiletree) {
				uifiletree.check_all(true);
				hwv.getModel().setNodesVisibility([0], true);
			}
		}
	});

	$('#bv_space_clear').click(function(e) {
		//		uitree.uncheck_all(true);
		uitree.deselect_all();
		hwv.getModel().setNodesTransparency([0], 1.0);
		hwv.getModel().setNodesHighlighted([0], false);
	});

	$('#bv_orispace_clear').click(function(e) {
		if(uifiletree) {

			uifiletree.uncheck_all(true);
			hwv.getModel().setNodesVisibility([0], false);
		}
	});
};