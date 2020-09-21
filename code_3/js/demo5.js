//测试下载方法（鸡肋）(不适用前后端分离项目项目)
layui.use(["jquery"], function () {
    var $ = layui.jquery;

    $("#btn").click(function () {
        //下载模板文件
    //    window.location.href = "resources/template/testFile.xlsx";
        
        window.location.href = "resources/img/testImg.png";
    })
})