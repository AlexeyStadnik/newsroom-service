package com.newsroom.newsroomservice.entity;

import com.service.model.rest.RestLanguage;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "summary")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SummaryEntity extends AuditableEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String content;

    @Type(ListArrayType.class)
    @Column(
            name = "tags",
            columnDefinition = "text[]"
    )
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    private RestLanguage language;

    private String originalContent;

}
