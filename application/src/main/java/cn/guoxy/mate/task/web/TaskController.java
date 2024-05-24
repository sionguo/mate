package cn.guoxy.mate.task.web;

import cn.guoxy.mate.task.Task;
import cn.guoxy.mate.task.dto.CreateTaskRequest;
import cn.guoxy.mate.task.dto.TaskResponse;
import cn.guoxy.mate.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

  private TaskResponse toTaskResponse(Task task) {
    TaskResponse taskResponse = new TaskResponse();
    taskResponse.setId(task.getId());
    taskResponse.setCreateDate(task.getCreateDate());
    taskResponse.setCreateBy(task.getCreateBy());
    taskResponse.setLastModifiedDate(task.getLastModifiedDate());
    taskResponse.setLastModifiedBy(task.getLastModifiedBy());
    taskResponse.setTitle(task.getTitle());
    taskResponse.setDescription(task.getDescription());
    taskResponse.setStartDate(task.getStartDate());
    taskResponse.setDueDate(task.getDueDate());
    taskResponse.setPriority(task.getPriority());
    taskResponse.setDone(task.getDone());
    taskResponse.setDoneDate(task.getDoneDate());
    return taskResponse;
  }
}
