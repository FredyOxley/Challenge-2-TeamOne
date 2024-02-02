package com.compassuol.sp.challenge.ecommerce.web.client;

import com.compassuol.sp.challenge.ecommerce.web.dto.ViaCepClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    @GetMapping("/{cep}/json")
    ViaCepClientDto findByCep(@PathVariable String cep);

}

