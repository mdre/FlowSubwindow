/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.awesomecontrols.subwindow;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
@Tag("wd-label")
// @StyleSheet("frontend://bower_components/sub-window/cards.css")
@JsModule("./sub-window/wd-label.js")
public class WDLabel extends PolymerTemplate<TemplateModel> implements HasSize, HasTheme, HasStyle  {
    private final static Logger LOGGER = Logger.getLogger(WDLabel.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.INFO);
        }
    }
    
    SubWindow sw;
    String label;
    VaadinIcon icon;
    SubWindowDesktop swd;
    
    @Id("caption")
    private Div caption;

    private Image closeButton;
    
    public WDLabel(SubWindow sw, SubWindowDesktop swd) {
        this.swd = swd;
        this.sw = sw;
        this.label = sw.getTitle();
        this.icon = sw.getIcon();
        this.init();
    }
    
    private void init() {
        
        this.caption.removeAll();

        if (this.icon != null) {
            Icon i = this.icon.create();
            i.setClassName("wdlabel-icon");
            this.caption.add(i);
        }

        Label lbl = new Label(this.label);
        lbl.setClassName("wdlabel-label");
        this.caption.add(lbl);
        
        this.closeButton = new Image("./frontend/sub-window/icons/baseline-close-24px.svg","");
        this.closeButton.setClassName("wdlabel-close-button");
        
        this.closeButton.getElement().addEventListener("click", e -> {
            sw.close();
        });                
        this.caption.add(closeButton);

        // Ocultar/mostrar closeButton
        boolean visible = this.getSw().isCloseButtonVisible();
        this.closeButton.getStyle().set("display", visible?"inline-block":"none");
    }

    public SubWindow getSw() {
        return sw;
    }

    public void setSw(SubWindow sw) {
        this.sw = sw;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public Image getCloseButton() {
        return closeButton;
    }

    @ClientCallable
    public void onLabelClick() {
//        this.swd.bringToFront(this.sw);
        this.sw.bringToFront();
    }

    @Override
    public void setClassName(String className, boolean set) {
        this.caption.setClassName(className, set); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setClassName(String className) {
        this.caption.setClassName(className); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
