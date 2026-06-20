document
.getElementById("categoriaFiltro")
.addEventListener("change",function(){


let categoria=this.value;
let productos=document.querySelectorAll("#productoSelect option");
productos.forEach(p=>{

if(categoria=="" || p.dataset.categoria==categoria){
   p.style.display="block";
}else{
    p.style.display="none";
}

});

});