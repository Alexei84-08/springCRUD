function myFunc() {
    getUsers();
}

function getUsers() {
    $.ajax({
        url: 'admin/users',
        type: "GET"
    }).done(function (data) {
        userTable(data);
    });
}

function userTable(data) {
    clearTable();
    const divTable = $('<div></div>').attr('id', 'divTable');
    const table = $('<table></table>').addClass('table table-striped').attr('id', 'table');
    const tableName = $('<h5><b>All users</b></h5>');
    const thead = $('<thead><tr><th>ID</th><th>Role</th><th>Login</th><th>Password</th><th>Email</th><th>Edit</th></tr></thead>');
    const tbody = $('<tbody></tbody>');

    $('#tableUni').append(divTable);
    divTable.append(tableName);
    divTable.append(table);
    table.append(thead);
    table.append(tbody);

    $.each(data, function (key, value) {
        var roles = "";
        var flag = false;
        // value.roles.forEach(element => roles += element.role + ' ');
        $.each(value.roles, function (key, value) {
            if (value.size % 2 !== 0) {
                if (flag) {
                    roles += ", ";
                } else {
                    flag = true;
                }
                roles += value.role;
            }
        });
        tbody.append($('<tr>').append(
            $('<td>').text(value.id),
            $('<td>').text(roles),
            $('<td>').text(value.login),
            $('<td>').text(value.password),
            $('<td>').text(value.email),
            $('<td>').append($('<button onclick="getUserUpdate(this.dataset.id)"></button>').text('Edit').attr("data-id", value.id).addClass('btn btn-info')),
        ));
    });
}

function getUserUpdate(id) {
    $.ajax({
        url: 'admin/user/' + id,
        type: "GET"
    }).done(function (data) {
        showModalUpdateUser(data);
    });
}

function showModalUpdateUser(data) {
    $('.input').val('');
    var modal = $('#updateUserModal').modal('show');
    modal.on('shown.bs.modal', function () {
        $('#inputEmailUpdate').focus()
    });
    populate('#formUpdateUserModal', data);
}

function populate(frm, data) {
    var roles = "";
    var flag = false;
    $.each(data, function (keyi, valuei) {
        if (Array.isArray(valuei)) {
            $.each(valuei, function (key, value) {
                if (value.size % 2 !== 0) {
                    if (flag) {
                        roles += ", ";
                    } else {
                        flag = true;
                    }
                    roles += value.role;
                }
            });
            $('[name=' + keyi + ']', frm).val(roles);
        } else {
            $('[name=' + keyi + ']', frm).val(valuei);
        }
    });
}

function updateUser() {
    var obj = $("form#formUpdateUserModal").serializeToJSON({
        // options here
    });
    var data = JSON.stringify(obj);

    $.ajax({
        url: 'admin/users',
        type: "PUT",
        contentType: "application/json",
        data: data
    }).done(function () {
        $('#updateUserModal').modal('hide');
        getUsers();
    }).fail(function () {
    });
}

function addUser() {
    var obj = $("form#formAddUser").serializeToJSON({
        // options here
    });
    var data = JSON.stringify(obj);
    $.ajax({
        url: 'admin/users',
        type: "POST",
        contentType: "application/json",
        data: data
    }).done(function () {
        clearFormAddUser();
        getUsers();
    });
}

function clearFormAddUser() {
    $('#formAddUser input').val('');
    // $('#name').val('');

}

function clearTable() {
    $('#divTable').remove();
}
