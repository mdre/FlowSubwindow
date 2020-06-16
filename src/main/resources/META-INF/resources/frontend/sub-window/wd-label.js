import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

import "@vaadin/flow-frontend/sub-window/card-styles.js";
import "@vaadin/flow-frontend/sub-window/wd-label-styles.js"


/**
 * `sub-windows-desktop`
 * SubWindow Desktop element
 *
 * @customElement
 * @polymer
 */
class WDLabel extends ThemableMixin(PolymerElement) {

    static get template() {
        return html `
            <style include="wd-label-styles">
            </style>

            <div id="caption" 
                class="wdlabel-caption"
                on-click="onLabelClick"
                > 
            </div>
            `;
    }


    static get is() {
        return 'wd-label';
    }


}

customElements.define(WDLabel.is, WDLabel);