package com.compassuol.sp.challenge.ecommerce.domain.endereco.entity;

import com.compassuol.sp.challenge.ecommerce.domain.pedido.entity.Pedido;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface EnderecoCep {

    @GetMapping("/{cep}/json")
    Pedido findByCep(@PathVariable String cep);

}
