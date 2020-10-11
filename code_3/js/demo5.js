layui.use(["jquery"], function () {
    var $ = layui.jquery;

    $("#downloadbtn").click(function () {
        //下载模板文件 
        window.location.href = _url + "/downFile";
    })

    $("#downloadbtn2").click(function () {
        $("#queryCourseForm").attr("action", _url + "/downFile"); //改变表单的提交地址为下载的地址
        $("#queryCourseForm3").attr("method", "get")
        //提交表单
        $("#queryCourseForm").submit();
    })
    
    $("#downloadbtn3").click(function () {
        $("#queryCourseForm3").attr("action", _url + "/downFile2"); //改变表单的提交地址为下载的地址
        $("#queryCourseForm3").attr("method", "post")
        //提交表单
        $("#queryCourseForm3").submit();
    })
})