var BIMVIZ;
BIMVIZ = {
	'\x52\x45\x56\x49\x53\x49\x4f\x4e': '1.6.6'
};
BIMVIZ["WORKDEFINE"] = {
	'\x47\x65\x74\x50\x69\x63\x6b\x65\x64\x44\x61\x74\x61': 7,
	'\x4c\x6f\x61\x64\x54\x72\x65\x65': 6,
	'\x43\x6c\x6f\x73\x65': 2,
	'\x4c\x6f\x61\x64\x54\x72\x65\x65\x4e\x6f\x64\x65\x4d\x65\x73\x68': 3,
	'\x50\x69\x63\x6b\x42\x79\x52\x61\x79': 4,
	'\x47\x65\x74\x45\x6c\x65\x6d\x65\x6e\x74\x44\x61\x74\x61': 5,
	'\x4c\x6f\x61\x64\x46\x69\x6c\x65': 8,
	'\x53\x74\x61\x72\x74': 1
};

BIMVIZ.UI = function (){
	
}


String["prototype"]["format"] = function (W8pM) {
	var c8pM,
	L8pM;
	c8pM = this;
	if (arguments["length"] > 0) {
		if (arguments["length"] == 1 && typeof W8pM == "object") {
			for (var x8pM in W8pM) {
				if (W8pM[x8pM] != undefined) {
					var T4pM = "rep";
					T4pM += "lace";
					L8pM = new RegExp("({" + x8pM + "})", "g");
					c8pM = c8pM[T4pM](L8pM, W8pM[x8pM]);
				}
			}
		} else {
			for (var G8pM = 0; G8pM < arguments["length"]; G8pM++) {
				if (arguments[G8pM] != undefined) {
					L8pM = new RegExp("({)" + G8pM + "(})", "g");
					c8pM = c8pM["replace"](L8pM, arguments[G8pM]);
				}
			}
		}
	}
	return c8pM;
};