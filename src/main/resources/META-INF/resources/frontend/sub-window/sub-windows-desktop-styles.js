import { html } from '@polymer/polymer/polymer-element.js';
const subwindowDesktopStyleElement = document.createElement('dom-module');

subwindowDesktopStyleElement.innerHTML = `
    <template>
        <style>
            .subwindow-desktop {
                position:relative; 
                width: 100%;
                height: 100%;
                background: var(--subwindow-desktop-bg,#f6f6f6);
                overflow: hidden
            }

            .subwindow-desktop-bar {
                background: var(--subwindow-desktop-bar-bg,#e0e0e0);
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 30px;
                z-index: 100;
            }
        </style>
    </template>
    `;

subwindowDesktopStyleElement.register('sub-windows-desktop-styles');
