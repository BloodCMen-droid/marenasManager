// dashboard.js - Versión segura
document.addEventListener("DOMContentLoaded", function() {

    // Evitar que se ejecute varias veces
    if (window.dashboardInitialized) return;
    window.dashboardInitialized = true;

    console.log("✅ Dashboard JS cargado correctamente");

    // Activar menú lateral
    const links = document.querySelectorAll(".sidebar a");
    links.forEach(link => {
        link.addEventListener("click", function() {
            links.forEach(l => l.classList.remove("active"));
            this.classList.add("active");
        });
    });

}); 