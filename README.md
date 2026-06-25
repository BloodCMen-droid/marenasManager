🔑 MÓDULO ADMINISTRACIÓN — ROLE_ADMIN
VISTA (administrador/)
        ↓
    [Empleados]
    mantenimiento-empleado.html
        → btn "Nuevo" → empleado-form.html
        → btn "Editar" → empleado-form.html (precargado)
        → btn "Eliminar" → confirma → elimina
        ↓
CONTROLLER (EmpleadoController)
    GET  /listEmpleados  → empImp.getAllEmpleado()
    GET  /newEmpleado    → modelo vacío
    POST /saveEmpleado   → empImp.createEmple(empleado)
    GET  /editEmpleado/{id} → empImp.searchEmpleado(id)
    GET  /deleteEmpleado/{id} → empImp.deleteEmpleado(id)
        ↓
SERVICE (EmpleadoImplement)
    createEmple() → encripta password → empRepo.save()
    getAllEmpleado() → empRepo.findAll()
    searchEmpleado() → empRepo.findById()
    deleteEmpleado() → empRepo.deleteById()
        ↓
REPOSITORY (IEmpleadoRepository)
    extends JpaRepository<Empleado, Long>
    → Spring genera SQL automáticamente
        ↓
ENTIDAD (Empleado + UsuarioCredential)
    tbl_empleados ←→ tbl_usuario_credential
    CASCADE ALL → si eliminas empleado elimina usuario
Lo mismo aplica para: Rol, Producto, Categoría, Mesa, Insumo
[Receta Producto] — especial
    mantenimiento-productos.html
        → btn "Receta" → receta-producto.html
        ↓
    ProductosController
    GET  /recetaProducto/{id} → carga producto + insumos
    POST /saveReceta → DetalleProductoInsumoImplement.crear()
    GET  /deleteReceta/{id}/{productoId} → eliminar insumo de receta
        ↓
    DetalleProductoInsumoImplement
        → repo.save(detalle)
        ↓
    detalle_producto_insumo (tabla)
        id | gasto | id_insumo | id_producto

🧾 MÓDULO GESTIÓN DE PEDIDO — ROLE_MESERO
VISTA (mesero/)
        ↓
registrar-pedido.html
    → Selecciona mesa (guardada en sesión)
    → Filtra productos por categoría (JS)
    → Agrega productos temporalmente
    → Ve lista temporal con subtotales
    → Presiona "Guardar Pedido"
        ↓
CONTROLLER (PedidoController)
@SessionAttributes({"detalleTemporal","mesaSeleccionada"})

    GET  /newPedido
        → inicializa sesión detalleTemporal y mesaSeleccionada
        → carga productos, mesas, categorias
        → toma usuario logueado del Authentication

    POST /saveDetalle
        → recibe productoId, cantidad, adicional, mesaId
        → guarda mesaId en sesión
        → crea DetallePedidoDTO con subtotal calculado
        → agrega a lista temporal en sesión
        → redirect:/newPedido

    GET  /deleteDetalle/{uuid}
        → elimina de lista temporal por UUID
        → redirect:/newPedido

    POST /savePedido
        → toma usuario del Authentication
        → crea Pedido (estado=PENDIENTE, fechaHora=now())
        → busca Mesa por mesaId
        → guarda Pedido en BD
        → por cada DTO → crea DetallePedido real en BD
        → status.setComplete() → limpia sesión
        → redirect:/pedidos

    GET  /pedidos
        → pedidoService.getAllPedido()

    GET  /detallePedido/{id}
        → carga pedido con sus detalles
        → carga productos y categorias

    POST /saveDetallePedido
        → agrega producto a pedido existente en BD
        → estado PENDIENTE

    GET  /eliminarDetallePedido/{id}
        → busca detalle → guarda pedidoId
        → elimina detalle
        → redirect:/detallePedido/{pedidoId}

    GET  /demoras/{pedidoId}
        → lista demoras del pedido
        → mesero las ve y notifica

    GET  /notificarDemora/{id}
        → cambia estado demora a NOTIFICADO
        ↓
SERVICE
    IPedidoService → PedidoImplement → IPedidoRepository
    IDetallePedidoService → DetallePedidoImplement
    IDemoraService → DemoraImplement
        ↓
ENTIDADES
    tbl_pedidos
        id | estado | fechaHora | total | mesa_id | usuario_id
    tbl_detalle_pedidos
        id | cantidad | estado | adicional | pedido_id | producto_id
    tbl_demoras
        id | motivo | tiempoMinutos | fecha | estado | pedido_id

👨‍🍳 MÓDULO PREPARACIÓN DE PEDIDO — ROLE_COCINERO
VISTA (cocinero/)
        ↓
cocina.html
    → Lista pedidos en estado PENDIENTE
    → btn "Ver Detalle" → detalle-cocina.html
        ↓
detalle-cocina.html
    → Lista productos del pedido con su estado
    → btn "✅ Preparado" por cada producto PENDIENTE
    → Form registrar demora (motivo + minutos)
    → Lista demoras registradas
        ↓
