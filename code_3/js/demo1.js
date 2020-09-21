//发送get请求
var divid = $("#divid")
var params = {
    page: 1,
    limit: 5
}
$("#btn").click(function () {
    $.ajax({
        type: "GET",
        url: _url + "/userModer/user/all",
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