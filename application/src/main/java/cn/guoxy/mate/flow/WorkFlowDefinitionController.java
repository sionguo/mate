package cn.guoxy.mate.flow;

import cn.guoxy.flow.TaskProcessor;
import cn.guoxy.flow.TaskProcessorFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流量定义控制器
 *
 * @author Guo XiaoYong
 */
@RestController
@Tag(name = "workflow", description = "流程相关API")
public class WorkFlowDefinitionController {

  @GetMapping("/flow/node-types")
  @Operation(summary = "获取流程节点元数据")
  public ResponseEntity<List<TaskNodeInfo>> nodeTypes(HttpServletRequest request) {
    Collection<TaskProcessor> taskProcessors = TaskProcessorFactory.getTaskProcessors();
    List<TaskNodeInfo> list = taskProcessors.stream().map(TaskNodeInfo::newNodeInfo).toList();
    return ResponseEntity.ok(list);
  }

  @GetMapping(value = "/js/{nodeType}")
  @Operation(summary = "获取流程节点js文件")
  public void js(
      @Parameter(description = "节点类型") @PathVariable String nodeType,
      HttpServletResponse response) {
    TaskProcessor taskProcessor = TaskProcessorFactory.getTaskProcessor(nodeType);
    response.setContentType("text/javascript");
    response.setCharacterEncoding("utf-8");
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.copy(taskProcessor.getJsResource(), outputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  ;

  @GetMapping(value = "/css/{nodeType}")
  @Operation(summary = "获取流程节点css文件")
  public void css(
      @Parameter(description = "节点类型") @PathVariable String nodeType,
      HttpServletResponse response) {
    TaskProcessor taskProcessor = TaskProcessorFactory.getTaskProcessor(nodeType);
    response.setContentType("text/css");
    response.setCharacterEncoding("utf-8");
    try (ServletOutputStream outputStream = response.getOutputStream()) {
      IOUtils.copy(taskProcessor.getJsResource(), outputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public record TaskNodeInfo(String type, boolean hasCss) {
    public static TaskNodeInfo newNodeInfo(TaskProcessor taskProcessor) {
      return new TaskNodeInfo(taskProcessor.getName(), taskProcessor.hasCssResource());
    }
  }
}
