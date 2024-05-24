package cn.guoxy.mate.task.service;

import cn.guoxy.mate.common.MethodContext;
import cn.guoxy.mate.task.Task;
import cn.guoxy.mate.task.TaskStatus;
import cn.guoxy.mate.task.dto.CreateTaskRequest;
import cn.guoxy.mate.task.dto.ListTaskRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
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
    task.setPlannedStart(
        Objects.isNull(request.getPlannedStart()) ? LocalDate.now() : request.getPlannedStart());
    task.setPlannedFinish(request.getPlannedFinish());
    task.setTaskListId(request.getTaskListId());
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
  public List<Task> listTasks(ListTaskRequest request) {
    String currentUser = MethodContext.getCurrentUser();
    Criteria criteria = Criteria.where("createBy").is(currentUser);

    String query = request.getQuery();
    if ("STAR".equals(query)) {
      criteria = criteria.and("star").isTrue();
    } else if ("TODAY".equals(query)) {
      criteria =
          criteria
              .and("plannedStart")
              .lessThanOrEquals(Instant.now())
              .and("plannedFinish")
              .greaterThanOrEquals(Instant.now());
    } else if ("ALL".equals(query)) {
      criteria = Criteria.from(criteria);
    } else {
      criteria = criteria.and("taskListId").is(query);
    }
    List<Task> tasks = new ArrayList<>();
    Iterable<Task> all = jdbcAggregateOperations.findAll(Query.query(criteria), Task.class);
    all.forEach(tasks::add);
    tasks.sort(Comparator.comparing(Task::getStatus).thenComparing(Task::getPlannedStart));
    return tasks;
  }

  @Override
  public Task updateTaskState(String taskId, TaskStatus taskStatus) {
    Task task = getTask(taskId);
    if (task == null) {
      return null;
    }
    switch (taskStatus) {
      case TODO:
        task.setStatus(TaskStatus.TODO);
        task.setActualStart(null);
        task.setActualFinish(null);
        break;
      case PROCESSING:
        task.setStatus(TaskStatus.PROCESSING);
        task.setActualStart(LocalDate.now());
        task.setActualFinish(null);
        break;
      case COMPLETED:
        task.setStatus(TaskStatus.COMPLETED);
        task.setActualFinish(LocalDate.now());
        break;
    }
    return jdbcAggregateOperations.update(task);
  }

  @Override
  public Task updateTaskStar(String taskId, Boolean star) {
    Task task = getTask(taskId);
    if (task == null) {
      return null;
    }
    task.setStar(star);
    return jdbcAggregateOperations.update(task);
  }
}
