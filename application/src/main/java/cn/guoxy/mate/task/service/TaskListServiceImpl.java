package cn.guoxy.mate.task.service;

import cn.guoxy.mate.common.BusinessException;
import cn.guoxy.mate.common.MethodContext;
import cn.guoxy.mate.task.TaskList;
import cn.guoxy.mate.task.dto.TaskListRequest;
import io.micrometer.observation.annotation.Observed;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 任务列表服务实现
 *
 * @author GuoXiaoyong
 */
@Service
public class TaskListServiceImpl implements TaskListService {

  private static final Logger logger = LoggerFactory.getLogger(TaskListServiceImpl.class);
  private final JdbcAggregateOperations jdbcAggregateOperations;

  public TaskListServiceImpl(JdbcAggregateOperations jdbcAggregateOperations) {
    this.jdbcAggregateOperations = jdbcAggregateOperations;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public TaskList createTaskList(TaskListRequest request) {
    boolean exists =
        jdbcAggregateOperations.exists(
            Query.query(
                Criteria.where("name")
                    .is(request.getName())
                    .and("createBy")
                    .is(MethodContext.getCurrentUser())),
            TaskList.class);
    if (exists) {
      throw new BusinessException("任务列表[{}]已存在", request.getName());
    }
    TaskList taskList = new TaskList();
    taskList.setName(request.getName());
    taskList.setColor(request.getColor());
    return jdbcAggregateOperations.insert(taskList);
  }

  @Override
  public TaskList updateTaskList(String id, TaskListRequest request) {
    return null;
  }

  @Override
  public TaskList getTaskList(String id) {
    return null;
  }

  @Override
  public void deleteTaskList(String id) {}

  @Override
  @Observed
  public List<TaskList> listTaskList() {
    logger.info("aa");
    Iterable<TaskList> createBy =
        jdbcAggregateOperations.findAll(
            Query.query(Criteria.where("createBy").is(MethodContext.getCurrentUser())),
            TaskList.class);
    List<TaskList> taskLists = new ArrayList<>();
    createBy.forEach(taskLists::add);
    return taskLists;
  }
}
