package marenas.pe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import marenas.pe.model.Demora;
import marenas.pe.repository.IDemoraRepository;

//DemoraImplement
@Service
public class DemoraImplement implements IDemoraService {

 @Autowired
 private IDemoraRepository demoraRepo;

 @Override
 public Demora crearDemora(Demora demora){
     demora.setFecha(LocalDateTime.now());
     demora.setEstado("PENDIENTE");
     return demoraRepo.save(demora);
 }

 @Override
 public List<Demora> getDemorasPorPedido(Long pedidoId){
     return demoraRepo.findByPedidoId(pedidoId);
 }

 @Override
 public Demora notificarDemora(Long id){
     Demora demora = demoraRepo.findById(id).orElseThrow();
     demora.setEstado("NOTIFICADO");
     return demoraRepo.save(demora);
 }
}