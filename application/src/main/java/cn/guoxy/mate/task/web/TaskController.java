package cn.guoxy.mate.task.web;

import cn.guoxy.mate.task.Task;
import cn.guoxy.mate.task.TaskStatus;
import cn.guoxy.mate.task.dto.CreateTaskRequest;
import cn.guoxy.mate.task.dto.ListTaskRequest;
import cn.guoxy.mate.task.dto.TaskResponse;
import cn.guoxy.mate.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务控制器
 *
 * @author GuoXiaoyong
 */
@RestController
@Tag(name = "tasks", description = "任务API")
@RequestMapping("tasks")
public class TaskController {
  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping()
  @Operation(summary = "创建任务")
  public ResponseEntity<TaskResponse> createTask(@RequestBody CreateTaskRequest request) {
    Task task = taskService.createTask(request);
    return ResponseEntity.ok(toTaskResponse(task));
  }

  @GetMapping("/{taskId}")
  @Operation(summary = "任务详情")
  public ResponseEntity<TaskResponse> getTask(@PathVariable String taskId) {
    Task task = taskService.getTask(taskId);
    return ResponseEntity.ok(toTaskResponse(task));
  }

  @GetMapping()
  @Operation(summary = "任务列表")
  public ResponseEntity<List<TaskResponse>> listTasks(@ParameterObject ListTaskRequest request) {
    List<Task> tasks = taskService.listTasks(request);
    return ResponseEntity.ok(tasks.stream().map(this::toTaskResponse).toList());
  }

  @PutMapping("/{taskId}/state")
  @Operation(summary = "修改任务状态")
  public ResponseEntity<TaskResponse> updateTaskState(
      @PathVariable String taskId, @RequestParam String status) {
    TaskStatus taskStatus = EnumUtils.getEnum(TaskStatus.class, status);
    Task task = taskService.updateTaskState(taskId, taskStatus);
    return ResponseEntity.ok(toTaskResponse(task));
  }

  @PutMapping("/{taskId}/star")
  @Operation(summary = "加/减星")
  public ResponseEntity<TaskResponse> updateTaskStar(
      @PathVariable String taskId, @RequestParam Boolean star){
    Task task = taskService.updateTaskStar(taskId, star);
    return ResponseEntity.ok(toTaskResponse(task));
  }

  private TaskResponse toTaskResponse(Task task) {
    TaskResponse taskResponse = new TaskResponse();
    taskResponse.setId(task.getId());
    taskResponse.setCreateDate(task.getCreateDate());
    taskResponse.setCreateBy(task.getCreateBy());
    taskResponse.setLastModifiedDate(task.getLastModifiedDate());
    taskResponse.setLastModifiedBy(task.getLastModifiedBy());
    taskResponse.setTitle(task.getTitle());
    taskResponse.setDescription(task.getDescription());
    taskResponse.setPriority(task.getPriority());
    taskResponse.setStatus(task.getStatus().name());
    taskResponse.setPlannedStart(task.getPlannedStart());
    taskResponse.setPlannedFinish(task.getPlannedFinish());
    taskResponse.setActualStart(task.getActualStart());
    taskResponse.setActualFinish(task.getActualFinish());
    taskResponse.setStar(task.getStar());
    return taskResponse;
  }
}
