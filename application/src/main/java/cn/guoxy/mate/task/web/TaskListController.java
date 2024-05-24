package cn.guoxy.mate.task.web;

import cn.guoxy.mate.task.TaskList;
import cn.guoxy.mate.task.dto.TaskListRequest;
import cn.guoxy.mate.task.dto.TaskListResponse;
import cn.guoxy.mate.task.service.TaskListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务列表控制器
 *
 * @author GuoXiaoyong
 */
@RestController
@RequestMapping("/task-list")
@Tag(name = "tasks")
public class TaskListController {
  private final TaskListService taskListService;

  public TaskListController(TaskListService taskListService) {
    this.taskListService = taskListService;
  }

  @PostMapping
  @Operation(summary = "创建任务清单")
  public ResponseEntity<TaskListResponse> createTaskList(@RequestBody TaskListRequest request) {
    TaskList taskList = taskListService.createTaskList(request);
    return ResponseEntity.ok(toTaskListResponse(taskList));
  }

  @PutMapping("/{taskListId}")
  @Operation(summary = "修改任务清单")
  public ResponseEntity<TaskListResponse> updateTaskList(
      @PathVariable String taskListId, @RequestBody TaskListRequest request) {
    TaskListResponse response = new TaskListResponse();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{taskListId}")
  @Operation(summary = "任务清单详情")
  public ResponseEntity<TaskListResponse> getTaskList(@PathVariable String taskListId) {
    return ResponseEntity.ok(toTaskListResponse(taskListService.getTaskList(taskListId)));
  }

  @DeleteMapping("/{taskListId}")
  @Operation(summary = "删除任务清单")
  public ResponseEntity<Void> deleteTaskList(@PathVariable String taskListId) {
    taskListService.deleteTaskList(taskListId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  @Operation(summary = "任务清单列表")
  public ResponseEntity<List<TaskListResponse>> listTaskList() {
    List<TaskList> taskLists = taskListService.listTaskList();
    return ResponseEntity.ok(taskLists.stream().map(this::toTaskListResponse).toList());
  }

  private TaskListResponse toTaskListResponse(TaskList taskList) {
    TaskListResponse response = new TaskListResponse();
    response.setName(taskList.getName());
    response.setColor(taskList.getColor());
    response.setId(taskList.getId());
    return response;
  }
}
