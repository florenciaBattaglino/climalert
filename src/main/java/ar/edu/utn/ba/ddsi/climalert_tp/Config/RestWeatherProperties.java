package ar.edu.utn.ba.ddsi.climalert_tp.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weatherapi")
@Data
public class RestWeatherProperties {
  private String baseURL;
  private String key;
}
