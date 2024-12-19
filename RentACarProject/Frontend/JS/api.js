const API_URL = "http://localhost:8080/api"; // Backend API URL'si

function getHeaders(includeAuth = false) {
    const headers = { "Content-Type": "application/json" };
    if (includeAuth) {
        const token = localStorage.getItem("token");
        if (token) {
            headers.Authorization = `Bearer ${token}`;
        }
    }
    return headers;
}

async function fetchData(url, options = {}) {
    try {
        const response = await fetch(url, options);
        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }
        return await response.json();
    } catch (error) {
        console.error("API çağrısı sırasında hata oluştu:", error);
        throw error; // Hata fırlatılır ve üst katmanda yakalanabilir
    }
}

// Giriş işlemi
async function login(username, password) {
    try {
        const data = await fetchData(`${API_URL}/auth/login`, {
            method: "POST",
            headers: getHeaders(),
            body: JSON.stringify({ username, password }),
        });
        localStorage.setItem("token", data.token);
        return true;
    } catch {
        return false;
    }
}

// Kayıt işlemi
async function register(user) {
    return fetchData(`${API_URL}/auth/register`, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(user),
    });
}

// Aktif kiralamaları alma
async function getRentals() {
    return fetchData(`${API_URL}/rentals/active`, {
        headers: getHeaders(true),
    });
}

// Araba listesi ve filtreleme
async function getCars(filters = {}) {
    const queryParams = new URLSearchParams(filters).toString();
    return fetchData(`${API_URL}/cars?${queryParams}`, {
        headers: getHeaders(),
    });
}

// Kiralama işlemi
async function rentCarRequest(carId, startDate, endDate) {
    return fetchData(`${API_URL}/rentals/${carId}`, {
        method: "POST",
        headers: getHeaders(true),
        body: JSON.stringify({ startDate, endDate }),
    });
}

// Profil güncelleme
async function updateProfile(profileData) {
    return fetchData(`${API_URL}/profile`, {
        method: "PUT",
        headers: getHeaders(true),
        body: JSON.stringify(profileData),
    });
}

// Kiralama geçmişi
async function getRentalHistory() {
    return fetchData(`${API_URL}/rentals/completed`, {
        headers: getHeaders(true),
    });
}
