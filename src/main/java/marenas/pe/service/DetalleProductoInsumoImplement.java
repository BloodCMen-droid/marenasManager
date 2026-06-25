package marenas.pe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.DetalleProductoInsumo;
import marenas.pe.repository.IDetalleProductoInsumoRepository;

@Service
public class DetalleProductoInsumoImplement implements IDetalleProductoInsumoService {

    @Autowired
    private IDetalleProductoInsumoRepository repo;

    @Override
    public DetalleProductoInsumo crear(DetalleProductoInsumo detalle){
        return repo.save(detalle);
    }

    @Override
    public List<DetalleProductoInsumo> getByProducto(Long productoId){
        return repo.findByProductoId(productoId);
    }

    @Override
    public void eliminar(Long id){
        repo.deleteById(id);
    }
}