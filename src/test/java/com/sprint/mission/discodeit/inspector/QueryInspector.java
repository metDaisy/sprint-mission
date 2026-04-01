package com.sprint.mission.discodeit.inspector;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

@Component
public class QueryInspector implements StatementInspector {

  private final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

  @Override
  public String inspect(String sql) {
    count.set(count.get() + 1);
    return sql;
  }

  public int getCount() {
    return count.get();
  }

  public void clear() {
    count.remove();
  }
}
