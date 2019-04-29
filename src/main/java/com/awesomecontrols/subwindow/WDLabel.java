/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.awesomecontrols.subwindow;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
@StyleSheet("frontend://bower_components/sub-window/cards.css")
@HtmlImport("bower_components/sub-window/wd-label.html")
class WDLabel extends PolymerTemplate<TemplateModel> implements HasSize, HasTheme, HasStyle  {
    private final static Logger LOGGER = Logger.getLogger(WDLabel.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.INFO);
        }
    }
    
    SubWindow sw;
    String label;
    SubWindowDesktop swd;
    
    @Id("caption")
    private Div caption;
    
    public WDLabel(SubWindow sw, SubWindowDesktop swd) {
        this.swd = swd;
        this.sw = sw;
        this.label = sw.getTitle();
        this.init();
    }
    
    private void init() {
        this.caption.removeAll();
        this.caption.add(new Label(label));
        
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE), evt -> {
            sw.close();
        });
        closeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        closeButton.setClassName("wdlabel-close-button");
        
        this.caption.add(closeButton);
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
