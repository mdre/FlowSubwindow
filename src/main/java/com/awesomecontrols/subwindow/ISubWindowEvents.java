/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.awesomecontrols.subwindow;

/**
 *
 * @author Marcelo D. Ré {@literal <marcelo.re@gmail.com>}
 */
public interface ISubWindowEvents {
    void close();
    void minimize();
    void maximize();
    void restore();
    void updatePosition(int x, int y);
    void updateSize(String w, String h);
    void toggleMaximizeRestore();
}
