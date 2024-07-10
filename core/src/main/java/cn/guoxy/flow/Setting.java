package cn.guoxy.flow;

import java.util.Properties;
import java.util.function.Function;

/**
 * 设置
 *
 * @author Guo XiaoYong
 */
public class Setting<T> {
  private final String key;
  private final T defaultValue;
  private final Function<String, T> parser;

  public Setting(String key, T defaultValue, Function<String, T> parser) {
    assert key != null;
    assert defaultValue != null;
    assert parser != null;
    this.key = key;
    this.defaultValue = defaultValue;
    this.parser = parser;
  }

  public T get(Properties properties) {
    Object found = properties.get(key);
    String value = found != null ? String.valueOf(found) : String.valueOf(defaultValue);
    return parser.apply(value);
  }
}
