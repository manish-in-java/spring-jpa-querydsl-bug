package org.example.data;

import org.example.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = "classpath:springContext.xml")
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonRepositoryTest
{
  @Autowired
  private PersonRepository personRepository;

  @Before
  public void setup()
  {
    personRepository.saveAndFlush(new Person("John", "Smith"));
  }

  @Test
  public void testFindAllByName()
  {
    final Iterable<Person> persons = personRepository.findAllByName("John Smith");

    assertNotNull(persons);
    assertTrue(persons.iterator().hasNext());

    persons.forEach(person -> {
      assertNotNull(person);
      assertNotNull(person.getFirstName());
      assertNotNull(person.getLastName());
    });
  }
}
