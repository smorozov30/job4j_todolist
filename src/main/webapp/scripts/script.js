$(document).ready(function() {
    load();
})

function load() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todolist/tasks',
        data: {showAll : $('#showAll').is(':checked')},
        dataType: 'json'
    }).done(function (data) {
        $("tbody").empty();
        for (let i = 0; i < data.length; i++) {
            let done = Boolean(data[i]["done"]) ? '<input type="checkbox" checked onclick="done($(this))" id="' + data[i]["id"] + '">'
                : '<input type="checkbox" onclick="done($(this))" id="' + data[i]["id"] + '">';
            let categories = data[i]["categories"];
            $('tbody').append('<tr>'
                + '<th>' + data[i]["id"] + '</th>'
                + '<th>' + data[i]["description"] + '</th>'
                + '<th>' + taskCategories(categories) + '</th>'
                + '<th>' + data[i]["created"] + '</th>'
                + '<th>' + data[i]["user"]["name"] + '</th>'
                + '<th>' + done + '</th>'
            );
        }
        loadCategories();
        currentUser();
    })
}

function taskCategories(data) {
    let text = "";
    for (let j = 0; j < data.length; j++) {
        text = text + data[j]["name"] + '<br>';
    }
    return text;
}

function loadCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todolist/categories',
        dataType: 'json'
    }).done(function (data) {
        $("select").empty();
        for (let i = 0; i < data.length; i++) {
            $('#cIds').append('<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>');
        }
    })
}

function add() {
    if (validate()) {
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/todolist/add.do',
            data: {description : $('#description').val(), cIds : $('#cIds').val()}
        }).done(function() {
            load();
        });
    }
}

function done(checkbox) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todolist/done',
        data: {id : checkbox.attr('id')}
    }).done(function() {
        load();
    });

}

function currentUser() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todolist/user'
    }).done(function(data) {
        $('a').replaceWith('<a id="enter" class="nav-link" href="http://localhost:8080/todolist/login.do">' + data["name"] + '</a>');
    })
}

function validate() {
    let result = true;
    let message = "";
    let field = $('#description');
    if (field.val() === "") {
        message = message + (field.attr("placeholder") + "\n");
        result = false;
    }
    if (!result) {
        alert(message);
    }
    return result;
}