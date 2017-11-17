function allowDrop(event) {
    event.preventDefault();
}

function drag(event) {
    event.dataTransfer.setData("text", event.target.id);
}

function drop(event) {
    event.preventDefault();

    if (!event.target.getAttribute("ondrop"))
        return false;

    var taskId = event.dataTransfer.getData("text");
    var userId = event.target.getAttribute('id');
    var data = taskId + "&" + userId;
    event.target.appendChild(document.getElementById(taskId));

    var http = new XMLHttpRequest();

    http.open("POST", "/", true);
    http.send(data);
    alert(data);
}