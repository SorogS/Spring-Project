function validatePassword() {

}

function sendAuthRequest() {


    if (validate()) {

        let form = document.forms.RegForm;
        let UserRegObject = {
            name: form.elements.name.value,
            surname: form.elements.surname.value,
            login: form.elements.login.value,
            password: form.elements.password.value,
            email: form.elements.email.value
        }

        fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(UserRegObject)
        }).then(result => {
            if (result.ok) {
                window.location.replace('http://localhost:8080/loginin');
            } else {
                alert("this user already exist");
            }
        })
    }


}

function validate() {
    let form = document.forms.RegForm;

    if (form.elements.name.value.length == 0 ||
        form.elements.name.value == 0 ||
        form.elements.surname.value == 0 ||
        form.elements.surname.value == 0 ||
        form.elements.password.value == 0 ||
        form.elements.email.value.length == 0) {
        alert('Input Data')
        return false;
    } else {
        return true;
    }
}