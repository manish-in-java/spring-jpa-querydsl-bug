package org.example.domain;

import com.querydsl.core.annotations.QueryEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@QueryEntity
@Table(name = "person")
public class Person extends Model
{
  @Column(length = 100, name = "name", nullable = false)
  private String name;

  Person()
  {
    super();
  }

  public Person(final String name)
  {
    this();

    this.name = name;
  }

  public String getName()
  {
    return name;
  }
}
