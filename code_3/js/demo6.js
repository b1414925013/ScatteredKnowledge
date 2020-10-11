//ajax上传文件
layui.use(["jquery"], function () {
    var $ = layui.jquery;

    var divid = $("#divid")

    $("#btn").click(function () {
        var formData = new FormData();
        formData.append("uploadfile", $('#uploadfile')[0].files[0]);
        $.ajax({
            url: _url + '/upload',
            dataType: 'json',
            type: 'POST',
            async: false,
            data: formData,
            processData: false, // 使数据不做处理
            contentType: false, // 不要设置Content-Type请求头
            success: function (data) {
                console.log(data);
                divid.text("请求成功")
            },
            error: function (data) {
                console.log(data);
                divid.text("请求失败")
            }
        });
    })
})