package ru.clevertec.course.spring.gateway;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import ru.clevertec.course.spring.model.dto.GatewayResponse;

@FeignClient(name = "directoryGateway",
        url = "${clevertec.gateway.url}")
public interface BlackListGateway {

    @GetMapping(path = "${clevertec.gateway.getBlackList}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    GatewayResponse getShortInfoOffers();


}