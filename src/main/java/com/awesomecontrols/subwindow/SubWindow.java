/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.awesomecontrols.subwindow;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
@Tag("sub-window")
// @StyleSheet("frontend://bower_components/sub-window/cards.css")
@JsModule("./sub-window/sub-window.js")
public class SubWindow extends PolymerTemplate<SubWindowModel> 
        implements ISubWindowEvents, HasComponents, HasSize, HasTheme, HasStyle, Focusable {
    private static final long serialVersionUID = -8955205816352713674L;
    private final static Logger LOGGER = Logger.getLogger(SubWindow.class .getName());
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.INFO);
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
    String width;
    String height;
    int zindex;
    
    // keep the subwindow on top of other, but swith between other subwindows with stayOnTop
    boolean stayOnTop;
    
    String title;
    VaadinIcon icon;

    // referencias al modelo html
    @Id("subwindow")
    private Div subwindow;
    
    //-----------------------------------------------------
    @Id("header")
    private Div header;

    @Id("caption")
    private Div caption;

    @Id("icon")
    private Div iconContainer;

    @Id("custom-controls")
    private Div customControls;
    
    
    
    @Id("minimizeButton")
    private Div minimizeButton;
    
    @Id("maximizeButton")
    private Div maximizeButton;
    
    @Id("restoreButton")
    private Div restoreButton;
    
    @Id("closeButton")
    private Div closeButton;
    
    private boolean closeButtonVisible = true;
    private boolean maximized = false;
    
    //-----------------------------------------------------
    
    
    @Id("content")
    private Div content;
    
    @Id("resize")
    private Div resize;
    //-----------------------------------------------------
    
    boolean grayOnFocusLost = true;
    Div grayGlass = new Div();
    
    private boolean inFront = true;

    public SubWindow() {
        setId("subwindow");
        this.title = "subwindow";
        this.caption.removeAll();
        this.caption.add(new Label(this.title));
        init();
    }

    public SubWindow(String title) {
        setId("subwindow");
        this.setTitle(title);
        
        init();
    }

    public SubWindow(String title, VaadinIcon icon) {
        setId("subwindow");
        this.setTitle(title);
        this.setIcon(icon);

        init();
    }

    private void init() {
        grayGlass.setClassName("subwindow-focuslost");
        grayGlass.getElement().addEventListener("click", (event) -> {
            LOGGER.log(Level.FINER, "grayGlass click listener!");
            this.bringToFront();
        });
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
    public SubWindow setTitle(String title) {
        this.title = title;
        this.caption.removeAll();
        this.caption.add(new Label(getTitle()));
        return this;
    }

    
    /**
     * Set the subwindow icon.
     * @return
     */
    public SubWindow setIcon(VaadinIcon icon) {
        this.icon = icon;
        this.iconContainer.removeAll();
        this.iconContainer.add(icon.create());
        return this;
    }

    /**
     * Return the subwindow title
     * @return
     */
    public String getTitle() {
        return this.title;
    }
    

    /**
     * Return the subwindow icon
     * @return
     */
    public VaadinIcon getIcon() {
        return this.icon;
    }

    /**
     * Set the subwindow top position in pixels
     * @param top in pixels
     * @return  this
     */
    public SubWindow setTop(int top) {
        this.top = top;
        this.subwindow.getStyle().set("top", ""+top+"px");
        return this;
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

    
    /**
     * Set the left position in pixels.
     * 
     * @param left in pixels
     * @return  this
     */
    public SubWindow setLeft(int left) {
        this.left = left;
        this.subwindow.getStyle().set("left", ""+left+"px");
        return this;
    }
    
    /**
     * Set the position in pixels
     * @param left in pixels
     * @param top in pixels
     * @return this
     */
    public SubWindow setPosition(int left, int top) {
        this.setTop(top);
        this.setLeft(left);
        return this;
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
    
    /**
     * Set the height in pixels
     * @param h is the height in pixels
     */
    public void setHeight(int h) {
        this.subwindow.getStyle().set("height", h+"px");
        this.height = h+"px";
    }

    @Override
    public String getWidth() {
        return HasSize.super.getWidth(); 
    }

    @Override
    public void setWidth(String w) {
        this.subwindow.getStyle().set("width", w);
        this.width = w;
    }
    
    /**
     * Set the width in pixels
     * @param w is the width in pixels
     * @return 
     */
    public SubWindow setWidth(int w) {
        this.subwindow.getStyle().set("width", w+"px");
        this.width = w+"px";
        return this;
    }
    
    @ClientCallable
    public void updatePosition(int top, int left){
        
        LOGGER.log(Level.FINER, ""+top+","+left);
        if (isMaximized()) {
            restore();
        }
        else {
            this.top = top;
            this.left = left;

            // disparar el evento.
            for (ISubWindowEvents el : this.eventListeners) {
                el.updatePosition(top, left);
            }
        }
    }

    @ClientCallable
    public void updateSize(String w, String h){
        this.width = w;
        this.height = h;
        LOGGER.log(Level.FINER, "update size: w: "+this.width +" h: "+this.height);

        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.updateSize(w, h);
        }

    };
    
    @ClientCallable
    public void onWindowsClick() {
        LOGGER.log(Level.FINER, "Click Event!");
        if (!this.inFront) {
            this.bringToFront();
        }
    }
    
    @ClientCallable
    public void bringToFront() {
        this.inFront = true;
        this.show();
        this.subwindowDesktop.bringToFront(this);
        if (this.grayOnFocusLost) {
            this.subwindow.getStyle().set("filter", "brightness(100%)");
        }
        this.focus();
        this.setTabIndex(-1);
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

    @ClientCallable
    public void toggleMaximizeRestore() {
        if (isMaximized()) {
            restore();
        }
        else {
            maximize();
        }
    }

    @Override
    @ClientCallable
    public void maximize() {
        
        // preserve the size;
        if (this.width ==null || this.width.isEmpty()) {
            this.width = this.subwindow.getStyle().get("width");
        }
        
        if (this.width == null || this.height.isEmpty()) {
            this.height = this.subwindow.getStyle().get("height");
        }
        
        String newHeight = "calc(100% - 30px)";
        
        //this.updateSize();
        this.state = SubWindowState.MAXIMIZED;
        this.subwindow.getStyle().set("left", ""+0+"px");
        this.subwindow.getStyle().set("top", ""+0+"px");
        this.subwindow.getStyle().set("width", "100%");
        this.subwindow.getStyle().set("height", newHeight);
        this.maximizeButton.getStyle().set("display", "none");
//        this.maximizeButton.setVisible(false);
//        this.restoreButton.setVisible(true);
        this.restoreButton.getStyle().set("display", "inline-block");
        this.maximized = true;
        
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.maximize();
        }
    }
    
    public void setMaximized(Boolean bool) {
        maximized = bool;
    }    
    
    public Boolean isMaximized() {
        return maximized;
    }    

    @Override
    @ClientCallable
    public void restore() {
        this.state = SubWindowState.NORMAL;
        LOGGER.log(Level.INFO, "Restore");
        LOGGER.log(Level.INFO, "L:"+this.left+", T:"+this.top+" - w:"+this.width +", h:"+this.height);
        this.subwindow.getStyle().set("left", ""+this.left+"px");
        this.subwindow.getStyle().set("top", ""+this.top+"px");
        this.subwindow.getStyle().set("width", ""+this.width);
        this.subwindow.getStyle().set("height", ""+this.height);
        this.restoreButton.getStyle().set("display", "none");
//        this.restoreButton.setVisible(false);
//        this.maximizeButton.setVisible(true);
        this.maximizeButton.getStyle().set("display", "inline-block");
        this.maximized=false;
        
        // disparar el evento.
        for (ISubWindowEvents el : this.eventListeners) {
            el.restore();
        }
    }

    void focusLost() {
        LOGGER.log(Level.INFO, "focuslost: "+this.grayOnFocusLost+" - "+this.inFront);
        if (this.grayOnFocusLost && this.inFront) {
            LOGGER.log(Level.INFO, "bundle_key");
            this.subwindow.getStyle().set("filter", "brightness(90%)");
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
        
        // Oculto/muestro el closeButton de la barra inferior
        if (this.subwindowDesktop == null) {
            LOGGER.log(Level.WARNING, "Subwindow no added to desktop yet");
            return;
        }
        this.subwindowDesktop.setCloseButtonVisible(this, visible);
        this.closeButtonVisible = visible;
    }
    
    public boolean isCloseButtonVisible() {
        return closeButtonVisible;
        /*String display = this.closeButton.getStyle().get("display");
        return (display != null && !display.equals("none"));*/
    }
    
    public void setMaximizeButtonVisible(boolean visible) {
        this.maximizeButton.getStyle().set("display", visible?"inline-block":"none");
    }
    
    public void setResizable(boolean visible) {
        this.resize.getStyle().set("display", visible?"inline-block":"none");
    }
    
    /**
     * Set the bottom right resize size. 
     * @param size 
     */
    public void setResizeSize(int size) {
        this.resize.getStyle().set("width",""+size+"px"); 
        this.resize.getStyle().set("height",""+size+"px"); 
    }
    
    /**
     * Agrega el componente a la barra de título de la ventana.
     * @param c componente a agregar
     */
    public void addCustomTitleControl(Component c) {
        this.customControls.add(c);
    }

    public SubWindowState getState() {
        return state;
    }

    public void setState(SubWindowState state) {
        this.state = state;
    }
    
    /**
     * Set the stay on top attribute. 
     * A window with stay on top will be always over other windows, 
     * but will swith with other windows that has marked as stayOnTop.
     * 
     * @param stay boolean
     */
    public void stayOnTop(boolean stay) {
        this.stayOnTop = stay;
    }
    
    /**
     * Return the stayOnTop state.
     * @return boolean 
     */
    public boolean isStayOnTop() {
        return this.stayOnTop;
    }
    /**
     * Return a reference to the subwindow Div element.
     * @return 
     */
    public Element getSubwindowElementReference() {
        return this.subwindow.getElement();
    }
}
