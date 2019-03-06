package com.awesomecontrols.subwindow;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag("sub-windows-desktop")
@StyleSheet("frontend://bower_components/sub-window/cards.css")
@HtmlImport("bower_components/sub-window/sub-windows-desktop.html")
public class SubWindowDesktop extends PolymerTemplate<TemplateModel> implements HasComponents, HasSize {
    private final static Logger LOGGER = Logger.getLogger(SubWindowDesktop.class .getName());
    private static final long serialVersionUID = -8955205816352713674L;
    static {
        if (LOGGER.getLevel() == null) {
            LOGGER.setLevel(Level.INFO);
        }
    }
    
    @Id("desktop")
    private Div desktop;
    
    @Id("windows-bar")
    private Div windowsBar;
    
    List<WDLabel> windows = new ArrayList<>();
    private int subwindowsId = 0;
    
    private int ZTOP = 50;
    private int ZLOWER = 10;
    
    // establece el desplazamiento por defecto para las ventanas que se agregan.
    private int windowsOffset = 30;
    
    /** 
     * Default state for new added windows
     */
    private SubWindowState defaultWindowState = null;

    
    public SubWindowDesktop() {
        // getElement().getStyle().set("background-color","#fffdb2");
    }

    /**
     * Add a subwindow to the desktop
     * @param subw 
     */
    public void add(SubWindow subw) {
        desktop.add(subw); 
        
        subw.setDesktopId(this.subwindowsId);
        this.subwindowsId++;
        
        WDLabel wdl = new WDLabel(subw, this);
        
        this.windows.add(wdl);
        subw.setSubWindowDesktop(this);
        
        // calcular la posición.
        subw.setTop(this.windowsOffset * this.windows.size());
        subw.setLeft(this.windowsOffset * this.windows.size());
        
        bringToFront(subw);
        
        if (this.defaultWindowState==SubWindowState.NORMAL) {
            subw.restore();
        } else if (this.defaultWindowState==SubWindowState.MAXIMIZED) {
            subw.maximize();
        } else if (this.defaultWindowState==SubWindowState.MINIMIZED) {
            subw.minimize();
        }
        
        this.windowsBar.add(wdl);
    }

    /**
     * Return the offset in pixels to apply to the subwindow when is added to the desktop.
     * 
     * Spanish:
     * Retorna el desplazamiento en pixels que se aplica a una ventana cuando se agrega
     * al desktop.
     * 
     * @return 
     */
    public int getWindowsOffset() {
        return windowsOffset;
    }

    /**
     * Set the default offset in pixels to apply to added subwindows.
     * 
     * Spanish:
     * Establece el desplazamiento en pixels por defecto que tendrán las ventanas
     * que se agregen al desktop
     * 
     * @param windowsOffset 
     */
    public void setWindowsOffset(int windowsOffset) {
        this.windowsOffset = windowsOffset;
    }
    
    public void bringToFront(SubWindow sw) {
        LOGGER.log(Level.INFO, "bringToFront: "+sw.getTitle());
        for (WDLabel wdl : this.windows) {
            SubWindow window = wdl.getSw();
            if (window == sw) {
                LOGGER.log(Level.FINER, "*******************************************");
                LOGGER.log(Level.FINER, "TOP: "+window.getTitle());
                LOGGER.log(Level.FINER, "*******************************************");
                window.setZIndex(ZTOP);
//                window.focus();
                window.setClassStyle("card card-4");
                wdl.setClassName("wdlabel-caption-focus");
            } else {
                LOGGER.log(Level.FINER, "low: "+window.getTitle());
                window.setZIndex(ZLOWER);
                window.setClassStyle("card card-1");
                window.focusLost();
                wdl.setClassName("wdlabel-caption-focus",false);
            }
        }
    }

    /**
     * Remove the subwindow from the desktop.
     * 
     * Spanish:
     * Elimina la ventana del desktop.
     * 
     * @param subw 
     */
    public void remove(SubWindow subw) {
        this.desktop.remove(subw);
        for (Iterator<WDLabel> iterator = this.windows.iterator(); iterator.hasNext();) {
            WDLabel wdl = iterator.next();
            if (wdl.getSw() == subw) {
                this.windowsBar.remove(wdl);
                iterator.remove();
            }
            
        }
        
    }
    
    /**
     * Return de defaultWindowState to set for the new added subwindows.
     * It is null, do nothing. Otherwise it set the state to the added subwindow
     * @return 
     */
    public SubWindowState getDefaultWindowState() {
        return defaultWindowState;
    }

    /**
     * Set the default window state for the new added subwindows.
     * If is set to null, do nothing.
     * @param defaultWindowState 
     */
    public void setDefaultWindowState(SubWindowState defaultWindowState) {
        this.defaultWindowState = defaultWindowState;
    }
    
}
