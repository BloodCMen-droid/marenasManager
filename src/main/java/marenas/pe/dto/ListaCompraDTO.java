package marenas.pe.dto;

public class ListaCompraDTO {
	
	   private String nombre;
	   
	    private String unidadMedida;
	    
	    private int stockActual;
	    
	    private int stockMinimo;
	    
	    private int cantidadAComprar; // stockMinimo - stockActual

	    public ListaCompraDTO(String nombre, String unidadMedida, 
	                          int stockActual, int stockMinimo){
	        this.nombre = nombre;
	        this.unidadMedida = unidadMedida;
	        this.stockActual = stockActual;
	        this.stockMinimo = stockMinimo;
	        this.cantidadAComprar = stockMinimo - stockActual;
	    }

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getUnidadMedida() {
			return unidadMedida;
		}

		public void setUnidadMedida(String unidadMedida) {
			this.unidadMedida = unidadMedida;
		}

		public int getStockActual() {
			return stockActual;
		}

		public void setStockActual(int stockActual) {
			this.stockActual = stockActual;
		}

		public int getStockMinimo() {
			return stockMinimo;
		}

		public void setStockMinimo(int stockMinimo) {
			this.stockMinimo = stockMinimo;
		}

		public int getCantidadAComprar() {
			return cantidadAComprar;
		}

		public void setCantidadAComprar(int cantidadAComprar) {
			this.cantidadAComprar = cantidadAComprar;
		}

	    
}
