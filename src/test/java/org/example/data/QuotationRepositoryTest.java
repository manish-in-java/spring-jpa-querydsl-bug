package org.example.data;

import org.example.domain.*;
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
public class QuotationRepositoryTest
{
  @Autowired
  private CompanyRepository    companyRepository;
  @Autowired
  private DepartmentRepository departmentRepository;
  @Autowired
  private EmployeeRepository   employeeRepository;
  @Autowired
  private JobRepository        jobRepository;
  @Autowired
  private PersonRepository     personRepository;
  @Autowired
  private PositionRepository   positionRepository;
  @Autowired
  private QuotationRepository  quotationRepository;

  @Before
  public void setup()
  {
    final Person person = personRepository.saveAndFlush(new Person("John Smith"));

    final Company customer = companyRepository.saveAndFlush(new Company("Customer"));

    final Company supplier = companyRepository.saveAndFlush(new Company("Supplier"));

    final Employee employee = employeeRepository.saveAndFlush(new Employee(supplier, person, "Sales Executive"));

    final Department department = departmentRepository.saveAndFlush(new Department(supplier, "Sales"));

    final Position position = positionRepository.saveAndFlush(new Position(department, "Sales Executive - North"));

    jobRepository.saveAndFlush(new Job(position, employee));

    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
    quotationRepository.saveAndFlush(new Quotation(customer, supplier, position));
  }

  @Test
  public void testFindAllByRepresentativeName()
  {
    final Iterable<Quotation> quotations = quotationRepository.findAllByRepresentativeName("John Smith");

    assertNotNull(quotations);
    assertTrue(quotations.iterator().hasNext());

    quotations.forEach(quotation -> {
      assertNotNull(quotation);
      assertNotNull(quotation.getCustomer());
      assertNotNull(quotation.getCustomer().getName());
      assertNotNull(quotation.getRepresentative());
      assertNotNull(quotation.getRepresentative().getDepartment());
      assertNotNull(quotation.getRepresentative().getTitle());
      assertNotNull(quotation.getSupplier());
      assertNotNull(quotation.getSupplier().getName());
    });
  }
}
