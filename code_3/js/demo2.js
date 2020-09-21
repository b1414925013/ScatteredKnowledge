//发送url加密格式的请求
var divid = $("#divid")
var params = {
    id: 2
}
$("#btn").click(function () {
    $.ajax({
        type: "POST",
        url: _url + "/userModer/user/byid",
        data: params,
        dataType: "json",
        success: function (result) {
            var data = JSON.stringify(result);
            console.log("成功==>" + data);
            divid.text("请求成功")
        },
        error: function (result) {
            var data = JSON.stringify(result);
            console.log("失败==>" + data);
            divid.text("请求失败")
        }
    })
})