CONTROLLER (CocineroController)

    GET  /cocina
        → filtra pedidos estado PENDIENTE

    GET  /cocinaPedido/{id}
        → carga pedido completo
        → model.addAttribute("demora", new Demora())

    GET  /prepararDetalle/{id}
        → busca detalle
        → actualizarEstado(id, "PREPARADO")
        → verificarEstadoPedido(pedidoId)
            → si TODOS los detalles = PREPARADO
            → pedido.setEstado("LISTO")
        → redirect:/cocinaPedido/{pedidoId}

    POST /saveDemora
        → crea Demora con fecha=now(), estado=PENDIENTE
        → asocia al pedido
        → redirect:/cocinaPedido/{pedidoId}
        ↓
SERVICE
    IDetallePedidoService
        actualizarEstado() → repository.save()
        verificarEstadoPedido()
            → stream().allMatch(PREPARADO)
            → pedido.setEstado("LISTO")
    IDemoraService
        crearDemora() → fecha=now(), estado=PENDIENTE
        ↓
ENTIDADES
    tbl_detalle_pedidos → estado: PENDIENTE → PREPARADO
    tbl_pedidos → estado: PENDIENTE → LISTO (automático)
    tbl_demoras → estado: PENDIENTE → NOTIFICADO (mesero)

💰 MÓDULO EMISIÓN DE CUENTA — ROLE_CAJERO
VISTA (cajero/)
        ↓
pedidos.html
    → Filtra por estado (LISTO / CERRADO)
    → btn "Ver Detalle" → detalle.html
    → btn "Ver Factura" (si ya está CERRADO)
        ↓
detalle.html
    → Info del pedido completa
    → Lista productos con precios
    → Preview subtotal
    → btn "Proceder al Pago" → pagar.html
        ↓
pagar.html
    → Form datos cliente (DNI + Nombre + Teléfono)
    → Resumen: Subtotal, IGV 18%, Total
    → Selector método de pago
    → btn "Registrar Pago y Generar Factura"
        ↓
CONTROLLER (CajeroController)

    GET  /cajero?estado=LISTO
        → filtra pedidos por estado

    GET  /cajero/detalle/{id}
        → pedido.calcularTotal()

    GET  /cajero/pagar/{id}
        → calcula subtotal, igv, totalFinal
        → manda al model para preview

    POST /cajero/registrarPago
        → verifica si ya tiene factura → redirect a verla
        → buscarPorDni() → si no existe crea cliente
        → calcularTotal() → IGV 18% → total
        → crea Factura con todos los datos
        → pedido.setEstado("CERRADO")
        → descuenta stock con try/catch
        → redirect:/cajero/factura/{id}

    GET  /cajero/factura/{id}
        → carga factura completa
        → renderiza cajero/factura.html
        ↓
SERVICE
    IClienteService → buscarPorDni() / crearCliente()
    IFacturaService → crearFactura() / buscarPorPedido()
    IPedidoService  → calcularTotal() / save()
    IInsumoService  → createInsumo() (descuenta stock)
        ↓
ENTIDADES
    tbl_cliente
        id | dni(unique) | nombre | telefono
    tbl_facturas
        id | fechaEmision | subtotal | igv | total
           | metodoPago | cliente_id | pedido_id
    tbl_pedidos → estado: LISTO → CERRADO
    tbl_insumos → stock -= (gasto * cantidad)

📦 MÓDULO ADMINISTRACIÓN DE INVENTARIO — ROLE_INVENTARIO
VISTA (inventario/)
        ↓
inventario.html
    → Lista TODOS los insumos con estados coloreados
        DISPONIBLE → verde
        BAJO STOCK → naranja
        CRÍTICO    → rojo
        AGOTADO    → rojo oscuro
    → btn "Agregar Stock" → anadir-stock.html
    → Sección Lista de Compras (solo CRÍTICO y AGOTADO)
    → btn "Exportar PDF" → genera reporte Jasper
        ↓
anadir-stock.html
    → Muestra info insumo actual
    → Input cantidad a agregar
    → btn "Guardar Stock"
        ↓
CONTROLLER (InventarioController)

    GET  /inventario
        → getAllInsumo()
        → filtra CRÍTICO y AGOTADO → listaCompras

    GET  /inventario/addStock/{id}
        → carga insumo

    POST /inventario/saveStock
        → agregarStock(id, cantidad)
            → stock += cantidad
            → validarEstado() automático

    GET  /inventario/exportarCompras
        → exportarListaCompras()
        → genera PDF con Jasper
        → ResponseEntity<byte[]>
        ↓
SERVICE (InsumoImplement)

    validarEstado() — PRIVADO, se llama automático
        stock == 0         → AGOTADO
        stock < minimo     → CRÍTICO
        stock <= minimo+5  → BAJO STOCK
        else               → DISPONIBLE

    agregarStock()
        → stock += cantidad
        → validarEstado()
        → save()

    exportarListaCompras()
        → filtra CRÍTICO y AGOTADO
        → mapea a ListaCompraDTO
            nombre | unidad | stockActual
            stockMinimo | cantidadAComprar
        → compila listaCompras.jrxml
        → JRBeanCollectionDataSource(lista)
        → exportReportToPdf()
        ↓
ENTIDADES
    tbl_insumos
        id | nombre | stock | stockMinimo
           | unidadMedida | estado
    detalle_producto_insumo
        id | gasto | id_insumo | id_producto
