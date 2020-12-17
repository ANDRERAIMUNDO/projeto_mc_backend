package com.andre.mc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andre.mc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository <Pedido, Integer> {

}
