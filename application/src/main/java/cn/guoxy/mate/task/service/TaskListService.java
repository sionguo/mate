package cn.guoxy.mate.task.service;

import cn.guoxy.mate.task.TaskList;
import cn.guoxy.mate.task.dto.TaskListRequest;
import java.util.List;

/**
 * 任务列表服务
 *
 * @author GuoXiaoyong
 */
public interface TaskListService {
  /**
   * 创建任务列表
   *
   * @param request 要求
   * @return {@code TaskList}
   */
  TaskList createTaskList(TaskListRequest request);

  /**
   * 更新任务列表
   *
   * @param id 身份证件
   * @param request 要求
   * @return {@code TaskList}
   */
  TaskList updateTaskList(String id, TaskListRequest request);

  /**
   * 获取任务列表
   *
   * @param id 身份证件
   * @return {@code TaskList}
   */
  TaskList getTaskList(String id);

  /**
   * 删除任务列表
   *
   * @param id 身份证件
   */
  void deleteTaskList(String id);

  /**
   * 任务列表
   *
   * @return {@code List<TaskList>}
   */
  List<TaskList> listTaskList();
}
