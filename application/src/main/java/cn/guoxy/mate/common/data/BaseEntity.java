package cn.guoxy.mate.data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

/**
 * 基本实体
 *
 * @author GuoXiaoyong
 */
@Data
public class BaseEntity implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  @Id private String id;
  @Version private Integer version;
  @CreatedDate private Instant createDate;
  @CreatedBy private String createBy;
  @LastModifiedDate private Instant lastModifiedDate;
  @LastModifiedBy private String lastModifiedBy;
}
