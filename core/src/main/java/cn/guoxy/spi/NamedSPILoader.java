package cn.guoxy.spi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * 服务接口加载器
 *
 * @author GuoXiaoyong
 */
public final class NamedSPILoader<S extends NamedSPI> implements Iterable<S> {
  private final Class<S> spiClass;
  private volatile Map<String, S> services;

  public NamedSPILoader(Class<S> spiClass) {
    this(spiClass, null);
  }

  public NamedSPILoader(Class<S> spiClass, ClassLoader classloader) {
    this.services = Collections.emptyMap();
    this.spiClass = spiClass;
    ClassLoader clazzClassloader = spiClass.getClassLoader();
    if (classloader == null) {
      classloader = clazzClassloader;
    }
    if (clazzClassloader != null
        && !ClassLoaderUtils.isParentClassLoader(clazzClassloader, classloader)) {
      this.reload(clazzClassloader);
    }

    this.reload(classloader);
  }

  private void reload(ClassLoader classloader) {
    Objects.requireNonNull(classloader, "classloader");
    final LinkedHashMap<String, S> services = new LinkedHashMap<>(this.services);
    for (final S service : ServiceLoader.load(spiClass, classloader)) {
      final String name = service.getName();
      // only add the first one for each name, later services will be ignored
      // this allows to place services before others in classpath to make
      // them used instead of others
      if (!services.containsKey(name)) {
        services.put(name, service);
      }
    }
    this.services = Collections.unmodifiableMap(services);
  }

  public S lookup(String name) {
    final S service = services.get(name);
    if (service != null) {
      return service;
    }
    throw new IllegalArgumentException(
        "An SPI class of type "
            + this.spiClass.getName()
            + " with name '"
            + name
            + "' does not exist."
            + "  You need to add the corresponding JAR file supporting this SPI to your classpath."
            + "  The current classpath supports the following names: "
            + availableServices());
  }

  public Set<String> availableServices() {
    return services.keySet();
  }

  /**
   * 所有服务
   *
   * @return {@code Set<S> }
   */
  public Set<S> allServices() {
    return new HashSet<>(services.values());
  }

  @Override
  public Iterator<S> iterator() {
    return services.values().iterator();
  }
}
