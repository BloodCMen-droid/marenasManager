document.addEventListener("DOMContentLoaded", function() {
    const categoriaFiltro = document.getElementById("categoriaFiltro");
    
    if (categoriaFiltro) {
        categoriaFiltro.addEventListener("change", function() {
            const categoria = this.value;
            const opciones = document.querySelectorAll("#productoSelect option");

            opciones.forEach(op => {
                if (categoria === "" || op.dataset.categoria === categoria) {
                    op.style.display = "block";
                } else {
                    op.style.display = "none";
                }
            });
        });
    }
});