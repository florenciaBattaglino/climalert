package ar.edu.utn.ba.ddsi.climalert_tp.service;


import ar.edu.utn.ba.ddsi.climalert_tp.Config.RestWeatherProperties;
import ar.edu.utn.ba.ddsi.climalert_tp.dto.Response;
import ar.edu.utn.ba.ddsi.climalert_tp.models.Clima;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
public class ServiceClima implements ClimaService_ {

  private final RestTemplate restTemplate;
  private final RestWeatherProperties properties;

  public ServiceClima(RestTemplate restTemplate, RestWeatherProperties properties) {
    this.restTemplate = restTemplate;
    this.properties = properties;
  }

  @Override
  public Clima obtenerClima() {
    URI uri = UriComponentsBuilder.fromUriString(properties.getBaseURL())
        .queryParam("key", properties.getKey())
        .queryParam("q", "CABA") // Ubicación fija que pide el enunciado
        .build()
        .toUri();

    Response cuerpo = restTemplate.getForObject(uri, Response.class);

    if (cuerpo == null || cuerpo.getCurrent() == null) {
      throw new RuntimeException("No se pudieron obtener datos de WeatherAPI");
    }

    Clima nuevoClima = new Clima();
    nuevoClima.setPais(cuerpo.getLocation().getCountry());
    nuevoClima.setRegion(cuerpo.getLocation().getRegion());
    nuevoClima.setNombreCiudad(cuerpo.getLocation().getName());
    nuevoClima.setTemp_celcius(cuerpo.getCurrent().getTemp_c());
    nuevoClima.setTemp_f(cuerpo.getCurrent().getTemp_f());
    nuevoClima.setHumedad(cuerpo.getCurrent().getHumidity());


    return nuevoClima;
  }
}
