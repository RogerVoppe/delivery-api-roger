package com.deliverytech.delivery.config;

// Importações necessárias para a correção
import com.deliverytech.delivery.dto.ProdutoDTO;
import com.deliverytech.delivery.entity.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // --- CORREÇÃO (Roteiro 4) ---
        // O ModelMapper está confundindo 'restauranteId' (do DTO) com 'id' (da Entidade)
        // Vamos criar um mapeamento customizado para o ProdutoDTO -> Produto
        
        modelMapper.typeMap(ProdutoDTO.class, Produto.class)
            .addMappings(mapper -> {
                // Mapeia todos os campos, mas...
                mapper.skip(Produto::setId); // ...explicitamente PULA o 'setId'
            });
        // --- FIM DA CORREÇÃO ---

        return modelMapper;
    }
}