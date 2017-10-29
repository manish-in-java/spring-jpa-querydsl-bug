package org.example.data;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.example.domain.Model;
import org.example.domain.QPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ModelRepository<T extends Model> extends JpaRepository<T, Long>
{
  default BooleanExpression getPersonNameRestriction(final QPerson person, final String name)
  {
    return person.firstName.concat(" ")
                           .concat(person.middleName.concat(" ").coalesce(""))
                           .concat(person.lastName)
                           .containsIgnoreCase(name);
  }
}
