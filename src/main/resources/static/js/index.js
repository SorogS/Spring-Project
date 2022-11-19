let token = localStorage.getItem('token');

function indexOnLoadFunc() {
    if (token) {
        fetch('/getUserInfo', {
            headers: {'Authorization': `Bearer ${token}`}
        })
            .then(result => {

                if (result.ok) {
                    return result.json()
                }
            }).then(data => {
            console.log(data);
            index_header.innerHTML = `
                    <ul class="menu-main">
                        <li> <a href="/loginin">Log Out</a> </li>
                        <li> <a href="/profile/${data.login}">Profile</a> </li>  
                        ${data.userRole == 'ROLE_ADMIN' ? '<li> <a href="admin/adminpage/NoTable">Admin</a></li>' : ""}                      
                    </ul>`

            originRows = Array.from(document.getElementById("carsTable").rows);

            console.log(document.getElementById("carsTable"));
        });
    } else {

        index_header.innerHTML = `
            <ul class="menu-main">
            <li>
                <a href="/loginin">Login</a>
            </li>
            </li>
            <li>
                <a href="/registration">Registration</a>
            </li>`


    }

}

function openAdminPage() {
    fetch("/admin/getadminaccess", {
        headers: {'Authorization': `Bearer ${token}`},
        redirect: "follow"
    }).then(result => {
        if (result.redirected) {
            window.location.replace(result.url);
        }
    })

}


//очень стыдно, но как есть;
function filterChange() {

    let firstRow = document.createElement("tr");

    firstRow.insertCell().innerHTML = ' <h3>Image</h3>';
    firstRow.insertCell().innerHTML = ' <h3>Car Name</h3>';
    firstRow.insertCell().innerHTML = ' <h3>Type</h3>';
    firstRow.insertCell().innerHTML = ' <h3>Cost per day</h3>';

    let newRows = [];
    newRows.push(firstRow)
    let table = document.getElementById("carsTable");
    let selectedType = filterSelect.value;
    if (selectedType != 'All') {
        let arr = Array.prototype.slice.call(originRows);

        arr.forEach(element => {
            if (element.children[2].innerText == selectedType) {

                newRows.push(element);
            }
        })

        let rows = table.rows.length;
        for (let i = 0; i < rows; i++) {
            table.deleteRow(0);
        }
    } else {
        newRows = originRows;
    }

    newRows.forEach((element, index) => {
        table.insertRow(index).append(element);
    })


}
