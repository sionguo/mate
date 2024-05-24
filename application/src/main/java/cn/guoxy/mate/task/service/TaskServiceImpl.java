package cn.guoxy.mate.task.service;

import cn.guoxy.mate.common.BusinessException;
import cn.guoxy.mate.task.Task;
import cn.guoxy.mate.task.dto.CreateTaskRequest;
import cn.guoxy.mate.task.dto.ListTaskRequest;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务服务实现
 *
 * @author GuoXiaoyong
 */
@Service
public class TaskServiceImpl implements TaskService {
  private final JdbcAggregateOperations jdbcAggregateOperations;

  public TaskServiceImpl(JdbcAggregateOperations jdbcAggregateOperations) {
    this.jdbcAggregateOperations = jdbcAggregateOperations;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Task createTask(CreateTaskRequest request) {
    Task task = new Task();
    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setStartDate(
        Objects.isNull(request.getStartDate()) ? Instant.now() : request.getStartDate());
    task.setDueDate(request.getDueDate());
    return jdbcAggregateOperations.insert(task);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteTask(String taskId) {
    jdbcAggregateOperations.delete(taskId);
  }

  @Override
  public Task getTask(String taskId) {
    return jdbcAggregateOperations.findById(taskId, Task.class);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Task doneTask(String taskId, boolean done) {
    Task task = jdbcAggregateOperations.findById(taskId, Task.class);
    if (task == null) {
      throw new BusinessException("任务{}不存在", taskId);
    }
    if (done) {
      task.setDone(true);
      task.setDoneDate(Instant.now());
    } else {
      task.setDone(false);
      task.setDoneDate(null);
    }
    return jdbcAggregateOperations.update(task);
  }

  @Override
  public List<Task> getTasks(ListTaskRequest request) {
    return List.of();
  }
}
