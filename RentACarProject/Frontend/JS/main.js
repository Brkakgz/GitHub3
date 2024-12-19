document.getElementById("login-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const success = await login(username, password);
    if (success) {
        alert("Giriş başarılı!");
        showSection("rentals-section");
    } else {
        document.getElementById("login-error").textContent = "Giriş başarısız. Lütfen bilgilerinizi kontrol edin.";
    }
});

document.getElementById("register-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const user = {
        username: document.getElementById("reg-username").value,
        password: document.getElementById("reg-password").value,
        firstName: document.getElementById("reg-firstname").value,
        lastName: document.getElementById("reg-lastname").value,
        nationalId: document.getElementById("reg-nationalid").value,
        phoneNumber: document.getElementById("reg-phone").value,
        address: document.getElementById("reg-address").value,
    };

    const success = await register(user);
    if (success) {
        alert("Kayıt başarılı! Şimdi giriş yapabilirsiniz.");
        showSection("login-section");
    } else {
        document.getElementById("register-error").textContent = "Kayıt başarısız. Lütfen bilgilerinizi kontrol edin.";
    }
});

function showSection(sectionId) {
    document.querySelectorAll("section").forEach((section) => {
        section.classList.add("hidden");
    });
    document.getElementById(sectionId).classList.remove("hidden");

    switch (sectionId) {
        case "cars-section":
            loadCars();
            break;
        case "rentals-section":
            loadRentals();
            break;
        case "profile-section":
            loadProfile();
            break;
        case "history-section":
            loadHistory();
            break;
        default:
            break;
    }
}
