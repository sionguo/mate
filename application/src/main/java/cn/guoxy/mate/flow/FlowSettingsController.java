package cn.guoxy.mate.flow;

import cn.guoxy.flow.ActionNodeExtension;
import cn.guoxy.flow.NodeExtension.UiComponent;
import cn.guoxy.spi.NamedSPILoader;
import com.nimbusds.jose.util.IOUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 流设置控制器
 *
 * @author GuoXiaoyong
 */
@Controller
@RequestMapping("flow")
public class FlowSettingsController {
  private static final Logger logger = LoggerFactory.getLogger(FlowSettingsController.class);

  @GetMapping("components")
  private ResponseEntity flowNodeComponents(HttpServletRequest request) throws IOException {
    String s =
        UriComponentsBuilder.newInstance()
            .scheme(request.getScheme())
            .host(request.getServerName())
            .port(request.getServerPort())
            .path(request.getContextPath())
            .build()
            .encode()
            .toString();
    logger.info(s);
    NamedSPILoader<ActionNodeExtension> loader = new NamedSPILoader<>(ActionNodeExtension.class);
    ActionNodeExtension http = loader.lookup("http");
    UiComponent uiComponent = http.getUiComponent();
    InputStream stream = http.readResource(uiComponent.jsPath());
    String s1 = IOUtils.readInputStreamToString(stream);
    logger.info(s1);
    return ResponseEntity.ok("extensions");
  }

  @GetMapping("/")
  private ResponseEntity flowStatic(HttpServletRequest request) throws IOException {
    return flowNodeComponents(request);
  }
}
