var offx = 0;
var offy = 0;

function paint(ctx) {
	ctx.clearRect(0, 0, window.innerWidth, window.innerHeight);
	// ctx.translate(0.5, 0.5);
	// ctx.lineWidth = .5;
	// ctx.fillStyle = "rgb(200,0,0)";
	// ctx.fillRect(10, 10, 55, 50);
	//
	// ctx.fillStyle = "rgba(0, 0, 200, 0.5)";
	// ctx.fillRect(30, 30, 55, 50);

	// ctx.fillRect(25, 25, 100, 100);
	// ctx.clearRect(45, 45, 60, 60);
	// ctx.strokeRect(50, 50, 50, 50);

	var max = 50;
	var dec = 10;
	for (var i = 0; i < max; i++) {
		for (var j = 0; j < max; j++) {
			ctx.fillStyle = 'rgb(' + Math.floor(255 - 255 / max * i) + ','
					+ Math.floor(255 - 255 / max * j) + ',0)';
			ctx.fillRect(offx + j * 25, offy + i * 25, 25, 25);
		}
	}
}
window.addEventListener("resize", function() {
	var c = document.getElementById('canvas');
	var ctx = c.getContext('2d');
	var cw = c.width = window.innerWidth;
	var ch = c.height = window.innerHeight;
	// window.clearTimeout(window.resizeTimeout);
	// window.resizeTimeout = window.setTimeout(function() {
	// }, 1);
	paint(ctx);

});
window.addEventListener("hashchange", function(evt) {
	alert(location.hash);
})
window.addEventListener("load", function() {
	var c = document.getElementById('canvas');
	var ctx = c.getContext('2d');
	c.onclick = function() {
		alert(c);
	};
	c.onmousewheel = function(evt) {
		offx += evt.deltaX;
		offy += evt.deltaY;
		paint(ctx);
		return false;
	}
	var cw = c.width = window.innerWidth;
	var ch = c.height = window.innerHeight;
	paint(ctx);
}, false);
