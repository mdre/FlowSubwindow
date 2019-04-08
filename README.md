# FlowSubwindow

## SubwindowDesktop/Subwindow components
This components let you correctly implement an MDI interface. 

No more windows floating over menu bar or other components.

First you define the SubWindowDesktop and add the SubWindows to it. 

The SubWindows will float only inside it. 

Features:
- fire events for onMove, onClose, onMinimize, onMaximize and onRestore.
- set default window state for new windows.
- hide windows buttons. 
- add components to the title bar.

###Theme variables:
####Subwindows:
--subwindow-bg: subwindow background color: #fff.
--subwindow-border-radius: 2px.

--subwindow-header-bg: none
--subwindow-header-color: none

--subwindow-header-bottomline-width: 1px
--subwindow-header-bottomline-style: solid
--subwindow-header-bottomline-color: #718191

--subwindow-resize-color: #666666

--subwindow-focus-lost-bg: #b3b3b340

####Desktop:
--subwindow-desktop-bg: #f6f6f6
--subwindow-desktop-bar-bg: #e0e0e0

--subwindow-desktop-bar-label-width: 150px  the label width for each subwindow shown.

--subwindow-desktop-bar-label-top-width: 3px
--subwindow-desktop-bar-label-top-style: solid
--subwindow-desktop-bar-label-top-color: #666666

--subwindow-desktop-bar-label-top-hover-width: 3px
--subwindow-desktop-bar-label-top-hover-style: solid
--subwindow-desktop-bar-label-top-hover-color: #0066ff

--subwindow-desktop-bar-label-top-focus-width: 3px
--subwindow-desktop-bar-label-top-focus-style: solid
--subwindow-desktop-bar-label-top-focus-color: #0066ff

TODO: 
- close from tab bar.
