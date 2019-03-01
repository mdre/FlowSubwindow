/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.awesomecontrols.subwindow;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public interface SubWindowModel extends TemplateModel {
    int getTop();
    int getLeft();
    void setTop(int top);
    void setLeft(int left);
    
//    void setTitle(String title);
//    String getTitle();
    
    @ClientCallable 
    void updatePosition(int top, int left);
}
