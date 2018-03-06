window.wsConnect = function () {
    window.ws = new WebSocket("ws://localhost:9090");
    ws.onopen = function () {
        console.log("open")
    };
    ws.onclose = function () {
        setTimeout(function () {
            window.wsConnect();
        }, 1000);
    };
    ws.onerror = function (err) {
        console.log("err: " + err)
    };
    ws.onmessage = function (evt) {
        console.log("msg: " + evt.data)
    };
};
window.onload = function () {
    window.wsConnect();
};