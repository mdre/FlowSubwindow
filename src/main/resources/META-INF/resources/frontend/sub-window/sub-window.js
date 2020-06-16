import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

import "@vaadin/flow-frontend/sub-window/card-styles.js";
import "@vaadin/flow-frontend/sub-window/sub-window-styles.js"
//import "@vaadin/flow-frontend/sub-window/WindowEvents.js"
//<script src="WindowEvents.js"></script>


/**
 * `sub-window`
 * Floating SubWindow element
 *
 * @customElement
 * @polymer
 * @demo demo/index.html
 */
class SubWindow extends ThemableMixin(PolymerElement) {

    static get template() {
        return html `
               <style include="card-styles sub-window-styles">
               </style>

               <div id="subwindow" 
                    class="card card-1 subwindow"
                    draggable="true"
                    on-click="onWindowClick"
                    > 
                    <div id="header" 
                         class="subwindow-header"
                         on-mousedown="beginDrag"
                         >
                         <div id="caption"
                              class="subwindow-header-caption"
                              >
                              header
                         </div>
                         <div id="custom-controls"
                              class="subwindow-custom-controls"
                              ></div>
                         <div id="subwindow-top-buttons"
                              class="subwindow-top-buttonsbar"
                              >
                              <img id="minimizeButton"
                              class="subwindow-top-button" 
                              on-click="onMinimizeClick" 
                              src="./frontend/sub-window/icons/baseline-minimize-24px.svg"
                              />
                              
                              <img id="maximizeButton"
                                   class="subwindow-top-button"
                                   on-click="onMaximizeClick"
                                   src="./frontend/sub-window/icons/baseline-fullscreen-24px.svg"
                                   />
                              
                              <img id="restoreButton"
                                   class="subwindow-top-button"
                                   on-click="onRestoreClick"
                                   src="./frontend/sub-window/icons/baseline-fullscreen_exit-24px.svg"
                                   />
                              
                              <img id="closeButton"
                                   class="subwindow-top-button"
                                   on-click="onCloseClick"
                                   src="./frontend/sub-window/icons/baseline-close-24px.svg"
                                   />
                         </div>
                    </div>
                    <div id="content" 
                         class="subwindow-content"
                    >content</div>
                    <div id="resize" 
                         class="subwindow-resize"
                         on-mousedown="beginResize"></div>
               </div>
          `;
    }

    static get is() {
        return 'sub-window';
    }

    onWindowClick(event) {
        event.stopPropagation();

        ////console.log("subwindows click!");
        ////console.log(event);
        // verificar que se haya hecho clic en la ventana.
        // busco el primer elemento cuyo ID != "" y verifico que sea un subwindow.

        // fix para FF y Chrome. event.path no es estandar. 
        var path = event.path || (event.composedPath && event.composedPath());

        ////console.log(path);
        var i = 0;
        while (path[i].id != "subwindow") {
            i++;
        }
        ////console.log(path[i]);
        if (path[i].id == "subwindow") {
            //var flowElement = element.parentNode.host;
            this.$server.onWindowsClick();
        }
    }




    beginDrag(event) {
        var selfRef = this;
        // obtener una referencia al flow Element
        // para esto es necesario acceder al host del shadowRoot
        var elementToDrag = this.shadowRoot.getElementById('subwindow');
        this.$server.bringToFront();

        //console.log("drag event: ", event);
        //console.log("this: ", this);

        // //console.log("parentNode: ", elementToDrag.parentNode);
        // //console.log("parentNode.host: ", elementToDrag.parentNode.host);
        // //console.log("parentNode.host.parentNode: ", elementToDrag.parentNode.host.parentNode);

        // acceder al contenerdor del shadowRoot
        //var rect = elementToDrag.parentNode.host.parentNode.getBoundingClientRect();
        var rect = elementToDrag.getBoundingClientRect();

        // //console.log("desktop:",rect.top, rect.right, rect.bottom, rect.left);
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
            var dx = e.clientX - deltaX - desktopX;
            //dx = dx > desktopX ? dx : desktopX;
            dx = dx < 0 ? 0 : dx;

            var dy = e.clientY - deltaY - desktopY;
            //dy = dy > desktopY ? dy : desktopY;
            dy = dy < 0 ? 0 : dy;

            // //console.log("dx:",e.clientX, deltaX, desktopX, dx);
            // //console.log("dy:",e.clientY, deltaY, desktopY, dy);
            elementToDrag.style.left = dx + "px";
            elementToDrag.style.top = dy + "px";
            if (e.stopPropagation)
                e.stopPropagation();
            else
                e.cancelBubble = true;
            // //console.log("element: ", elementToDrag);

            // invocar al callback en Java. Tener en cuenta que el método 
            // debe estar anotado como @ClientCallable
            selfRef.$server.updatePosition(dy, dx);
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



    beginResize(event) {
        // obtener una referencia al flow Element
        // para esto es necesario acceder al host del shadowRoot
        var selfRef = this;
        this.$server.bringToFront();

        // acceder al contenerdor del shadowRoot
        var elementToResize = this.shadowRoot.getElementById('subwindow');
        var rect = elementToResize.getBoundingClientRect();
        // //console.log("desktop:",rect.top, rect.right, rect.bottom, rect.left);
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
            var dx = e.clientX - resizeStartX;
            var dy = e.clientY - resizeStartY;

            //console.log("*********************************************")
            //console.log("dx:", dx, "cx:", e.clientX, "rsx", resizeStartX, "w:", width);
            //console.log("*********************************************")

            // //console.log("dy:",e.clientY, deltaY, desktopY, dy);
            elementToResize.style.width = (dx + width) + "px";
            elementToResize.style.height = (dy + height) + "px";
            if (e.stopPropagation)
                e.stopPropagation();
            else
                e.cancelBubble = true;
            // //console.log("element: ", elementToDrag);

            // invocar al callback en Java. Tener en cuenta que el método 
            // debe estar anotado como @ClientCallable
            selfRef.$server.updateSize(elementToResize.style.width, elementToResize.style.height);
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


    onCloseClick(event) {
        //var flowElement = element.parentNode.host;
        //flowElement.$server.close();
        this.$server.close();
        event.stopPropagation();
    }

    onMinimizeClick(event) {
        //var flowElement = element.parentNode.host;
        //flowElement.$server.minimize();
        this.$server.minimize();
        event.stopPropagation();
    }

    onMaximizeClick(event) {
        // var flowElement = element.parentNode.host;
        // flowElement.$server.maximize();
        this.$server.maximize();
        event.stopPropagation();
    }

    onRestoreClick(event) {
        // var flowElement = element.parentNode.host;
        // flowElement.$server.restore();
        this.$server.restore();
        event.stopPropagation();
    }

}

customElements.define(SubWindow.is, SubWindow);