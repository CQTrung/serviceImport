package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString(
        callSuper = true,
        exclude = {})
public class DtbRttDeliveryOrderFile extends EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private Integer status;
    private String updateByName;
    private String pathFile;
    private String pathFileError;
    private Integer progress;
    private String errorKey;
    private String siteDc;
    private Integer totalRecord;
    private Integer successRecord;
    private Integer updateRecord;
    private Integer failRecord;
}

