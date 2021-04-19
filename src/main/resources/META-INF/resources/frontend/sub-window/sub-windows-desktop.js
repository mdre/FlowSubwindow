import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

import "@vaadin/flow-frontend/sub-window/card-styles.js";
import "@vaadin/flow-frontend/sub-window/sub-windows-desktop-styles.js";

/**
 * `sub-windows-desktop`
 * SubWindow Desktop element
 *
 * @customElement
 * @polymer
 */
class SubWindowDesktop extends ThemableMixin(PolymerElement) {

    static get template() {
        return html`
        <style include="sub-windows-desktop-styles">
        </style>

        <div id="main-desktop" 
             class="subwindow-desktop"
             > 
            <div id="desktop"
                 style="width: 100%;
                 height: 100%"></div>
            <div id="windows-bar"
                 class="subwindow-desktop-bar"
                 
            ></div>
        </div>
        `;
    }


    static get is() {
        return 'sub-windows-desktop';
    }

}

customElements.define(SubWindowDesktop.is, SubWindowDesktop);