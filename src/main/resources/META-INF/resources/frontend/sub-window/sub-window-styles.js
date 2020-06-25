import { html } from '@polymer/polymer/polymer-element.js';
import styles from '@vaadin/flow-frontend/sub-window/sub-window-style.css';

const subwindowStyleElement = document.createElement('dom-module');

subwindowStyleElement.innerHTML = `
    <template>
        <style>
            ${styles}
        </style>
    </template>
    `;

subwindowStyleElement.register('sub-window-styles');
