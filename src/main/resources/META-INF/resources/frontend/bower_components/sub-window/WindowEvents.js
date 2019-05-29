function beginDrag(elementToDrag, event) {
    // obtener una referencia al flow Element
    // para esto es necesario acceder al host del shadowRoot
    var flowElement = elementToDrag.parentNode.host;
    flowElement.$server.bringToFront();

    console.log("flow element: ", flowElement);
    console.log("parentNode: ", elementToDrag.parentNode);
    console.log("parentNode.host: ", elementToDrag.parentNode.host);
    console.log("parentNode.host.parentNode: ", elementToDrag.parentNode.host.parentNode);
    // acceder al contenerdor del shadowRoot
    var rect = elementToDrag.parentNode.host.parentNode.getBoundingClientRect();
    // console.log("desktop:",rect.top, rect.right, rect.bottom, rect.left);
    var desktopX = rect.left;
    var desktopY = rect.top;

    var deltaX = event.clientX - parseInt(elementToDrag.style.left) - desktopX;
    var deltaY = event.clientY - parseInt(elementToDrag.style.top) - desktopY;


    if (document.addEventListener) {
        document.addEventListener("mousemove", moveHandler, true);
        document.addEventListener("mouseup", upHandler, true);
    } else if (document.attachEvent) {
        document.attachEvent("onmousemove", moveHandler);
        document.attachEvent("onmouseup", upHandler);
    } else {
        var oldmovehandler = document.onmousemove;
        var olduphandler = document.onmouseup;
        document.onmousemove = moveHandler;
        document.onmouseup = upHandler;
    }
    if (event.stopPropagation)
        event.stopPropagation();
    else
        event.cancelBubble = true;
    if (event.preventDefault)
        event.preventDefault();
    else
        event.returnValue = false;
    function moveHandler(e) {
        if (!e)
            e = window.event;
        dx = e.clientX - deltaX - desktopX;
        //dx = dx > desktopX ? dx : desktopX;
        dx = dx < 0 ? 0 : dx;

        dy = e.clientY - deltaY - desktopY;
        //dy = dy > desktopY ? dy : desktopY;
        dy = dy < 0 ? 0 : dy;

        // console.log("dx:",e.clientX, deltaX, desktopX, dx);
        // console.log("dy:",e.clientY, deltaY, desktopY, dy);
        elementToDrag.style.left = dx + "px";
        elementToDrag.style.top = dy + "px";
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;
        // console.log("element: ", elementToDrag);

        // invocar al callback en Java. Tener en cuenta que el método 
        // debe estar anotado como @ClientCallable
        flowElement.$server.updatePosition(dy, dx);
    }
    function upHandler(e) {
        if (!e)
            e = window.event;
        if (document.removeEventListener) {
            document.removeEventListener("mouseup", upHandler, true);
            document.removeEventListener("mousemove", moveHandler, true);
        } else if (document.detachEvent) {
            document.detachEvent("onmouseup", upHandler);
            document.detachEvent("onmousemove", moveHandler);
        } else {
            document.onmouseup = olduphandler;
            document.onmousemove = oldmovehandler;
        }
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;
    }
}



function beginResize(elementToResize, event) {
    // obtener una referencia al flow Element
    // para esto es necesario acceder al host del shadowRoot
    var flowElement = elementToResize.parentNode.host;
    flowElement.$server.bringToFront();

    console.log("flow element: ", flowElement);
    console.log("parentNode: ", elementToResize.parentNode);
    console.log("parentNode.host: ", elementToResize.parentNode.host);
    console.log("parentNode.host.parentNode: ", elementToResize.parentNode.host.parentNode);

    // acceder al contenerdor del shadowRoot
    var rect = elementToResize.parentNode.host.parentNode.getBoundingClientRect();
    // console.log("desktop:",rect.top, rect.right, rect.bottom, rect.left);
    var desktopX = rect.left;
    var desktopY = rect.top;
    var width = parseInt(elementToResize.style.width);
    var height = parseInt(elementToResize.style.height);


    var resizeStartX = event.clientX;
    var resizeStartY = event.clientY;


    if (document.addEventListener) {
        document.addEventListener("mousemove", resizeMoveHandler, true);
        document.addEventListener("mouseup", resizeUpHandler, true);
    } else if (document.attachEvent) {
        document.attachEvent("onmousemove", resizeMoveHandler);
        document.attachEvent("onmouseup", resizeUpHandler);
    } else {
        var oldmovehandler = document.onmousemove;
        var olduphandler = document.onmouseup;
        document.onmousemove = resizeMoveHandler;
        document.onmouseup = resizeUpHandler;
    }
    if (event.stopPropagation)
        event.stopPropagation();
    else
        event.cancelBubble = true;
    if (event.preventDefault)
        event.preventDefault();
    else
        event.returnValue = false;

    function resizeMoveHandler(e) {
        if (!e)
            e = window.event;
        dx = e.clientX - resizeStartX;
        dy = e.clientY - resizeStartY;

        console.log("*********************************************")
        console.log("dx:", dx, "cx:", e.clientX, "rsx", resizeStartX, "w:", width);
        console.log("*********************************************")

        // console.log("dy:",e.clientY, deltaY, desktopY, dy);
        elementToResize.style.width = (dx + width) + "px";
        elementToResize.style.height = (dy + height) + "px";
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;
        // console.log("element: ", elementToDrag);

        // invocar al callback en Java. Tener en cuenta que el método 
        // debe estar anotado como @ClientCallable
        flowElement.$server.updateSize(elementToResize.style.width, elementToResize.style.height);
    }

    function resizeUpHandler(e) {
        if (!e)
            e = window.event;
        if (document.removeEventListener) {
            document.removeEventListener("mouseup", resizeUpHandler, true);
            document.removeEventListener("mousemove", resizeMoveHandler, true);
        } else if (document.detachEvent) {
            document.detachEvent("onmouseup", resizeUpHandler);
            document.detachEvent("onmousemove", resizeMoveHandler);
        } else {
            document.onmouseup = olduphandler;
            document.onmousemove = oldmovehandler;
        }
        if (e.stopPropagation)
            e.stopPropagation();
        else
            e.cancelBubble = true;
    }
}

function onWindowClick(element, event) {
    event.stopPropagation();
    // verificar que se haya hecho clic en la ventana.
    // busco el primer elemento cuyo ID != "" y verifico que sea un subwindow.

    // fix para FF y Chrome. event.path no es estandar. 
    var path = event.path || (event.composedPath && event.composedPath());


    var i = 0;
    while (path[i].id == "") {
        i++;
    }
    if (path[i].id == "subwindow") {
        var flowElement = element.parentNode.host;
        flowElement.$server.onWindowsClick();
    }
}

function onCloseClick(element, event) {
    var flowElement = element.parentNode.host;
    flowElement.$server.close();
    event.stopPropagation();
}

function onMinimizeClick(element, event) {
    var flowElement = element.parentNode.host;
    flowElement.$server.minimize();
    event.stopPropagation();
}

function onMaximizeClick(element, event) {
    var flowElement = element.parentNode.host;
    flowElement.$server.maximize();
    event.stopPropagation();
}

function onRestoreClick(element, event) {
    var flowElement = element.parentNode.host;
    flowElement.$server.restore();
    event.stopPropagation();
}


