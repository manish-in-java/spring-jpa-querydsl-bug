package org.example.data;

import org.example.domain.Person;
import org.example.domain.QPerson;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PersonRepository extends ModelRepository<Person>, QueryDslPredicateExecutor<Person>
{
  default Iterable<Person> findAllByName(final String name)
  {
    return findAll(getPersonNameRestriction(QPerson.person, name));
  }
}
