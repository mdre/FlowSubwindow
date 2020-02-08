import { html } from '@polymer/polymer/polymer-element.js';
const subwindowStyleElement = document.createElement('dom-module');

subwindowStyleElement.innerHTML = `
    <template>
        <style>
            .subwindow {
                position:absolute; 
                z-index:10;
                overflow: hidden;
            }
            
            .subwindow-header {
                position: relative;
                width:100%; 
                height: 30px;
                /*padding-bottom:8px;*/
                background: var(--subwindow-header-bg);
                
                border-bottom: var(--subwindow-header-bottomline-width,1px) 
                               var(--subwindow-header-bottomline-style,solid) 
                               var(--subwindow-header-bottomline-color,#718191);


                display: -webkit-box;
                display: -moz-box;
                display: -ms-flexbox;
                display: -webkit-flex;
                display: flex;

                -webkit-flex-flow: row nowrap;
                justify-content: space-between;
                align-items: stretch;
                
                cursor: move;

            }
            .subwindow-header-caption {
                width: 100%;
                padding-left: 8px;
                display: -webkit-box;
                display: -moz-box;
                display: -ms-flexbox;
                display: -webkit-flex;
                display: flex;

                -webkit-flex-flow: column nowrap;
                justify-content: center;
                align-items: stretch;

                align-content: center;

                color: var(--subwindow-header-color);
            }
            .subwindow-custom-controls {
                height: 18px;
                align-content: center;
            }

            .subwindow-top-buttonsbar {
                display: -webkit-box;
                display: -moz-box;
                display: -ms-flexbox;
                display: -webkit-flex;
                display: flex;

                -webkit-flex-flow: row nowrap;
                justify-content: space-between;

                align-content: center;

                padding: 0px;
                margin-top: 0px;

            }
            
            .subwindow-top-button {
                border-radius: 3px;
                border: 1px solid #666666;
                width: 15px;
                height: 15px;
                text-align: center;
                display: inline-block;
            }

            .subwindow-content {
                /*background-color:#66ff66;*/ 
                width: 100%; 
                height: calc(100% - 31px);
                overflow: auto;
            }

            .subwindow-resize {
                position: absolute; 
                bottom: 0px;
                right: 0px; 
                width: 5px;
                height: 5px; 
                /* border-style: solid;
                border-width: 5px 5px 0 0;*/
                background: var(--subwindow-resize-color,#666666);
                
                -webkit-clip-path: polygon(100% 0, 100% 100%, 0 100%);
                clip-path: polygon(100% 0, 100% 100%, 0 100%);   
                
                cursor: se-resize;
            }

            .subwindow-focuslost {
                position: absolute;
                top: 0px;
                left: 0px;
                width: 100%;
                height: 100%;
                background-color: var(--subwindow-focus-lost-bg, #b3b3b340);
            }
        </style>
    </template>
    `;

subwindowStyleElement.register('sub-window-styles');