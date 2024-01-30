package com.compassuol.sp.challenge.ecommerce.domain.endereco.entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface EnderecoCep {

    @GetMapping("/{cep}/json")
    Endereco findByCep(String cep);

}
