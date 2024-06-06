package cn.guoxy.spi;

public interface ClassLoaderUtils {
  static boolean isParentClassLoader(final ClassLoader parent, final ClassLoader child) {
    try {
      ClassLoader cl = child;
      while (cl != null) {
        if (cl == parent) {
          return true;
        }
        cl = cl.getParent();
      }
      return false;
    } catch (
        @SuppressWarnings("unused")
        SecurityException se) {
      return false;
    }
  }
}
