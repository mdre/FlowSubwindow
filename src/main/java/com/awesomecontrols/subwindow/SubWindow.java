/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.awesomecontrols.subwindow;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
@Tag("sub-window")
@StyleSheet("frontend://bower_components/sub-window/cards.css")
@HtmlImport("bower_components/sub-window/sub-window.html")
public class SubWindow extends PolymerTemplate<SubWindowModel> 
        implements ISubWindowEvents, HasComponents, HasSize, HasTheme, HasStyle {
    private static final long serialVersionUID = -8955205816352713674L;
    private final static Logger LOGGER = Logger.getLogger(SubWindow.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.FINER);
        }
    }

    private SubWindowDesktop subwindowDesktop;
    
    private SubWindowState state = SubWindowState.NORMAL;
    /**
     * número de identificación asignado por el desktop.
     */
    private int subwindowDesktopId;

    List<ISubWindowEvents> eventListeners = new ArrayList<>();
    
    int top;
    int left;
    String widht;
    String height;
    int zindex;
    
    String title;
    
    
    // referencias al modelo html
    @Id("subwindow")
    private Div subwindow;
    
    //-----------------------------------------------------
    @Id("header")
    private Div header;
    
    @Id("caption")
    private Div caption;
    
    @Id("custom-controls")
    private Div customControls;
    
    
    
    @Id("minimizeButton")
    private Image minimizeButton;
    
    @Id("maximizeButton")
    private Image maximizeButton;
    
    @Id("restoreButton")
    private Image restoreButton;
    
    @Id("closeButton")
    private Image closeButton;
    
    //-----------------------------------------------------
    
    
    @Id("content")
    private Div content;
    
    @Id("resize")
    private Div resize;
    //-----------------------------------------------------
    
    boolean grayOnFocusLost = true;
    Div greyGlass = new Div();
    
    private boolean inFront = true;
    
    public SubWindow(String title) {
        setId("subwindow");
        this.title = title;
        
        this.caption.removeAll();
        this.caption.add(new Label(this.title));
        
        init();
    }

    private void init() {
        greyGlass.setClassName("subwindow-focuslost");
        
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        this.setContent(vl);
        this.setWidth("75px");
        this.setHeight("75px");
        
        this.restoreButton.getStyle().set("display", "none");
    }
    
    /**
     * Set the subwindow desktop for the subwindow.
     * 
     * @param swd
     * @return this
     */
    SubWindow setSubWindowDesktop(SubWindowDesktop swd) {
        this.subwindowDesktop = swd;
        return this;
    }

    
    void setDesktopId(int id) {
        this.subwindowDesktopId = id;
    }
    
    int getSubwindowDesktopId() {
        return this.subwindowDesktopId;
    }
    
    /**
     * Set the subwindow content
     * 
     * @param content 
     */
    public void setContent(Component content) {
        this.content.removeAll();
        this.content.add(content);
    }
    
    
    /**
     * Set the subwindow title.
     * @return 
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * Set the subwindow top position in pixels
     * @param top 
     */
    public void setTop(int top) {
        this.top = top;
        this.subwindow.getStyle().set("top", ""+top+"px");
    }

    /**
     * Return the subwindow top position in pixels
     * @return top position
     */
    public int getTop() {
        return top;
    }

    /**
     * Return the left position in pixels.
     * @return left position
     */
    public int getLeft() {
        return left;
    }

