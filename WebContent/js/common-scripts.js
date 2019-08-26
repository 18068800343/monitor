var Script = function () {

    // 侧边栏收缩
    var flag = true
    $('.icon-caidan').click(function () {
        if (flag) {
            $('#main-content').animate({
                'margin-left': '0px'
            },200);
            $('#sidebar').animate({
                'margin-left': '-170px'
            },200);
            flag = false;
        } else {
            $('#main-content').animate({
                'margin-left': '170px'
            },200);
            $('#sidebar > ul').show();
            $('#sidebar').animate({
                'margin-left': '0'
            },200);
            flag = true;
        }
    });


    // 获取iframe高度
    var wh=$(window).height();
    $('.w_r,.w_l').css('min-height',wh-265);


}();