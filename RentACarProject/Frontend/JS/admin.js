async function loadAllRentals() {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_URL}/admin/rentals`, {
        headers: { Authorization: `Bearer ${token}` },
    });

    if (response.ok) {
        const rentals = await response.json();
        const list = document.getElementById("all-rentals-list");
        list.innerHTML = "";

        rentals.forEach((rental) => {
            const li = document.createElement("li");
            li.innerHTML = `
                <div>
                    <h3>${rental.car.brand} - ${rental.car.model}</h3>
                    <p>Kiralayan: ${rental.user.username}</p>
                    <p>Tarih: ${rental.startDate} - ${rental.endDate}</p>
                    <p>Ücret: ${rental.totalPrice} TL</p>
                </div>
                <button onclick="loadCarRentals(${rental.car.id})">Arabanın Kiralamaları</button>
            `;
            list.appendChild(li);
        });
    } else {
        alert("Kiralama bilgileri yüklenemedi.");
    }
}

async function loadCarRentals(carId) {
    const token = localStorage.getItem("token");
    const response = await fetch(`${API_URL}/admin/rentals/car/${carId}`, {
        headers: { Authorization: `Bearer ${token}` },
    });

    if (response.ok) {
        const rentals = await response.json();
        const list = document.getElementById("car-rentals-list");
        list.innerHTML = "";

        rentals.forEach((rental) => {
            const li = document.createElement("li");
            li.innerHTML = `
                <div>
                    <h3>${rental.car.brand} - ${rental.car.model}</h3>
                    <p>Kiralayan: ${rental.user.username}</p>
                    <p>Tarih: ${rental.startDate} - ${rental.endDate}</p>
                    <p>Ücret: ${rental.totalPrice} TL</p>
                </div>
            `;
            list.appendChild(li);
        });

        showSection("car-rentals");
    } else {
        alert("Araba kiralama bilgileri yüklenemedi.");
    }
}

function showSection(sectionId) {
    document.querySelectorAll("section").forEach((section) => {
        section.classList.add("hidden");
    });
    document.getElementById(sectionId).classList.remove("hidden");
}

document.getElementById("logout-link").addEventListener("click", () => {
    localStorage.removeItem("token");
    alert("Çıkış yapıldı!");
    window.location.href = "index.html";
});

// Sayfa yüklendiğinde tüm kiralamaları getir
document.addEventListener("DOMContentLoaded", loadAllRentals);
