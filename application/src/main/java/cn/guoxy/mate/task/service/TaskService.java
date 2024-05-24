package cn.guoxy.mate.task.service;

import cn.guoxy.mate.task.Task;
import cn.guoxy.mate.task.dto.CreateTaskRequest;
import cn.guoxy.mate.task.dto.ListTaskRequest;
import java.util.List;

/**
 * 任务服务
 *
 * @author GuoXiaoyong
 */
public interface TaskService {
  /**
   * 创建任务
   *
   * @param request 要求
   * @return {@code Task}
   */
  Task createTask(CreateTaskRequest request);

  /**
   * 删除任务
   *
   * @param taskId 任务id
   */
  void deleteTask(String taskId);

  /**
   * 获取任务
   *
   * @param taskId 任务id
   * @return {@code Task}
   */
  Task getTask(String taskId);

  /**
   * 已完成任务
   *
   * @param taskId 任务id
   * @param done 完成
   * @return {@code Task}
   */
  Task doneTask(String taskId, boolean done);

  /**
   * 获取任务
   *
   * @return {@code List<Task>}
   */
  List<Task> getTasks(ListTaskRequest request);
}