//    public String getWidht() {
//        return widht;
//    }
    
    /**
     * Set the left position in pixels.
     * 
     * @param left 
     */
    public void setLeft(int left) {
        this.left = left;
        this.subwindow.getStyle().set("left", ""+left+"px");
    }

    @Override
    public void setSizeUndefined() {
        HasSize.super.setSizeUndefined(); 
    }

    @Override
    public void setSizeFull() {
        HasSize.super.setSizeFull(); 
    }

    @Override
    public String getHeight() {
        return HasSize.super.getHeight(); 
    }

    @Override
    public void setHeight(String height) {
        this.subwindow.getStyle().set("height", height);
        this.height = height;
    }

    @Override
    public String getWidth() {
        return HasSize.super.getWidth(); 
    }

    @Override
    public void setWidth(String width) {
        this.subwindow.getStyle().set("width", width);
        this.widht = widht;
    }
    
    
    @ClientCallable
    private void updatePosition(int top, int left){ 
        LOGGER.log(Level.INFO, ""+top+","+left);
        this.top = top;
        this.left = left;
    };
    
    
    @ClientCallable
    private void updateSize(String w, String h){ 
        this.widht = w;
        this.height = h;
        LOGGER.log(Level.INFO, "update size: w: "+this.widht+" h: "+this.height);
    };
    
    @ClientCallable
    public void onWindowsClick() {
        this.bringToFront();
    }
    
    @ClientCallable
    public void bringToFront() {
        this.inFront = true;
        this.show();
        this.subwindowDesktop.bringToFront(this);
        if (this.grayOnFocusLost) {
            this.subwindow.remove(greyGlass);
        }
    }
    
    void setZIndex(int idx) {
        this.zindex = idx;
        this.subwindow.getStyle().set("z-index", ""+idx);
    }
    
    /**
     * Set the subwindow css class style
     * @param style
     * @return this
     */
    public SubWindow setClassStyle(String style) {
        this.subwindow.setClassName(style);
        return this;
    }
    
    @Override
    @ClientCallable
    public void close() {
        this.subwindowDesktop.remove(this);
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.close();
        }
    }
    
    @Override
    @ClientCallable
    public void minimize() {
        this.getStyle().set("display", "none");
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.minimize();
        }
    }
    
    @Override
    @ClientCallable
    public void maximize() {
        
        // preserve the size;
        if (this.widht==null || this.widht.isEmpty()) {
            this.widht = this.subwindow.getStyle().get("width");
        }
        
        if (this.widht == null || this.height.isEmpty()) {
            this.height = this.subwindow.getStyle().get("height");
        }
        
        //this.updateSize();
        this.state = SubWindowState.MAXIMIZED;
        this.subwindow.getStyle().set("left", ""+0+"px");
        this.subwindow.getStyle().set("top", ""+0+"px");
        this.subwindow.getStyle().set("width", "100%");
        this.subwindow.getStyle().set("height", "100%");
        this.maximizeButton.getStyle().set("display", "none");
//        this.maximizeButton.setVisible(false);
//        this.restoreButton.setVisible(true);
        this.restoreButton.getStyle().set("display", "inline-block");
        
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.maximize();
        }
    }
    
    @Override
    @ClientCallable
    public void restore() {
        this.state = SubWindowState.NORMAL;
        LOGGER.log(Level.INFO, "Restore");
        LOGGER.log(Level.INFO, "L:"+this.left+", T:"+this.top+" - w:"+this.widht+", h:"+this.height);
        this.subwindow.getStyle().set("left", ""+this.left+"px");
        this.subwindow.getStyle().set("top", ""+this.top+"px");
        this.subwindow.getStyle().set("width", ""+this.widht);
        this.subwindow.getStyle().set("height", ""+this.height);
        this.restoreButton.getStyle().set("display", "none");
//        this.restoreButton.setVisible(false);
//        this.maximizeButton.setVisible(true);
        this.maximizeButton.getStyle().set("display", "inline-block");
        
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.restore();
        }
    }
    
    
    void focusLost() {
        LOGGER.log(Level.INFO, "focuslost: "+this.grayOnFocusLost+" - "+this.inFront);
        if (this.grayOnFocusLost && this.inFront) {
            LOGGER.log(Level.INFO, "bundle_key");
            this.subwindow.add(greyGlass);
        }
        this.inFront = false;
    }
    
    
    public void setGreyOnFocus(boolean b) {
        this.grayOnFocusLost = b;
    }
    
    public void show() {
        this.getStyle().set("display", "inline-block");
    }
    
    /**
     * Agrega un listener para los eventos de la ventana
     * @param eventListener listener
     */
    public void addEventListener(ISubWindowEvents eventListener) {
        if (this.eventListeners == null) {
            this.eventListeners = new ArrayList<>();
        }
        this.eventListeners.add(eventListener);
    }
    
    public void removeEventListener(ISubWindowEvents eventListener) {
        if (this.eventListeners!=null) {
            this.eventListeners.remove(eventListener);
        }
    }
    
    public void setMinimizeButtonVisible(boolean visible) {
        this.minimizeButton.getStyle().set("display", visible?"inline-block":"none");
    }
    
    public void setCloseButtonVisible(boolean visible) {
        this.closeButton.getStyle().set("display", visible?"inline-block":"none");
    }
    
    public void setMaximizeButtonVisible(boolean visible) {
        this.maximizeButton.getStyle().set("display", visible?"inline-block":"none");
    }
    
    public void setResizable(boolean visible) {
        this.resize.getStyle().set("display", visible?"inline-block":"none");
    }
    
    /**
     * Agrega el componente a la barra de título de la ventana.
     * @param c componente a agregar
     */
    public void addCustomTitleControl(Component c) {
        this.customControls.add(c);
    }
}
