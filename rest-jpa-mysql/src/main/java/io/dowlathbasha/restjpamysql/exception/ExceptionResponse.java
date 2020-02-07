package io.dowlathbasha.restjpamysql.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
   private Date timeStamp;
   private String message;
   private String description;
}
