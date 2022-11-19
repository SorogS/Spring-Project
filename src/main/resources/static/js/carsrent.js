let token = localStorage.getItem('token');
let curUrl = document.URL.split('/')
let carId = curUrl[4]
let carCostPerDay;


function carRentPageOnload() {

    fetch(`/getrentcar/${carId}`, {
        method: 'GET',
        headers: {'Authorization': `Bearer ${token}`}
    })
        .then(result => {
            console.log(result)
            if (result.ok) {
                console.log(result);
                return result.json()
            }
        }).then(data => {
        CarForRent.innerHTML = `Model: ${data.carName} <br> 
                Type: ${data.type} <br> 
                Cost per day: ${data.costPerDay}`;
        carCostPerDay = data.costPerDay;
    })
}

function setMinDateForEnd() {
    let mindate = document.getElementById("RentStart").value;

    document.getElementById("RentEnd").setAttribute("min", mindate);
}

async function sendRentRequest() {


    if (confirm(`Total cost of rent: ${costCalculate()}. Rent?`)) {
        fetch('/makeorder', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                carId: carId,
                dateRentStart: document.getElementById("RentStart").value,
                dateRentEnd: document.getElementById("RentEnd").value,
                sumRentCost: costCalculate()
            })
        }).then(result => {
            if (result.ok) {
                alert("Vrooom..");
                window.location.replace(window.location.origin);
            }
        })
    }

}

function costCalculate() {
    let start = Date.parse(document.getElementById("RentStart").value);
    let end = Date.parse(document.getElementById("RentEnd").value);

    let timeDiff = Math.abs(end - start);
    let diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));


    return diffDays == 0 ? carCostPerDay : diffDays * carCostPerDay;

}

function setMinDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("RentEnd").setAttribute("min", today);
    document.getElementById("RentStart").setAttribute("min", today);
}