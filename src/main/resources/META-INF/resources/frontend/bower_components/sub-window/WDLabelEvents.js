function onLabelClick(elementToDrag, event) {
    var flowElement = elementToDrag.host;
    flowElement.$server.onLabelClick();
}