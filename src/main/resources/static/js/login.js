async function logUser(data) {
    return await fetch("/login", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

function validate() {
    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;

    if (login.length == 0 || password.length == 0) {
        alert('Input data!')
        return false;
    }

    return true;
}

async function login() {
    let login = document.getElementById("login").value;
    let password = document.getElementById("password").value;
    let mes = document.getElementById("message");
    let data = {login: login, password: password};
    if (validate()) {
        let result = await logUser(data);
        if (result.ok) {
            let body = await result.text();
            let info = JSON.parse(body);
            localStorage.setItem('token', info['token']);
            document.cookie = `token=${info['token']};Path=/`;
            window.location.replace(window.location.origin);
        } else {
            mes.innerHTML = 'Error occured: ';
            mes.innerHTML += `${result.text().length > 0 ? result.text() : 'user not found'}`;
        }
    }

}

function loginOnload() {
    if (localStorage.getItem('token')) {
        localStorage.removeItem('token');
    }
    if (document.cookie) {
        document.cookie = 'token="";max-age=-1'
    }
}