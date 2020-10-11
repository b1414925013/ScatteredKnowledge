//发送json格式的请求
var divid = $("#divid")
var params = {
    id: 2
}
$("#btn").click(function () {
    $.ajax({
        type: "POST",
        url: _url + "/userModer/user/byid2",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(params),
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
$("#btn2").click(function () {
    axios.post(_url + "/userModer/user/byid2", params)
        .then(function (response) {
            console.log(response);
            divid.text("请求成功")
        })
        .catch(function (error) {
            console.log(error);
            divid.text("请求失败")
        });
})