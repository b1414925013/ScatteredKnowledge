var _url = "http://127.0.0.1:80"
$.ajaxSetup({
    beforeSend: function () {
        //ajax请求之前
        console.log("ajax请求之前")
    },
    complete: function () {
        //ajax请求完成，不管成功失败
        console.log("ajax请求完成，不管成功失败")
    },
    error: function () {
        //ajax请求失败
        console.log("ajax请求失败")
    }
});
